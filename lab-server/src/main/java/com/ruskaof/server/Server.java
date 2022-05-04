package com.ruskaof.server;

import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;
import com.ruskaof.common.util.State;
import com.ruskaof.server.data.remote.posturesql.DataManagerImpl;
import com.ruskaof.server.data.remote.posturesql.Database;
import com.ruskaof.server.executing.Console;
import com.ruskaof.server.executing.MainApp;
import com.ruskaof.server.util.HistoryManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("FieldCanBeLocal")
public final class Server {
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));
    private static int serverPort;
    private static String serverIp;
    private static final int MAX_PORT = 65535;
    private static final int MIN_PORT = 1024;
    private static final Logger LOGGER
            = LoggerFactory.getLogger(Server.class);
    private static String dbHost;
    private static String dbName;
    private static String username;
    private static String password;

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        try {
            initMainInfoForConnection();

            Connection connection;
            try {
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://" + dbHost + "/" + dbName,
                        username,
                        password
                );
            } catch (SQLException e) {
                LOGGER.error("Cold not connect to the server. Please check if your login and password were correct.");
                return;
            }

            LOGGER.info("Successfully made a connection with the database");

            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            ForkJoinPool forkJoinPool = new ForkJoinPool();

            State<Boolean> serverIsWorkingState = new State<>(true);
            HistoryManager historyManager = new HistoryManagerImpl();
            Database database = new Database(connection, LOGGER);
            DataManager dataManager = new DataManagerImpl(database, LOGGER);
            Console console = new Console(serverIsWorkingState, LOGGER);
            MainApp serverApp;
            serverApp = new MainApp(
                    LOGGER,
                    serverPort,
                    serverIp,
                    cachedThreadPool,
                    forkJoinPool
            );
            cachedThreadPool.submit(console::start);
            serverApp.start(historyManager, dataManager, serverIsWorkingState);
            cachedThreadPool.shutdown();
            forkJoinPool.shutdown();
        } catch (IOException e) {
            LOGGER.error("An unexpected IO error occurred. The message is: " + e.getMessage());
        }
    }

    private static void initMainInfoForConnection() throws IOException {
        serverPort = ask(
                value -> (value >= MIN_PORT && value <= MAX_PORT),
                "Enter server port",
                "Server port must be an int number",
                "Sever port must be between 1024 and 65535",
                Integer::parseInt
        );

        serverIp = ask("Enter server IP");
        dbHost = ask("Enter database host");

        dbName = ask("Enter database name");

        username = ask("Enter username");
        password = ask("Enter password");
    }

    private static <T> T ask(
            Predicate<T> predicate,
            String askMessage,
            String errorMessage,
            String wrongValueMessage,
            Function<String, T> converter
    ) throws IOException {
        LOGGER.info(askMessage);
        String input;
        T value;
        do {
            try {
                input = BUFFERED_READER.readLine();
                value = converter.apply(input);
            } catch (IllegalArgumentException e) {
                LOGGER.error(errorMessage);
                continue;
            }
            if (predicate.test(value)) {
                return value;
            } else {
                LOGGER.error(wrongValueMessage);
            }
        } while (true);
    }

    private static String ask(
            String askMessage
    ) throws IOException {
        LOGGER.info(askMessage);
        String input;
        input = BUFFERED_READER.readLine();
        return input;
    }
}



