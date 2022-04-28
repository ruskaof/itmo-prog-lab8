package com.ruskaof.server.connection;

import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;
import com.ruskaof.common.util.Pair;
import com.ruskaof.common.util.State;
import com.ruskaof.server.data.remote.repository.posturesql.Database;
import com.ruskaof.server.util.CommandHandler;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.TimeoutException;

public class MainApp {
    private final Logger logger;
    private final int port;
    private final String ip;
    private final CommandHandler commandHandler;
    private final ClientDataReceiver clientDataReceiver;
    private final Database database;

    public MainApp(
            Logger logger,
            int port,
            String ip,
            CommandHandler commandHandler,
            ClientDataReceiver clientDataReceiver,
            Database database) {
        this.logger = logger;
        this.ip = ip;
        this.port = port;
        this.commandHandler = commandHandler;
        this.clientDataReceiver = clientDataReceiver;
        this.database = database;
    }

    public void start(
            HistoryManager historyManager,
            CollectionManager collectionManager,
            State<Boolean> isWorking
    ) throws IOException {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            datagramChannel.bind(new InetSocketAddress(ip, port));
            datagramChannel.configureBlocking(false);

            while (isWorking.getValue()) {
                Pair<CommandFromClientDto, SocketAddress> receivedCommandAndAddress =
                        clientDataReceiver.receiveData(datagramChannel, isWorking);

                CommandResultDto commandResultDto = commandHandler.handle(
                        receivedCommandAndAddress.getFirst(),
                        historyManager,
                        collectionManager,
                        database
                );

                send(commandResultDto, datagramChannel, receivedCommandAndAddress.getSecond(), 10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private void send(
            CommandResultDto commandResultDto,
            DatagramChannel datagramChannel,
            SocketAddress clientSocketAddress,
            int timeoutToSend
    ) throws TimeoutException, IOException {
        Pair<byte[], byte[]> pair = serializeWithHeader(commandResultDto);

        byte[] sendDataBytes = pair.getFirst();
        byte[] sendDataAmountBytes = pair.getSecond();


        try {
            ByteBuffer sendDataAmountWrapper = ByteBuffer.wrap(sendDataAmountBytes);
            int limit = timeoutToSend;
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
        byte[] sizeBytes = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();

        return new Pair<>(byteArrayOutputStream.toByteArray(), sizeBytes); // в первых 4 байтах будет храниться число-количество данных отправления
    }
}
