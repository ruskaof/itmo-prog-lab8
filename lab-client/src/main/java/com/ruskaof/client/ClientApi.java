package com.ruskaof.client;

import com.ruskaof.client.connection.CommandSender;
import com.ruskaof.client.connection.CommandSenderTCP;
import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.client.logic.Console;
import com.ruskaof.client.util.InputManager;
import com.ruskaof.client.util.Localisation;
import com.ruskaof.client.util.Localisator;
import com.ruskaof.common.commands.AddCommand;
import com.ruskaof.common.commands.AddIfMinCommand;
import com.ruskaof.common.commands.RegisterCommand;
import com.ruskaof.common.commands.RemoveByIdCommand;
import com.ruskaof.common.commands.ShowCommand;
import com.ruskaof.common.commands.UpdateCommand;
import com.ruskaof.common.commands.ValidateCommand;
import com.ruskaof.common.data.StudyGroup;
import com.ruskaof.common.dto.CommandFromClientDto;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public final class ClientApi {
    private static final long UPDATE_PERIOD = 3000;
    private static volatile ClientApi instance;
    private Locale locale;
    private volatile List<StudyGroupRow> currentData = new ArrayList<>();
    private CommandSender commandSender;
    private CommandSender updater;
    private boolean serverInfoWasInitialised = false;
    private int serverPort;
    private String serverIp;
    private String login;
    private String password;
    private Color color;



    private ClientApi() {

    }

    public void setColor(Color color) {
        this.color = color;
    }



    public Locale getLocale() {
        return locale;
    }

    public String getPassword() {
        return password;
    }

    public void setLocalisation(Localisation localisation) {
        switch (localisation) {
            case SPANISH:
                locale = new Locale("es");
                break;


            case FRENCH:
                locale = Locale.FRENCH;
                break;

            case RUSSIAN:
                locale = new Locale("ru");
                break;

            case ROMANIAN:
                locale = new Locale("ro");
                break;


            default:
                throw new RuntimeException();
        }
    }


    public String getLogin() {
        return login;
    }

    public static ClientApi getInstance() {
        if (instance == null) {
            synchronized (ClientApi.class) {
                if (instance == null) {
                    instance = new ClientApi();
                }
            }
        }
        return instance;
    }


    public void startUpdating() throws IOException {
        updater = new CommandSenderTCP(serverPort, serverIp);
        final Thread updatingThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(UPDATE_PERIOD);
                    currentData =
                            ((ShowCommand.ShowCommandResult) updater.sendCommand(new CommandFromClientDto(new ShowCommand(login, password)))).getData()
                                    .stream().map(StudyGroupRow::mapStudyGroupToRow).collect(Collectors.toList());
                } catch (InterruptedException e) {
                    break;
                } catch (IOException e) {
                    System.out.println("Server disconnect");
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

    public List<StudyGroupRow> getCurrentData() {
        return currentData;
    }

    public void updateData() {
        checkConnection();
        try {
            currentData =
                    ((ShowCommand.ShowCommandResult) commandSender.sendCommand(new CommandFromClientDto(new ShowCommand(login, password)))).getData()
                            .stream().map(StudyGroupRow::mapStudyGroupToRow).collect(Collectors.toList());
        } catch (IOException e) {
            notifyDisconnect();
        } catch (ClassCastException e) {
            System.out.println("Received unknown info from server");
        }
    }

    /**
     * @return true if login and password are correct
     */
    public boolean setLoginAndPassword(String newLogin, String newPassword) {
        checkConnection();
        login = newLogin;
        password = newPassword;
        final boolean result;
        try {
            ValidateCommand.ValidateCommandResult validateCommandResult = (ValidateCommand.ValidateCommandResult)
                    commandSender.sendCommand(
                            new CommandFromClientDto(
                                    new ValidateCommand(newLogin, newPassword)
                            )
                    );
            result = validateCommandResult
                    .isLoginAndPasswordCorrect();
            System.out.println(result);
            color = Color.valueOf(validateCommandResult.getColor());
        } catch (IOException e) {
            notifyDisconnect();
            return false;
        }
        return result;
    }

    public boolean registerUser(String newLogin, String newPassword) {
        try {
            RegisterCommand.RegisterCommandResult registerCommandResult = ((RegisterCommand.RegisterCommandResult)
                    commandSender.sendCommand(
                            new CommandFromClientDto(new RegisterCommand(newLogin, newPassword))
                    )
            );
            color = Color.valueOf(registerCommandResult.getColor());
            return registerCommandResult.wasRegistered();
        } catch (IOException e) {
            notifyDisconnect();
            return false;
        }
    }

    public void update(StudyGroup newStudyGroup) {
        try {
            System.out.println("updating");
            commandSender.sendCommand(new CommandFromClientDto(new UpdateCommand(login, password, newStudyGroup, null)));
            System.out.println("gotovo");
        } catch (IOException e) {
            notifyDisconnect();
        }
    }

    private void checkConnection() {
        if (!serverInfoWasInitialised) {
            throw new RuntimeException("You must connect to the server first");
        }
    }

    public void add(StudyGroup newStudyGroup) {
        try {
            commandSender.sendCommand(new CommandFromClientDto(new AddCommand(login, password, newStudyGroup)));
        } catch (IOException e) {
            notifyDisconnect();
        }
    }

    public void addIfMin(StudyGroup newStudyGroup) {
        try {
            commandSender.sendCommand(new CommandFromClientDto(new AddIfMinCommand(login, password, newStudyGroup)));
        } catch (IOException e) {
            notifyDisconnect();
        }
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

    public void removeById(int id) {
        try {
            commandSender.sendCommand(new CommandFromClientDto(new RemoveByIdCommand(login, password, id)));
        } catch (IOException e) {
            notifyDisconnect();
        }
    }

    public String executeScript(File selectedFile) throws IOException {
        return new Console(new InputManager(), getLogin(), getPassword()).start(commandSender, selectedFile.getAbsolutePath());
    }

    public void notifyDisconnect() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(new Localisator().get("error.disconnection"));
        errorAlert.showAndWait();

        try {
            commandSender = new CommandSenderTCP(serverPort, serverIp);
        } catch (IOException e) {
            notifyDisconnect();
        }
    }

    public Color getColor() {
        return color;
    }
}
