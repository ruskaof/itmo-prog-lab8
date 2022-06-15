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
    private static final int MIN_HEIGHT = 480;
    private static final int MIN_WIDTH = 640;
    private static final int HEIGHT = 600;
    private static final int WIDTH = 800;
    private final Color mainBackgroundColor = Color.WHITE;
    private final String appName = "Study group manager";

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Parent root = FXMLLoader
                .load(Objects.requireNonNull(getClass().getResource("/screen_connection_info.fxml")));
        final Scene scene = new Scene(root, mainBackgroundColor);
        final Image icon = new Image("icon.png");

        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);

        stage.setHeight(HEIGHT);
        stage.setWidth(WIDTH);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/button.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/text_field.css")).toExternalForm());

        stage.getIcons().add(icon);
        stage.setTitle(appName);
        stage.setScene(scene);
        stage.show();
    }

}
