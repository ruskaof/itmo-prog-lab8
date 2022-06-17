package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.util.Localisation;
import com.ruskaof.client.util.Localisator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Objects;

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
        Localisator localisator = new Localisator();
        if (languageCB.getValue() != null) {
            try {
                ClientApi.getInstance().init(Integer.parseInt(portField.getText()), ipField.getText());
                ClientApi.getInstance().setLocalisation(Objects.requireNonNull(parseLocal(languageCB.getValue())));
                Navigator.navigateToLoginScreen(event, this.getClass());
            } catch (IllegalArgumentException e) {
                errorLabel.setText(localisator.get("error.port"));
            } catch (IOException e) {
                errorLabel.setText(localisator.disconnection);
            }
        } else {
            errorLabel.setText(localisator.get("error.language"));
        }

    }

    @FXML
    public void initialize() {
        ClientApi.getInstance().setLocalisation(Localisation.RUSSIAN);
        languageCB.setItems(FXCollections.observableArrayList(
                "русский",
                "Română",
                "Français",
                "español"
        ));
    }

    private Localisation parseLocal(String s) {
        Localisation toRet = null;
        switch (s) {
            case "русский":
                toRet = Localisation.RUSSIAN;
                break;
            case "Română":
                toRet = Localisation.ROMANIAN;
                break;
            case "Français":
                toRet = Localisation.FRENCH;
                break;
            case "español":
                toRet = Localisation.SPANISH;
                break;
            default: break;
        }
        return toRet;
    }

    @FXML
    public void onLanguageChange() {
        setLocalisation();
    }

    private void setLocalisation() {
        if (languageCB.getValue() != null) {
            ClientApi.getInstance().setLocalisation(Objects.requireNonNull(parseLocal(languageCB.getValue())));
        }
        final Localisator localisator = new Localisator();
        portField.setPromptText(localisator.get("label.port"));
    }


}
