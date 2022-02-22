package com.ruskaof.client.commands;

public class CommandResult {
    public CommandResult(boolean exit, boolean executed, String output) {
        this.exit = exit;
        this.executed = executed;
        this.output = output;
    }

    private final boolean exit;
    private final boolean executed;
    private final String output;

    public boolean isExit() {
        return exit;
    }

    public boolean isExecuted() {
        return executed;
    }

    public String getOutput() {
        return output;
    }
}
