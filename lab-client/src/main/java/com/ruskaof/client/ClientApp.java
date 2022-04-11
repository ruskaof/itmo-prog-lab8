package com.ruskaof.client;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.dto.ToServerDto;
import com.ruskaof.common.util.Pair;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public final class ClientApp {
    private final int clientPort;
    private final int serverPort;
    private final String ip;
    private final int waitingTime = 500;
    private final int countOfBytesForSize = 4;

    public ClientApp(int clientPort, int serverPort, String ip) {
        this.clientPort = clientPort;
        this.serverPort = serverPort;
        this.ip = ip;
    }

    public CommandResultDto sendCommand(ToServerDto toServerDto) throws ClassNotFoundException {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            datagramChannel.configureBlocking(false);
            // Send
            datagramChannel.bind(new InetSocketAddress(ip, clientPort));
            SocketAddress socketAddress = new InetSocketAddress(ip, serverPort);

            Pair<byte[], byte[]> pair = serialize(toServerDto);

            byte[] sendDataBytes = pair.getFirst();
            byte[] sendDataAmountBytes = pair.getSecond();

            ByteBuffer sendDataAmountWrapper = ByteBuffer.wrap(sendDataAmountBytes);
            datagramChannel.send(sendDataAmountWrapper, socketAddress);

            ByteBuffer sendBuffer = ByteBuffer.wrap(sendDataBytes);
            datagramChannel.send(sendBuffer, socketAddress);

            return receive(datagramChannel);
        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResultDto("Something went wrong executing the command");
        }

    }

    private CommandResultDto receive(DatagramChannel datagramChannel) throws IOException {
        // Receive
        byte[] amountOfBytesHeader = new byte[countOfBytesForSize];
        ByteBuffer amountOfBytesHeaderWrapper = ByteBuffer.wrap(amountOfBytesHeader);
        SocketAddress checkingAddress = null;
        int timeout = waitingTime;
        while (checkingAddress == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace(); // never thrown
            }
            timeout--;
            checkingAddress = datagramChannel.receive(amountOfBytesHeaderWrapper);
            if (timeout == 0) {
                return new CommandResultDto("Could not receive any answer from server");
            }
        }

        byte[] dataBytes = new byte[bytesToInt(amountOfBytesHeader)];

        ByteBuffer dataBytesWrapper = ByteBuffer.wrap(dataBytes);
        datagramChannel.receive(dataBytesWrapper);

        try {
            return (CommandResultDto) deserialize(dataBytes);
        } catch (ClassNotFoundException e) {
            return new CommandResultDto("Received incorrect answer from server");
        }
    }

    /**
     * @return first - data itself, second - amount of bytes in data
     */
    private Pair<byte[], byte[]> serialize(Object obj) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(obj);
        byte[] sizeBytes = ByteBuffer.allocate(countOfBytesForSize).putInt(byteArrayOutputStream.size()).array();

        return new Pair<>(byteArrayOutputStream.toByteArray(), sizeBytes); // в первых 4 байтах будет храниться число-количество данных отправления
    }

    public static int bytesToInt(byte[] bytes) {
        final int vosem = 8;
        final int ff = 0xFF;

        int value = 0;
        for (byte b : bytes) {
            value = (value << vosem) + (b & ff);
        }
        return value;
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}


