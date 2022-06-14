package com.ruskaof.client;

import com.ruskaof.client.connection.CommandSender;
import com.ruskaof.client.connection.CommandSenderTCP;
import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.client.logic.Console;
import com.ruskaof.client.util.Localisation;
import com.ruskaof.common.commands.*;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.util.DataCantBeSentException;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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

    private static Stage stage;

    public static void setStage(Stage stage) {
        ClientApi.stage = stage;
    }

    private static Locale locale;

    public static Locale getLocale() {
        return locale;
    }

    public static String getPassword() {
        return password;
    }

    public static void setLocalisation(Localisation localisation) {
        switch (localisation) {
            case SPANISH: {
                ClientApi.locale = new Locale("es");
                break;
            }

            case FRENCH: {
                ClientApi.locale = Locale.FRENCH;
                break;
            }
            case RUSSIAN: {
                ClientApi.locale = new Locale("ru");
                break;
            }
            case ROMANIAN: {
                ClientApi.locale = new Locale("ro");
                break;
            }
        }
    }

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

    public void addIfMin(StudyGroup newStudyGroup) throws DataCantBeSentException {
        commandSender.sendCommand(new CommandFromClientDto(new AddIfMinCommand(login, password, newStudyGroup)));
    }

    public static String readFileAsString(String fileName) {
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    public void removeById(int id) throws DataCantBeSentException {
        commandSender.sendCommand(new CommandFromClientDto(new RemoveByIdCommand(login, password, id)));
    }

    public void executeScript(File selectedFile) throws IOException {

        new Console().start(readFileAsString(selectedFile.getAbsolutePath()), getLogin(), commandSender);
    }

    public void notifyDisconnect() {

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Disconnected from server");
        errorAlert.showAndWait();

        System.exit(1);
    }
}
