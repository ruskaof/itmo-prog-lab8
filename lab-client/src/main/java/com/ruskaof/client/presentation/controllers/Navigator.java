package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.data.StudyGroupRow;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public final class Navigator {
    private Navigator() {

    }

    public static void navigateToMainScreen(ActionEvent event, Class<?> clazz) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(clazz.getResource("/screen_main.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        new CSSBuilder(scene).addButtonCSS();
        stage.setScene(scene);
    }

    //CHECKSTYLE:OFF
    public static void navigateToMainScreen(ActionEvent event, Class<?> clazz, SortingOrder sortingOrder, Field sortingField, double leftValue, double rightValue, Field filteringField, LocalDate leftDate, LocalDate rightDate) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(clazz.getResource("/screen_main.fxml")));
        final Parent root = loader.load();
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        new CSSBuilder(scene).addButtonCSS().addTextFieldCSS();
        stage.setScene(scene);
        ((MainScreenController) loader.getController()).getTableViewScreenController().updateData();
        ((MainScreenController) loader.getController()).getTableViewScreenController().setLeftAndRightValues(leftValue, rightValue);
        ((MainScreenController) loader.getController()).getTableViewScreenController().setFilterField(filteringField);
        ((MainScreenController) loader.getController()).getTableViewScreenController().setSortingField(sortingField);
        ((MainScreenController) loader.getController()).getTableViewScreenController().setSortingOrder(sortingOrder);
        ((MainScreenController) loader.getController()).getTableViewScreenController().setFilterDates(leftDate, rightDate);
    }
    //CHECKSTYLE:ON


    public static void navigateToRegisterScreen(ActionEvent event, Class<?> clazz) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(clazz.getResource("/screen_register.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        new CSSBuilder(scene).addButtonCSS().addTextFieldCSS();
        stage.setScene(scene);
    }

    public static void navigateToLoginScreen(ActionEvent event, Class<?> clazz) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(clazz.getResource("/screen_login.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        new CSSBuilder(scene).addButtonCSS().addTextFieldCSS();
        stage.setScene(scene);
    }

    public static void navigateToInfoScreen(Object source, Class<?> clazz, StudyGroupRow object) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(clazz.getResource("/screen_info.fxml")));
        final Parent root = loader.load();
        ((InfoScreenController) loader.getController()).setObject(object);
        ((InfoScreenController) loader.getController()).reload();
//        ((InfoScreenController) loader.getController()).setListOfObjectData();
        final Stage stage = (Stage) ((Node) source).getScene().getWindow();
        final Scene scene = new Scene(root);
        new CSSBuilder(scene).addButtonCSS().addTextFieldCSS();
        stage.setScene(scene);
    }

    public static void navigateToSortFilterScreen(ActionEvent event, Class<?> clazz) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(clazz.getResource("/screen_sort_filter.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        new CSSBuilder(scene).addButtonCSS().addTextFieldCSS();
        stage.setScene(scene);
    }

    public static void navigateToMainScreenToVisualisation(ActionEvent event, Class<? extends InfoScreenController> aClass) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(aClass.getResource("/screen_main.fxml")));
        final Parent root = loader.load();
        ((MainScreenController) loader.getController()).navigateToVisualisation(event);
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        new CSSBuilder(scene).addButtonCSS();
        stage.setScene(scene);
    }

    private static class CSSBuilder {
        private final Scene scene;

        CSSBuilder(Scene scene) {
            this.scene = scene;
        }

        public CSSBuilder addLabelCSS() {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/label.css")).toExternalForm());
            return this;
        }

        public CSSBuilder addButtonCSS() {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/button.css")).toExternalForm());
            return this;
        }

        public CSSBuilder addTextFieldCSS() {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/text_field.css")).toExternalForm());
            return this;
        }
    }
}
