package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.client.util.Localisator;
import javafx.animation.FadeTransition;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class VisualisationScreenController {
    private static final double OBJECT_HEIGHT = 100D;
    private static final double OBJECT_WIDTH = 100D;
    private static final double CIRCLE_RADIUS = 10D;
    private static final double ANIM_DURATION = 1000D;
    private static final double ANGLE_OF_ROTATION = 360D;
    private static final double LINE_WIDTH = 3;

    @FXML
    private Button reloadBTN;
    @FXML
    private Pane pane;
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private List<StudyGroupRow> currentData = new ArrayList<>();
    private List<StudyGroupRow> updatedData = new ArrayList<>();
    private HashMap<Integer, Drawing> drawings = new HashMap<>();

    @FXML
    public void onRefrClick(ActionEvent event) {
        refresh();
    }


    public void initializee() {
        gc = canvas.getGraphicsContext2D();
        drawAxis();
        refresh();
        setClickable();
        setLocalisation();
    }

    private void setLocalisation() {
        final Localisator localisator = new Localisator();
        reloadBTN.setText(localisator.get("button.reload"));
    }

    private boolean objectIsClickedOn(StudyGroupRow object, double x, double y) {
        return (x <= object.getX() + OBJECT_WIDTH / 2 && x >= object.getX() - OBJECT_WIDTH / 2)
                && (y <= object.getY() + OBJECT_HEIGHT / 2 && y >= object.getY() - OBJECT_HEIGHT / 2);
    }

    private void refresh() {
        updatedData = ClientApi.getInstance().getCurrentData();
        drawGroups();
        currentData = updatedData;
    }

    private void drawGroups() {
        for (StudyGroupRow studyGroupRow : updatedData) {
            if (!currentData.contains(studyGroupRow)) {
                drawGroup(convertX(studyGroupRow.getX()), convertY(studyGroupRow.getY()), Color.valueOf(studyGroupRow.getColor()), studyGroupRow.getId());
            }
        }

        for (StudyGroupRow studyGroupRow : currentData) {
            if (!updatedData.contains(studyGroupRow)) {
                removeGroup(studyGroupRow.getId());
            }
        }
    }

    private void removeGroup(int id) {
        final Drawing drawing = drawings.get(id);
        drawings.remove(id);
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setNode(drawing.circle);
        fadeTransition.setOnFinished((it) -> pane.getChildren().remove(drawing.circle));
        FadeTransition fadeTransition2 = new FadeTransition();
        fadeTransition2.setFromValue(1);
        fadeTransition2.setToValue(0);
        fadeTransition2.setNode(drawing.rectangle);
        fadeTransition2.setOnFinished((it) -> pane.getChildren().remove(drawing.rectangle));
        fadeTransition.play();
        fadeTransition2.play();
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private void drawAxis() {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(LINE_WIDTH);
        gc.strokeLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2);
        gc.strokeLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight());
    }

    private void drawGroup(double normalX, double normalY, Color color, int id) {
        Circle circle = new Circle(normalX, normalY - OBJECT_HEIGHT / 2, OBJECT_WIDTH, color);
        circle.setRadius(CIRCLE_RADIUS);
        Rectangle rectangle = studyGroupRect(normalX, normalY, color);

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.setNode(rectangle);
        scaleTransition.play();

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(ANIM_DURATION));
        rotateTransition.setNode(rectangle);
        rotateTransition.setByAngle(ANGLE_OF_ROTATION);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();

        RotateTransition rotateTransition2 = new RotateTransition();
        rotateTransition2.setDuration(Duration.millis(ANIM_DURATION));
        rotateTransition2.setNode(circle);
        rotateTransition2.setByAngle(ANGLE_OF_ROTATION);
        rotateTransition2.setCycleCount(1);
        rotateTransition2.setAutoReverse(false);
        rotateTransition2.play();

        drawings.put(id, new Drawing(rectangle, circle));

        pane.getChildren().add(circle);
        pane.getChildren().add(rectangle);
    }

    private Rectangle studyGroupRect(double normalX, double normalY, Color color) {
        final Rectangle rectangle = new Rectangle(normalX - OBJECT_WIDTH / 2, normalY - OBJECT_HEIGHT / 2, OBJECT_WIDTH, OBJECT_WIDTH);
        rectangle.setFill(color);
        return rectangle;
    }

    private double convertX(double x) {
        return x + canvas.getWidth() / 2;
    }

    private double convertY(double y) {
        return -y + canvas.getHeight() / 2;
    }

    class Drawing {
        private final Rectangle rectangle;
        private final Circle circle;


        Drawing(Rectangle rectangle, Circle circle) {
            this.rectangle = rectangle;
            this.circle = circle;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Drawing drawing = (Drawing) o;
            return Objects.equals(rectangle, drawing.rectangle) && Objects.equals(circle, drawing.circle);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rectangle, circle);
        }
    }
}
