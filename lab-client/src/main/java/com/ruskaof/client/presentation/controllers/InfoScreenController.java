package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.common.util.DataCantBeSentException;
import com.ruskaof.common.util.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InfoScreenController {


    @FXML
    TableView<Pair<String, String>> infoTW;
    private StudyGroupRow object;
    private ObservableList<Pair<String, String>> dataForTable;

    @FXML
    public void initialize() throws DataCantBeSentException {

        final TableColumn<Pair<String, String>, String> column = new TableColumn<>("parameter name");
        column.setCellValueFactory(new PropertyValueFactory<>("first"));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        final TableColumn<Pair<String, String>, String> column2 = new TableColumn<>("parameter");
        column2.setCellFactory(TextFieldTableCell.forTableColumn());
        column2.setCellValueFactory(new PropertyValueFactory<>("second"));


        infoTW.getColumns().add(column);
        infoTW.getColumns().add(column2);

    }

    public void setListOfObjectData() throws DataCantBeSentException {
        final List<Pair<String, String>> list = new ArrayList<>();
        list.add(new Pair<>("id", Integer.toString(object.getId())));
        list.add(new Pair<>("name", object.getName()));
        list.add(new Pair<>("x", Long.toString(object.getX())));
        list.add(new Pair<>("y", Double.toString(object.getY())));
        list.add(new Pair<>("creation date", object.getCreationDate().toString()));
        list.add(new Pair<>("students count", object.getStudentsCount().toString()));
        list.add(new Pair<>("form of education", object.getFormOfEducation()));
        list.add(new Pair<>("semester", object.getSemester()));
        list.add(new Pair<>("admin name", object.getAdminName()));
        list.add(new Pair<>("admin height", Integer.toString(object.getAdminHeight())));
        list.add(new Pair<>("admin nationality", object.getAdminNationality()));
        list.add(new Pair<>("admin x", Float.toString(object.getAdminX())));
        list.add(new Pair<>("admin y", Long.toString(object.getAdminY())));
        list.add(new Pair<>("admin location name", object.getAdminLocationName()));
        list.add(new Pair<>("author name", object.getAuthorName()));

        dataForTable = FXCollections.observableList(list);
        infoTW.setItems(dataForTable);
    }


    @FXML
    public void onOkClick(ActionEvent event) throws IOException {

        Navigator.navigateToMainScreen(event, this.getClass());

    }

    public void setObject(StudyGroupRow object) {
        this.object = object;
    }
}
