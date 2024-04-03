package com.example.edtunivavignon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

public class WeekController implements CalendarController {
    @FXML
    private AnchorPane monday;
    @FXML
    private AnchorPane tuesday;
    @FXML
    private AnchorPane wednesday;
    @FXML
    private AnchorPane thursday;
    @FXML
    private AnchorPane friday;

    @FXML
    private AnchorPane root;
    private WeekdayController mondayController;
    private WeekdayController tuesdayController;
    private WeekdayController wednesdayController;
    private WeekdayController thursdayController;
    private WeekdayController fridayController;
    private EDTCalendar edtCalendar;
    private LocalDateTime currentlyDisplayed;

    @FXML
    public void initialize() throws IOException {
        //Set responsiveness
        monday.prefWidthProperty().bind(root.prefWidthProperty().divide(5));
        tuesday.prefWidthProperty().bind(root.prefWidthProperty().divide(5));
        wednesday.prefWidthProperty().bind(root.prefWidthProperty().divide(5));
        thursday.prefWidthProperty().bind(root.prefWidthProperty().divide(5));
        friday.prefWidthProperty().bind(root.prefWidthProperty().divide(5));

        monday.prefHeightProperty().bind(root.prefHeightProperty());
        tuesday.prefHeightProperty().bind(root.prefHeightProperty());
        wednesday.prefHeightProperty().bind(root.prefHeightProperty());
        thursday.prefHeightProperty().bind(root.prefHeightProperty());
        friday.prefHeightProperty().bind(root.prefHeightProperty());

        //Load WeekDays
        FXMLLoader mondayLoader = new FXMLLoader(WeekController.class.getResource("weekday.fxml"));
        monday.getChildren().add(mondayLoader.load());
        mondayController = mondayLoader.getController();
        mondayController.setBinding(monday.prefWidthProperty(),monday.prefHeightProperty());
        mondayController.setDayOfTheWeek("Lundi");

        FXMLLoader tuesdayLoader = new FXMLLoader(WeekController.class.getResource("weekday.fxml"));
        tuesday.getChildren().add(tuesdayLoader.load());
        tuesdayController = tuesdayLoader.getController();
        tuesdayController.setBinding(tuesday.prefWidthProperty(),tuesday.prefHeightProperty());
        tuesdayController.setDayOfTheWeek("Mardi");

        FXMLLoader wednesdayLoader = new FXMLLoader(WeekController.class.getResource("weekday.fxml"));
        wednesday.getChildren().add(wednesdayLoader.load());
        wednesdayController = wednesdayLoader.getController();
        wednesdayController.setBinding(wednesday.prefWidthProperty(),wednesday.prefHeightProperty());
        wednesdayController.setDayOfTheWeek("Mercredi");

        FXMLLoader thursdayLoader = new FXMLLoader(WeekController.class.getResource("weekday.fxml"));
        thursday.getChildren().add(thursdayLoader.load());
        thursdayController = thursdayLoader.getController();
        thursdayController.setBinding(thursday.prefWidthProperty(),thursday.prefHeightProperty());
        thursdayController.setDayOfTheWeek("Jeudi");

        FXMLLoader fridayLoader = new FXMLLoader(WeekController.class.getResource("weekday.fxml"));
        friday.getChildren().add(fridayLoader.load());
        fridayController = fridayLoader.getController();
        fridayController.setBinding(friday.prefWidthProperty(),friday.prefHeightProperty());
        fridayController.setDayOfTheWeek("Vendredi");
        currentlyDisplayed = LocalDateTime.now();
        setAllDates();
    }

    public void setAllDates() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d");
        LocalDateTime mondayTime = currentlyDisplayed.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        mondayController.setDate(mondayTime.format(dateTimeFormatter));
        LocalDateTime dateTime = mondayTime.plusDays(5).with(TemporalAdjusters.previousOrSame(DayOfWeek.TUESDAY));
        tuesdayController.setDate(dateTime.format(dateTimeFormatter));
        dateTime = mondayTime.plusDays(5).with(TemporalAdjusters.previousOrSame(DayOfWeek.WEDNESDAY));
        wednesdayController.setDate(dateTime.format(dateTimeFormatter));
        dateTime = mondayTime.plusDays(5).with(TemporalAdjusters.previousOrSame(DayOfWeek.THURSDAY));
        thursdayController.setDate(dateTime.format(dateTimeFormatter));
        dateTime = mondayTime.plusDays(5).with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY));
        fridayController.setDate(dateTime.format(dateTimeFormatter));
    }

    public void setAllReservations() throws IOException {
        setAllDates();
        ArrayList<ArrayList<Reservation>> weeklyReservations = edtCalendar.findWeeklyReservations(currentlyDisplayed);
        mondayController.setDailyReservations(weeklyReservations.get(0));
        mondayController.setCurrentlyDisplayed(currentlyDisplayed.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)));
        tuesdayController.setDailyReservations(weeklyReservations.get(1));
        tuesdayController.setCurrentlyDisplayed(currentlyDisplayed.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(5).with(TemporalAdjusters.previousOrSame(DayOfWeek.TUESDAY)));
        wednesdayController.setDailyReservations(weeklyReservations.get(2));
        wednesdayController.setCurrentlyDisplayed(currentlyDisplayed.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(5).with(TemporalAdjusters.previousOrSame(DayOfWeek.WEDNESDAY)));
        thursdayController.setDailyReservations(weeklyReservations.get(3));
        thursdayController.setCurrentlyDisplayed(currentlyDisplayed.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(5).with(TemporalAdjusters.previousOrSame(DayOfWeek.THURSDAY)));
        fridayController.setDailyReservations(weeklyReservations.get(4));
        fridayController.setCurrentlyDisplayed(currentlyDisplayed.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusDays(5).with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY)));
    }

    @Override
    public void setEdtToDisplay(String url) throws IOException {
        edtCalendar = ICSParser.readICS(url);
        mondayController.setEdtCalendar(edtCalendar);
        tuesdayController.setEdtCalendar(edtCalendar);
        wednesdayController.setEdtCalendar(edtCalendar);
        thursdayController.setEdtCalendar(edtCalendar);
        fridayController.setEdtCalendar(edtCalendar);
    }

    @Override
    public void displayNext() throws IOException {
        currentlyDisplayed = currentlyDisplayed.plusWeeks(1);
        setAllReservations();
    }

    @Override
    public void displayToday() throws IOException {
        currentlyDisplayed = LocalDateTime.now();
        setAllReservations();
    }

    @Override
    public void displayPrevious() throws IOException {
        currentlyDisplayed = currentlyDisplayed.plusWeeks(-1);
        setAllReservations();
    }

    @Override
    public void displaySpecific(LocalDateTime localDateTime) throws IOException {
        currentlyDisplayed = localDateTime;
        setAllReservations();
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
        mondayController.setFilterCourses(courses);
        tuesdayController.setFilterCourses(courses);
        wednesdayController.setFilterCourses(courses);
        thursdayController.setFilterCourses(courses);
        fridayController.setFilterCourses(courses);
    }

    @Override
    public void setFilterGroups(ArrayList<String> groups) throws IOException {
        mondayController.setFilterGroups(groups);
        tuesdayController.setFilterGroups(groups);
        wednesdayController.setFilterGroups(groups);
        thursdayController.setFilterGroups(groups);
        fridayController.setFilterGroups(groups);
    }

    @Override
    public void setFilterRooms(ArrayList<String> rooms) throws IOException {
        mondayController.setFilterRooms(rooms);
        tuesdayController.setFilterRooms(rooms);
        wednesdayController.setFilterRooms(rooms);
        thursdayController.setFilterRooms(rooms);
        fridayController.setFilterRooms(rooms);
    }

    @Override
    public void setFilterTypes(ArrayList<String> types) throws IOException {
        mondayController.setFilterTypes(types);
        tuesdayController.setFilterTypes(types);
        wednesdayController.setFilterTypes(types);
        thursdayController.setFilterTypes(types);
        fridayController.setFilterTypes(types);
    }
}