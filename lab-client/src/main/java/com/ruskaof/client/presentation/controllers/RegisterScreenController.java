package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.util.Localisator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Label errorLabel;

    @FXML
    public void register(ActionEvent event) throws IOException {
        Localisator localisator = new Localisator();
        if (passwordField.getText().equals(repeatedPasswordField.getText())) {
            if (ClientApi.getInstance().registerUser(loginField.getText(), passwordField.getText())) {
                ClientApi.getInstance().setLoginAndPassword(loginField.getText(), passwordField.getText());

                ClientApi.getInstance().startUpdating();
                Navigator.navigateToMainScreen(event, getClass());
            } else {
                errorLabel.setText(localisator.get("error.login_already_present"));
            }
        } else {
            errorLabel.setText(localisator.get("error.passwords_not_same"));
        }
    }

    @FXML
    public void initialize() {
        setLocalisation();
    }

    private void setLocalisation() {
        final Localisator localisator = new Localisator();
        registerBTN.setText(localisator.register);
        loginField.setPromptText(localisator.login);
        passwordField.setPromptText(localisator.password);
        repeatedPasswordField.setPromptText(localisator.password);
    }
}
