package com.ruskaof.client.presentation.controllers;

import com.ruskaof.client.ClientApi;
import com.ruskaof.client.data.StudyGroupRow;
import com.ruskaof.client.util.Localisator;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    private static final int TIME_TO_RELOAD = 3000;
    private static final double OBJECT_WIDTH_C = 2.6;
    private static final double OFFSET_FOR_OBJ = 2.2;

    @FXML
    private Button reloadBTN;
    @FXML
    private Pane pane;
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private volatile List<StudyGroupRow> currentData = new ArrayList<>();
    private volatile List<StudyGroupRow> updatedData = new ArrayList<>();
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

    @FXML
    public void initialize() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {

                            refresh();

                        }
                    });
                    try {
                        Thread.sleep(TIME_TO_RELOAD);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }


    public void setLocalisation() {
        final Localisator localisator = new Localisator();
        reloadBTN.setText(localisator.get("button.reload"));
    }

    private boolean objectIsClickedOn(StudyGroupRow object, double x, double y) {
        final double normalizedSize = object.getStudentsCount() / 10D;
        final double size = normalizedSize*OBJECT_WIDTH;
        return (x <= object.getX() + size / 2 && x >= object.getX() - size / 2)
                && (y <= object.getY() + size / 2 && y >= object.getY() - size / 2);
    }

    private void refresh() {
        updatedData = ClientApi.getInstance().getCurrentData();
        drawGroups();
        currentData = updatedData;
    }

    private void drawGroups() {
        for (StudyGroupRow studyGroupRow : currentData) {
            if (!updatedData.contains(studyGroupRow)) {
                removeGroup(studyGroupRow.getId());
            }
        }
        for (StudyGroupRow studyGroupRow : updatedData) {
            if (!currentData.contains(studyGroupRow)) {
                drawGroup(convertX(studyGroupRow.getX()), convertY(studyGroupRow.getY()), Color.valueOf(studyGroupRow.getColor()), studyGroupRow.getId(), studyGroupRow.getStudentsCount());
            }
        }

    }

    private void removeGroup(int id) {
        final Drawing drawing = drawings.get(id);
        drawings.remove(id);
        makeFade(drawing.circle1);
        makeFade(drawing.circle2);
        makeFade(drawing.rectangle1);
        makeFade(drawing.rectangle2);
    }

    private void makeFade(Node node) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setNode(node);
        fadeTransition.setOnFinished((it) -> pane.getChildren().remove(node));
        fadeTransition.play();
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

    private void drawGroup(double normalX, double normalY, Color color, int id, Integer studentsCount) {
        final double normalizedSize = studentsCount / 10D;
        final double size = normalizedSize*OBJECT_WIDTH;
        Circle circle1 = new Circle(normalX + size / OBJECT_WIDTH_C / 2, normalY - size / 2, size, color);
        Circle circle2 = new Circle(normalX - size / 2, normalY - size / 2, size, color);
        circle1.setRadius(CIRCLE_RADIUS*normalizedSize);
        circle2.setRadius(CIRCLE_RADIUS*normalizedSize);

        Rectangle rectangle1 = new Rectangle(normalX - size / 2, normalY - size / 2, size / OBJECT_WIDTH_C, size);
        Rectangle rectangle2 = new Rectangle(normalX - size / 2 + size / OFFSET_FOR_OBJ, normalY - size / 2, size / OBJECT_WIDTH_C, size);

        rectangle1.setFill(color);
        rectangle2.setFill(color);


        makeRotate(rectangle1);
        makeScalable(rectangle1);
        makeRotate(rectangle2);
        makeScalable(rectangle2);

        drawings.put(id, new Drawing(rectangle1, rectangle2, circle1, circle2));

        pane.getChildren().add(circle1);
        pane.getChildren().add(rectangle1);
        pane.getChildren().add(circle2);
        pane.getChildren().add(rectangle2);
    }

    private void makeScalable(Node node) {
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.setNode(node);
        scaleTransition.play();

    }

    private void makeRotate(Node node) {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(ANIM_DURATION));
        rotateTransition.setNode(node);
        rotateTransition.setByAngle(ANGLE_OF_ROTATION);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
        rotateTransition.play();

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
        private final Rectangle rectangle1;
        private final Rectangle rectangle2;
        private final Circle circle1;
        private final Circle circle2;


        Drawing(Rectangle rectangle1, Rectangle rectangle2, Circle circle1, Circle circle2) {
            this.rectangle1 = rectangle1;
            this.circle1 = circle1;
            this.rectangle2 = rectangle2;
            this.circle2 = circle2;
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
            return Objects.equals(rectangle1, drawing.rectangle1) && Objects.equals(rectangle2, drawing.rectangle2) && Objects.equals(circle1, drawing.circle1) && Objects.equals(circle2, drawing.circle2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rectangle1, rectangle2, circle1, circle2);
        }
    }
}
