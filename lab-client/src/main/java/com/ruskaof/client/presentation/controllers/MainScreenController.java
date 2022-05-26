package com.ruskaof.client.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainScreenController {
    @FXML
    AnchorPane tableView;
    @FXML
    AnchorPane visualisation;

    @FXML
    void navigateToTableView(ActionEvent event) throws IOException {
        visualisation.setVisible(false);
        visualisation.setManaged(false);

        tableView.setVisible(true);
        tableView.setManaged(true);
    }

    @FXML
    void navigateToVisualisation(ActionEvent event) throws IOException {
        tableView.setVisible(false);
        tableView.setManaged(false);

        visualisation.setVisible(true);
        visualisation.setManaged(true);
    }
}
