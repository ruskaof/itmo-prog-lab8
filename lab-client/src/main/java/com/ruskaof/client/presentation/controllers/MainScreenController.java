package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.util.Localisator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainScreenController {
    @FXML
    private AnchorPane tableView;
    @FXML
    private AnchorPane visualisation;
    @FXML
    private AnchorPane execute;
    @FXML
    private Button tableBTN;
    @FXML
    private Button objectsBTN;
    @FXML
    private Button scriptBTN;

    @FXML
    private TableViewScreenController tableViewController;
    @FXML
    private VisualisationScreenController visualisationController;
    @FXML
    private Label loginLabel;

    public TableViewScreenController getTableViewScreenController() {
        return tableViewController;
    }

    public VisualisationScreenController getVisualisationScreenController() {
        return visualisationController;
    }


    @FXML
    void initialize() {
        setLocalisation();
        loginLabel.setText("logined with: " + ClientApi.getInstance().getLogin());
        loginLabel.setTextFill(ClientApi.getInstance().getColor());
    }

    private void setLocalisation() {
        Localisator localisator = new Localisator();
        tableBTN.setText(localisator.get("button.table"));
        objectsBTN.setText(localisator.get("button.objects"));
        scriptBTN.setText(localisator.get("button.script"));
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

        visualisationController.initializee();
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
