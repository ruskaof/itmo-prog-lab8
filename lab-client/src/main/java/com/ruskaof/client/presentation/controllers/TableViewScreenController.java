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

    private static void addIntColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<Integer> onCommitCellModifier) {
        final TableColumn<StudyGroupRow, Integer> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Integer>forTableColumn(new IntegerStringConverter()));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addLongColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<Long> onCommitCellModifier) {
        final TableColumn<StudyGroupRow, Long> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Long>forTableColumn(new LongStringConverter()));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addStringColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<String> onCommitCellModifier) {
        final TableColumn<StudyGroupRow, String> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow>forTableColumn());
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addFloatColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<Float> onCommitCellModifier) {
        final TableColumn<StudyGroupRow, Float> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Float>forTableColumn(new FloatStringConverter()));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addDoubleColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<Double> onCommitCellModifier) {
        final TableColumn<StudyGroupRow, Double> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Double>forTableColumn(new DoubleStringConverter()));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addDateColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<LocalDate> onCommitCellModifier) {
        final TableColumn<StudyGroupRow, LocalDate> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, LocalDate>forTableColumn(new LocalDateStringConverter()));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    @FXML
    public void reload(ActionEvent event) {
        try {
            final ObservableList<StudyGroupRow> list = FXCollections.observableList(
                    ClientApi.getInstance().getCurrentData().stream().map(StudyGroupRow::mapStudyGroupToRow).collect(Collectors.toList())
            );
            table.setItems(list);
            table.refresh();
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        TableViewScreenController.addIntColumn(table, "id", "id", StudyGroupRow::setId);
        TableViewScreenController.addStringColumn(table, "name", "name", StudyGroupRow::setName);
        TableViewScreenController.addLongColumn(table, "x", "x", StudyGroupRow::setX);
        TableViewScreenController.addDoubleColumn(table, "y", "y", StudyGroupRow::setY);
        TableViewScreenController.addDateColumn(table, "creationDate", "creationDate", StudyGroupRow::setCreationDate);
        TableViewScreenController.addIntColumn(table, "studentsCount", "studentsCount", StudyGroupRow::setStudentsCount);
        TableViewScreenController.addStringColumn(table, "formOfEducation", "formOfEducation", StudyGroupRow::setFormOfEducation);
        TableViewScreenController.addStringColumn(table, "semester", "semester", StudyGroupRow::setSemester);
        TableViewScreenController.addStringColumn(table, "adminName", "adminName", StudyGroupRow::setAdminName);
        TableViewScreenController.addIntColumn(table, "adminHeight", "adminHeight", StudyGroupRow::setAdminHeight);
        TableViewScreenController.addStringColumn(table, "adminNationality", "adminNationality", StudyGroupRow::setAdminNationality);
        TableViewScreenController.addFloatColumn(table, "adminX", "adminX", StudyGroupRow::setAdminX);
        TableViewScreenController.addLongColumn(table, "adminY", "adminY", StudyGroupRow::setAdminY);
        TableViewScreenController.addStringColumn(table, "adminLocationName", "adminLocationName", StudyGroupRow::setAdminLocationName);
        TableViewScreenController.addStringColumn(table, "authorName", "authorName", StudyGroupRow::setAuthorName);

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setEditable(true);
    }

    @FunctionalInterface
    interface OnCommitCellModifier<T> {
        void modifyColumn(StudyGroupRow studyGroupRow, T newValue);
    }
}
