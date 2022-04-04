package com.ruskaof.server;

import com.ruskaof.common.commands.Command;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.dto.ToServerDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;
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
    private static final int BF_SIZE = 2048;
    private final HistoryManager historyManager;
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final Logger logger;


    public ServerApp(HistoryManager historyManager, CollectionManager collectionManager, FileManager fileManager, Logger logger) {
        this.logger = logger;
        this.collectionManager = collectionManager;
        this.historyManager = historyManager;
        this.fileManager = fileManager;
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public void start(int serverPort, String IP) throws IOException, ClassNotFoundException {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            datagramChannel.bind(new InetSocketAddress(IP, serverPort));
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
                        System.out.println(new SaveCommand(fileManager).execute(collectionManager,historyManager));
                    }
                }
                byte[] receiveBuffer = new byte[BF_SIZE];
                ByteBuffer receiveWrapperBuffer = ByteBuffer.wrap(receiveBuffer);
                SocketAddress socketAddress = datagramChannel.receive(receiveWrapperBuffer);

                if (Objects.nonNull(socketAddress)) {
                    // Receive
                    ToServerDto toServerDto = (ToServerDto) deserialize(receiveBuffer);
                    logger.info("received a data object: " + toServerDto.getCommand().toString());
                    final Command command = (toServerDto).getCommand();

                    // Execute
                    CommandResultDto commandResultDto = command.execute(collectionManager, historyManager);
                    logger.info("executed the command with result: " + commandResultDto.toString());

                    // Send
                    byte[] sendBuffer = serialize(commandResultDto);
                    ByteBuffer sendWrapBuffer = ByteBuffer.wrap(sendBuffer);
                    datagramChannel.send(sendWrapBuffer, socketAddress);
                    logger.info("sent the command result to the client");
                }
            }

            System.out.println(new SaveCommand(fileManager).execute(collectionManager, historyManager));
        }
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
}
