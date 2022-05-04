package com.ruskaof.common.commands;

import com.ruskaof.common.data.User;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

import java.util.Objects;

/**
 * This command doesn't even need a user to use correct login+password because it doesn't check on server side
 */
public class RegisterCommand extends Command {
    private final String[] loginAndPassword;

    public RegisterCommand(String[] loginAndPassword) {
        super("register");
        this.loginAndPassword = loginAndPassword;
    }

    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager,
            String username
    ) {
        historyManager.addNote(this.getName());

        if (dataManager.checkIfUsernameUnique(loginAndPassword[0])) {
            dataManager.addUser(new User(-1, loginAndPassword[1], loginAndPassword[0]));
        } else {
            return new RegisterCommand.RegisterCommandResult(false);
        }


        return new RegisterCommand.RegisterCommandResult(true);
    }

    public static class RegisterCommandResult extends CommandResultDto{
        private final boolean wasRegistered;

        public RegisterCommandResult(boolean wasRegistered) {
            super(wasRegistered ? "New user registered!" : "Username was not unique so the user was not registered.", true);
            this.wasRegistered = wasRegistered;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RegisterCommandResult that = (RegisterCommandResult) o;
            return wasRegistered == that.wasRegistered;
        }

        @Override
        public int hashCode() {
            return Objects.hash(wasRegistered);
        }

        public boolean isWasRegistered() {
            return wasRegistered;
        }

        @Override
        public String toString() {
            return "RegisterCommandResult{" +
                    "wasRegistered=" + wasRegistered +
                    '}';
        }
    }
}
