package com.ruskaof.client;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.dto.ToServerDto;
import com.ruskaof.common.util.Pair;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.UnresolvedAddressException;

public final class ClientApp {
    private final int clientPort;
    private final int serverPort;
    private final String clientIp;
    private final String serverIp;
    private final int waitingTime = 500;
    private final int countOfBytesForSize = 4;

    public ClientApp(int clientPort, int serverPort, String clientIp, String serverIp) {
        this.clientPort = clientPort;
        this.serverPort = serverPort;
        this.clientIp = clientIp;
        this.serverIp = serverIp;
    }

    public CommandResultDto sendCommand(ToServerDto toServerDto) {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            datagramChannel.configureBlocking(false); // нужно, чтобы в случае, если от сервера не придет никакого ответа не блокироваться навсегда
            send(datagramChannel, toServerDto);
            return receive(datagramChannel);
        } catch (BindException e) {
            return new CommandResultDto("Could not send data on the Inet address, bind exception. Please re-start client with another arguments");
        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResultDto("Something went wrong executing the command");
        }

    }

    private void send(DatagramChannel datagramChannel, ToServerDto toServerDto) throws IOException {

        datagramChannel.bind(new InetSocketAddress(clientIp, clientPort));

        SocketAddress serverSocketAddress = new InetSocketAddress(serverIp, serverPort);

        Pair<byte[], byte[]> pair = serialize(toServerDto);

        byte[] sendDataBytes = pair.getFirst();
        byte[] sendDataAmountBytes = pair.getSecond();

        try {
            ByteBuffer sendDataAmountWrapper = ByteBuffer.wrap(sendDataAmountBytes);
            datagramChannel.send(sendDataAmountWrapper, serverSocketAddress); // сначала отправляем число-количество байтов в основном массиве байтов

            ByteBuffer sendBuffer = ByteBuffer.wrap(sendDataBytes);
            datagramChannel.send(sendBuffer, serverSocketAddress);
        } catch (IOException | UnresolvedAddressException e) {
            System.out.println("Could not resolve the Inet address you wrote. Please check it and maybe restart the client");
            System.out.println("Could not send data because it was too big");
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


