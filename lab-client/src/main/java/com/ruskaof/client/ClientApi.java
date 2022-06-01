package com.ruskaof.client;

import com.ruskaof.client.connection.ConnectionManager;
import com.ruskaof.client.connection.ConnectionManagerTCP;
import com.ruskaof.client.util.OutputManager;
import com.ruskaof.common.commands.*;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.util.DataCantBeSentException;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public final class ClientApi {
    private static final OutputManager OUTPUT_MANAGER = new OutputManager(System.out);
    private static final Collection<String> LIST_OF_COMMANDS = new HashSet<>();
    private static ConnectionManager connectionManager;
    private static boolean serverInfoWasInitialised = false;
    private static ClientApi instance;
    private static String login;
    private static String password;

    private ClientApi() {
    }

    public static ClientApi getInstance() {
        if (instance == null) {
            instance = new ClientApi();
        }
        return instance;
    }


    public void init(int enteredPort, String enteredIp) throws IOException {
//        connectionManager = new ConnectionManagerUDP(0, enteredPort, "0.0.0.0", enteredIp, OUTPUT_MANAGER);
        connectionManager = new ConnectionManagerTCP(enteredPort, enteredIp);
        serverInfoWasInitialised = true;
    }

    public List<StudyGroup> getCurrentData() throws DataCantBeSentException {
        checkConnection();
        return (
                (ShowCommand.ShowCommandResult)
                        connectionManager.sendCommand(
                                new CommandFromClientDto(new ShowCommand(password, login))
                        )
        ).getData();
    }

    /**
     * @return true if login and password are correct
     */
    public boolean setLoginAndPassword(String newLogin, String newPassword) throws DataCantBeSentException {
        checkConnection();
        login = newLogin;
        password = newPassword;
        return (
                (ValidateCommand.ValidateCommandResult)
                        connectionManager.sendCommand(
                                new CommandFromClientDto(
                                        new ValidateCommand(newLogin, newPassword)
                                )
                        )
        ).isLoginAndPasswordCorrect();
    }

    public boolean registerUser(String login, String password) throws DataCantBeSentException {
        return ((RegisterCommand.RegisterCommandResult)
                connectionManager.sendCommand(
                        new CommandFromClientDto(new RegisterCommand(login, password))
                )
        ).wasRegistered();
    }

    public void update(StudyGroup newStudyGroup) throws DataCantBeSentException {
        connectionManager.sendCommand(new CommandFromClientDto(new UpdateCommand(login, password, newStudyGroup)));
    }

    public static String getLogin() {
        return login;
    }

    public void add(StudyGroup newStudyGroup) throws DataCantBeSentException {
        connectionManager.sendCommand(new CommandFromClientDto(new AddCommand(login, password, newStudyGroup)));
    }

    private void checkConnection() {
        if (!serverInfoWasInitialised) {
            throw new RuntimeException("You must connect to the server first");
        }
    }

    public void addIfMin(StudyGroup newStudyGroup) throws DataCantBeSentException {
        connectionManager.sendCommand(new CommandFromClientDto(new AddIfMinCommand(login, password, newStudyGroup)));
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
}
