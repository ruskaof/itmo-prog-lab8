package com.ruskaof.client.main;

import com.ruskaof.client.utility.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        if (!new File(args[0]).exists()) {
            System.out.println("File was not found");
            return;
        }

        final HistoryManager historyManager = new HistoryManager();
        final OutputManager outputManager = new OutputManager();
        final CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = null;
        try {
            fileManager = new FileManager(args[0]);
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
