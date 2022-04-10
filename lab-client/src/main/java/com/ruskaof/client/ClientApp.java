package com.ruskaof.client;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.dto.ToServerDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public final class ClientApp {
    private static final int BF_SIZE = 2048;
    private final int clientPort;
    private final int serverPort;
    private final String ip;

    public ClientApp(int clientPort, int serverPort, String ip) {
        this.clientPort = clientPort;
        this.serverPort = serverPort;
        this.ip = ip;
    }

    public CommandResultDto sendCommand(ToServerDto toServerDto) throws ClassNotFoundException {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            // Send
            datagramChannel.bind(new InetSocketAddress(ip, clientPort));
            SocketAddress socketAddress = new InetSocketAddress(ip, serverPort);

            Pair<byte[], byte[]> pair = serialize(toServerDto);

            byte[] sendDataBytes = pair.first;
            byte[] sendDataAmountBytes = pair.second;

            ByteBuffer sendDataAmountWrapper = ByteBuffer.wrap(sendDataAmountBytes);
            datagramChannel.send(sendDataAmountWrapper, socketAddress);

            ByteBuffer sendBuffer = ByteBuffer.wrap(sendDataBytes);
            datagramChannel.send(sendBuffer, socketAddress);

            // Receive
            byte[] amountOfBytesHeader = new byte[4];
            ByteBuffer amountOfBytesHeaderWrapper = ByteBuffer.wrap(amountOfBytesHeader);
            datagramChannel.receive(amountOfBytesHeaderWrapper);

            byte[] dataBytes = new byte[bytesToInt(amountOfBytesHeader)];

            ByteBuffer dataBytesWrapper = ByteBuffer.wrap(dataBytes);
            datagramChannel.receive(dataBytesWrapper);

            return (CommandResultDto) deserialize(dataBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResultDto("Something went wrong executing the command");
        }

    }

    /**
     *
     * @param obj
     * @return first - data itself, second - amount of bytes in data
     * @throws IOException
     */
    public static Pair<byte[], byte[]> serialize(Object obj) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(obj);
        byte[] sizeBytes = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();

        return new Pair<>(byteArrayOutputStream.toByteArray(), sizeBytes); // в первых 4 байтах будет храниться число-количество данных отправления
    }

    public static int bytesToInt(byte[] bytes) {
        int value = 0;
        for (byte b : bytes) {
            value = (value << 8) + (b & 0xFF);
        }
        return value;
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}

class Pair<T, U> {
    public T first;
    public U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }
}