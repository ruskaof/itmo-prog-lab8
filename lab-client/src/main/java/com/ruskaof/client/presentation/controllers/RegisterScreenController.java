package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.common.util.DataCantBeSentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ResourceBundle;

public class RegisterScreenController {
    @FXML
    TextField loginField;
    @FXML
    TextField passwordField;
    @FXML
    TextField repeatedPasswordField;
    @FXML
    Button registerBTN;

    @FXML
    public void register(ActionEvent event) throws IOException, DataCantBeSentException {
        if (passwordField.getText().equals(repeatedPasswordField.getText())) {
            if (ClientApi.getInstance().registerUser(loginField.getText(), passwordField.getText())) {
                ClientApi.getInstance().setLoginAndPasswordAndStartUpdating(loginField.getText(), passwordField.getText());

                ClientApi.getInstance().startUpdating();
                Navigator.navigateToMainScreen(event, getClass());
            }
        }
    }

    @FXML
    public void initialize() {
        setLocalisation();
    }

    private void setLocalisation() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", ClientApi.getLocale());
        registerBTN.setText(resourceBundle.getString("button.register"));
        loginField.setPromptText(resourceBundle.getString("label.login"));
        passwordField.setPromptText(resourceBundle.getString("label.password"));
        repeatedPasswordField.setPromptText(resourceBundle.getString("label.password"));
    }
}
