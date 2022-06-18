package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.util.Localisator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
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
    private CheckBox sortingCB;
    @FXML
    private CheckBox filteringCB;
    @FXML
    private CheckBox datingCB;
    @FXML
    private DatePicker dateLeft;
    @FXML
    private DatePicker dateRight;

    @FXML
    public void initialize() {
        sortingOrderCB.setItems(FXCollections.observableArrayList(Arrays.stream(SortingOrder.values()).map(Enum::toString).collect(Collectors.toList())));
        sortingFieldCB.setItems(FXCollections.observableArrayList(Arrays.stream(Field.values()).map(Enum::toString).collect(Collectors.toList())));
        filteringFieldCB.setItems(FXCollections.observableArrayList(Arrays.stream(Field.values()).map(Enum::toString).collect(Collectors.toList())));
        setLocalisation();
    }
    private void setLocalisation() {
        Localisator localisator = new Localisator();
        sortingCB.setText(localisator.takeAccount);
        filteringCB.setText(localisator.takeAccount);
        datingCB.setText(localisator.takeAccount);
    }
    //CHECKSTYLE:OFF
    @FXML
    public void okClick(ActionEvent event) throws IOException {
        Localisator localisator = new Localisator();
        SortingOrder sortingOrder = null;
        Field sortingField = null;
        if (sortingCB.isSelected()) {
            if (sortingOrderCB.getValue() != null && sortingFieldCB.getValue() != null) {
                sortingOrder = SortingOrder.valueOf(sortingOrderCB.getValue());
                sortingField = Field.valueOf(sortingFieldCB.getValue());
            } else {
                errorLabel.setText(localisator.get("error.chose_order_field"));
                return;
            }
        }
        double leftValue = 0D;
        double rightValue = 0D;
        Field filteringField = null;
        if (filteringCB.isSelected()) {
            if (filteringFieldCB.getValue() != null) {
                try {
                    leftValue = Double.parseDouble(leftField.getText());
                    rightValue = Double.parseDouble(rightField.getText());
                    filteringField = Field.valueOf(filteringFieldCB.getValue());
                } catch (Exception e) {
                    errorLabel.setText(localisator.get("error.incorrect_numbers"));
                    return;
                }
            } else {
                errorLabel.setText(localisator.get("error.chose_filter_field"));
                return;
            }
        }
        LocalDate leftDate = null;
        LocalDate rightDate = null;
        if (datingCB.isSelected()) {
            if (dateLeft.getValue() != null && dateRight.getValue() != null) {
                leftDate = dateLeft.getValue();
                rightDate = dateRight.getValue();
            } else {
                errorLabel.setText(localisator.get("error.chose_dates"));
                return;
            }
        }
        Navigator.navigateToMainScreen(event, this.getClass(), sortingOrder, sortingField, leftValue, rightValue, filteringField, leftDate, rightDate);
    }
    //CHECKSTYLE:ON
}
