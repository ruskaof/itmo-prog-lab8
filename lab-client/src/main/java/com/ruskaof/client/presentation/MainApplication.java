package com.ruskaof.client.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApplication extends Application {
    private final Color mainBackgroundColor = Color.WHITE;
    private final String appName = "Study group manager";

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Parent root = FXMLLoader
                .load(Objects.requireNonNull(getClass().getResource("/screen_login.fxml")));
        final Scene scene = new Scene(root, mainBackgroundColor);
        final Image icon = new Image("icon.png");

        stage.setHeight(600);
        stage.setWidth(800);

        stage.setMinHeight(600);
        stage.setMinWidth(800);

        scene.getStylesheets()
                .add(Objects.requireNonNull(getClass().getResource("/button.css")).toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle(appName);
        stage.setScene(scene);
        stage.show();
    }

}
