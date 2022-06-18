package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.util.Localisator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class ExecuteScreenController {
    @FXML
    private VBox vBox;
    @FXML
    private Button executeBTN;
    @FXML
    private TextArea textArea;


    @FXML
    public void onClick(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(vBox.getScene().getWindow());

        if (selectedFile != null) {
            textArea.setText(ClientApi.getInstance().executeScript(selectedFile));
        }
    }

    @FXML
    void initialize() {
        setLocalisation();
    }

    public void setLocalisation() {
        Localisator localisator = new Localisator();
        executeBTN.setText(localisator.get("button.execute"));
    }

}
