package com.ruskaof.server;

import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;
import com.ruskaof.common.util.State;
import com.ruskaof.server.connection.ClientDataReceiver;
import com.ruskaof.server.connection.MainApp;
import com.ruskaof.server.data.remote.repository.posturesql.Database;
import com.ruskaof.server.domain.repository.DataManager;
import com.ruskaof.server.util.CommandHandler;
import com.ruskaof.server.util.HistoryManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Server {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static int serverPort;
    private static String serverIp;
    private static String studyGroupsFilename;
    private static String usersFilename;
    private static final int MAX_PORT = 65535;
    private static final Logger LOGGER
            = LoggerFactory.getLogger(Server.class);
    private static String dbHost;
    private static String dbName;
    private static String username;
    private static String password;

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws SQLException, InterruptedException, IOException {


        initMainInfoForConnection();
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                username,
                password
        );

        System.out.println(connection);

        HistoryManager historyManager = new HistoryManagerImpl();
        Database database = new Database(connection);
        CollectionManager collectionManager = new DataManager(database);
        MainApp serverApp;
        try {
            serverApp = new MainApp(
                    LOGGER,
                    serverPort,
                    serverIp,
                    new CommandHandler(),
                    new ClientDataReceiver(LOGGER),
                    database
            );
            serverApp.start(historyManager, collectionManager, new State<>(true));
        } catch (IOException e) {
            LOGGER.error("An unexpected IO error occurred. The message is: " + e.getMessage());
        }
    }

    private static void initMainInfoForConnection() throws IOException {
        serverPort = ask(
                (value) -> (value >= 1024 && value <= 65535),
                "Enter server port",
                "Server port must be an int number",
                "Sever port must be between 1024 and 65535",
                Integer::parseInt
        );

        serverIp = ask("Enter server IP");

        studyGroupsFilename = ask(
                (value) -> {
                    File file = new File(value);
                    return (file.exists() && value.endsWith(".json"));
                },
                "Enter filename for storing data",
                "Filename must end with .json and file must exist"
        );

        usersFilename = ask(
                (value) -> {
                    File file = new File(value);
                    return (file.exists() && value.endsWith(".json"));
                },
                "Enter filename for storing users",
                "Filename must end with .json and file must exist"
        );

        dbHost = ask("Enter database host");

        dbName = ask("Enter database name");

        username = ask("Enter username");
        password = ask("Enter password");
    }

    private static <T> T ask(Predicate<T> predicate,
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
                input = bufferedReader.readLine();
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

    private static String ask(Predicate<String> predicate,
                              String askMessage,
                              String wrongValueMessage
    ) throws IOException {
        LOGGER.info(askMessage);
        String input;
        do {
            input = bufferedReader.readLine();
            if (predicate.test(input)) {
                return input;
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
        input = bufferedReader.readLine();
        return input;
    }
}



