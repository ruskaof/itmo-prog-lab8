package com.ruskaof.client;

import com.ruskaof.client.util.Console;
import com.ruskaof.client.util.InputManager;
import com.ruskaof.client.util.OutputManager;

import java.util.Collection;
import java.util.HashSet;

public final class Client {
    private static final Collection<String> LIST_OF_COMMANDS = new HashSet<>();

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        OutputManager outputManager = new OutputManager(System.out);
        final int SERVER_PORT;
        final int CLIENT_PORT;
        final String IP;

        try {
            SERVER_PORT = Integer.parseInt(args[0]);
            CLIENT_PORT = Integer.parseInt(args[1]);
            if (SERVER_PORT > 9999 || CLIENT_PORT > 9999) {
                throw new IllegalArgumentException("Port number out of range");
            }
            IP = args[2];
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            outputManager.println("Found invalid arguments. Please use the program as \"java -jar <name> <server port> <client port> <ip>\"");
            return;
        }

        initCommandList(LIST_OF_COMMANDS);


        try {
            ClientApp clientApp = new ClientApp(CLIENT_PORT, SERVER_PORT, IP);
            new Console(outputManager, new InputManager(System.in), clientApp, LIST_OF_COMMANDS).start();
        } catch (ClassNotFoundException e) {
            outputManager.println("Found incorrect data from server.");
        }
    }

    public static void initCommandList(Collection<String> l) {
        l.add("add");
        l.add("add_if_min");
        l.add("clear");
        l.add("exit");
        l.add("help");
        l.add("history");
        l.add("info");
        l.add("min_by_id");
        l.add("print_ascending");
        l.add("remove_by_id");
        l.add("remove_greater");
        l.add("show");
        l.add("update");
    }
}
