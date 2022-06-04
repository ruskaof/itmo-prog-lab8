package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.common.util.DataCantBeSentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    public void login(ActionEvent event) throws IOException, DataCantBeSentException {
        if (
                ClientApi.getInstance().setLoginAndPasswordAndStartUpdating(loginField.getText(), passwordField.getText())
        ) {
            ClientApi.getInstance().startUpdating();
            Navigator.navigateToMainScreen(event, getClass());
            ClientApi.startUpdating();
        } else {
            errorLabel.setVisible(true);
        }

    }

    public void register(ActionEvent event) throws IOException, DataCantBeSentException {
        Navigator.navigateToRegisterScreen(event, getClass());
    }
}
