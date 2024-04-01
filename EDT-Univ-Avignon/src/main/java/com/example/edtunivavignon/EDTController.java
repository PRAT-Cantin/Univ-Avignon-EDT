package com.example.edtunivavignon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EDTController {
    @FXML
    private HBox controlsHbox;
    @FXML
    private Label leftArrow;
    @FXML
    private Button today;
    @FXML
    private Label rightArrow;
    @FXML
    private Label month;
    @FXML
    private ComboBox displayMode;
    @FXML
    private StackPane controls;
    @FXML
    private VBox vBox;
    @FXML
    private AnchorPane root;
    AnchorPane edtView;
    CalendarController edtController;
    private User user;



    @FXML
    public void initialize() throws IOException {
        vBox.prefWidthProperty().bind(root.widthProperty());
        vBox.prefHeightProperty().bind(root.heightProperty());
        controls.prefHeightProperty().bind(vBox.prefHeightProperty().divide(10));
        controls.setMinHeight(0);
        FXMLLoader fxmlLoader = new FXMLLoader(EDTController.class.getResource("weekView.fxml"));
        edtView = fxmlLoader.load();
        edtView.prefHeightProperty().bind(vBox.prefHeightProperty().divide(10).multiply(9));
        edtView.prefWidthProperty().bind(vBox.prefWidthProperty());
        edtView.setMinHeight(0);
        edtController = fxmlLoader.getController();
        vBox.getChildren().add(edtView);
        month.prefWidthProperty().bind(controls.prefWidthProperty());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        month.setText(LocalDateTime.now().format(dateTimeFormatter).toUpperCase());
        StackPane.setAlignment(controlsHbox, Pos.TOP_LEFT);
        StackPane.setAlignment(displayMode,Pos.TOP_RIGHT);
        StackPane.setAlignment(month,Pos.BOTTOM_CENTER);
    }

    public void setToToday(ActionEvent  event) throws IOException {
        edtController.displayToday();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        month.setText(edtController.getDisplayedDate().format(dateTimeFormatter).toUpperCase());
    }

    public void setToPrevious(MouseEvent  event) throws IOException {
        edtController.displayPrevious();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        month.setText(edtController.getDisplayedDate().format(dateTimeFormatter).toUpperCase());
    }

    public void setToNext(MouseEvent event) throws IOException {
        edtController.displayNext();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        month.setText(edtController.getDisplayedDate().format(dateTimeFormatter).toUpperCase());
    }

    public void setUser(User user) throws IOException {
        this.user = user;
        edtController.setEdtToDisplay(user.getEdtURL());
        edtController.displayToday();
    }
}
