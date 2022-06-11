package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.common.util.DataCantBeSentException;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;

public class VisualisationScreenController {
    private static final double objectHeight = 100D;
    private static final double objectWidth = 100D;

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private List<StudyGroupRow> currentData;

    @FXML
    public void initialize() throws DataCantBeSentException {
        gc = canvas.getGraphicsContext2D();

        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000);
                    refresh();
                }
            } catch (DataCantBeSentException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        setClickable();
        thread.start();

    }

    private boolean objectIsClickedOn(StudyGroupRow object, double x, double y) {
        return (x <= object.getX() + objectWidth / 2 && x >= object.getX() - objectWidth / 2)
                && (y <= object.getY() + objectHeight / 2 && y >= object.getY() - objectHeight / 2);
    }

    private void refresh() throws DataCantBeSentException {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawAxis();
        currentData = ClientApi.getInstance().getCurrentData();
        drawGroups(currentData);
    }

    private void drawGroups(List<StudyGroupRow> data) {
        gc.setFill(Color.RED);
        gc.setStroke(Color.RED);

        for (StudyGroupRow studyGroupRow : data) {
            gc.fillRect(convertX(studyGroupRow.getX()) - objectWidth / 2, convertY(studyGroupRow.getY()) - objectHeight / 2, objectWidth, objectWidth);
        }
    }

    private void setClickable() {
        canvas.setOnMouseClicked(event -> {
            final double x = event.getX() - canvas.getWidth() / 2;
            final double y = convertY(event.getY());

            System.out.println(x);
            System.out.println(y);
            currentData.stream().filter(it -> objectIsClickedOn(it, x, y)).findAny().ifPresent(it -> {
                try {
                    Navigator.navigateToInfoScreen(event.getSource(), this.getClass(), it);
                } catch (IOException | DataCantBeSentException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private void drawAxis() {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2);
        gc.strokeLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight());
    }

    private double convertX(double x) {
        return x + canvas.getWidth() / 2;
    }

    private double convertY(double y) {
        return -y + canvas.getHeight() / 2;
    }
}
