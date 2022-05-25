package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.common.util.DataCantBeSentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginScreenController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    public void login(ActionEvent event) throws IOException, DataCantBeSentException {
        ClientApi.getInstance().setLoginAndPassword(loginField.getText(), passwordField.getText());

        final Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/screen_main.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/button.css")).toExternalForm());
        stage.setScene(scene);
    }

    public void register(ActionEvent event) throws IOException, DataCantBeSentException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/screen_register.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/button.css")).toExternalForm());
        stage.setScene(scene);
    }
}
