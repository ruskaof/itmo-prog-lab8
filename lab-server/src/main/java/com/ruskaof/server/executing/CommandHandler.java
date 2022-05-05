package com.ruskaof.server.executing;

import com.ruskaof.common.commands.PrivateAccessedStudyGroupCommand;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataManager;
import com.ruskaof.common.util.HistoryManager;
import com.ruskaof.common.util.Pair;
import com.ruskaof.common.util.State;
import org.slf4j.Logger;

import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

public class CommandHandler {
    private final Queue<Pair<CommandFromClientDto, SocketAddress>> queueToBeExecuted;
    private final Queue<Pair<CommandResultDto, SocketAddress>> queueToBeSent;
    private final Logger logger;
    private final DataManager dataManager;
    private final HistoryManager historyManager;

    public CommandHandler(
            Queue<Pair<CommandFromClientDto, SocketAddress>> queueToBeExecuted,
            Queue<Pair<CommandResultDto, SocketAddress>> queueToBeSent,
            Logger logger,
            DataManager dataManager,
            HistoryManager historyManager) {
        this.queueToBeExecuted = queueToBeExecuted;
        this.queueToBeSent = queueToBeSent;
        this.logger = logger;
        this.dataManager = dataManager;
        this.historyManager = historyManager;
    }

    public void startToHandleCommands(
            State<Boolean> isWorkingState,
            ExecutorService threadPool
    ) {
        Runnable startCheckingForAvailableCommandsToRun = new Runnable() {
            @Override
            public void run() {
                while (isWorkingState.getValue()) {
                    if (!queueToBeExecuted.isEmpty()) {
                        Pair<CommandFromClientDto, SocketAddress> pairOfCommandAndClientAddress = queueToBeExecuted.poll();
                        Runnable executeFirstCommandTack = new Runnable() {
                            @Override
                            public void run() {
                                logger.info("Starting to execute a new command");
                                assert pairOfCommandAndClientAddress != null;
                                CommandFromClientDto commandFromClientDto = pairOfCommandAndClientAddress.getFirst();
                                SocketAddress clientAddress = pairOfCommandAndClientAddress.getSecond();

                                executeWithValidation(commandFromClientDto, clientAddress);

                                logger.info("Successfully executed the command command");


                            }
                        };
                        threadPool.submit(executeFirstCommandTack);
                    }
                }
            }
        };
        threadPool.submit(startCheckingForAvailableCommandsToRun);
    }

    private void executeWithValidation(CommandFromClientDto commandFromClientDto, SocketAddress clientAddress) {
        if (dataManager.validateUser(commandFromClientDto.getLogin(), commandFromClientDto.getPassword())) {
            if (commandFromClientDto.getCommand() instanceof PrivateAccessedStudyGroupCommand) {
                final int id = ((PrivateAccessedStudyGroupCommand) commandFromClientDto.getCommand()).getStudyGroupId();
                if (dataManager.validateOwner(commandFromClientDto.getLogin(), id)) {
                    queueToBeSent.add(new Pair<>(commandFromClientDto.getCommand().execute(dataManager, historyManager, commandFromClientDto.getLogin()), clientAddress));
                } else {
                    queueToBeSent.add(new Pair<>(new CommandResultDto("You are not the owner of the object so you can't do anything with it", true), clientAddress));
                }
                return;
            }
            queueToBeSent.add(new Pair<>(commandFromClientDto.getCommand().execute(dataManager, historyManager, commandFromClientDto.getLogin()), clientAddress));
        } else {
            queueToBeSent.add(new Pair<>(new CommandResultDto("Invalid login or password. Command was not executed", false), clientAddress));
        }
    }

}
