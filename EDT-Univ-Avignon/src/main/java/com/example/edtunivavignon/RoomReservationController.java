package com.example.edtunivavignon;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RoomReservationController {
    @FXML
    private TextField eventName;
    @FXML
    private TextField teachers;
    @FXML
    private ComboBox rooms;
    @FXML
    private DatePicker eventDate;
    @FXML
    private ComboBox startTime;
    @FXML
    private ComboBox endTime;
    @FXML
    private ComboBox eventType;
    @FXML
    private TextField attendingGroups;
    @FXML
    private TextField attendingPromotions;
    @FXML
    private ColorPicker color;
    @FXML
    private TextArea memo;
    @FXML
    private Button addButton;
    @FXML
    private Label errorMsg;
    private HashMap<String,String> roomToURL;
    private String teacherName;
    private String currentRoom;
    private String owner;
    private Stage stage;
    private Boolean isRoomReservation;
    private EDTCalendar edtCalendar;

    public RoomReservationController(String teachers, String currentRoom, HashMap<String,String> roomToURL, String owner, Boolean isRoomReservation) throws IOException {
        teacherName = teachers;
        this.currentRoom = currentRoom;
        this.roomToURL = roomToURL;
        this.owner = owner;
        this.isRoomReservation = isRoomReservation;
        if (isRoomReservation) {
            edtCalendar = ICSParser.readICS(roomToURL.get(currentRoom));
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        this.teachers.setText(teacherName);
        this.rooms.setValue(currentRoom);
        for (Map.Entry<String,String> entry : roomToURL.entrySet()
        ) {
            rooms.getItems().add(entry.getKey());
        }
        eventType.getItems().add("TD");
        eventType.getItems().add("TP");
        eventType.getItems().add("CM");
        eventType.getItems().add("Evaluation");
        eventType.getItems().add("Conférence");
        eventType.getItems().add("Custom");

        LocalTime localTime = LocalTime.of(8,0,0);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        for (int i = 0; i < 24; i++) {
            startTime.getItems().add(localTime.format(dateTimeFormatter));
            endTime.getItems().add(localTime.format(dateTimeFormatter));
            localTime = localTime.plusMinutes(30);
        }

        addButton.setOnAction(actionEvent -> {
            addEvent();
        });
    }

    public void addEvent() {
        Boolean isValid = true;
        if (Objects.equals(eventName.getText(), "")) {
            isValid = false;
            eventName.setStyle("-fx-border-color: red");
        }
        else {
            eventName.setStyle("");
        }
        if (eventDate.getValue() == null) {
            isValid = false;
            eventDate.setStyle("-fx-border-color: red");
        }
        else {
            eventDate.setStyle("");
        }
        if (startTime.getValue() == null) {
            isValid = false;
            startTime.setStyle("-fx-border-color: red");
        }
        else {
            startTime.setStyle("");
        }
        if (endTime.getValue() == null) {
            isValid = false;
            endTime.setStyle("-fx-border-color: red");
        }
        else {
            endTime.setStyle("");
        }
        if (!isValid) {
            errorMsg.setText("Veuillez corriger les erreurs");
            return;
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd:");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm");
        LocalDateTime eventStart = LocalDateTime.parse((eventDate.getValue().format(dateFormatter)+startTime.getValue().toString()),dateTimeFormatter);
        LocalDateTime eventEnd = LocalDateTime.parse((eventDate.getValue().format(dateFormatter)+endTime.getValue().toString()),dateTimeFormatter);
        if (eventStart.isAfter(eventEnd) || eventStart.isEqual(eventEnd)) {
            isValid = false;
            startTime.setStyle("-fx-border-color: red");
            endTime.setStyle("-fx-border-color: red");
            errorMsg.setText("Heures dans le mauvais sens");
            return;
        }
        else {
            startTime.setStyle("");
            endTime.setStyle("");
        }
        System.out.println(isRoomReservation);
        System.out.println(edtCalendar.checkIfTaken(eventStart,eventEnd));
        if (isRoomReservation && edtCalendar.checkIfTaken(eventStart,eventEnd)) {
            isValid = false;
            errorMsg.setText("Salle déjà occupée");
            return;
        }
        if (isValid) {

            UserDB userDB = new UserDB();
            String roomList = null;
            if (rooms.getValue() != null) {
                roomList = rooms.getValue().toString();
            }
            String event = null;
            if (rooms.getValue() != null) {
                event = rooms.getValue().toString();
            }
            userDB.addEvent(
                    owner,
                    eventName.getText(),
                    roomList,
                    memo.getText(),
                    event,
                    color.getValue().toString(),
                    eventStart,
                    eventEnd,
                    teachers.getText(),
                    attendingGroups.getText(),
                    attendingPromotions.getText()
            );
            stage.close();
        }
    }
}
