package com.ruskaof.server;

import com.ruskaof.common.commands.Command;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.dto.ToServerDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;
import com.ruskaof.common.util.JsonParser;
import com.ruskaof.server.commands.SaveCommand;
import com.ruskaof.server.util.FileManager;
import org.slf4j.Logger;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.TreeSet;

public class ServerApp {
    private static final int BF_SIZE = 2048;
    private final HistoryManager historyManager;
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final DatagramSocket datagramSocket;
    private final int clientPort;
    private final ReceiveModule receiveModule;
    private final ExecuteModule executeModule;
    private final SendModule sendModule;
    private final Logger logger;
    private boolean isWorking = false;


    public ServerApp(HistoryManager historyManager, CollectionManager collectionManager, FileManager fileManager, int serverPort, int clientPort, Logger logger) throws SocketException {
        this.logger = logger;
        this.collectionManager = collectionManager;
        this.historyManager = historyManager;
        this.fileManager = fileManager;
        datagramSocket = new DatagramSocket(serverPort);
        logger.info("Made a datagram socket");
        this.clientPort = clientPort;
        this.receiveModule = new ReceiveModule();
        this.executeModule = new ExecuteModule();
        this.sendModule = new SendModule();
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    /*
    Серверное приложение должно состоять из следующих модулей (реализованных в виде одного или нескольких классов):
    Модуль приёма подключений. (not needed)
    Модуль чтения запроса.
    Модуль обработки полученных команд.
    Модуль отправки ответов клиенту.
     */

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public void start() throws IOException, ClassNotFoundException {

        String stringData = fileManager.read();
        TreeSet<StudyGroup> studyGroups = new JsonParser().deSerialize(stringData);
        collectionManager.initialiseData(studyGroups);
        logger.info("Initialized collection, ready to receive data.");


        isWorking = true;

        while (isWorking) {
            Command command = receiveModule.invoke();
            logger.info("Received data from client: " + command.toString());
            CommandResultDto commandResultDto = executeModule.invoke(command);
            logger.info("Executed the command: " + commandResultDto.toString());
            sendModule.invoke(commandResultDto);
            logger.info("Send command result");
        }

        System.out.println(new SaveCommand().execute(fileManager, collectionManager));
    }

    class ReceiveModule {
        public Command invoke() throws IOException, ClassNotFoundException {
            byte[] buf = new byte[BF_SIZE];
            DatagramPacket toReceivePacket = new DatagramPacket(buf, BF_SIZE);
            datagramSocket.receive(toReceivePacket);
            return ((ToServerDto) deserialize(toReceivePacket.getData())).getCommand();
        }
    }

    class ExecuteModule {
        public CommandResultDto invoke(Command command) {
            historyManager.addNote(command.getName());
            return command.execute(collectionManager, historyManager);
        }
    }

    class SendModule {
        public void invoke(CommandResultDto commandResultDto) throws IOException {
            byte[] toSend = serialize(commandResultDto);

            final DatagramPacket datagramPacket = new DatagramPacket(toSend, toSend.length, InetAddress.getByName("127.0.0.1"), clientPort);

            datagramSocket.send(datagramPacket);
        }
    }
}
