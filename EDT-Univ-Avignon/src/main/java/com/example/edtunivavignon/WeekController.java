package com.example.edtunivavignon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class WeekController {
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

    @FXML
    public void initialize() throws IOException {
        //Set responsiveness
        monday.prefWidthProperty().bind(root.widthProperty().divide(5));
        tuesday.prefWidthProperty().bind(root.widthProperty().divide(5));
        wednesday.prefWidthProperty().bind(root.widthProperty().divide(5));
        thursday.prefWidthProperty().bind(root.widthProperty().divide(5));
        friday.prefWidthProperty().bind(root.widthProperty().divide(5));

        monday.prefHeightProperty().bind(root.heightProperty());
        tuesday.prefHeightProperty().bind(root.heightProperty());
        wednesday.prefHeightProperty().bind(root.heightProperty());
        thursday.prefHeightProperty().bind(root.heightProperty());
        friday.prefHeightProperty().bind(root.heightProperty());

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
    }

    @FXML
    public void onTestButtonClicked(MouseEvent t) throws IOException {
        ArrayList<ArrayList<Reservation>> weeklyReservations = edtCalendar.findWeeklyReservations(LocalDateTime.now().plusWeeks(0));
        mondayController.addDailyReservations(weeklyReservations.get(0));
        tuesdayController.addDailyReservations(weeklyReservations.get(1));
        wednesdayController.addDailyReservations(weeklyReservations.get(2));
        thursdayController.addDailyReservations(weeklyReservations.get(3));
        fridayController.addDailyReservations(weeklyReservations.get(4));
    }
}