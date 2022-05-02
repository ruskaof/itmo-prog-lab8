package com.ruskaof.common.commands;

import com.ruskaof.common.data.User;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.CollectionManager;
import com.ruskaof.common.util.HistoryManager;

public class RegisterCommand extends Command {

    public RegisterCommand(String name) {
        super(name, "register");
    }

    @Override
    public CommandResultDto execute(CollectionManager collectionManager, HistoryManager historyManager) {
        final String login = ((String) arg).split("\\.")[0];
        final String password = ((String) arg).split("\\.")[1];
        collectionManager.addUser(new User(-1, password, login));

        return new CommandResultDto("New user added!");
    }
}
