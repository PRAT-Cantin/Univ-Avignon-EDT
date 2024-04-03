package com.example.edtunivavignon;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EDTUAPV extends Application { ;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EDTUAPV.class.getResource("EDT.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        EDTController edtController = fxmlLoader.getController();
        stage.getIcons().add(new Image(Objects.requireNonNull(EDTUAPV.class.getResourceAsStream("logo_uapv_bleu.png"))));
        stage.setTitle("EDT UAPV");
        stage.setScene(scene);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.F && keyEvent.isControlDown()) {
                    edtController.toFormation(new ActionEvent());
                }
                else if (keyEvent.getCode() == KeyCode.R && keyEvent.isControlDown()) {
                    edtController.toRooms(new ActionEvent());
                }
                else if (keyEvent.getCode() == KeyCode.P && keyEvent.isControlDown()) {
                    try {
                        edtController.toPersonal(new ActionEvent());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        stage.show();
        UserDB userDB = new UserDB();
        userDB.resetDB();
        userDB.selectAll();
        Stage loginModal = new Stage();
        loginModal.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader modalLoader = new FXMLLoader(EDTUAPV.class.getResource("login.fxml"));
        loginModal.setScene(new Scene(modalLoader.load()));
        LoginController loginController = modalLoader.getController();
        loginController.setStage(loginModal);
        loginModal.setOnCloseRequest(windowEvent -> {
            Platform.exit();
        });
        loginModal.showAndWait();
        User user = loginController.getUser();
        if (user != null)
            edtController.setUser(user);

    }

    public void shortcuts(KeyEvent keyEvent) {

    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}