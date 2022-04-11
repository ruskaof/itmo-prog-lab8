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
    private static int serverPort;
    private static String serverIp;
    private static String filename;
    private static final int MAX_PORT = 9999;
    private static final Logger LOGGER
            = LoggerFactory.getLogger(Server.class);

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        try {
            setParameterValues(args);
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            LOGGER.error("Found Invalid arguments. Please use this program as \"java -jar <name> <datafile name> <server port> <ip>\"");
        }
        CollectionManager collectionManager = new CollectionManagerImpl();
        HistoryManager historyManager = new HistoryManagerImpl();
        FileManager fileManager = new FileManager(filename);
        ServerApp serverApp;
        try {
            serverApp = new ServerApp(historyManager, collectionManager, fileManager, LOGGER);
            serverApp.start(serverPort, serverIp);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("There was a problem with a datafile. Please check if it is available.");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Found incorrect request from client");
        }
    }

    private static void setParameterValues(String[] args) throws IllegalArgumentException, IndexOutOfBoundsException {

        filename = args[0];
        serverPort = Integer.parseInt(args[1]);
        if (serverPort > MAX_PORT) {
            throw new IllegalArgumentException("Port number out of range");
        }
        serverIp = args[2];

    }
}


