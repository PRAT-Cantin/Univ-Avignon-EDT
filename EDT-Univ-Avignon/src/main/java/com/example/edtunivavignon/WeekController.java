package com.example.edtunivavignon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.LongAccumulator;

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

        //edtCalendar = ICSParser.readICS("https://edt-api.univ-avignon.fr/api/exportAgenda/tdoption/def50200ad5a0ca5125bdb750196290524cae727ac39a81f9db9e9741ca5b0964fb9817c6c12c28a5093cf2325a9c9db60b66bb0bac5d0ee31b5e5dacbad7e352956879ca36257c22197d8edc7087687f097470a2e168421ca548440a8933ed37b6b09d094b149");
        edtCalendar = ICSParser.readICS("https://edt-api.univ-avignon.fr/api/exportAgenda/tdoption/def502001eafd38c6be9b62798de135592ecdecec8d8f8c6dc24d4a29b99ab29b20b16c500d11e5fb9815a21952b048a697e9b8cd43bb905521c03c03793609b386e8fb17197b0ccafd5d21d3b2332e91120c702d6c26ed8");

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
    }

    public void setAllReservations() throws IOException {
        ArrayList<ArrayList<Reservation>> weeklyReservations = edtCalendar.findWeeklyReservations(currentlyDisplayed);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM d");
        mondayController.addDailyReservations(weeklyReservations.get(0));
        LocalDateTime mondayTime = currentlyDisplayed.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        mondayController.setDate(mondayTime.format(dateTimeFormatter));
        tuesdayController.addDailyReservations(weeklyReservations.get(1));
        LocalDateTime dateTime = mondayTime.plusDays(5).with(TemporalAdjusters.previousOrSame(DayOfWeek.TUESDAY));
        tuesdayController.setDate(dateTime.format(dateTimeFormatter));
        wednesdayController.addDailyReservations(weeklyReservations.get(2));
        dateTime = mondayTime.plusDays(5).with(TemporalAdjusters.previousOrSame(DayOfWeek.WEDNESDAY));
        wednesdayController.setDate(dateTime.format(dateTimeFormatter));
        thursdayController.addDailyReservations(weeklyReservations.get(3));
        dateTime = mondayTime.plusDays(5).with(TemporalAdjusters.previousOrSame(DayOfWeek.THURSDAY));
        thursdayController.setDate(dateTime.format(dateTimeFormatter));
        fridayController.addDailyReservations(weeklyReservations.get(4));
        dateTime = mondayTime.plusDays(5).with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY));
        fridayController.setDate(dateTime.format(dateTimeFormatter));
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
}