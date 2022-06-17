package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.util.Localisator;
import com.ruskaof.common.data.Coordinates;
import com.ruskaof.common.data.Country;
import com.ruskaof.common.data.FormOfEducation;
import com.ruskaof.common.data.Location;
import com.ruskaof.common.data.Person;
import com.ruskaof.common.data.Semester;
import com.ruskaof.common.data.StudyGroup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
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
        if (checkFields()) {

            ClientApi.getInstance().add(createStudyGroupByFields());
            Navigator.navigateToMainScreen(event, getClass());

        }
    }

    @FXML
    public void addIfMin(ActionEvent event) throws IOException {
        if (checkFields()) {

            ClientApi.getInstance().addIfMin(createStudyGroupByFields());
            Navigator.navigateToMainScreen(event, getClass());

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

    private boolean checkFields() {
        Localisator localisator = new Localisator();
        boolean toRet = true;
        if (!checkString(nameField.getText())) {
            errorLabel.setText(localisator.get("error.string_field"));
            toRet = false;
        } else if (!checkLong(xField.getText(), (l) -> l > X_FIELD_LIMITATION)) {
            errorLabel.setText(localisator.get("error.x_field"));
            toRet = false;
        } else if (!checkDouble(yField.getText(), (d) -> d <= Y_FIELD_LIMITATION)) {
            errorLabel.setText(localisator.get("error.y_field"));
            toRet = false;
        } else if (!checkLong(studentsCountField.getText(), (l) -> l > 0 && l <= Integer.MAX_VALUE)) {
            errorLabel.setText(localisator.get("error.students_count"));
            toRet = false;
        } else if (!checkString(adminNameField.getText())) {
            errorLabel.setText(localisator.get("error.admin_name"));
            toRet = false;
        } else if (!checkLong(adminHeightField.getText(), (l) -> l > 0 && l <= Integer.MAX_VALUE)) {
            errorLabel.setText(localisator.get("error.admin_height"));
            toRet = false;
        } else if (!checkFloat(adminXField.getText(), (f) -> true)) {
            errorLabel.setText(localisator.get("error.admin_x"));
            toRet = false;
        } else if (!checkLong(adminYField.getText(), (l) -> true)) {
            errorLabel.setText(localisator.get("error.admin_y"));
            toRet = false;
        } else if (!checkString(adminLocationNameField.getText())) {
            errorLabel.setText(localisator.get("error.admin_location"));
            toRet = false;
        } else if (adminNationalityCombo.getValue() == null || formOfEducationCombo.getValue() == null || semesterCombo == null) {
            errorLabel.setText(localisator.get("error.you_must_choose"));
            toRet = false;
        }
        return toRet;
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
                ClientApi.getInstance().getLogin(),
                ClientApi.getInstance().getColor().toString());
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
