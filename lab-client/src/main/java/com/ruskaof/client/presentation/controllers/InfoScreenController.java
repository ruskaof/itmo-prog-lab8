package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.common.util.DataCantBeSentException;
import com.ruskaof.common.util.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
        final TableColumn<Pair<String, String>, String> column2 = new TableColumn<>("parameter");

        infoTW.getColumns().add(column);
        infoTW.getColumns().add(column2);

    }

    public void setListOfObjectData() throws DataCantBeSentException {
        final List<Pair<String, String>> list = new ArrayList<>();
        list.add(new Pair<>("id", Integer.toString(object.getId())));
        list.add(new Pair<>("name", object.getName()));
        list.add(new Pair<>("x", Long.toString(object.getX())));

        dataForTable = FXCollections.observableList(list);
        infoTW.setItems(dataForTable);
    }

    public void setObject(StudyGroupRow object) {
        this.object = object;
    }
}
