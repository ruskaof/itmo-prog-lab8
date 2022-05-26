package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.common.util.DataCantBeSentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class TableViewScreenController {
    @FXML
    TableView<StudyGroupRow> table;

    private static <T> void addColumn(TableView<StudyGroupRow> table, String fieldName, String columnName) {
        final TableColumn<StudyGroupRow, T> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        table.getColumns().add(newColumn);
    }

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

    @FXML
    public void initialize() {
        TableViewScreenController.<Integer>addColumn(table, "id", "id");
        TableViewScreenController.<String>addColumn(table, "name", "name");
        TableViewScreenController.<Long>addColumn(table, "x", "x");
        TableViewScreenController.<Double>addColumn(table, "y", "y");
        TableViewScreenController.<LocalDate>addColumn(table, "creationDate", "creationDate");
        TableViewScreenController.<Integer>addColumn(table, "studentsCount", "studentsCount");
        TableViewScreenController.<String>addColumn(table, "formOfEducation", "formOfEducation");
        TableViewScreenController.<String>addColumn(table, "semester", "semester");
        TableViewScreenController.<String>addColumn(table, "adminName", "adminName");
        TableViewScreenController.<Integer>addColumn(table, "adminHeight", "adminHeight");
        TableViewScreenController.<String>addColumn(table, "adminNationality", "adminNationality");
        TableViewScreenController.<Float>addColumn(table, "adminX", "adminX");
        TableViewScreenController.<Long>addColumn(table, "adminY", "adminY");
        TableViewScreenController.<String>addColumn(table, "adminLocationName", "adminLocationName");
        TableViewScreenController.<String>addColumn(table, "authorName", "authorName");
    }
}
