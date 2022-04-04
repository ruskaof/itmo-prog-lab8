package com.ruskaof.server;

import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;
import com.ruskaof.server.util.CollectionManagerImpl;
import com.ruskaof.server.util.FileManager;
import com.ruskaof.server.util.HistoryManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class Server {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(Server.class);

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        final int SERVER_PORT;
        final String IP;
        final String FILENAME;
        try {
            FILENAME = args[0];
            SERVER_PORT = Integer.parseInt(args[1]);
            if (SERVER_PORT > 9999) {
                throw new IllegalArgumentException("Port number out of range");
            }
            IP = args[2];
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            LOGGER.error("Found Invalid arguments. Please use this program as \"java -jar <name> <datafile name> <server port> <ip>\"");
            return;
        }

        CollectionManager collectionManager = new CollectionManagerImpl();
        HistoryManager historyManager = new HistoryManagerImpl();
        FileManager fileManager = new FileManager(FILENAME);
        ServerApp serverApp;

        try {
            serverApp = new ServerApp(historyManager, collectionManager, fileManager, LOGGER);
            serverApp.start(SERVER_PORT, IP);
        } catch (IOException e) {
            LOGGER.error("There was a problem with a datafile. Please check if it is available.");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Found incorrect request from client");
        }
    }
}
