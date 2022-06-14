package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.common.util.DataCantBeSentException;
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

    public static void navigateToMainScreen(ActionEvent event, Class<?> clazz, SortingOrder sortingOrder, Field sortingField, double leftValue, double rightValue, Field filteringField) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(clazz.getResource("/screen_main.fxml")));
        final Parent root = loader.load();
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/label.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/button.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/text_field.css")).toExternalForm());
        stage.setScene(scene);
        ((MainScreenController) loader.getController()).getTableViewScreenController().setLeftAndRightValues(leftValue, rightValue);
        ((MainScreenController) loader.getController()).getTableViewScreenController().setFilterField(filteringField);
        ((MainScreenController) loader.getController()).getTableViewScreenController().setSortingField(sortingField);
        ((MainScreenController) loader.getController()).getTableViewScreenController().setSortingOrder(sortingOrder);
    }

    public static void navigateToRegisterScreen(ActionEvent event, Class<?> clazz) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(clazz.getResource("/screen_register.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/button.css")).toExternalForm());
        stage.setScene(scene);
    }

    public static void navigateToLoginScreen(ActionEvent event, Class<?> clazz) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(clazz.getResource("/screen_login.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/label.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/button.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/text_field.css")).toExternalForm());

        stage.setScene(scene);
    }

    public static void navigateToInfoScreen(Object source, Class<?> clazz, StudyGroupRow object) throws IOException, DataCantBeSentException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(clazz.getResource("/screen_info.fxml")));
        final Parent root = loader.load();
        ((InfoScreenController) loader.getController()).setObject(object);
        ((InfoScreenController) loader.getController()).setListOfObjectData();
        System.out.println(object);
        final Stage stage = (Stage) ((Node) source).getScene().getWindow();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/label.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/button.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/text_field.css")).toExternalForm());
        stage.setScene(scene);
    }

    public static void navigateToSortFilterScreen(ActionEvent event, Class<?> clazz) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(clazz.getResource("/screen_sort_filter.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/label.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/button.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(clazz.getResource("/text_field.css")).toExternalForm());

        stage.setScene(scene);
    }
}
