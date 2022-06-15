package com.ruskaof.client.presentation.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SortFilterScreen {
    @FXML
    private ComboBox<String> sortingOrderCB;
    @FXML
    private ComboBox<String> sortingFieldCB;
    @FXML
    private ComboBox<String> filteringFieldCB;
    @FXML
    private TextField rightField;
    @FXML
    private TextField leftField;
    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        sortingOrderCB.setItems(FXCollections.observableArrayList(Arrays.stream(SortingOrder.values()).map(Enum::toString).collect(Collectors.toList())));
        sortingFieldCB.setItems(FXCollections.observableArrayList(Arrays.stream(Field.values()).map(Enum::toString).collect(Collectors.toList())));
        filteringFieldCB.setItems(FXCollections.observableArrayList(Field.ID.toString(),
                Field.X.toString(),
                Field.Y.toString(),
                Field.STUDENTS_COUNT.toString(),
                Field.ADMIN_HEIGHT.toString(),
                Field.ADMIN_X.toString(),
                Field.ADMIN_Y.toString()));
    }

    @FXML
    public void okClick(ActionEvent event) throws IOException {
        if (sortingOrderCB.getValue() != null && sortingFieldCB.getValue() != null && filteringFieldCB.getValue() != null) {
            try {
                Navigator.navigateToMainScreen(event,
                        this.getClass(),
                        SortingOrder.valueOf(sortingOrderCB.getValue()),
                        Field.valueOf(sortingFieldCB.getValue()),
                        Double.parseDouble(leftField.getText()),
                        Double.parseDouble(rightField.getText()),
                        Field.valueOf(filteringFieldCB.getValue()));

            } catch (IllegalArgumentException e) {
                errorLabel.setText("The values in fields must be numbers");
            }
        } else {
            errorLabel.setText("Please choose all options in combo boxes");
        }
    }

}
