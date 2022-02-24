package com.ruskaof.client.commands;

import com.ruskaof.client.utility.UserInputManager;

import java.io.File;
import java.io.FileNotFoundException;

public class ExecuteScriptCommand extends Command {

    private final UserInputManager userInputManager;

    public ExecuteScriptCommand(UserInputManager userInputManager) {
        super("execute_script");
        this.userInputManager = userInputManager;
    }

    @Override
    public CommandResult execute(String arg) {
        try {
            userInputManager.connectToFile(new File(arg));
            return new CommandResult(false, "Starting to execute script...");
        } catch (FileNotFoundException e) {
            return new CommandResult(false, "The file was not found, the script was not executed.");
        }
    }
}
