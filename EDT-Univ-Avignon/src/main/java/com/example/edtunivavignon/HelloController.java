package com.example.edtunivavignon;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HelloController {
    @FXML
    private Pane monday;
    @FXML
    private AnchorPane tuesday;
    @FXML
    private AnchorPane wednesday;
    @FXML
    private AnchorPane thursday;
    @FXML
    private AnchorPane friday;

    @FXML
    private VBox root;

    private WeekdayController mondayController;
    private WeekdayController tuesdayController;
    private WeekdayController wednesdayController;
    private WeekdayController thursdayController;
    private WeekdayController fridayController;
    private EDTCalendar edtCalendar;

    @FXML
    public void initialize() throws IOException {
        edtCalendar = ICSParser.readICS("https://edt-api.univ-avignon.fr/api/exportAgenda/tdoption/def502001eafd38c6be9b62798de135592ecdecec8d8f8c6dc24d4a29b99ab29b20b16c500d11e5fb9815a21952b048a697e9b8cd43bb905521c03c03793609b386e8fb17197b0ccafd5d21d3b2332e91120c702d6c26ed8");

        FXMLLoader mondayLoader = new FXMLLoader(HelloController.class.getResource("weekday.fxml"));
        //monday.getChildren().add(mondayLoader.load());
        mondayController = mondayLoader.getController();
        FXMLLoader tuesdayLoader = new FXMLLoader(HelloController.class.getResource("weekday.fxml"));
        tuesday.getChildren().add(tuesdayLoader.load());
        tuesdayController = tuesdayLoader.getController();
        FXMLLoader wednesdayLoader = new FXMLLoader(HelloController.class.getResource("weekday.fxml"));
        wednesday.getChildren().add(wednesdayLoader.load());
        wednesdayController = wednesdayLoader.getController();
        FXMLLoader thursdayLoader = new FXMLLoader(HelloController.class.getResource("weekday.fxml"));
        thursday.getChildren().add(thursdayLoader.load());
        thursdayController = thursdayLoader.getController();
        FXMLLoader fridayLoader = new FXMLLoader(HelloController.class.getResource("weekday.fxml"));
        friday.getChildren().add(fridayLoader.load());
        fridayController = fridayLoader.getController();
    }
}