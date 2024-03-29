package com.example.edtunivavignon;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Objects;

public class ReservationController {

    @FXML
    private Label hours;
    @FXML
    private Label rooms;
    @FXML
    private Label information;
    @FXML
    private VBox vBox;
    @FXML
    private AnchorPane root;

    @FXML
    public void initialize() {
        vBox.prefHeightProperty().bind(root.prefHeightProperty());
        vBox.prefWidthProperty().bind(root.prefWidthProperty());
        information.prefHeightProperty().bind(vBox.prefHeightProperty().subtract(hours.heightProperty()).subtract(rooms.heightProperty()));
        information.prefWidthProperty().bind(vBox.prefWidthProperty());
        information.setMinHeight(0);
        rooms.setMinHeight(0);
        hours.setMinHeight(0);
    }

    public void setAll(Reservation reservation) {
        if (reservation.getHoliday()) {
            hours.setText("/");
            hours.prefWidthProperty().bind(root.prefWidthProperty());
            hours.setStyle("-fx-background-color: darkgray;-fx-text-fill: gray");
            root.setStyle("-fx-background-color: lightgray");
            rooms.setText("Férié");
            rooms.setStyle("-fx-text-fill: gray");
            information.setText("");
            return;
        }
        String startEnd = reservation.getStart().getHour() + ":" + reservation.getStart().getMinute();
        if (reservation.getStart().getMinute() == 0) {
            startEnd += "0";
        }
        startEnd += " - " + reservation.getEnd().getHour() + ":" + reservation.getEnd().getMinute();
        if (reservation.getEnd().getMinute() == 0) {
            startEnd += "0";
        }
        startEnd += " / ";
        if (reservation.getType() != null)
            startEnd += reservation.getType();
        hours.setText(startEnd);

        String allRooms = "";
        for (String room : reservation.getRooms()
             ) {
            allRooms += room;
        }

        if (Objects.equals(reservation.getType(), "Evaluation")) {
            root.setStyle("-fx-background-color: salmon;-fx-border-color: salmon;-fx-background-radius: 10; -fx-border-radius: 10");
            hours.setStyle("-fx-text-fill: white;-fx-font-weight: bold");
            rooms.setStyle("-fx-text-fill: white;-fx-font-weight: bold");
            information.setStyle("-fx-background-color: lightblue");
        }

        rooms.setText(allRooms);
        rooms.prefWidthProperty().bind(vBox.prefWidthProperty());

        String extraInfo = "Matière : " + reservation.getNameOfReservation() + "\n";

        extraInfo += listToString(reservation.getTeachers(),"Enseignant");
        extraInfo += listToString(reservation.getAttendingPromotions(),"Promotion");
        extraInfo += listToString(reservation.getAttendingGroups(),"TD");
        extraInfo += listToString(reservation.getRooms(),"Salle");
        if (reservation.getType() != null)
            extraInfo += "Type : " + reservation.getType() + "\n";
        if (reservation.getMemo() != null)
            extraInfo += reservation.getMemo();
        information.setText(extraInfo);
    }

    private String listToString(ArrayList<String> list, String name) {
        String extraInfo = "";
        if (list != null) {
            extraInfo += name;
            if (list.size() > 1) {
                extraInfo += "s";
            }
            extraInfo += " : ";
            for (String element: list
            ) {
                extraInfo += element;
            }
            extraInfo += "\n";
        }
        return extraInfo;
    }
}
