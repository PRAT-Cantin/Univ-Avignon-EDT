package com.example.edtunivavignon;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class WeekdayController {
    @FXML
    private Label dayOfTheWeek;
    @FXML
    private Label date;
    @FXML
    private AnchorPane schedule;
    @FXML
    AnchorPane labels;
    @FXML
    private VBox root;
    private VBox intervals;
    private VBox courses;

    @FXML
    public void initialize() {
        Pane pane;
        schedule.prefWidthProperty().bind(root.prefWidthProperty());
        schedule.prefHeightProperty().bind(root.prefHeightProperty().subtract(labels.heightProperty()));
        intervals = new VBox();
        intervals.prefWidthProperty().bind(schedule.prefWidthProperty());
        intervals.prefHeightProperty().bind(schedule.prefHeightProperty());
        int numOfPanes = 24;
        for (int i = 0; i < numOfPanes/2; i++) {
            for (int j = 0; j < 2; j++) {
                pane = new HBox();
                pane.setOpacity(0.5);
                pane.setStyle("-fx-border-color:black;-fx-border-style: hidden hidden dashed hidden;");
                Pane finalPane = pane;
                pane.setOnMouseEntered((MouseEvent t) -> {
                    finalPane.setStyle("-fx-background-color:gray;-fx-border-color:black;-fx-border-style: hidden hidden dashed hidden;");
                });
                if (!(i%2== 0)) {
                    pane.setStyle("-fx-background-color:lightgray;-fx-border-color:black;-fx-border-style: hidden hidden dashed hidden;");
                    Pane finalPane1 = pane;
                    pane.setOnMouseExited((MouseEvent t) -> {
                        finalPane1.setStyle("-fx-background-color:lightgray;-fx-border-color:black;-fx-border-style: hidden hidden dashed hidden;");
                    });
                }
                else {
                    Pane finalPane2 = pane;
                    pane.setOnMouseExited((MouseEvent t) -> {
                        finalPane2.setStyle("-fx-border-color:black;-fx-border-style: hidden hidden dashed hidden;");
                    });
                }
                pane.setId(String.valueOf(i+j));
                pane.prefHeightProperty().bind(Bindings.divide(intervals.prefHeightProperty(),numOfPanes));
                pane.prefWidthProperty().bind(intervals.prefWidthProperty());
                intervals.getChildren().add(pane);
            }
        }
        schedule.getChildren().add(intervals);
        courses = new VBox();
        schedule.getChildren().add(courses);
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek.setText(dayOfTheWeek);
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public void setBinding(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height) {
        root.prefWidthProperty().bind(width);
        root.prefHeightProperty().bind(height);
    }

    public void setDailyReservations(ArrayList<Reservation> weeklyReservations) throws IOException {
        courses.getChildren().clear();
        if (weeklyReservations.isEmpty()) return;
        courses.prefWidthProperty().bind(schedule.prefWidthProperty());
        courses.prefHeightProperty().bind(schedule.prefHeightProperty());
        courses.setPickOnBounds(false);
        LocalDateTime previousReservationEnd = weeklyReservations.get(0).getStart().withHour(8).withMinute(0);
        long distanceToLastReservation;
        long reservationLength;
        Region region;
        AnchorPane pane;
        for (Reservation reservation : weeklyReservations
             ) {
            distanceToLastReservation = (int) (Duration.between(previousReservationEnd,reservation.getStart()).toMinutes() / 30);
            if (distanceToLastReservation > 0) {
                region = new Region();
                region.prefWidthProperty().bind(courses.prefWidthProperty());
                region.prefHeightProperty().bind(courses.prefHeightProperty().divide(24).multiply(distanceToLastReservation));
                region.setMouseTransparent(true);
                courses.getChildren().add(region);
            }
            previousReservationEnd = reservation.getEnd();
            reservationLength = (int) (Duration.between(reservation.getStart(),reservation.getEnd()).toMinutes() / 30);
            FXMLLoader fxmlLoader = new FXMLLoader(WeekdayController.class.getResource("reservation.fxml"));
            pane = fxmlLoader.load();
            ReservationController reservationController = fxmlLoader.getController();
            Tooltip tooltip = new Tooltip();
            tooltip.setText(reservation.toTooltip());
            tooltip.setShowDelay(javafx.util.Duration.seconds(0));
            tooltip.setHideDelay(javafx.util.Duration.seconds(0));
            Tooltip.install(pane,tooltip);
            pane.prefWidthProperty().bind(courses.prefWidthProperty());
            pane.prefHeightProperty().bind(courses.prefHeightProperty().divide(24).multiply(reservationLength));
            pane.setStyle("-fx-background-color: lightblue;-fx-border-color: #8bd2f1;-fx-background-radius: 10; -fx-border-radius: 10");
            reservationController.setAll(reservation);
            courses.getChildren().add(pane);
        }
    }
}
