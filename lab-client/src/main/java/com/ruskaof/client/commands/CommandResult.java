package com.ruskaof.client.commands;

public class CommandResult {
    public CommandResult(boolean exit, String output) {
        this.exit = exit;
        this.output = output;
    }

    private final boolean exit;
    private final String output;

    public boolean isExit() {
        return exit;
    }

    public String getOutput() {
        return output;
    }
}
