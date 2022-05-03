package com.ruskaof.server.connection;

import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.util.Pair;
import com.ruskaof.common.util.State;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class ClientDataReceiver {
    private static final int HEADER_LENGTH = 4;
    private static final int TIMEOUT_MILLS = 5;
    private final Logger logger;

    public ClientDataReceiver(Logger logger) {
        this.logger = logger;
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

    public Pair<CommandFromClientDto, SocketAddress> receiveData(DatagramChannel datagramChannel, State<Boolean> isWorking) throws IOException, InterruptedException, TimeoutException, ClassNotFoundException {
        ByteBuffer amountOfBytesInMainDataBuffer = ByteBuffer.wrap(new byte[HEADER_LENGTH]);
        receiveActiveWaiting(datagramChannel, amountOfBytesInMainDataBuffer, isWorking);
        ByteBuffer dataByteBuffer = ByteBuffer.wrap(new byte[bytesToInt(amountOfBytesInMainDataBuffer.array())]);
        SocketAddress clientSocketAddress = receiveWithTimeout(datagramChannel, dataByteBuffer, TIMEOUT_MILLS);
        CommandFromClientDto receivedCommand = ((CommandFromClientDto) deserialize(dataByteBuffer.array()));
        return new Pair<>(receivedCommand, clientSocketAddress);
    }

    private SocketAddress receiveWithTimeout(
            DatagramChannel datagramChannel,
            ByteBuffer byteBuffer,
            int timeoutMills
    ) throws IOException, InterruptedException, TimeoutException {
        int amountToWait = timeoutMills;
        SocketAddress receivedSocketAddress = null;
        while (amountToWait > 0) {
            receivedSocketAddress = datagramChannel.receive(byteBuffer);
            if (Objects.nonNull(receivedSocketAddress)) {
                return receivedSocketAddress;
            } else {
                Thread.sleep(1);
                amountToWait--;
            }
        }
        throw new TimeoutException();
    }

    private SocketAddress receiveActiveWaiting(
            DatagramChannel datagramChannel,
            ByteBuffer byteBuffer,
            State<Boolean> isWorking
    ) throws IOException, InterruptedException {
        while (isWorking.getValue()) {
            SocketAddress receivedSocketAddress = datagramChannel.receive(byteBuffer);
            if (Objects.nonNull(receivedSocketAddress)) {
                return receivedSocketAddress;
            }
        }
        return null;
    }
}
