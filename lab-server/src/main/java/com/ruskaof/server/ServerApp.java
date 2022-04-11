package com.ruskaof.server;

import com.ruskaof.common.commands.Command;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.dto.ToServerDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;
import com.ruskaof.common.util.Pair;
import com.ruskaof.server.commands.SaveCommand;
import com.ruskaof.server.util.FileManager;
import com.ruskaof.server.util.JsonParser;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeSet;

public class ServerApp {
    private final HistoryManager historyManager;
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final Logger logger;
    private final int countOfBytesForSize = 4;
    private final int serverWaitingPeriod = 50;

    public ServerApp(HistoryManager historyManager, CollectionManager collectionManager, FileManager fileManager, Logger logger) {
        this.logger = logger;
        this.collectionManager = collectionManager;
        this.historyManager = historyManager;
        this.fileManager = fileManager;
    }

    public void start(int serverPort, String ip) throws IOException, ClassNotFoundException {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            datagramChannel.bind(new InetSocketAddress(ip, serverPort));
            logger.info("Made a datagram channel");
            String stringData = fileManager.read();
            TreeSet<StudyGroup> studyGroups = new JsonParser().deSerialize(stringData);
            collectionManager.initialiseData(studyGroups);
            logger.info("Initialized collection, ready to receive data.");
            boolean isWorkingState = true;
            datagramChannel.configureBlocking(false);
            Scanner scanner = new Scanner(System.in);
            while (isWorkingState) {
                if (System.in.available() > 0) { // возможно, не лучшее решение, но другое мне найти не удалось
                    final String inp = scanner.nextLine();
                    if ("exit".equals(inp)) {
                        isWorkingState = false;
                    }
                    if ("save".equals(inp)) {
                        System.out.println(new SaveCommand(fileManager).execute(collectionManager, historyManager));
                    }
                }
                byte[] amountOfBytesHeader = new byte[countOfBytesForSize];
                ByteBuffer amountOfBytesHeaderWrapper = ByteBuffer.wrap(amountOfBytesHeader);
                SocketAddress socketAddress = datagramChannel.receive(amountOfBytesHeaderWrapper);

                if (Objects.nonNull(socketAddress)) {
                    Command command = receive(amountOfBytesHeader, datagramChannel);
                    // Execute
                    CommandResultDto commandResultDto = command.execute(collectionManager, historyManager);
                    logger.info("executed the command with result: " + commandResultDto.toString());
                    send(commandResultDto, datagramChannel, socketAddress);
                }
            }

            System.out.println(new SaveCommand(fileManager).execute(collectionManager, historyManager));
        }
    }

    private void send(CommandResultDto commandResultDto, DatagramChannel datagramChannel, SocketAddress socketAddress) throws IOException {
        // Send
        Pair<byte[], byte[]> pair = serialize(commandResultDto);

        byte[] sendDataBytes = pair.getFirst();
        byte[] sendDataAmountBytes = pair.getSecond();

        ByteBuffer sendDataAmountWrapper = ByteBuffer.wrap(sendDataAmountBytes);
        datagramChannel.send(sendDataAmountWrapper, socketAddress); // сначала отправляется файл-количество байтов в основном массиве байтов

        ByteBuffer sendBuffer = ByteBuffer.wrap(sendDataBytes);
        datagramChannel.send(sendBuffer, socketAddress);

        logger.info("sent the command result to the client");
    }

    private Command receive(byte[] amountOfBytesHeader, DatagramChannel datagramChannel) throws IOException, ClassNotFoundException {
        // Receive
        byte[] dataBytes = new byte[bytesToInt(amountOfBytesHeader)];

        ByteBuffer dataBytesWrapper = ByteBuffer.wrap(dataBytes);

        try {
            Thread.sleep(serverWaitingPeriod);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }

        SocketAddress checkAddress = datagramChannel.receive(dataBytesWrapper);
        while (checkAddress == null) {
            checkAddress = datagramChannel.receive(dataBytesWrapper);
        }

        ToServerDto toServerDto = (ToServerDto) deserialize(dataBytes);
        logger.info("received a data object: " + toServerDto.getCommand().toString());
        return (toServerDto).getCommand();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    /**
     *
     * @return first - data itself, second - amount of bytes in data
     */
    public Pair<byte[], byte[]> serialize(Object obj) throws IOException {

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
}
