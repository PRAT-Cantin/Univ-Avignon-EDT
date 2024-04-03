package com.example.edtunivavignon;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class MonthController implements CalendarController{
    @FXML
    AnchorPane root;
    @FXML
    GridPane days;
    private EDTCalendar edtCalendar;
    private LocalDateTime currentlyDisplayed;
    private ArrayList<StackPane> panes;

    @FXML
    public void initialize() {
        panes = new ArrayList<>();
        for (int row = 0; row < days.getRowCount(); row++) {
            for (int col = 0; col < days.getColumnCount(); col++) {
                StackPane pane = new StackPane();
                pane.setStyle("-fx-border-color: lightgray");
                panes.add(pane);
                days.add(pane,col,row);
            }
        }
    }

    public void updateDisplay() {
        for (StackPane pane: panes
             ) {
            pane.getChildren().clear();
        }
        ArrayList<ArrayList<ArrayList<Reservation>>> monthlyReservations = edtCalendar.findMonthlyReservations(currentlyDisplayed);
        LocalDateTime localDateTime = currentlyDisplayed.withDayOfMonth(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d");
        for (int i = 1; i < monthlyReservations.size()+1; i++) {
            for (int j = 0; j < monthlyReservations.get(i-1).size(); j++) {
                HBox hBox = new HBox();
                for (Reservation reservation: monthlyReservations.get(i-1).get(j)
                     ) {
                    Circle circle = new Circle(5);
                    Tooltip tooltip = new Tooltip();
                    tooltip.setShowDelay(Duration.millis(0));
                    tooltip.setText(reservation.toTooltip());
                    Tooltip.install(circle,tooltip);
                    hBox.getChildren().add(circle);
                }
                hBox.setPadding(new Insets(0,2,2,2));
                hBox.setAlignment(Pos.BOTTOM_RIGHT);
                panes.get((i*5)+j).getChildren().add(hBox);
                Label label = new Label();
                label.setText(localDateTime.format(dateTimeFormatter));
                StackPane.setAlignment(label,Pos.TOP_RIGHT);
                panes.get((i*5)+j).getChildren().add(label);
                if (hBox.getChildren().size() > 0) {
                    label = new Label();
                    label.setText(hBox.getChildren().size()+" séance(s)" );
                    label.setStyle("-fx-background-color: gray; -fx-background-radius: 5; -fx-text-fill: white");
                    label.setPadding(new Insets(2,2,2,2));
                    label.setAlignment(Pos.CENTER);
                    StackPane.setAlignment(label,Pos.TOP_LEFT);
                    panes.get((i*5)+j).getChildren().add(label);
                }
                localDateTime = localDateTime.plusDays(1);
            }
        }
    }
    @Override
    public void setEdtToDisplay(String url) throws IOException {
        edtCalendar = ICSParser.readICS(url);
    }

    @Override
    public void displayNext() throws IOException {
        currentlyDisplayed = currentlyDisplayed.plusMonths(1);
        updateDisplay();
    }

    @Override
    public void displayToday() throws IOException {
        currentlyDisplayed = LocalDateTime.now();
        updateDisplay();
    }

    @Override
    public void displayPrevious() throws IOException {
        currentlyDisplayed = currentlyDisplayed.plusMonths(-1);
        updateDisplay();
    }

    @Override
    public void displaySpecific(LocalDateTime localDateTime) throws IOException {
        currentlyDisplayed = localDateTime;
        updateDisplay();
    }

    @Override
    public LocalDateTime getDisplayedDate() {
        return currentlyDisplayed;
    }

    @Override
    public void setCustomCalendar(String customName) {
        edtCalendar.setCustomCalendar(customName);
    }
}
