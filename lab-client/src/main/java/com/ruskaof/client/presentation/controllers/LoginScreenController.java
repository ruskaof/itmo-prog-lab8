package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.util.Localisator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginScreenController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button enterBTN;
    @FXML
    private Button registerBTN;


    public void login(ActionEvent event) throws IOException {

        if (
                ClientApi.getInstance().setLoginAndPassword(loginField.getText(), passwordField.getText())
        ) {
            Navigator.navigateToMainScreen(event, getClass());
            ClientApi.getInstance().startUpdating();

        } else {
            Localisator localisator = new Localisator();
            errorLabel.setText(localisator.get("error.incorrect_login"));
            errorLabel.setVisible(true);
        }


    }

    @FXML
    void initialize() {
        setLocalisation();
    }

    private void setLocalisation() {
        Localisator localisator = new Localisator();
        loginField.setPromptText(localisator.login);
        passwordField.setPromptText(localisator.password);
        enterBTN.setText(localisator.enter);
        registerBTN.setText(localisator.register);
    }


    public void register(ActionEvent event) throws IOException {
        Navigator.navigateToRegisterScreen(event, getClass());
    }
}
