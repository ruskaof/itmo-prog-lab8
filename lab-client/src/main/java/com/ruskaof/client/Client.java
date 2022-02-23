package com.ruskaof.client;

import com.ruskaof.client.utility.CollectionManager;
import com.ruskaof.client.utility.CommandManager;
import com.ruskaof.client.utility.CommandRunManager;
import com.ruskaof.client.utility.Console;
import com.ruskaof.client.utility.FileManager;
import com.ruskaof.client.utility.HistoryManager;
import com.ruskaof.client.utility.OutputManager;
import com.ruskaof.client.utility.UserInputManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        final OutputManager outputManager = new OutputManager();
        if (args.length == 0) {
            outputManager.println("This program needs a path to a .json file");
            return;
        }
        final String filename = args[0];
        if (!new File(filename).exists()) {
            outputManager.println("The file does not exist");
            return;
        }

        if (!filename.endsWith(".json")) {
            outputManager.println("This program only works with json files");
            return;
        }

        final HistoryManager historyManager = new HistoryManager();
        final CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = null;
        try {
            fileManager = new FileManager(filename);
        } catch (FileNotFoundException e) {
            // never trows
            e.printStackTrace();
        }
        final UserInputManager userInputManager = new UserInputManager();
        final CommandManager commandManager = new CommandManager(fileManager, userInputManager, collectionManager, outputManager, historyManager);
        final CommandRunManager commandRunManager = new CommandRunManager(commandManager, historyManager);
        final Console console = new Console(fileManager,
                userInputManager, collectionManager, outputManager,
                commandRunManager);
        try {
            console.start();
        } catch (IOException e) {
            // never throws
            e.printStackTrace();
        }
    }
}
