package com.ruskaof.client.presentation.screen_login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginScreenController {

    public void register(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader
                .load(Objects.requireNonNull(getClass().getResource("/screen_register.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);

        stage.setScene(scene);
    }
}
