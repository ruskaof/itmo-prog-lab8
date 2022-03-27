package com.ruskaof.common.dto;

import java.io.Serializable;

public class CommandResultDto implements Serializable {
    private final String output;

    public CommandResultDto(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "CommandResult{"
                + "output='" + output + '\''
                + '}';
    }
}
