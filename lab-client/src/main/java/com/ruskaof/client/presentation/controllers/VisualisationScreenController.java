package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.common.util.DataCantBeSentException;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class VisualisationScreenController {
    private static final double objectHeight = 100D;
    private static final double objectWidth = 100D;

    @FXML
    private Button reloadBTN;
    @FXML
    private Pane pane;
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private List<StudyGroupRow> currentData;

    @FXML
    public void onRefrClick(ActionEvent event) throws DataCantBeSentException {
        refresh();
    }

    @FXML
    public void initialize() throws DataCantBeSentException {
        gc = canvas.getGraphicsContext2D();

        refresh();

        setClickable();

    }

    private void setLocalisation() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("labels", ClientApi.getLocale());
        reloadBTN.setText(resourceBundle.getString("button.reload"));
    }

    private boolean objectIsClickedOn(StudyGroupRow object, double x, double y) {
        return (x <= object.getX() + objectWidth / 2 && x >= object.getX() - objectWidth / 2)
                && (y <= object.getY() + objectHeight / 2 && y >= object.getY() - objectHeight / 2);
    }

    private void refresh() throws DataCantBeSentException {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        pane.getChildren().clear();
        drawAxis();
        currentData = ClientApi.getInstance().getCurrentData();
        drawGroups(currentData);
    }

    private void drawGroups(List<StudyGroupRow> data) {
        gc.setFill(Color.RED);
        gc.setStroke(Color.RED);


        for (StudyGroupRow studyGroupRow : data) {
            Rectangle rectangle = new Rectangle(convertX(studyGroupRow.getX()) - objectWidth / 2, convertY(studyGroupRow.getY()) - objectHeight / 2, objectWidth, objectWidth);
            rectangle.setFill(Color.RED);

            Circle circle = new Circle(convertX(studyGroupRow.getX()), convertY(studyGroupRow.getY()) - objectHeight / 2, objectWidth, Color.BLACK);
            circle.setRadius(10);

            ScaleTransition scaleTransition = new ScaleTransition();
            RotateTransition rotateTransition = new RotateTransition();
            rotateTransition.setDuration(Duration.millis(1000));
            rotateTransition.setNode(rectangle);
            rotateTransition.setByAngle(360);
            rotateTransition.setCycleCount(1);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();

            RotateTransition rotateTransition2 = new RotateTransition();
            rotateTransition2.setDuration(Duration.millis(1000));
            rotateTransition2.setNode(circle);
            rotateTransition2.setByAngle(360);
            rotateTransition2.setCycleCount(1);
            rotateTransition2.setAutoReverse(false);
            rotateTransition2.play();

            pane.getChildren().add(circle);
            pane.getChildren().add(rectangle);
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
