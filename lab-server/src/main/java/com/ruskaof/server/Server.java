package com.ruskaof.server;

import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;
import com.ruskaof.server.util.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class Server {
    private static final int SERVER_PORT = 3223;
    private static final int CLIENT_PORT = 2332;
    private static final Logger LOGGER
            = LoggerFactory.getLogger(Server.class);

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        CollectionManager collectionManager = new CollectionManager();
        HistoryManager historyManager = new HistoryManager();
        FileManager fileManager = new FileManager("C:\\Users\\199-4\\Desktop\\data.json");
        ServerApp serverApp;

        try {
            serverApp = new ServerApp(historyManager, collectionManager, fileManager, SERVER_PORT, CLIENT_PORT, LOGGER);
            serverApp.start();
        } catch (IOException e) {
            LOGGER.error("There was a problem with a datafile. Please check if it is available.");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Found incorrect request from client");
        }
    }


}
