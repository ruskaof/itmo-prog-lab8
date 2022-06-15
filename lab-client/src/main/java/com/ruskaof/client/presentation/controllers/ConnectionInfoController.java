package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.util.Localisation;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
    @FXML
    private ComboBox<String> languageCB;


    public void connect(ActionEvent event) {
        if (languageCB.getValue() != null) {
            try {
                ClientApi.getInstance().init(Integer.parseInt(portField.getText()), ipField.getText());
                ClientApi.setLocalisation(parseLocal(languageCB.getValue()));
                Navigator.navigateToLoginScreen(event, this.getClass());
            } catch (IllegalArgumentException e) {
                errorLabel.setText("Invalid port value");
            } catch (IOException e) {
                errorLabel.setText("Could not connect to server ");
            }
        } else {
            errorLabel.setText("please choose lang");
        }

    }

    @FXML
    public void initialize() {
        languageCB.setItems(FXCollections.observableArrayList(
                "русский",
                "Română",
                "Français",
                "español"
        ));
    }

    private Localisation parseLocal(String s) {
        switch (s) {
            case "русский":
                return Localisation.RUSSIAN;
            case "Română":
                return Localisation.ROMANIAN;
            case "Français":
                return Localisation.FRENCH;
            case "español":
                return Localisation.SPANISH;
        }
        return null;
    }
}
