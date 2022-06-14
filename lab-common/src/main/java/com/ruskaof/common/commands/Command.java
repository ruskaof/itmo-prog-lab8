package com.ruskaof.common.commands;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

import java.io.Serializable;
import java.util.Objects;


public abstract class Command implements Serializable {
    private final String username;
    private final String password;

    public Command(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Command command = (Command) o;
        return Objects.equals(username, command.username) && Objects.equals(password, command.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Command{"
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + '}';
    }

    public abstract CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager
    );
}
