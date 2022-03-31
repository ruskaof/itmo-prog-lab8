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
    private static final int SERVER_PORT = 2743;
    private static final int CLIENT_PORT = 3847;
    private static final Logger LOGGER
            = LoggerFactory.getLogger(Server.class);

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        if (args.length < 1 || !args[0].endsWith(".json")) {
            LOGGER.error("Could not start the server. Please check that your argument is correct and is a path to a .json file");
            return;
        }

        CollectionManager collectionManager = new CollectionManagerImpl();
        HistoryManager historyManager = new HistoryManagerImpl();
        FileManager fileManager = new FileManager(args[0]);
        ServerApp serverApp;

        try {
            serverApp = new ServerApp(historyManager, collectionManager, fileManager, LOGGER);
            serverApp.start(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("There was a problem with a datafile. Please check if it is available.");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Found incorrect request from client");
        }
    }
}
