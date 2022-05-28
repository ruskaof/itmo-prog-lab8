package com.ruskaof.client.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;


public class AddScreenController {
    @FXML
    TextField nameField;
    @FXML
    TextField xField;
    @FXML
    TextField yField;
    @FXML
    TextField studentsCountField;
    @FXML
    ComboBox<String> formOfEducationCombo;
    @FXML
    ComboBox<String> semesterCombo;
    @FXML
    TextField adminNameField;
    @FXML
    TextField adminHeightField;
    @FXML
    TextField adminNationalityField;
    @FXML
    TextField adminXField;
    @FXML
    TextField adminYField;
    @FXML
    TextField adminLocationNameField;

    @FXML
    public void add(ActionEvent event) {

    }

    @FXML
    public void addIfMin(ActionEvent event) {

    }

    @FXML
    public void cancel(ActionEvent event) throws IOException {
        Navigator.navigateToMainScreen(event, getClass());
    }

    @FXML
    public void initialize() {
        formOfEducationCombo.getItems().add("DISTANCE_EDUCATION");
        formOfEducationCombo.getItems().add("FULL_TIME_EDUCATION");
        formOfEducationCombo.getItems().add("EVENING_CLASSES");

        semesterCombo.getItems().add("THIRD");
        semesterCombo.getItems().add("FIFTH");
        semesterCombo.getItems().add("SIXTH");
        semesterCombo.getItems().add("SEVENTH");
        semesterCombo.getItems().add("null");
    }

}
