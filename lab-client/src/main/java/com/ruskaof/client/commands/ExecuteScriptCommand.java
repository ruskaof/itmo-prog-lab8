package com.ruskaof.client.commands;

import com.ruskaof.client.utility.UserInputManager;

import java.io.File;
import java.io.FileNotFoundException;

public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand(UserInputManager userInputManager) {
        super("execute_script");
        this.userInputManager = userInputManager;
    }

    private final UserInputManager userInputManager;

    @Override
    public CommandResult execute(String arg) {
        try {
            userInputManager.connectToFile(new File(arg));
            return new CommandResult(false, true, "The script was executed successfully.");
        } catch (FileNotFoundException e) {
            return new CommandResult(false, false, "The file was not found, the script was not executed.");
        }
    }
}
