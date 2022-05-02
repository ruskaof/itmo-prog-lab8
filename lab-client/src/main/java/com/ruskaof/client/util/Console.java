package com.ruskaof.client.util;

import com.ruskaof.client.ClientApp;
import com.ruskaof.client.commands.ExecuteScriptCommand;
import com.ruskaof.common.commands.*;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.util.DataCantBeSentException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.NoSuchElementException;


public class Console {
    private final OutputManager outputManager;
    private final InputManager inputManager;
    private final ClientApp clientApp;
    private final StudyGroupMaker studyGroupMaker;
    private final Collection<String> listOfCommands;
    private final String username;
    private final String password;


    public Console(
            OutputManager outputManager,
            InputManager inputManager,
            ClientApp clientApp,
            Collection<String> listOfCommands,
            String username,
            String password
    ) {
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.clientApp = clientApp;
        this.listOfCommands = listOfCommands;
        this.studyGroupMaker = new StudyGroupMaker(inputManager, outputManager);
        this.username = username;
        this.password = password;
    }

    public void start() throws ClassNotFoundException, IOException {
        String input;
        do {
            input = readNextCommand();
            final String[] parsedInp = parseToNameAndArg(input);
            final String commandName = parsedInp[0];
            Serializable commandArg = parsedInp[1];
            String commandArg2 = ""; // only for update command in this case
            if (listOfCommands.contains(commandName)) {
                if ("add".equals(commandName) || "add_if_min".equals(commandName) || "remove_greater".equals(commandName)) {
                    commandArg = studyGroupMaker.makeStudyGroup();
                }
                if ("update".equals(commandName)) {
                    commandArg2 = (String) commandArg;
                    commandArg = studyGroupMaker.makeStudyGroup();
                }
                if ("execute_script".equals(commandName)) {
                    new ExecuteScriptCommand((String) commandArg).execute(inputManager);
                } else {
                    try {
                        outputManager.println(
                                clientApp.sendCommand(new CommandFromClientDto(getCommandObjectByName(commandName, commandArg, commandArg2), username, password))
                                        .getOutput().toString()
                        );
                    } catch (DataCantBeSentException e) {
                        outputManager.println("Could not send a command");
                    }
                }
            } else {
                outputManager.println("The command was not found. Please use \"help\" to know about commands.");
            }
        } while (!"exit".equals(input));
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
            case "add":
                command = new AddCommand((StudyGroup) arg);
                break;
            case "add_if_min":
                command = new AddIfMinCommand((StudyGroup) arg);
                break;
            case "clear":
                command = new ClearCommand();
                break;
            case "filter_less_than_semester_enum":
                command = new FilterLessThanSemesterEnumCommand((String) arg);
                break;
            case "history":
                command = new HistoryCommand();
                break;
            case "info":
                command = new InfoCommand();
                break;
            case "min_by_id":
                command = new MinByIDCommand((String) arg);
                break;
            case "print_ascending":
                command = new PrintAscendingCommand();
                break;
            case "remove_by_id":
                command = new RemoveByIdCommand((String) arg);
                break;
            case "remove_greater":
                command = new RemoveGreaterCommand((StudyGroup) arg);
                break;
            case "show":
                command = new ShowCommand();
                break;
            case "update":
                command = new UpdateCommand((StudyGroup) arg, arg2);
                break;
            case "register":
                command = new RegisterCommand((String) arg);
                break;
            default:
                command = new HelpCommand();
                break;
        }
        return command;
    }
}
