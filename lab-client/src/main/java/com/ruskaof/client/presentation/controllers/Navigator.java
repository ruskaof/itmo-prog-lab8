package com.ruskaof.client.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Navigator {
    private Navigator() {

    }

    public static void navigateToMainScreen(ActionEvent event, Class<?> clazz) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(clazz.getResource("/screen_main.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/button.css")).toExternalForm());
        stage.setScene(scene);
    }

    public static void navigateToRegisterScreen(ActionEvent event, Class<?> clazz) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(clazz.getResource("/screen_register.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/button.css")).toExternalForm());
        stage.setScene(scene);
    }


}
