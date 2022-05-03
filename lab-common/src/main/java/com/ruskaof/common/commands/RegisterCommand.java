package com.ruskaof.common.commands;

import com.ruskaof.common.data.User;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

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
            CollectionManager collectionManager,
            HistoryManager historyManager,
            String username
    ) {
        historyManager.addNote(this.getName());

        if (collectionManager.checkIfUsernameUnique(loginAndPassword[0])) {
            collectionManager.addUser(new User(-1, loginAndPassword[1], loginAndPassword[0]));
        } else {
            return new CommandResultDto("The username is not unique");
        }


        return new CommandResultDto("New user added!");
    }
}
