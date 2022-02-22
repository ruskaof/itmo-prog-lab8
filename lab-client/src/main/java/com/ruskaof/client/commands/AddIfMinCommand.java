package com.ruskaof.client.commands;

import com.ruskaof.client.utility.CollectionManager;
import com.ruskaof.client.utility.OutputManager;
import com.ruskaof.client.utility.UserInputManager;


public class AddIfMinCommand extends Command {
    public AddIfMinCommand(CollectionManager collectionManager, UserInputManager userInputManager, OutputManager outputManager) {
        super("add_if_min");
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
        this.outputManager = outputManager;
    }

    final private OutputManager outputManager;
    final private CollectionManager collectionManager;
    final private UserInputManager userInputManager;

    @Override
    public CommandResult execute(String arg) {
        return new CommandResult(false, true, "fdsafdsaf");
    }
}
