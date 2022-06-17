package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.client.util.Localisator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TableViewScreenController {
    private static final StringConverter<Integer> SAFE_INTEGER_STRING_CONVERTER = new StringConverter<Integer>() {
        @Override
        public String toString(Integer object) {
            return NumberFormat.getInstance(ClientApi.getInstance().getLocale()).format(object);
        }

        @Override
        public Integer fromString(String string) {
            try {
                return Integer.parseInt(string);
            } catch (IllegalArgumentException e) {
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
                return Long.parseLong(string);
            } catch (IllegalArgumentException e) {
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
                return Double.parseDouble(string);
            } catch (IllegalArgumentException e) {
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
                return Float.parseFloat(string);
            } catch (IllegalArgumentException e) {
                return 1F;
            }
        }
    };
    @FXML
    private TableView<StudyGroupRow> table;
    private Field sortingField = Field.ID;
    private SortingOrder sortingOrder = SortingOrder.ASCENDING;

    //CHECKSTYLE:OFF
    private final Comparator<StudyGroupRow> comparator = new Comparator<StudyGroupRow>() {
        @Override
        public int compare(StudyGroupRow o1, StudyGroupRow o2) {
            int toRet = 0;
            switch (sortingField) {
                case ID: toRet = compareValues(o1.getId(), o2.getId());
                    break;
                case NAME: toRet = compareValues(o1.getName(), o2.getName());
                    break;
                case X: toRet = compareValues(o1.getX(), o2.getX());
                    break;
                case Y: toRet = compareValues(o1.getY(), o2.getY());
                    break;
                case CREATION_DATE: toRet = compareValues(o1.getCreationDate(), o2.getCreationDate());
                    break;
                case STUDENTS_COUNT: toRet = compareValues(o1.getStudentsCount(), o2.getStudentsCount());
                    break;
                case FORM_OF_EDUCATION: toRet = compareValues(o1.getFormOfEducation(), o2.getFormOfEducation());
                    break;
                case SEMESTER: toRet = compareValues(o1.getSemester(), o2.getSemester());
                    break;
                case ADMIN_NAME: toRet = compareValues(o1.getAdminName(), o2.getAdminName());
                    break;
                case ADMIN_HEIGHT: toRet = compareValues(o1.getAdminHeight(), o2.getAdminHeight());
                    break;
                case ADMIN_NATIONALITY: toRet = compareValues(o1.getAdminNationality(), o2.getAdminNationality());
                    break;
                case ADMIN_X: toRet = compareValues(o1.getAdminX(), o2.getAdminX());
                    break;
                case ADMIN_Y: toRet = compareValues(o1.getAdminY(), o2.getAdminY());
                    break;
                case ADMIN_LOCATION_NAME: toRet = compareValues(o1.getAdminLocationName(), o2.getAdminLocationName());
                    break;
                case AUTHOR_NAME: toRet = compareValues(o1.getAuthorName(), o2.getAuthorName());
                    break;
                default: break;
            }
            return toRet;
        }
    };
    //CHECKSTYLE:ON

    private Field filterField = Field.ID;
    private List<StudyGroupRow> data;
    private double leftValue;
    private double rightValue;
    @FXML
    private Button reloadBTN;
    @FXML
    private Button addBTN;
    @FXML
    private Button sortFilterBTN;
    @FXML
    private Button removeSelectedBTN;
    private Predicate<StudyGroupRow> filterPred;


    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private static void addIntColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<Integer> onCommitCellModifier, boolean editable) {
        TableColumn<StudyGroupRow, Integer> newColumn = makeStandardColumnConfig(columnName, fieldName, editable);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Integer>forTableColumn(SAFE_INTEGER_STRING_CONVERTER));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addSemesterColumn(TableView<StudyGroupRow> table, OnCommitCellModifier<String> onCommitCellModifier) {
        TableColumn<StudyGroupRow, String> newColumn = makeStandardColumnConfig("semester", "semester", true);
        newColumn.setCellFactory(ComboBoxTableCell.forTableColumn("THIRD",
                "FIFTH",
                "SIXTH",
                "SEVENTH"));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addAdminNationalityColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<String> onCommitCellModifier) {
        TableColumn<StudyGroupRow, String> newColumn = makeStandardColumnConfig(columnName, fieldName, true);
        newColumn.setCellFactory(ComboBoxTableCell.forTableColumn("RUSSIA",
                "SPAIN",
                "INDIA",
                "THAILAND",
                "NORTH_KOREA"));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addFormOfEduColumn(TableView<StudyGroupRow> table, OnCommitCellModifier<String> onCommitCellModifier) {
        TableColumn<StudyGroupRow, String> newColumn = makeStandardColumnConfig("formOfEducation", "formOfEducation", true);
        newColumn.setCellFactory(ComboBoxTableCell.forTableColumn("DISTANCE_EDUCATION",
                "FULL_TIME_EDUCATION",
                "EVENING_CLASSES"));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addLongColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<Long> onCommitCellModifier) {
        TableColumn<StudyGroupRow, Long> newColumn = makeStandardColumnConfig(columnName, fieldName, true);

        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow, Long>forTableColumn(SAFE_LONG_STRING_CONVERTER));
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addFloatColumn(TableView<StudyGroupRow> table, OnCommitCellModifier<Float> onCommitCellModifier) {
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

    private static void addStringColumn(TableView<StudyGroupRow> table, String fieldName, String columnName, OnCommitCellModifier<String> onCommitCellModifier, boolean editable) {
        TableColumn<StudyGroupRow, String> newColumn = makeStandardColumnConfig(columnName, fieldName, true);
        newColumn.setEditable(editable);
        newColumn.setCellFactory(TextFieldTableCell.<StudyGroupRow>forTableColumn());
        newColumn.setOnEditCommit(event -> onCommitCellModifier.modifyColumn(event.getRowValue(), event.getNewValue()));
        table.getColumns().add(newColumn);
    }

    private static void addDoubleColumn(TableView<StudyGroupRow> table, OnCommitCellModifier<Double> onCommitCellModifier) {
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

    public void setLeftAndRightValues(double newLeftValue, double newRightValue) {
        this.leftValue = newLeftValue;
        this.rightValue = newRightValue;
    }

    @FXML
    public void onRemoveClick(ActionEvent event) {
        table.getSelectionModel().getSelectedItems().forEach(it -> {

            ClientApi.getInstance().removeById(it.getId());
            reload();

        });
    }

    public void setFilterField(Field filterField) {
        this.filterField = filterField;
    }

    private boolean filterValue(StudyGroupRow studyGroupRow) {
        boolean toRet = true;
        switch (filterField) {
            case X: toRet = studyGroupRow.getX() <= rightValue && studyGroupRow.getX() >= leftValue;
                break;
            case Y: toRet = studyGroupRow.getY() <= rightValue && studyGroupRow.getY() >= leftValue;
                break;
            case ID: toRet = studyGroupRow.getId() <= rightValue && studyGroupRow.getId() >= leftValue;
                break;
            case STUDENTS_COUNT: toRet = studyGroupRow.getStudentsCount() <= rightValue && studyGroupRow.getStudentsCount() >= leftValue;
                break;
            case ADMIN_HEIGHT: toRet = studyGroupRow.getAdminHeight() <= rightValue && studyGroupRow.getAdminHeight() >= leftValue;
                break;
            case ADMIN_X: toRet = studyGroupRow.getAdminX() <= rightValue && studyGroupRow.getAdminX() >= leftValue;
                break;
            case ADMIN_Y: toRet = studyGroupRow.getAdminY() <= rightValue && studyGroupRow.getAdminY() >= leftValue;
                break;
            default: break;
        }
        return toRet;
    }

    private <T extends Comparable<T>> int compareValues(T o1, T o2) {
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        if (sortingOrder == SortingOrder.ASCENDING) {

            return o1.compareTo(o2);
        } else {
            return o2.compareTo(o1);
        }
    }

    public void setSortingField(Field sortingField) {
        System.out.println(leftValue);
        System.out.println(rightValue);
        this.sortingField = sortingField;
        reload();
        data = data.stream().filter(this::filterValue).sorted(comparator).collect(Collectors.toList());
        table.setItems(FXCollections.observableArrayList(data));
        table.refresh();
    }

    public void setSortingOrder(SortingOrder sortingOrder) {
        this.sortingOrder = sortingOrder;
        reload();
        data = data.stream().filter(this::filterValue).sorted(comparator).collect(Collectors.toList());
        table.setItems(FXCollections.observableArrayList(data));
        table.refresh();
    }

    @FXML
    public void onSortFilterBTN(ActionEvent event) throws IOException {
        Navigator.navigateToSortFilterScreen(event, this.getClass());
    }

    @FXML
    public void reload() {

        ClientApi.getInstance().updateData();
        data = ClientApi.getInstance().getCurrentData();
        table.setItems(FXCollections.observableList(data));
        table.refresh();

    }

    @FXML
    public void initialize() {
        TableViewScreenController.addIntColumn(table, "id", "id", StudyGroupRow::setId, false);
        TableViewScreenController.addStringColumn(table, "name", "name", StudyGroupRow::setName, true);
        TableViewScreenController.addLongColumn(table, "x", "x", StudyGroupRow::setX);
        TableViewScreenController.addDoubleColumn(table, StudyGroupRow::setY);
        TableViewScreenController.addDateColumn(table);
        TableViewScreenController.addIntColumn(table, "studentsCount", "studentsCount", StudyGroupRow::setStudentsCount, true);
        TableViewScreenController.addFormOfEduColumn(table, StudyGroupRow::setFormOfEducation);
        TableViewScreenController.addSemesterColumn(table, StudyGroupRow::setSemester);
        TableViewScreenController.addStringColumn(table, "adminName", "adminName", StudyGroupRow::setAdminName, true);
        TableViewScreenController.addIntColumn(table, "adminHeight", "adminHeight", StudyGroupRow::setAdminHeight, true);
        TableViewScreenController.addAdminNationalityColumn(table, "adminNationality", "adminNationality", StudyGroupRow::setAdminNationality);
        TableViewScreenController.addFloatColumn(table, StudyGroupRow::setAdminX);
        TableViewScreenController.addLongColumn(table, "adminY", "adminY", StudyGroupRow::setAdminY);
        TableViewScreenController.addStringColumn(table, "adminLocationName", "adminLocationName", StudyGroupRow::setAdminLocationName, false);
        TableViewScreenController.addStringColumn(table, "authorName", "authorName", StudyGroupRow::setAuthorName, false);
        setLocalisation();
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setEditable(true);
        reload();
    }

    private void setLocalisation() {
        Localisator localisator = new Localisator();
        reloadBTN.setText(localisator.get("button.reload"));
        addBTN.setText(localisator.get("button.add"));
        sortFilterBTN.setText(localisator.get("button.sort_filter"));
        removeSelectedBTN.setText(localisator.get("button.remove_selected"));
    }

    @FunctionalInterface
    interface OnCommitCellModifier<T> {
        void modifyColumn(StudyGroupRow studyGroupRow, T newValue);
    }


}


