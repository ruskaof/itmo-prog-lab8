package com.ruskaof.common.commands;

import com.ruskaof.common.data.User;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.Random;

/**
 * Login and password of this command represent login and password of a new user you want
 * to register.
 */
public class RegisterCommand extends Command implements LoginNeedlessCommand {

    public RegisterCommand(String loginToRegister, String passwordToRegister) {
        super(loginToRegister, passwordToRegister, "register");
    }

    private Color generateColor() {
        final Random random = new Random();
        return Color.color(random.nextFloat(), random.nextFloat(), random.nextFloat());
    }

    @Override
    public CommandResultDto execute(
            DataManager dataManager,
            HistoryManager historyManager
    ) {
        Color color = generateColor();
        if (dataManager.checkIfUsernameUnique(getUsername())) {
            dataManager.addUser(new User(-1, getPassword(), getUsername(), color.toString()));
            return new RegisterCommand.RegisterCommandResult(true, color.toString());
        } else {
            return new RegisterCommand.RegisterCommandResult(false, color.toString());
        }
    }
    public static class RegisterCommandResult extends CommandResultDto {
        private final boolean wasRegistered;

        private final String  color;

        public RegisterCommandResult(boolean wasRegistered, String color) {
            super(wasRegistered, "");
            this.wasRegistered = wasRegistered;
            this.color = color;
        }

        public String getColor() {
            return color;
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
