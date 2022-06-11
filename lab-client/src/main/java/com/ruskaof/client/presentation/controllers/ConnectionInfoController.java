package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ConnectionInfoController {
    @FXML
    private TextField portField;
    @FXML
    private TextField ipField;
    @FXML
    private Label errorLabel;

    public void connect(ActionEvent event) throws IOException {
        try {
            ClientApi.getInstance().init(Integer.parseInt(portField.getText()), ipField.getText());
            Navigator.navigateToLoginScreen(event, this.getClass());
        } catch (IllegalArgumentException e) {
            errorLabel.setText("Invalid port value");
        } catch (IOException e) {
            errorLabel.setText("Could not connect to server ");
        }

    }

}
