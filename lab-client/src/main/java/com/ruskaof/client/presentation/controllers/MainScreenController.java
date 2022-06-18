package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.util.Localisation;
import com.ruskaof.client.util.Localisator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

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
    private ComboBox<String> languageCB;

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
        languageCB.setItems(FXCollections.observableArrayList(
                "русский",
                "Română",
                "Français",
                "español"
        ));
    }

    @FXML
    private void onLangChange() {
        ClientApi.getInstance().setLocalisation(Objects.requireNonNull(parseLocal(languageCB.getValue())));
        setLocalisation();
        visualisationController.setLocalisation();
        tableViewController.setLocalisation();
    }

    private Localisation parseLocal(String s) {
        Localisation toRet = null;
        switch (s) {
            case "русский":
                toRet = Localisation.RUSSIAN;
                break;
            case "Română":
                toRet = Localisation.ROMANIAN;
                break;
            case "Français":
                toRet = Localisation.FRENCH;
                break;
            case "español":
                toRet = Localisation.SPANISH;
                break;
            default: break;
        }
        return toRet;
    }

    public void setLocalisation() {
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
