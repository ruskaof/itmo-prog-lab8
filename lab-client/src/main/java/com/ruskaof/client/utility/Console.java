package com.ruskaof.client.utility;

import com.ruskaof.client.commands.Command;
import com.ruskaof.client.commands.CommandResult;

import java.io.IOException;
import java.util.Objects;


/**
 * The main class for app to be run
 */
public class Console {
    public Console(FileManager fileManager, CommandManager commandManager, UserInputManager userInputManager, CollectionManager collectionManager, OutputManager outputManager) {
        this.commandManager = commandManager;
        this.fileManager = fileManager;
        this.userInputManager = userInputManager;
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    private final OutputManager outputManager;
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final CommandManager commandManager;
    private final UserInputManager userInputManager;


    /**
     * Loads data into fileManager.mainData (a TreeSet) and starts listening to user command input
     */
    public void start() throws IOException {
        collectionManager.initialiseData(new Parser().deSerialize(fileManager.read()));
        CommandResult commandResult = null;
        do {
            String input = readNextCommand();
            String[] parsedInput = parseInput(input);
            String inputCommand = parsedInput[0];
            String inputArg = parsedInput[1];

            for (Command command : commandManager.getCommands()) {
                if (command.getName().equals(inputCommand)) {
                    commandResult = command.execute(inputArg);
                    break;
                }
                commandResult = new CommandResult(false, false, "This command was not found. Please use \"help\" to know about available commands");
            }

            assert commandResult != null;
            outputManager.println(commandResult.getOutput());
        } while (!Objects.requireNonNull(commandResult).isExit());
    }

    private String[] parseInput(String input) {
        String[] arrayInput = input.split(" ");
        String inputCommand = arrayInput[0];
        String inputArg = "";

        if (arrayInput.length >= 2) {
            inputArg = arrayInput[1];
        }

        return new String[]{inputCommand, inputArg};
    }

    private String readNextCommand() {
        outputManager.print(">>>");
        return userInputManager.nextLine();
    }

}
