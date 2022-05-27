package com.ruskaof.server.connection.udp;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.Pair;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeoutException;

public class ClientDataSender extends RecursiveTask<Void> {
    private static final int TIMEOUT_TO_SEND = 10;
    private static final int HEADER_LENGTH = 4;
    private final CommandResultDto commandResultDto;
    private final transient DatagramChannel datagramChannel;
    private final SocketAddress socketAddress;
    private final transient Logger logger;

    public ClientDataSender(
            CommandResultDto commandResultDto,
            DatagramChannel datagramChannel,
            SocketAddress socketAddress,
            Logger logger
    ) {
        this.commandResultDto = commandResultDto;
        this.datagramChannel = datagramChannel;
        this.socketAddress = socketAddress;
        this.logger = logger;
    }

    @Override
    protected Void compute() {
        logger.info("Started to send message to the client");
        try {
            send(
                    socketAddress
            );
        } catch (TimeoutException | IOException e) {
            logger.error("Could not send answer to client");
        }

        return null;
    }

    private void send(
            SocketAddress clientSocketAddress
    ) throws TimeoutException, IOException {
        Pair<byte[], byte[]> pair = serializeWithHeader(commandResultDto);

        byte[] sendDataBytes = pair.getFirst();
        byte[] sendDataAmountBytes = pair.getSecond();


        try {
            ByteBuffer sendDataAmountWrapper = ByteBuffer.wrap(sendDataAmountBytes);
            int limit = TIMEOUT_TO_SEND;
            while (datagramChannel.send(sendDataAmountWrapper, clientSocketAddress) <= 0) {
                limit -= 1;
                logger.info("could not sent a package, re-trying");
                if (limit == 0) {
                    throw new TimeoutException();
                }
            } // сначала отправляется файл-количество байтов в основном массиве байтов
            ByteBuffer sendBuffer = ByteBuffer.wrap(sendDataBytes);
            while (datagramChannel.send(sendBuffer, clientSocketAddress) <= 0) {
                limit -= 1;
                logger.info("could not send a package, re-trying");
                if (limit == 0) {
                    throw new TimeoutException();
                }
            }
            logger.info("sent the command result to the client");
        } catch (IOException e) {
            logger.error("could not send the data to client because the message is too big");
        }


    }

    /**
     * @return first - data itself, second - amount of bytes in data
     */
    public Pair<byte[], byte[]> serializeWithHeader(Object obj) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(obj);
        byte[] sizeBytes = ByteBuffer.allocate(HEADER_LENGTH).putInt(byteArrayOutputStream.size()).array();

        return new Pair<>(byteArrayOutputStream.toByteArray(), sizeBytes); // в первых 4 байтах будет храниться число-количество данных отправления
    }
}
