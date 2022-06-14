package com.ruskaof.client.logic;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.connection.CommandSender;
import com.ruskaof.client.util.DataObjectsMaker;
import com.ruskaof.common.commands.*;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.util.DataCantBeSentException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Console {
    private final List<String> listOfCommands = Arrays.asList("add");


    public void start(String script, String username, CommandSender commandSender) throws IOException {
        if (script == null) {
            System.out.println("wtf");
            return;
        } else {
            System.out.println("the script is");
            System.out.println(script);
        }
        LinkedList<String> splitted = new LinkedList<>(Arrays.asList(script.split("\n")));
        DataObjectsMaker dataObjectsMaker = new DataObjectsMaker(splitted, username);
        String input;
        for (int lineInd = 0; lineInd < splitted.size(); lineInd++) {
            System.out.println("executing command");
            input = splitted.get(lineInd);
            if ("exit".equals(input)) {
                break;
            }
            final String[] parsedInp = parseToNameAndArg(input);
            final String commandName = parsedInp[0].trim();
            Serializable commandArg = parsedInp[1];
            String commandArg2 = ""; // only for update command in this case
            if (listOfCommands.contains(commandName)) {
                if ("add".equals(commandName) || "add_if_min".equals(commandName) || "remove_greater".equals(commandName)) {
                    System.out.println("executing add");
                    commandArg = dataObjectsMaker.makeStudyGroup();
                }
                if ("update".equals(commandName)) {
                    commandArg2 = (String) commandArg;
                    commandArg = dataObjectsMaker.makeStudyGroup();
                }
                if ("execute_script".equals(commandName)) {
                    ClientApi.getInstance().executeScript(new File((String) commandArg));
                } else {
                    try {
                        commandSender.sendCommand(new CommandFromClientDto(getCommandObjectByName(commandName, commandArg, commandArg2)));

                    } catch (DataCantBeSentException e) {

                    }
                }
            } else {
                System.out.println("no such command");
            }
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


    private Command getCommandObjectByName(String commandName, Serializable arg, String arg2) {
        Command command;
        switch (commandName) {
            case "add":
                command = new AddCommand(ClientApi.getLogin(), ClientApi.getPassword(), (StudyGroup) arg);
                break;
            case "add_if_min":
                command = new AddIfMinCommand(ClientApi.getLogin(), ClientApi.getPassword(), (StudyGroup) arg);
                break;
            case "clear":
                command = new ClearCommand(ClientApi.getLogin(), ClientApi.getPassword());
                break;
            case "remove_by_id":
                command = new RemoveByIdCommand(ClientApi.getLogin(), ClientApi.getPassword(), Integer.parseInt((String) arg));
                break;
            case "update":
                command = new UpdateCommand(ClientApi.getLogin(), ClientApi.getPassword(), (StudyGroup) arg);
                break;
            default:
                return null;
        }
        return command;
    }
}
