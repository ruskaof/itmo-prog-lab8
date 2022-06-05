package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.common.util.DataCantBeSentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class TableViewScreenController {
    private final static StringConverter<Integer> safeIntegerStringConverter = new StringConverter<Integer>() {
        @Override
        public String toString(Integer object) {
            return object.toString();
        }

        @Override
        public Integer fromString(String string) {
            try {
                final Integer conv = Integer.parseInt(string);
                return conv;
            } catch (IllegalArgumentException e) {
                return 1;
            }
        }
    };

    private final static StringConverter<Long> safeLongStringConverter = new StringConverter<Long>() {

        @Override
        public String toString(Long object) {
            return object.toString();
        }

        @Override
        public Long fromString(String string) {
            try {
                final Long conv = Long.parseLong(string);
                return conv;
            } catch (IllegalArgumentException e) {
                return 1L;
            }
        }
    };

    private final static StringConverter<Double> safeDoubleStringConverter = new StringConverter<Double>() {
        @Override
        public String toString(Double object) {
            return object.toString();
        }

        @Override
        public Double fromString(String string) {
            try {
                final Double conv = Double.parseDouble(string);
                return conv;
            } catch (IllegalArgumentException e) {
                return 1D;
            }
        }
    };

    private final static StringConverter<Float> safeFloatStringConverter = new StringConverter<Float>() {
        @Override
        public String toString(Float object) {
            return object.toString();
        }

        @Override
        public Float fromString(String string) {
            try {
                final Float conv = Float.parseFloat(string);
                return conv;
            } catch (IllegalArgumentException e) {
                return 1F;
            }
        }
    };

//    private final static StringConverter<LocalDate> safeDateStringConverter = new StringConverter<LocalDate>() {
//        @Override
//        public String toString(LocalDate object) {
//            return object.toString();
//        }
//
//        @Override
//        public LocalDate fromString(String string) {
//            try {
//                final LocalDate localDate = LocalDate.parse(string);
//                return localDate;
//            } catch (DateTimeParseException e) {
//                return LocalDate.now();
//            }
//        }
//    };


    @FXML
    TableView<StudyGroupRow> table;

    @FXML
    public void add(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/screen_add.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/text_field.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/button.css")).toExternalForm());
        stage.setScene(scene);
    }

    private static void addIntColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<Integer> onCommitCellModifier, boolean editable) {
        final TableColumn<StudyGroupRow, Integer> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(editable);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Integer>forTableColumn(safeIntegerStringConverter));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    @FXML
    public void reload() {
        try {
            ClientApi.getInstance().updateData();
            final ObservableList<StudyGroupRow> list = FXCollections.observableList(
                    ClientApi.getInstance().getCurrentData()
            );
            table.setItems(list);
            table.refresh();
        } catch (DataCantBeSentException e) {
            e.printStackTrace();
        }
    }

    private static void addSemesterColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<String> onCommitCellModifier) {
        final TableColumn<StudyGroupRow, String> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(ComboBoxTableCell.forTableColumn("THIRD",
                "FIFTH",
                "SIXTH",
                "SEVENTH"));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addAdminNationalityColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<String> onCommitCellModifier) {
        final TableColumn<StudyGroupRow, String> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(ComboBoxTableCell.forTableColumn("RUSSIA",
                "SPAIN",
                "INDIA",
                "THAILAND",
                "NORTH_KOREA"));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addFormOfEduColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<String> onCommitCellModifier) {
        final TableColumn<StudyGroupRow, String> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(ComboBoxTableCell.forTableColumn("DISTANCE_EDUCATION",
                "FULL_TIME_EDUCATION",
                "EVENING_CLASSES"));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addLongColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<Long> onCommitCellModifier) {
        final TableColumn<StudyGroupRow, Long> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Long>forTableColumn(safeLongStringConverter));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addFloatColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<Float> onCommitCellModifier) {
        final TableColumn<StudyGroupRow, Float> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Float>forTableColumn(safeFloatStringConverter));
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

    private static void addDoubleColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<Double> onCommitCellModifier) {
        final TableColumn<StudyGroupRow, Double> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(true);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Double>forTableColumn(safeDoubleStringConverter));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addDateColumn(TableView<StudyGroupRow> table, String fieldName, String columnName) {
        final TableColumn<StudyGroupRow, LocalDate> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(false);
        table.getColumns().add(newColumn);
    }

    @FXML
    public void initialize() {
        TableViewScreenController.addIntColumn(table, "id", "id", StudyGroupRow::setId, false);
        TableViewScreenController.addStringColumn(table, "name", "name", StudyGroupRow::setName);
        TableViewScreenController.addLongColumn(table, "x", "x", StudyGroupRow::setX);
        TableViewScreenController.addDoubleColumn(table, "y", "y", StudyGroupRow::setY);
        TableViewScreenController.addDateColumn(table, "creationDate", "creationDate");
        TableViewScreenController.addIntColumn(table, "studentsCount", "studentsCount", StudyGroupRow::setStudentsCount, true);
        TableViewScreenController.addFormOfEduColumn(table, "formOfEducation", "formOfEducation", StudyGroupRow::setFormOfEducation);
        TableViewScreenController.addSemesterColumn(table, "semester", "semester", StudyGroupRow::setSemester);
        TableViewScreenController.addStringColumn(table, "adminName", "adminName", StudyGroupRow::setAdminName);
        TableViewScreenController.addIntColumn(table, "adminHeight", "adminHeight", StudyGroupRow::setAdminHeight, true);
        TableViewScreenController.addAdminNationalityColumn(table, "adminNationality", "adminNationality", StudyGroupRow::setAdminNationality);
        TableViewScreenController.addFloatColumn(table, "adminX", "adminX", StudyGroupRow::setAdminX);
        TableViewScreenController.addLongColumn(table, "adminY", "adminY", StudyGroupRow::setAdminY);
        TableViewScreenController.addStringColumn(table, "adminLocationName", "adminLocationName", StudyGroupRow::setAdminLocationName);
        TableViewScreenController.addStringColumn(table, "authorName", "authorName", StudyGroupRow::setAuthorName);

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setEditable(true);
        reload();
    }

    @FunctionalInterface
    interface OnCommitCellModifier<T> {
        void modifyColumn(StudyGroupRow studyGroupRow, T newValue);
    }
}
