package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class ExecuteScreenController {
    @FXML
    AnchorPane execute;
    @FXML
    HBox hbox;


    @FXML
    public void onClick(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(hbox.getScene().getWindow());

        if (selectedFile != null) {

            ClientApi.getInstance().executeScript(selectedFile);
        }
    }

}
