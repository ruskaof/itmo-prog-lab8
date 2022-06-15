package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.common.data.*;
import com.ruskaof.common.util.DataCantBeSentException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public class AddScreenController {
    private static final long X_FIELD_LIMITATION = -896;
    private static final double Y_FIELD_LIMITATION = 135;
    private static final int STRING_LENGTH_LIMITATION = 100;
    @FXML
    private TextField nameField;
    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML
    private TextField studentsCountField;
    @FXML
    private ComboBox<String> formOfEducationCombo;
    @FXML
    private ComboBox<String> semesterCombo;
    @FXML
    private TextField adminNameField;
    @FXML
    private TextField adminHeightField;
    @FXML
    private ComboBox<String> adminNationalityCombo;
    @FXML
    private TextField adminXField;
    @FXML
    private TextField adminYField;
    @FXML
    private TextField adminLocationNameField;
    @FXML
    private Label errorLabel;


    @FXML
    public void add(ActionEvent event) throws IOException {
        if (checkFieldsAndAdd()) {
            try {
                ClientApi.getInstance().add(createStudyGroupByFields());
                Navigator.navigateToMainScreen(event, getClass());
            } catch (DataCantBeSentException e) {
                errorLabel.setText("There was some problem...");
            }
        }
    }

    @FXML
    public void addIfMin(ActionEvent event) throws IOException {
        if (checkFieldsAndAdd()) {
            try {
                ClientApi.getInstance().addIfMin(createStudyGroupByFields());
                Navigator.navigateToMainScreen(event, getClass());
            } catch (DataCantBeSentException e) {
                errorLabel.setText("There was some problem...");
            }
        }
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

        adminNationalityCombo.getItems().add("RUSSIA");
        adminNationalityCombo.getItems().add("SPAIN");
        adminNationalityCombo.getItems().add("INDIA");
        adminNationalityCombo.getItems().add("THAILAND");
        adminNationalityCombo.getItems().add("NORTH_KOREA");

        semesterCombo.getItems().add("THIRD");
        semesterCombo.getItems().add("FIFTH");
        semesterCombo.getItems().add("SIXTH");
        semesterCombo.getItems().add("SEVENTH");
        semesterCombo.getItems().add("null");
    }

    private boolean checkFieldsAndAdd() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels");
        if (!checkString(nameField.getText())) {
            errorLabel.setText(resourceBundle.getString("error.string_field"));
            return false;
        }
        if (!checkLong(xField.getText(), (l) -> l > -X_FIELD_LIMITATION)) {
            errorLabel.setText(resourceBundle.getString("error.x_field"));
            return false;
        }
        if (!checkDouble(yField.getText(), (d) -> d <= Y_FIELD_LIMITATION)) {
            errorLabel.setText(resourceBundle.getString("error.y_field"));
            return false;
        }
        if (!checkLong(studentsCountField.getText(), (l) -> l > 0 && l <= Integer.MAX_VALUE)) {
            errorLabel.setText(resourceBundle.getString("error.students_count"));
            return false;
        }
        if (!checkString(adminNameField.getText())) {
            errorLabel.setText(resourceBundle.getString("error.admin_name"));
            return false;
        }
        if (!checkLong(adminHeightField.getText(), (l) -> l > 0 && l <= Integer.MAX_VALUE)) {
            errorLabel.setText(resourceBundle.getString("error.admin_height"));
            return false;
        }
        if (!checkFloat(adminXField.getText(), (f) -> true)) {
            errorLabel.setText(resourceBundle.getString("error.admin_x"));
            return false;
        }
        if (!checkLong(adminYField.getText(), (l) -> true)) {
            errorLabel.setText(resourceBundle.getString("error.admin_y"));
            return false;
        }
        if (!checkString(adminLocationNameField.getText())) {
            errorLabel.setText(resourceBundle.getString("error.admin_location"));
            return false;
        }
        if (adminNationalityCombo.getValue() == null || formOfEducationCombo.getValue() == null || semesterCombo == null) {
            errorLabel.setText(resourceBundle.getString("error.you_must_choose"));
            return false;
        }
        return true;
    }

    private StudyGroup createStudyGroupByFields() {
        return new StudyGroup(
                nameField.getText(),
                new Coordinates(
                        Long.parseLong(xField.getText()),
                        Double.parseDouble(yField.getText())
                ),
                Integer.parseInt(studentsCountField.getText()),
                FormOfEducation.valueOf(formOfEducationCombo.getValue()),
                semesterCombo.getValue().equals("null") ? null : Semester.valueOf(semesterCombo.getValue()),
                new Person(
                        adminNameField.getText(),
                        Integer.parseInt(adminXField.getText()),
                        Country.valueOf(adminNationalityCombo.getValue()),
                        new Location(
                                Float.parseFloat(adminXField.getText()),
                                Long.parseLong(adminYField.getText()),
                                adminLocationNameField.getText()
                        )
                ),
                LocalDate.now(),
                ClientApi.getInstance().getLogin()
        );
    }


    private boolean checkLong(String l, Predicate<Long> checkFunc) {
        try {
            return checkFunc.test(Long.parseLong(l));
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkDouble(String d, Predicate<Double> checkFunc) {
        try {
            return checkFunc.test(Double.parseDouble(d));
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkFloat(String d, Predicate<Float> checkFunc) {
        try {
            return checkFunc.test(Float.parseFloat(d));
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkString(String s) {
        return s.length() > 0 && s.length() < STRING_LENGTH_LIMITATION;
    }


}
