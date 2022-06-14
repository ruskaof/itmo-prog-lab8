package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.common.util.DataCantBeSentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ResourceBundle;

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


    public void login(ActionEvent event) throws IOException, DataCantBeSentException {

        if (
                ClientApi.getInstance().setLoginAndPasswordAndStartUpdating(loginField.getText(), passwordField.getText())
        ) {
            Navigator.navigateToMainScreen(event, getClass());
            ClientApi.startUpdating();

        } else {
            errorLabel.setText(ResourceBundle.getBundle("labels", ClientApi.getLocale()).getString("error.incorrect_login"));
            errorLabel.setVisible(true);
        }


    }

    @FXML
    void initialize() {
        setLocalisation();
    }

    private void setLocalisation() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", ClientApi.getLocale());
        loginField.setPromptText(resourceBundle.getString("label.login"));
        passwordField.setPromptText(resourceBundle.getString("label.password"));
        enterBTN.setText(resourceBundle.getString("button.enter"));
        registerBTN.setText(resourceBundle.getString("button.register"));
    }


    public void register(ActionEvent event) throws IOException, DataCantBeSentException {
        Navigator.navigateToRegisterScreen(event, getClass());
    }
}
