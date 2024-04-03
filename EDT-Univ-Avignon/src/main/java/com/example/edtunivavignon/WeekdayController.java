package com.example.edtunivavignon;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class WeekdayController implements CalendarController {
    @FXML
    private Label dayOfTheWeek;
    @FXML
    private Label date;
    @FXML
    private AnchorPane schedule;
    @FXML
    private AnchorPane labels;
    @FXML
    private AnchorPane root;
    private VBox intervals;
    private VBox courses;
    private EDTCalendar edtCalendar;
    private LocalDateTime currentlyDisplayed;
    private ArrayList<String> filteredCourses;
    private ArrayList<String> filteredGroups;
    private ArrayList<String> filteredRooms;
    private ArrayList<String> filteredTypes;
    @FXML
    public void initialize() {
        currentlyDisplayed = LocalDateTime.now();
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

    public Boolean checkOverlapping(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        if (start1.isBefore(end2) && end1.isAfter(start2)) {
            return true;
        }
        return false;
    }

    public Boolean checkOneOverlapping(ArrayList<Reservation> reservations, Reservation reservationToTest) {
        for (Reservation reservation:reservations
             ) {
            if (checkOverlapping(reservationToTest.getStart(),reservationToTest.getEnd(),reservation.getStart(),reservation.getEnd()))
                return true;
        }
        return false;
    }
    public ArrayList<ArrayList<Reservation>> getOverlappingReservations(ArrayList<Reservation> weeklyReservations) {
        ArrayList<ArrayList<Reservation>> groupsOfOverlapping = new ArrayList<>();
        int i = 0;
        while (i < weeklyReservations.size()) {
            ArrayList<Reservation> overlappingGroup = new ArrayList<>();
            overlappingGroup.add(weeklyReservations.get(i));
            i++;
            while (i < weeklyReservations.size() && checkOneOverlapping(overlappingGroup,weeklyReservations.get(i))) {
                overlappingGroup.add(weeklyReservations.get(i));
                i++;
            }
            groupsOfOverlapping.add(overlappingGroup);
        }
        return groupsOfOverlapping;
    }

    public Boolean isInList(ArrayList<String> filters, String check) {
        if (check == null) {
            return false;
        }
        for (String filter: filters
             ) {
            if (check.contains(filter)) {
                return true;
            }
        }
        return false;
    }

    public Boolean isInFilters(Reservation reservation) {
        if (filteredCourses != null && !filteredCourses.isEmpty()) {
            if (!isInList(filteredCourses,reservation.getNameOfReservation())) {
                return false;
            }
        }
        if (filteredGroups != null && !filteredGroups.isEmpty()) {
            if (reservation.getAttendingGroups() == null)
                return false;
            Boolean isValid = false;
            for (String group: reservation.getAttendingGroups()
                 ) {
                if (isInList(filteredGroups,group)) {
                    isValid = true;
                }
            }
            if (!isValid)
                return false;
        }
        if (filteredRooms != null && !filteredRooms.isEmpty()) {
            if (reservation.getRooms() == null)
                return false;
            Boolean isValid = false;
            for (String room: reservation.getRooms()
            ) {
                if (isInList(filteredRooms,room)) {
                    isValid = true;
                }
            }
            if (!isValid)
                return false;
        }
        if (filteredTypes != null && !filteredTypes.isEmpty()) {
            if (!isInList(filteredTypes,reservation.getType())) {
                return false;
            }
        }
        return true;
    }

    public void setDailyReservations(ArrayList<Reservation> weeklyReservations) throws IOException {
        courses.getChildren().clear();
        if (weeklyReservations.isEmpty()) return;
        courses.prefWidthProperty().bind(schedule.prefWidthProperty());
        courses.prefHeightProperty().bind(schedule.prefHeightProperty());
        courses.setPickOnBounds(false);
        ArrayList<ArrayList<Reservation>> overlaps = getOverlappingReservations(weeklyReservations);
        LocalDateTime previousReservationEnd = weeklyReservations.get(0).getStart().withHour(8).withMinute(0);
        for (ArrayList<Reservation> overlap: overlaps
             ) {
            int i = 0;
            while (i < overlap.size() && !isInFilters(overlap.get(i))) {
                i++;
            }
            if (i == overlap.size()) {
                continue;
            }
            LocalDateTime startOfHbox = overlap.get(i).getStart();
            long distanceToLastReservation = (int) (Duration.between(previousReservationEnd,startOfHbox).toMinutes() / 30);
            if (distanceToLastReservation > 0) {
                Region region = new Region();
                region.prefWidthProperty().bind(courses.prefWidthProperty());
                region.prefHeightProperty().bind(courses.prefHeightProperty().divide(24).multiply(distanceToLastReservation));
                region.setMouseTransparent(true);
                courses.getChildren().add(region);
            }
            previousReservationEnd = overlap.get(i).getEnd();
            HBox hBox = new HBox();
            hBox.prefWidthProperty().bind(courses.prefWidthProperty());
            for (Reservation reservation: overlap.subList(i,overlap.size())
                 ) {
                if (!isInFilters(reservation)) {
                    continue;
                }
                VBox vBox = new VBox();
                long distanceToStartOfHbox = (int) (Duration.between(startOfHbox,reservation.getStart()).toMinutes() / 30);
                if (distanceToStartOfHbox > 0) {
                    Region region = new Region();
                    region.prefWidthProperty().bind(courses.prefWidthProperty());
                    region.prefHeightProperty().bind(courses.prefHeightProperty().divide(24).multiply(distanceToStartOfHbox));
                    region.setMouseTransparent(true);
                    vBox.getChildren().add(region);
                }
                long reservationLength = (int) (Duration.between(reservation.getStart(),reservation.getEnd()).toMinutes() / 30);
                FXMLLoader fxmlLoader = new FXMLLoader(WeekdayController.class.getResource("reservation.fxml"));
                AnchorPane pane = fxmlLoader.load();
                ReservationController reservationController = fxmlLoader.getController();
                Tooltip tooltip = new Tooltip();
                tooltip.setText(reservation.toTooltip());
                tooltip.setShowDelay(javafx.util.Duration.seconds(0));
                tooltip.setHideDelay(javafx.util.Duration.seconds(0));
                Tooltip.install(pane,tooltip);
                pane.prefWidthProperty().bind(hBox.prefWidthProperty().divide(overlap.size()));
                pane.prefHeightProperty().bind(courses.prefHeightProperty().divide(24).multiply(reservationLength));
                pane.setStyle("-fx-background-color: lightblue;-fx-border-color: #8bd2f1;-fx-background-radius: 10; -fx-border-radius: 10; text-emphasis: white;");
                reservationController.setAll(reservation);
                vBox.getChildren().add(pane);
                hBox.getChildren().add(vBox);
                if (reservation.getEnd().isAfter(previousReservationEnd)) {
                    previousReservationEnd = reservation.getEnd();
                }
            }
            courses.getChildren().add(hBox);
        }
    }

    public void updateDisplay() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", Locale.FRENCH);
        setDayOfTheWeek(currentlyDisplayed.format(formatter));
        formatter = DateTimeFormatter.ofPattern("MMMM d");
        setDate(currentlyDisplayed.format(formatter));
        setDailyReservations(edtCalendar.findDailyReservations(currentlyDisplayed));

    }
    @Override
    public void setEdtToDisplay(String url) throws IOException {
        edtCalendar = ICSParser.readICS(url);
    }

    public void setEdtCalendar(EDTCalendar edtCalendar) {
        this.edtCalendar = edtCalendar;
    }

    public void setCurrentlyDisplayed(LocalDateTime currentlyDisplayed) {
        this.currentlyDisplayed = currentlyDisplayed;
    }

    @Override
    public void displayNext() throws IOException {
        currentlyDisplayed = currentlyDisplayed.plusDays(1);
        updateDisplay();
    }

    @Override
    public void displayToday() throws IOException {
        currentlyDisplayed = LocalDateTime.now();
        updateDisplay();
    }

    @Override
    public void displayPrevious() throws IOException {
        currentlyDisplayed = currentlyDisplayed.plusDays(-1);
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

    @Override
    public ArrayList<String> getCourses() {
        return edtCalendar.getCourses();
    }

    @Override
    public ArrayList<String> getGroups() {
        return edtCalendar.getGroups();
    }

    @Override
    public ArrayList<String> getRooms() {
        return edtCalendar.getRooms();
    }

    @Override
    public ArrayList<String> getTypes() {
        return edtCalendar.getTypes();
    }

    @Override
    public void setFilterCourses(ArrayList<String> courses) throws IOException {
        filteredCourses = courses;
        updateDisplay();
    }

    @Override
    public void setFilterGroups(ArrayList<String> groups) throws IOException {
        filteredGroups = groups;
        updateDisplay();
    }

    @Override
    public void setFilterRooms(ArrayList<String> rooms) throws IOException {
        filteredRooms = rooms;
        updateDisplay();
    }

    @Override
    public void setFilterTypes(ArrayList<String> types) throws IOException {
        filteredTypes = types;
        updateDisplay();
    }
}
