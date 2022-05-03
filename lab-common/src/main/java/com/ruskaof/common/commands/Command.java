package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

import java.io.Serializable;


public abstract class Command implements Serializable {
    private final String name;

    protected Command(String name) {
        this.name = name;
    }

    public abstract CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager,
            String username
    );

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Command{"
                + "name='" + name + '\''
                + '}';
    }
}
