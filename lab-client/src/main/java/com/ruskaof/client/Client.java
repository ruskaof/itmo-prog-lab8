package com.ruskaof.client;

import com.ruskaof.client.util.Console;
import com.ruskaof.client.util.InputManager;
import com.ruskaof.client.util.OutputManager;

import java.util.Collection;
import java.util.HashSet;

public final class Client {
    private static final int SERVER_PORT = 2743;
    private static final int CLIENT_PORT = 3847;

    private static final Collection<String> LIST_OF_COMMANDS = new HashSet<>();

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        LIST_OF_COMMANDS.add("add");
        LIST_OF_COMMANDS.add("add_if_min");
        LIST_OF_COMMANDS.add("clear");
        LIST_OF_COMMANDS.add("exit");
        LIST_OF_COMMANDS.add("help");
        LIST_OF_COMMANDS.add("history");
        LIST_OF_COMMANDS.add("info");
        LIST_OF_COMMANDS.add("min_by_id");
        LIST_OF_COMMANDS.add("print_ascending");
        LIST_OF_COMMANDS.add("remove_by_id");
        LIST_OF_COMMANDS.add("remove_greater");
        LIST_OF_COMMANDS.add("show");
        LIST_OF_COMMANDS.add("update");

        OutputManager outputManager = new OutputManager(System.out);

        try {
            ClientApp clientApp = new ClientApp(CLIENT_PORT, SERVER_PORT);
            new Console(outputManager, new InputManager(System.in), clientApp, LIST_OF_COMMANDS).start();
        } catch (ClassNotFoundException e) {
            outputManager.println("Found incorrect data from server.");
        }
    }


}
