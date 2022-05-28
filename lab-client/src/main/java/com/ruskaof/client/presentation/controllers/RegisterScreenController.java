package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.common.util.DataCantBeSentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterScreenController {
    @FXML
    TextField loginField;
    @FXML
    TextField passwordField;
    @FXML
    TextField repeatedPasswordField;

    @FXML
    public void register(ActionEvent event) throws IOException, DataCantBeSentException {
        if (passwordField.getText().equals(repeatedPasswordField.getText())) {
            ClientApi.getInstance().registerUser(loginField.getText(), passwordField.getText());
            ClientApi.getInstance().setLoginAndPassword(loginField.getText(), passwordField.getText());

            Navigator.navigateToMainScreen(event, getClass());
        }
    }
}
