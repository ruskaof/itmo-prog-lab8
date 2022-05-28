package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
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

public class ConnectionInfoController {
    @FXML
    private TextField portField;
    @FXML
    private TextField ipField;

    public void connect(ActionEvent event) throws IOException {
        ClientApi.getInstance().init(Integer.parseInt(portField.getText()), ipField.getText());

        final Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/screen_login.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/label.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/button.css")).toExternalForm());
        stage.setScene(scene);
    }


}
