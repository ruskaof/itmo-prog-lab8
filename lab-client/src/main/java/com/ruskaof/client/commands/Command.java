package com.ruskaof.client.commands;

/**
 * This is an abstract class for all the commands.
 */
public abstract class Command {
    /**
     * String that is used to execute a command in console.
     */
    private final String name;

    protected Command(String name) {
        this.name = name;
    }


    /**
     * Each Command class represents a command and has an execute() method to execute it.
     *
     * @param arg argument of a command
     * @return returns a command result
     */
    public abstract CommandResult execute(String arg);

    public String getName() {
        return name;
    }
}
