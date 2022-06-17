package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.util.Localisator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterScreenController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField repeatedPasswordField;
    @FXML
    private Button registerBTN;

    @FXML
    public void register(ActionEvent event) throws IOException {
        if (passwordField.getText().equals(repeatedPasswordField.getText())) {
            if (ClientApi.getInstance().registerUser(loginField.getText(), passwordField.getText())) {
                ClientApi.getInstance().setLoginAndPassword(loginField.getText(), passwordField.getText());

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
        final Localisator localisator = new Localisator();
        registerBTN.setText(localisator.get("button.register"));
        loginField.setPromptText(localisator.get("label.login"));
        passwordField.setPromptText(localisator.get("label.password"));
        repeatedPasswordField.setPromptText(localisator.get("label.password"));
    }
}
