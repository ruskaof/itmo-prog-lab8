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
import java.util.TreeSet;

public class ServerApp {
    private static final int BF_SIZE = 2048;
    private final HistoryManager historyManager;
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final Logger logger;
    private final IsWorkingState isWorkingState = new IsWorkingState(false);


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

    public void start(int serverPort) throws IOException, ClassNotFoundException {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            datagramChannel.bind(new InetSocketAddress("127.0.0.1", serverPort));
            logger.info("Made a datagram channel");

            String stringData = fileManager.read();
            TreeSet<StudyGroup> studyGroups = new JsonParser().deSerialize(stringData);
            collectionManager.initialiseData(studyGroups);
            logger.info("Initialized collection, ready to receive data.");
            isWorkingState.setValue(true);
            datagramChannel.configureBlocking(false);
            while (isWorkingState.getValue()) {
                // Тут нужно как-то проверить, ввели ли в консоль сервера команду exit, чтобы остановить цикл.
                // Как это сделать без создания второго потока - информацию мне найти не удалось
                byte[] buf1 = new byte[BF_SIZE];
                ByteBuffer receiveBuffer = ByteBuffer.wrap(buf1);
                SocketAddress socketAddress = datagramChannel.receive(receiveBuffer);

                if (Objects.nonNull(socketAddress)) {
                    // Receive
                    ToServerDto toServerDto = (ToServerDto) deserialize(buf1);
                    logger.info("received a data object: " + toServerDto.getCommand().toString());
                    final Command command = (toServerDto).getCommand();

                    // Execute
                    CommandResultDto commandResultDto = command.execute(collectionManager, historyManager);
                    logger.info("executed the command with result: " + commandResultDto.toString());

                    // Send
                    byte[] buf2 = serialize(commandResultDto);
                    ByteBuffer sendBuffer = ByteBuffer.wrap(buf2);
                    datagramChannel.send(sendBuffer, socketAddress);
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

    /**
     * Этот класс нужен, чтобы была теоретическая возможность выйти из цикла
     * получения новых команд. Команды exit по тз нет вообще, но я посчитал, что лучше сделать так,
     * чем примитив. Для того чтобы выйти из цикла будет достаточно лишь использовать метод
     * setValue с аргументом false.
     */
    final class IsWorkingState {
        private Boolean value;

        private IsWorkingState(Boolean initValue) {
            this.value = initValue;
        }

        public Boolean getValue() {
            return value;
        }

        public void setValue(Boolean value) {
            this.value = value;
        }
    }
}
