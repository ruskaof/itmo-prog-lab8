package com.ruskaof.common.dto;

import com.ruskaof.common.commands.Command;

import java.io.Serializable;
import java.util.Objects;

public class CommandFromClientDto implements Serializable {
    private final Command command;

    public CommandFromClientDto(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommandFromClientDto that = (CommandFromClientDto) o;
        return Objects.equals(command, that.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command);
    }
}
