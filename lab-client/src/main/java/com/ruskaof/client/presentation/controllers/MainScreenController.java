package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.util.ResourceBundle;

public class MainScreenController {
    @FXML
    AnchorPane tableView;
    @FXML
    AnchorPane visualisation;
    @FXML
    AnchorPane execute;
    @FXML
    Button tableBTN;
    @FXML
    Button objectsBTN;
    @FXML
    Button scriptBTN;

    @FXML
    private TableViewScreenController tableViewController;
    @FXML
    private VisualisationScreenController visualisationController;

    public TableViewScreenController getTableViewScreenController() {
        return tableViewController;
    }

    public VisualisationScreenController getVisualisationScreenController() {
        return visualisationController;
    }


    @FXML
    void initialize() {
        setLocalisation();
    }

    private void setLocalisation() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", ClientApi.getLocale());
        tableBTN.setText(resourceBundle.getString("button.table"));
        objectsBTN.setText(resourceBundle.getString("button.objects"));
        scriptBTN.setText(resourceBundle.getString("button.script"));
    }

    @FXML
    void navigateToTableView(ActionEvent event) {
        visualisation.setVisible(false);
        visualisation.setManaged(false);
        execute.setVisible(false);
        execute.setManaged(false);

        tableView.setVisible(true);
        tableView.setManaged(true);
    }

    @FXML
    void navigateToVisualisation(ActionEvent event) {
        tableView.setVisible(false);
        tableView.setManaged(false);
        execute.setVisible(false);
        execute.setManaged(false);

        visualisation.setVisible(true);
        visualisation.setManaged(true);
    }

    @FXML
    void navigateToExecute(ActionEvent event) {
        tableView.setVisible(false);
        tableView.setManaged(false);
        visualisation.setVisible(false);
        visualisation.setManaged(false);

        execute.setVisible(true);
        execute.setManaged(true);
    }
}
