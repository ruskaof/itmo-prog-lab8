package com.ruskaof.client.utility;

import com.ruskaof.client.commands.AddCommand;
import com.ruskaof.client.commands.AddIfMinCommand;
import com.ruskaof.client.commands.ClearCommand;
import com.ruskaof.client.commands.Command;
import com.ruskaof.client.commands.ExecuteScriptCommand;
import com.ruskaof.client.commands.ExitCommand;
import com.ruskaof.client.commands.FilterLessThanSemesterEnumCommand;
import com.ruskaof.client.commands.HelpCommand;
import com.ruskaof.client.commands.HistoryCommand;
import com.ruskaof.client.commands.InfoCommand;
import com.ruskaof.client.commands.MinByIDCommand;
import com.ruskaof.client.commands.PrintAscendingCommand;
import com.ruskaof.client.commands.RemoveByIdCommand;
import com.ruskaof.client.commands.RemoveGreaterCommand;
import com.ruskaof.client.commands.SaveCommand;
import com.ruskaof.client.commands.ShowCommand;
import com.ruskaof.client.commands.UpdateCommand;

import java.util.HashSet;

/**
 * Class for storing commands objects.
 */
public class CommandManager {
    private final HashSet<Command> commands = new HashSet<>();

    public CommandManager(FileManager fileManager, UserInputManager userInputManager,
                          CollectionManager collectionManager, OutputManager outputManager,
                          HistoryManager historyManager) {
        commands.add(new HelpCommand());
        commands.add(new AddCommand(collectionManager, userInputManager, outputManager));
        commands.add(new SaveCommand(fileManager, collectionManager));
        commands.add(new ShowCommand(collectionManager));
        commands.add(new UpdateCommand(collectionManager, userInputManager, outputManager));
        commands.add(new RemoveByIdCommand(collectionManager));
        commands.add(new ClearCommand(collectionManager));
        commands.add(new ExecuteScriptCommand(userInputManager));
        commands.add(new AddIfMinCommand(collectionManager, userInputManager, outputManager));
        commands.add(new RemoveGreaterCommand(collectionManager, userInputManager, outputManager));
        commands.add(new MinByIDCommand(collectionManager));
        commands.add(new FilterLessThanSemesterEnumCommand(collectionManager));
        commands.add(new PrintAscendingCommand(collectionManager));
        commands.add(new InfoCommand(collectionManager));
        commands.add(new ExitCommand());
        commands.add(new HistoryCommand(historyManager));
    }

    public HashSet<Command> getCommands() {
        return commands;
    }
}
