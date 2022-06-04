package com.ruskaof.client;

import com.ruskaof.client.connection.CommandSender;
import com.ruskaof.client.connection.CommandSenderTCP;
import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.common.commands.*;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.util.DataCantBeSentException;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public final class ClientApi {
    private static final Collection<String> LIST_OF_COMMANDS = new HashSet<>();
    private static CommandSender commandSender;
    private static CommandSender updater;
    private static boolean serverInfoWasInitialised = false;
    private static int serverPort;
    private static String serverIp;
    private static ClientApi instance;
    private static String login;
    private static String password;
    private volatile static List<StudyGroupRow> currentData;


    private ClientApi() {

    }

    public static ClientApi getInstance() {
        if (instance == null) {
            instance = new ClientApi();
        }
        return instance;
    }

    public static void startUpdating() throws IOException {
        updater = new CommandSenderTCP(serverPort, serverIp);
        final Thread updatingThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(3000);
                    System.out.println("update");
                    currentData =
                            ((ShowCommand.ShowCommandResult) updater.sendCommand(new CommandFromClientDto(new ShowCommand(login, password)))).getData()
                                    .stream().map(StudyGroupRow::mapStudyGroupToRow).collect(Collectors.toList());
                } catch (DataCantBeSentException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        updatingThread.setDaemon(true);
        updatingThread.start();
    }

    public void init(int enteredPort, String enteredIp) throws IOException {
        serverIp = enteredIp;
        serverPort = enteredPort;

        commandSender = new CommandSenderTCP(enteredPort, enteredIp);
        serverInfoWasInitialised = true;

    }

    public List<StudyGroupRow> getCurrentData() throws DataCantBeSentException {
        return currentData;
    }

    public void updateData() throws DataCantBeSentException {
        checkConnection();
        currentData = (
                (ShowCommand.ShowCommandResult)
                        commandSender.sendCommand(
                                new CommandFromClientDto(new ShowCommand(password, login))
                        )
        ).getData().stream().map(StudyGroupRow::mapStudyGroupToRow).collect(Collectors.toList());
    }

    /**
     * @return true if login and password are correct
     */
    public boolean setLoginAndPasswordAndStartUpdating(String newLogin, String newPassword) throws DataCantBeSentException, IOException {
        checkConnection();
        login = newLogin;
        password = newPassword;
        final boolean result = (
                (ValidateCommand.ValidateCommandResult)
                        commandSender.sendCommand(
                                new CommandFromClientDto(
                                        new ValidateCommand(newLogin, newPassword)
                                )
                        )
        ).isLoginAndPasswordCorrect();
        return result;
    }

    public boolean registerUser(String login, String password) throws DataCantBeSentException {
        return ((RegisterCommand.RegisterCommandResult)
                commandSender.sendCommand(
                        new CommandFromClientDto(new RegisterCommand(login, password))
                )
        ).wasRegistered();
    }

    public static String getLogin() {
        return login;
    }

    public void update(StudyGroup newStudyGroup) throws DataCantBeSentException {
        commandSender.sendCommand(new CommandFromClientDto(new UpdateCommand(login, password, newStudyGroup)));
    }

    private void checkConnection() {
        if (!serverInfoWasInitialised) {
            throw new RuntimeException("You must connect to the server first");
        }
    }

    public void add(StudyGroup newStudyGroup) throws DataCantBeSentException {
        commandSender.sendCommand(new CommandFromClientDto(new AddCommand(login, password, newStudyGroup)));
    }

    private void initCommandList() {
        ClientApi.LIST_OF_COMMANDS.add("add");
        ClientApi.LIST_OF_COMMANDS.add("add_if_min");
        ClientApi.LIST_OF_COMMANDS.add("clear");
        ClientApi.LIST_OF_COMMANDS.add("exit");
        ClientApi.LIST_OF_COMMANDS.add("help");
        ClientApi.LIST_OF_COMMANDS.add("history");
        ClientApi.LIST_OF_COMMANDS.add("info");
        ClientApi.LIST_OF_COMMANDS.add("min_by_id");
        ClientApi.LIST_OF_COMMANDS.add("print_ascending");
        ClientApi.LIST_OF_COMMANDS.add("remove_by_id");
        ClientApi.LIST_OF_COMMANDS.add("remove_greater");
        ClientApi.LIST_OF_COMMANDS.add("show");
        ClientApi.LIST_OF_COMMANDS.add("update");
        ClientApi.LIST_OF_COMMANDS.add("execute_script");
        ClientApi.LIST_OF_COMMANDS.add("filter_less_than_semester_enum");
        ClientApi.LIST_OF_COMMANDS.add("print_ascending");
        ClientApi.LIST_OF_COMMANDS.add("register");
    }

    public void addIfMin(StudyGroup newStudyGroup) throws DataCantBeSentException {
        commandSender.sendCommand(new CommandFromClientDto(new AddIfMinCommand(login, password, newStudyGroup)));
    }
}
