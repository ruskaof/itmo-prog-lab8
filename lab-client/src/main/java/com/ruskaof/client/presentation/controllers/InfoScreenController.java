package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.data.StudyGroupRow;
import javafx.collections.FXCollections;
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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InfoScreenController {
    private static final StringConverter<Integer> SAFE_INTEGER_STRING_CONVERTER = new StringConverter<Integer>() {
        @Override
        public String toString(Integer object) {
            if (object == null) {
                return null;
            }
            return NumberFormat.getInstance(ClientApi.getInstance().getLocale()).format(object.intValue());
        }

        @Override
        public Integer fromString(String string) {
            try {
                return NumberFormat.getInstance(ClientApi.getInstance().getLocale()).parse(string).intValue();
            } catch (IllegalArgumentException | ParseException e) {
                return 1;
            }
        }
    };
    private static final StringConverter<Integer> SAFE_NULLABLE_INTEGER_STRING_CONVERTER = new StringConverter<Integer>() {
        @Override
        public String toString(Integer object) {
            if (object == null) {
                return null;
            }
            return NumberFormat.getInstance(ClientApi.getInstance().getLocale()).format(object.intValue());
        }

        @Override
        public Integer fromString(String string) {
            if (string.isEmpty()) {
                return null;
            }
            try {
                return NumberFormat.getInstance(ClientApi.getInstance().getLocale()).parse(string).intValue();
            } catch (IllegalArgumentException | ParseException e) {
                return 1;
            }
        }
    };
    private static final StringConverter<LocalDate> LOCAL_DATE_STRING_CONVERTER = new StringConverter<LocalDate>() {
        @Override
        public String toString(LocalDate object) {
            return DateFormat.getDateInstance(DateFormat.FULL, ClientApi.getInstance().getLocale()).format(convertToDateViaInstant(object));
        }

        @Override
        public LocalDate fromString(String string) {
            try {
                return convertToLocalDate(DateFormat.getDateInstance(DateFormat.FULL, ClientApi.getInstance().getLocale()).parse(string));
            } catch (IllegalArgumentException | ParseException e) {
                return LocalDate.now();
            }
        }
    };
    private static final StringConverter<Long> SAFE_LONG_STRING_CONVERTER = new StringConverter<Long>() {

        @Override
        public String toString(Long object) {
            return NumberFormat.getInstance(ClientApi.getInstance().getLocale()).format(object);
        }

        @Override
        public Long fromString(String string) {
            try {
                return NumberFormat.getInstance(ClientApi.getInstance().getLocale()).parse(string).longValue();
            } catch (IllegalArgumentException | ParseException e) {
                return 1L;
            }
        }
    };
    private static final StringConverter<Double> SAFE_DOUBLE_STRING_CONVERTER = new StringConverter<Double>() {
        @Override
        public String toString(Double object) {
            return NumberFormat.getInstance(ClientApi.getInstance().getLocale()).format(object);
        }

        @Override
        public Double fromString(String string) {
            try {
                return NumberFormat.getInstance(ClientApi.getInstance().getLocale()).parse(string).doubleValue();
            } catch (IllegalArgumentException | ParseException e) {
                return 1D;
            }
        }
    };
    private static final StringConverter<Float> SAFE_FLOAT_STRING_CONVERTER = new StringConverter<Float>() {
        @Override
        public String toString(Float object) {
            return NumberFormat.getInstance(ClientApi.getInstance().getLocale()).format(object);
        }

        @Override
        public Float fromString(String string) {
            try {
                return NumberFormat.getInstance(ClientApi.getInstance().getLocale()).parse(string).floatValue();
            } catch (IllegalArgumentException | ParseException e) {
                return 1F;
            }
        }
    };
    private static final StringConverter<String> NULLABLE_STRING_CONVERTER = new StringConverter<String>() {
        @Override
        public String toString(String object) {
            return object;
        }

        @Override
        public String fromString(String string) {
            if (string.isEmpty()) {
                return null;
            } else {
                return string;
            }
        }
    };
    private List<StudyGroupRow> data;


    @FXML
    private TableView<StudyGroupRow> table;
    private StudyGroupRow object;




    @FXML
    public void onOkClick(ActionEvent event) throws IOException {
        Navigator.navigateToMainScreenToVisualisation(event, this.getClass());
    }

    public void setObject(StudyGroupRow newObject) {
        this.data = Arrays.asList(newObject);
        object = newObject;
    }


    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private static void addIntColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, TableViewScreenController.OnCommitCellModifier<Integer> onCommitCellModifier, boolean editable) {
        TableColumn<StudyGroupRow, Integer> newColumn = makeStandardColumnConfig(columnName, fieldName, editable);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Integer>forTableColumn(SAFE_INTEGER_STRING_CONVERTER));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addNullableIntColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, TableViewScreenController.OnCommitCellModifier<Integer> onCommitCellModifier, boolean editable) {
        TableColumn<StudyGroupRow, Integer> newColumn = makeStandardColumnConfig(columnName, fieldName, editable);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Integer>forTableColumn(SAFE_NULLABLE_INTEGER_STRING_CONVERTER));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addSemesterColumn(TableView<StudyGroupRow> table, TableViewScreenController.OnCommitCellModifier<String> onCommitCellModifier) {
        TableColumn<StudyGroupRow, String> newColumn = makeStandardColumnConfig("semester", "semester", true);
        newColumn.setCellFactory(ComboBoxTableCell.forTableColumn("THIRD",
                "FIFTH",
                "SIXTH",
                "SEVENTH",
                ""));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addAdminNationalityColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, TableViewScreenController.OnCommitCellModifier<String> onCommitCellModifier) {
        TableColumn<StudyGroupRow, String> newColumn = makeStandardColumnConfig(columnName, fieldName, true);
        newColumn.setCellFactory(ComboBoxTableCell.forTableColumn("RUSSIA",
                "SPAIN",
                "INDIA",
                "THAILAND",
                "NORTH_KOREA",
                ""));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addFormOfEduColumn(TableView<StudyGroupRow> table, TableViewScreenController.OnCommitCellModifier<String> onCommitCellModifier) {
        TableColumn<StudyGroupRow, String> newColumn = makeStandardColumnConfig("formOfEducation", "formOfEducation", true);
        newColumn.setCellFactory(ComboBoxTableCell.forTableColumn("DISTANCE_EDUCATION",
                "FULL_TIME_EDUCATION",
                "EVENING_CLASSES"));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addLongColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, TableViewScreenController.OnCommitCellModifier<Long> onCommitCellModifier) {
        TableColumn<StudyGroupRow, Long> newColumn = makeStandardColumnConfig(columnName, fieldName, true);

        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Long>forTableColumn(SAFE_LONG_STRING_CONVERTER));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addFloatColumn(TableView<StudyGroupRow> table, TableViewScreenController.OnCommitCellModifier<Float> onCommitCellModifier) {
        TableColumn<StudyGroupRow, Float> newColumn = makeStandardColumnConfig("adminX", "adminX", true);

        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Float>forTableColumn(SAFE_FLOAT_STRING_CONVERTER));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }


    @FXML
    public void add(ActionEvent event) throws IOException {
        final Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/screen_add.fxml")));
        final Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/text_field.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/button.css")).toExternalForm());
        stage.setScene(scene);
    }

    private static void addStringColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, TableViewScreenController.OnCommitCellModifier<String> onCommitCellModifier, boolean editable, boolean nullable) {
        TableColumn<StudyGroupRow, String> newColumn = makeStandardColumnConfig(columnName, fieldName, true);
        newColumn.setEditable(editable);
        if (nullable) {
            newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, String>forTableColumn(NULLABLE_STRING_CONVERTER));
        } else {
            newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow>forTableColumn());
        }
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addDoubleColumn(TableView<StudyGroupRow> table, TableViewScreenController.OnCommitCellModifier<Double> onCommitCellModifier) {
        TableColumn<StudyGroupRow, Double> newColumn = makeStandardColumnConfig("y", "y", true);

        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Double>forTableColumn(SAFE_DOUBLE_STRING_CONVERTER));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addDateColumn(TableView<StudyGroupRow> table) {
        TableColumn<StudyGroupRow, LocalDate> newColumn = makeStandardColumnConfig("creationDate", "creationDate", false);
        newColumn.setCellFactory(TextFieldTableCell.forTableColumn(LOCAL_DATE_STRING_CONVERTER));
        table.getColumns().add(newColumn);
    }

    private static <T> TableColumn<StudyGroupRow, T> makeStandardColumnConfig(String columnName, String fieldName, boolean editable) {
        final TableColumn<StudyGroupRow, T> newColumn = new TableColumn<>(columnName);
        newColumn.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        newColumn.setEditable(editable);
        newColumn.setSortable(false);
        return newColumn;
    }


    @FXML
    public void reload() {

        ClientApi.getInstance().updateData();
        data = ClientApi.getInstance().getCurrentData().stream().filter(it -> it.getId() == object.getId()).collect(Collectors.toList());
        table.setItems(FXCollections.observableList(data));
        table.refresh();

    }

    @FXML
    public void initialize() {
        addIntColumn(table, "id", "id", StudyGroupRow::setId, false);
        addStringColumn(table, "name", "name", StudyGroupRow::setName, true, false);
        addLongColumn(table, "x", "x", StudyGroupRow::setX);
        addDoubleColumn(table, StudyGroupRow::setY);
        addDateColumn(table);
        addNullableIntColumn(table, "studentsCount", "studentsCount", StudyGroupRow::setStudentsCount, true);
        addFormOfEduColumn(table, StudyGroupRow::setFormOfEducation);
        addSemesterColumn(table, StudyGroupRow::setSemester);
        addStringColumn(table, "adminName", "adminName", StudyGroupRow::setAdminName, true, false);
        addIntColumn(table, "adminHeight", "adminHeight", StudyGroupRow::setAdminHeight, true);
        addAdminNationalityColumn(table, "adminNationality", "adminNationality", StudyGroupRow::setAdminNationality);
        addFloatColumn(table, StudyGroupRow::setAdminX);
        addLongColumn(table, "adminY", "adminY", StudyGroupRow::setAdminY);
        addStringColumn(table, "adminLocationName", "adminLocationName", StudyGroupRow::setAdminLocationName, true, true);
        addStringColumn(table, "authorName", "authorName", StudyGroupRow::setAuthorName, false, false);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setEditable(true);
    }


}
