package com.ruskaof.common.dto;

import com.ruskaof.common.commands.Command;

import java.io.Serializable;

public class CommandFromClientDto implements Serializable {
    private final Command command;



    public CommandFromClientDto(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
