package com.ruskaof.common.commands;

import com.ruskaof.common.data.User;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;

import java.util.Objects;

/**
 * Login and password of this command represent login and password of a new user you want
 * to register.
 */
public class RegisterCommand extends Command {

    public RegisterCommand(String loginToRegister, String passwordToRegister) {
        super(loginToRegister, passwordToRegister);
    }

    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager
    ) {
        if (dataManager.checkIfUsernameUnique(getUsername())) {
            dataManager.addUser(new User(-1, getUsername(), getPassword()));
            return new RegisterCommand.RegisterCommandResult(true);
        } else {
            return new RegisterCommand.RegisterCommandResult(false);
        }
    }

    public static class RegisterCommandResult extends CommandResultDto {
        private final boolean wasRegistered;

        public RegisterCommandResult(boolean wasRegistered) {
            super(wasRegistered);
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

        public boolean wasRegistered() {
            return wasRegistered;
        }

        @Override
        public String toString() {
            return "RegisterCommandResult{"
                    + "wasRegistered=" + wasRegistered
                    + '}';
        }
    }
}
