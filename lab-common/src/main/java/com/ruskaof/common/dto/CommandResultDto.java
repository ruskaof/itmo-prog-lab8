package com.ruskaof.common.dto;

import java.io.Serializable;

public class CommandResultDto implements Serializable {
    private final boolean wasExecutedCorrectly;
    private final String strResult;

    public CommandResultDto(boolean wasExecutedCorrectly, String strResult) {
        this.wasExecutedCorrectly = wasExecutedCorrectly;
        this.strResult = strResult;
    }

    public String getStrResult() {
        return strResult;
    }

    public boolean isWasExecutedCorrectly() {
        return wasExecutedCorrectly;
    }
}
