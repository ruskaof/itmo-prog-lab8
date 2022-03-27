package com.ruskaof.client.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.InputManager;

import java.io.File;
import java.io.IOException;

public class ExecuteScriptCommand {

    private final String arg;

    public ExecuteScriptCommand(String arg) {
        this.arg = arg;
    }

    public CommandResultDto execute(InputManager inputManager) {
        try {
            inputManager.connectToFile(new File(arg));
            return new CommandResultDto("Starting to execute script...");
        } catch (IOException e) {
            return new CommandResultDto("There was a problem opening the file. Check if it is available and you have written it in the command arg correctly.");
        } catch (UnsupportedOperationException e) {
            return new CommandResultDto(e.getMessage());
        }
    }
}
