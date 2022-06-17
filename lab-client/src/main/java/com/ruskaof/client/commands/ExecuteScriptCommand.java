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
            new CommandResultDto(true, "Your file was executed");
        } catch (IOException | UnsupportedOperationException e) {
            new CommandResultDto( false, "could not execute");
        }
    }
}
