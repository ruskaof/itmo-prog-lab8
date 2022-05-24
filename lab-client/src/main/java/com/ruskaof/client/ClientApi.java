package com.ruskaof.client;

import com.ruskaof.client.connection.ConnectionManager;
import com.ruskaof.client.util.OutputManager;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandFromClientDto;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public final class ClientApi {
    private static final OutputManager OUTPUT_MANAGER = new OutputManager(System.out);
    private static final Collection<String> LIST_OF_COMMANDS = new HashSet<>();
    private static int serverPort;
    private static String serverIp;
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


    public static void init(int enteredPort, String enteredIp) {

        serverPort = enteredPort;
        serverIp = enteredIp;

        connectionManager = new ConnectionManager(0, serverPort, "0.0.0.0", serverIp, OUTPUT_MANAGER);
        serverInfoWasInitialised = true;

    }

    public static List<StudyGroup> getCurrentData() {
        return connectionManager.sendCommand(new CommandFromClientDto());
    }

    /**
     *
     * @param newLogin
     * @param newPassword
     * @return true if login and password are correct
     */
    public static boolean setLoginAndPassword(String newLogin, String newPassword) {
        login = newLogin;
        password = newPassword;
        connectionManager.sendCommand(new CommandFromClientDto())
    }



    private static void initCommandList() {
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
