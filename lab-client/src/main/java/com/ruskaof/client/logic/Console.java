package com.ruskaof.client.logic;

import com.ruskaof.client.commands.ExecuteScriptCommand;
import com.ruskaof.client.connection.CommandSender;
import com.ruskaof.client.util.DataObjectsMaker;
import com.ruskaof.client.util.InputManager;
import com.ruskaof.client.util.OutputManager;
import com.ruskaof.common.commands.*;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.nio.channels.UnresolvedAddressException;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;


public class Console {
    private static final int MAX_STRING_LENGTH = 100;
    private final InputManager inputManager;
    private final Collection<String> listOfCommands = Arrays.asList("help", "info", "show", "add", "update", "remove_by_id", "clear", "execute_script", "add_if_min", "remove_greater", "history", "min_by_id", "print_ascending", "filter_less_than_semester_enum");
    private final String username;
    private final String password;
    private final OutputManager outputManager = new StringOM();


    public Console(
            InputManager inputManager,
            String username,
            String password
    ) {
        this.username = username;
        this.password = password;
        this.inputManager = inputManager;
    }

    public String start(CommandSender commandSender, String filePath) throws IOException, UnresolvedAddressException {
        new ExecuteScriptCommand(filePath).execute(inputManager);
        DataObjectsMaker dataObjectsMaker = new DataObjectsMaker(inputManager, outputManager, username);
        String input;
        do {
            input = readNextCommand();
            if (null == input) {
                break;
            }
            final String[] parsedInp = parseToNameAndArg(input);
            final String commandName = parsedInp[0];
            Serializable commandArg = parsedInp[1];
            String commandArg2 = ""; // only for update command in this case
            if (listOfCommands.contains(commandName)) {
                if ("add".equals(commandName) || "add_if_min".equals(commandName) || "remove_greater".equals(commandName)) {
                    commandArg = dataObjectsMaker.makeStudyGroup();
                }
                if ("update".equals(commandName)) {
                    commandArg2 = (String) commandArg;
                    commandArg = dataObjectsMaker.makeStudyGroup();
                }
                if ("execute_script".equals(commandName)) {
                    new ExecuteScriptCommand((String) commandArg).execute(inputManager);
                } else {
                    outputManager.println(
                            commandSender.sendCommand(new CommandFromClientDto(getCommandObjectByName(commandName, commandArg, commandArg2))).getStrResult()
                    );
                }
            } else {
                outputManager.println("The command was not found. Please use \"help\" to know about commands.");
            }
        } while (true);

        return outputManager.getContaining();
    }


    private String[] parseToNameAndArg(String input) {
        String[] arrayInput = input.split(" ");
        String inputCommand = arrayInput[0];
        String inputArg = "";

        if (arrayInput.length >= 2) {
            inputArg = arrayInput[1];
        }

        return new String[]{inputCommand, inputArg};
    }

    private String readNextCommand() throws IOException {
        outputManager.print(">>>");
        try {
            return inputManager.nextLine();
        } catch (NoSuchElementException e) {
            return "exit";
        }
    }

    private Command getCommandObjectByName(String commandName, Serializable arg, String arg2) {
        Command command;
        switch (commandName) {
            case "add": command = new AddCommand(username, password, (StudyGroup) arg);
                break;
            case "add_if_min": command = new AddIfMinCommand(username, password, (StudyGroup) arg);
                break;
            case "clear": command = new ClearCommand(username, password);
                break;
            case "filter_less_than_semester_enum": command = new FilterLessThanSemesterEnumCommand(username, password, (String) arg);
                break;
            case "history": command = new HistoryCommand(username, password);
                break;
            case "info": command = new InfoCommand(username, password);
                break;
            case "min_by_id": command = new MinByIdCommand(username, password, (String) arg);
                break;
            case "print_ascending": command = new PrintAscendingCommand(username, password);
                break;
            case "remove_by_id": command = new RemoveByIdCommand(username, password, (String) arg);
                break;
            case "remove_greater": command = new RemoveGreaterCommand(username, password, (StudyGroup) arg);
                break;
            case "show": command = new ShowCommand(username, password);
                break;
            case "update": command = new UpdateCommand(username, password, (StudyGroup) arg, arg2);
                break;
            default: command = new HelpCommand(username, password);
                break;
        }
        return command;
    }

    private class StringOM implements OutputManager {
        StringBuilder stringBuilder = new StringBuilder();

        @Override
        public void println(String string) {
            stringBuilder.append(string + "\n");
        }

        @Override
        public void print(String string) {
            stringBuilder.append(string);
        }

        @Override
        public String getContaining() {
            return stringBuilder.toString();
        }

    }
}