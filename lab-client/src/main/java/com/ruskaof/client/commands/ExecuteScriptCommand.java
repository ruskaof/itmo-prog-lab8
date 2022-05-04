package com.ruskaof.client.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.client.util.InputManager;

import java.io.File;
import java.io.IOException;

public class ExecuteScriptCommand {

    private final String arg;

    public ExecuteScriptCommand(String arg) {
        this.arg = arg;
    }

    public void execute(InputManager inputManager) {
        try {
            inputManager.connectToFile(new File(arg));
            new CommandResultDto("Starting to execute script...", true);
        } catch (IOException e) {
            new CommandResultDto("There was a problem opening the file. Check if it is available and you have written it in the command arg correctly.", false);
        } catch (UnsupportedOperationException e) {
            new CommandResultDto(e.getMessage(), false);
        }
    }
}
