package com.ruskaof.client;

import com.google.gson.JsonSyntaxException;
import com.ruskaof.client.utility.CollectionManager;
import com.ruskaof.client.utility.CommandManager;
import com.ruskaof.client.utility.CommandRunManager;
import com.ruskaof.client.utility.Console;
import com.ruskaof.client.utility.FileManager;
import com.ruskaof.client.utility.HistoryManager;
import com.ruskaof.client.utility.OutputManager;
import com.ruskaof.client.utility.UserInputManager;

import java.io.IOException;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {

        final OutputManager outputManager = new OutputManager();

        if (args.length == 0) {
            outputManager.println("This program needs a file in argument to work with.");
            return;
        }

        if (!args[0].endsWith(".json")) {
            outputManager.println("This program can only work with .json file.");
            return;
        }

        final HistoryManager historyManager = new HistoryManager();
        final CollectionManager collectionManager = new CollectionManager();
        final FileManager fileManager = new FileManager(args[0]);
        final UserInputManager userInputManager = new UserInputManager();
        final CommandManager commandManager = new CommandManager(fileManager, userInputManager, collectionManager, outputManager, historyManager);
        final CommandRunManager commandRunManager = new CommandRunManager(commandManager, historyManager);
        final Console console = new Console(fileManager,
                userInputManager, collectionManager, outputManager,
                commandRunManager);
        try {
            console.start();
        } catch (IOException e) {
            outputManager.println("Could not read the file. Check if it is available.");
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            outputManager.println("The file does not keep data in correct format.");
        } catch (Exception e) {
            outputManager.println("Could not execute the program");
        }
    }
}
