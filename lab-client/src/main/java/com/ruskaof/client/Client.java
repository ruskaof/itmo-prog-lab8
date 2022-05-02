package com.ruskaof.client;

import com.ruskaof.client.util.Console;
import com.ruskaof.client.util.InputManager;
import com.ruskaof.client.util.OutputManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Client {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static final OutputManager outputManager = new OutputManager(System.out);
    private static final int MAX_PORT = 65635;
    private static final Collection<String> LIST_OF_COMMANDS = new HashSet<>();
    private static int serverPort;
    private static int clientPort;
    private static String clientIp;
    private static String serverIp;
    private static String username;
    private static String password;


    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        initCommandList();
        try {
            initMainInfoForConnection();
        } catch (IOException e) {
            outputManager.println("Something went wrong during initialisation client info");
            return;
        }
        try {
            ClientApp clientApp = new ClientApp(clientPort, serverPort, clientIp, serverIp, outputManager);
            new Console(outputManager, new InputManager(System.in), clientApp, LIST_OF_COMMANDS, username, password).start();
        } catch (ClassNotFoundException e) {
            outputManager.println("Found incorrect data from server.");
        } catch (IOException e) {
            outputManager.println("Something went wrong with IO, the message is: " + e.getMessage());
        }
    }

    private static void initCommandList() {
        Client.LIST_OF_COMMANDS.add("add");
        Client.LIST_OF_COMMANDS.add("add_if_min");
        Client.LIST_OF_COMMANDS.add("clear");
        Client.LIST_OF_COMMANDS.add("exit");
        Client.LIST_OF_COMMANDS.add("help");
        Client.LIST_OF_COMMANDS.add("history");
        Client.LIST_OF_COMMANDS.add("info");
        Client.LIST_OF_COMMANDS.add("min_by_id");
        Client.LIST_OF_COMMANDS.add("print_ascending");
        Client.LIST_OF_COMMANDS.add("remove_by_id");
        Client.LIST_OF_COMMANDS.add("remove_greater");
        Client.LIST_OF_COMMANDS.add("show");
        Client.LIST_OF_COMMANDS.add("update");
        Client.LIST_OF_COMMANDS.add("execute_script");
        Client.LIST_OF_COMMANDS.add("filter_less_than_semester_enum");
        Client.LIST_OF_COMMANDS.add("print_ascending");
        Client.LIST_OF_COMMANDS.add("register");
    }


    private static void initMainInfoForConnection() throws IOException {


        username = ask("Enter username");
        password = ask("Enter password");
        serverPort = ask(
                value -> (value >= 1024 && value <= MAX_PORT),
                "Enter server port",
                "Server port must be an int number",
                "Sever port must be between 1024 and 65535",
                Integer::parseInt
        );

        serverIp = ask("Enter server IP");

        clientPort = ask(
                value -> (value >= 1024 && value <= MAX_PORT),
                "Enter client port",
                "Client port must be an int number",
                "Client port must be between 1024 and 65535",
                Integer::parseInt
        );

        clientIp = ask("Enter client IP");
    }

    private static <T> T ask(Predicate<T> predicate,
                             String askMessage,
                             String errorMessage,
                             String wrongValueMessage,
                             Function<String, T> converter
    ) throws IOException {
        outputManager.println(askMessage);
        String input;
        T value;
        do {
            try {
                input = bufferedReader.readLine();
                value = converter.apply(input);
            } catch (IllegalArgumentException e) {
                outputManager.println(errorMessage);
                continue;
            }
            if (predicate.test(value)) {
                return value;
            } else {
                outputManager.println(wrongValueMessage);
            }
        } while (true);
    }

    private static String ask(
            String askMessage
    ) throws IOException {
        outputManager.println(askMessage);
        String input;
        input = bufferedReader.readLine();
        return input;
    }
}
