package com.example.edtunivavignon;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.Duration;
import java.time.LocalDateTime;

public class WeekdayController {
    @FXML
    private Label dayOfTheWeek;
    @FXML
    private Label date;
    @FXML
    private VBox schedule;
    @FXML
    AnchorPane labels;
    @FXML
    private VBox root;

    private VBox intervals;

    @FXML
    public void initialize() {
        Pane pane;
        schedule.prefWidthProperty().bind(root.prefWidthProperty());
        schedule.prefHeightProperty().bind(root.prefHeightProperty().subtract(labels.prefHeightProperty()));
        intervals = new VBox();
        intervals.prefWidthProperty().bind(schedule.prefWidthProperty());
        intervals.prefHeightProperty().bind(schedule.prefHeightProperty());
        int numOfPanes = 24;
        for (int i = 0; i < numOfPanes/2; i++) {
            for (int j = 0; j < 2; j++) {
                pane = new HBox();
                pane.setOpacity(0.5);
                pane.setStyle("-fx-border-color:black;-fx-border-style: dashed;");
                Pane finalPane = pane;
                pane.setOnMouseEntered((MouseEvent t) -> {
                    finalPane.setStyle("-fx-background-color:gray;-fx-border-color:black;-fx-border-style: dashed;");
                });
                if (!(i%2== 0)) {
                    pane.setStyle("-fx-background-color:lightgray;-fx-border-color:black;-fx-border-style: dashed;");
                    Pane finalPane1 = pane;
                    pane.setOnMouseExited((MouseEvent t) -> {
                        finalPane1.setStyle("-fx-background-color:lightgray;-fx-border-color:black;-fx-border-style: dashed;");
                    });
                }
                else {
                    Pane finalPane2 = pane;
                    pane.setOnMouseExited((MouseEvent t) -> {
                        finalPane2.setStyle("-fx-border-color:black;-fx-border-style: dashed;");
                    });
                }
                pane.setId(String.valueOf(i+j));
                pane.prefHeightProperty().bind(Bindings.divide(intervals.prefHeightProperty(),numOfPanes));
                pane.prefWidthProperty().bind(intervals.prefWidthProperty());
                intervals.getChildren().add(pane);
            }
        }
        schedule.getChildren().add(intervals);
    }

    public void setBinding(ReadOnlyDoubleProperty width,ReadOnlyDoubleProperty height) {
        root.prefWidthProperty().bind(width);
        root.prefHeightProperty().bind(height);
    }

    public void addReservation(Reservation reservation) {
        long dayLength = 720;
        long duration = Duration.between(reservation.getStart(),reservation.getEnd()).toMinutes();
        long distanceToStart = Duration.between(reservation.getStart().withHour(8).withMinute(0),reservation.getStart()).toMinutes();
        Pane pane = new Pane();
        int id = (int) (schedule.getPrefHeight() / dayLength * distanceToStart / 30);
        //pane.setLayoutY(intervals.getChildren().get(id).getLayoutY());
        pane.setManaged(false);
        pane.resize(schedule.getPrefWidth(),schedule.getPrefHeight() / dayLength * duration);
        pane.prefWidthProperty().bind(schedule.prefWidthProperty());
        pane.prefHeightProperty().bind(schedule.prefHeightProperty().divide(dayLength).multiply(duration));
        pane.layoutYProperty().bind(schedule.prefHeightProperty().divide(dayLength).multiply(distanceToStart));
        pane.setStyle("-fx-background-color:black");
        schedule.getChildren().add(pane);
    }
}
