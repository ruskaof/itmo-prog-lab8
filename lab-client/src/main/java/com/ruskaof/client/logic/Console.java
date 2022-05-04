package com.ruskaof.client.logic;

import com.ruskaof.client.connection.ConnectionManager;
import com.ruskaof.client.commands.ExecuteScriptCommand;
import com.ruskaof.client.util.InputManager;
import com.ruskaof.client.util.OutputManager;
import com.ruskaof.client.util.DataObjectsMaker;
import com.ruskaof.common.commands.AddCommand;
import com.ruskaof.common.commands.AddIfMinCommand;
import com.ruskaof.common.commands.ClearCommand;
import com.ruskaof.common.commands.Command;
import com.ruskaof.common.commands.FilterLessThanSemesterEnumCommand;
import com.ruskaof.common.commands.HelpCommand;
import com.ruskaof.common.commands.HistoryCommand;
import com.ruskaof.common.commands.InfoCommand;
import com.ruskaof.common.commands.MinByIDCommand;
import com.ruskaof.common.commands.PrintAscendingCommand;
import com.ruskaof.common.commands.RegisterCommand;
import com.ruskaof.common.commands.RemoveByIdCommand;
import com.ruskaof.common.commands.RemoveGreaterCommand;
import com.ruskaof.common.commands.ShowCommand;
import com.ruskaof.common.commands.UpdateCommand;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataCantBeSentException;

import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.UnresolvedAddressException;
import java.util.Collection;
import java.util.NoSuchElementException;


public class Console {
    private final OutputManager outputManager;
    private final InputManager inputManager;
    private final ConnectionManager connectionManager;
    private final DataObjectsMaker dataObjectsMaker;
    private final Collection<String> listOfCommands;
    private String username;
    private String password;


    public Console(
            OutputManager outputManager,
            InputManager inputManager,
            ConnectionManager connectionManager,
            Collection<String> listOfCommands
    ) {
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.connectionManager = connectionManager;
        this.listOfCommands = listOfCommands;
        this.dataObjectsMaker = new DataObjectsMaker(inputManager, outputManager, username);
    }

    public void start() throws ClassNotFoundException, IOException, DataCantBeSentException, UnresolvedAddressException {
        initUsernameAndPassword();
        String input;
        do {
            input = readNextCommand();
            if ("exit".equals(input)) {
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
                if ("register".equals(commandName)) {
                    commandArg = dataObjectsMaker.makeLoginAndPassword();
                }
                if ("execute_script".equals(commandName)) {
                    new ExecuteScriptCommand((String) commandArg).execute(inputManager);
                } else {
                    try {
                        outputManager.println(
                                connectionManager.sendCommand(new CommandFromClientDto(getCommandObjectByName(commandName, commandArg, commandArg2), username, password)).getOutput().toString()
                        );
                    } catch (DataCantBeSentException e) {
                        outputManager.println("Could not send a command");
                    }
                }
            } else {
                outputManager.println("The command was not found. Please use \"help\" to know about commands.");
            }
        } while (true);
    }

    private void initUsernameAndPassword() throws IOException, DataCantBeSentException, UnresolvedAddressException {
        outputManager.println("Would you like to register first? (type \"yes\" to register or something else to continue with your own password+login).");
        final String answer = inputManager.nextLine();
        if ("yes".equals(answer)) {
            outputManager.println("Enter your new login");
            final String loginToRegister = inputManager.nextLine();
            outputManager.println("Enter new password");
            final String passwordToRegister = inputManager.nextLine();


            CommandResultDto registerCommandResult = connectionManager.sendCommand(new CommandFromClientDto(new RegisterCommand(new String[]{loginToRegister, passwordToRegister})));
            if (registerCommandResult.isWasExecutedCorrectly()) {
                if (!((RegisterCommand.RegisterCommandResult) registerCommandResult).isWasRegistered()) {
                    outputManager.println("User was not registered because the username was not unique.");
                    initUsernameAndPassword();
                } else {
                    password = passwordToRegister;
                    username = loginToRegister;
                }
            } else {
                throw new DataCantBeSentException();
            }
        } else {
            outputManager.println("Enter login");
            username = inputManager.nextLine();
            outputManager.println("Enter password");
            password = inputManager.nextLine();
        }
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
            case "add": command = new AddCommand((StudyGroup) arg);
                break;
            case "add_if_min": command = new AddIfMinCommand((StudyGroup) arg);
                break;
            case "clear": command = new ClearCommand();
                break;
            case "filter_less_than_semester_enum": command = new FilterLessThanSemesterEnumCommand((String) arg);
                break;
            case "history": command = new HistoryCommand();
                break;
            case "info": command = new InfoCommand();
                break;
            case "min_by_id": command = new MinByIDCommand((String) arg);
                break;
            case "print_ascending": command = new PrintAscendingCommand();
                break;
            case "remove_by_id": command = new RemoveByIdCommand((String) arg);
                break;
            case "remove_greater": command = new RemoveGreaterCommand((StudyGroup) arg);
                break;
            case "show": command = new ShowCommand();
                break;
            case "update": command = new UpdateCommand((StudyGroup) arg, arg2);
                break;
            default: command = new HelpCommand();
                break;
        }
        return command;
    }
}
