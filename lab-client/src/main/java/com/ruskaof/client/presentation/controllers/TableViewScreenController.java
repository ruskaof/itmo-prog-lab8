package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.common.util.DataCantBeSentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class TableViewScreenController {
    @FXML
    TableView<StudyGroupRow> table;

    @FXML
    public void reload(ActionEvent event) {
        try {
            final ObservableList<StudyGroupRow> list = FXCollections.observableList(
                    ClientApi.getInstance().getCurrentData().stream().map(StudyGroupRow::mapStudyGroupToRow).collect(Collectors.toList())
            );
            table.setItems(list);
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    private static void addIntColumn(TableView<StudyGroupRow> table, String fieldName, String columnName) {
        final TableColumn<StudyGroupRow, Integer> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Integer>forTableColumn(new IntegerStringConverter()));
        table.getColumns().add(newColumn);
    }

    private static void addLongColumn(TableView<StudyGroupRow> table, String fieldName, String columnName) {
        final TableColumn<StudyGroupRow, Long> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Long>forTableColumn(new LongStringConverter()));
        table.getColumns().add(newColumn);
    }

    private static void addStringColumn(TableView<StudyGroupRow> table, String fieldName, String columnName) {
        final TableColumn<StudyGroupRow, String> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow>forTableColumn());
        table.getColumns().add(newColumn);
    }

    private static void addFloatColumn(TableView<StudyGroupRow> table, String fieldName, String columnName) {
        final TableColumn<StudyGroupRow, Float> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Float>forTableColumn(new FloatStringConverter()));
        table.getColumns().add(newColumn);
    }

    private static void addDoubleColumn(TableView<StudyGroupRow> table, String fieldName, String columnName) {
        final TableColumn<StudyGroupRow, Double> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Double>forTableColumn(new DoubleStringConverter()));
        table.getColumns().add(newColumn);
    }

    private static void addDateColumn(TableView<StudyGroupRow> table, String fieldName, String columnName) {
        final TableColumn<StudyGroupRow, LocalDate> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, LocalDate>forTableColumn(new LocalDateStringConverter()));
        table.getColumns().add(newColumn);
    }

    @FXML
    public void initialize() {
        TableViewScreenController.addIntColumn(table, "id", "id");
        TableViewScreenController.addStringColumn(table, "name", "name");
        TableViewScreenController.addLongColumn(table, "x", "x");
        TableViewScreenController.addDoubleColumn(table, "y", "y");
        TableViewScreenController.addDateColumn(table, "creationDate", "creationDate");
        TableViewScreenController.addIntColumn(table, "studentsCount", "studentsCount");
        TableViewScreenController.addStringColumn(table, "formOfEducation", "formOfEducation");
        TableViewScreenController.addStringColumn(table, "semester", "semester");
        TableViewScreenController.addStringColumn(table, "adminName", "adminName");
        TableViewScreenController.addIntColumn(table, "adminHeight", "adminHeight");
        TableViewScreenController.addStringColumn(table, "adminNationality", "adminNationality");
        TableViewScreenController.addFloatColumn(table, "adminX", "adminX");
        TableViewScreenController.addLongColumn(table, "adminY", "adminY");
        TableViewScreenController.addStringColumn(table, "adminLocationName", "adminLocationName");
        TableViewScreenController.addStringColumn(table, "authorName", "authorName");

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setEditable(true);
    }
}
