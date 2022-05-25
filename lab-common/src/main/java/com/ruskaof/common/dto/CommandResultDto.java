package com.ruskaof.common.dto;

import java.io.Serializable;

public class CommandResultDto implements Serializable {
    private final boolean wasExecutedCorrectly;

    public CommandResultDto(boolean wasExecutedCorrectly) {
        this.wasExecutedCorrectly = wasExecutedCorrectly;
    }

    public boolean isWasExecutedCorrectly() {
        return wasExecutedCorrectly;
    }
}
