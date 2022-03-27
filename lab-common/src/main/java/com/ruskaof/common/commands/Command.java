package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

import java.io.Serializable;


public abstract class Command implements Serializable {

    protected final Serializable arg;
    private final String name;

    protected Command(Serializable arg, String name) {
        this.arg = arg;
        this.name = name;
    }

    public abstract CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    );

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Command{"
                + "name='" + name + '\''
                + ", arg=" + arg
                + '}';
    }
}
