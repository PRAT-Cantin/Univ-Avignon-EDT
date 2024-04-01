package com.example.edtunivavignon;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.awt.Desktop;

public class EDTUAPV extends Application { ;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EDTUAPV.class.getResource("EDT.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        EDTController edtController = fxmlLoader.getController();
        stage.getIcons().add(new Image(Objects.requireNonNull(EDTUAPV.class.getResourceAsStream("logo_uapv_bleu.png"))));
        stage.setTitle("EDT UAPV");
        stage.setScene(scene);
        stage.show();
        UserDB userDB = new UserDB();
        //userDB.insert("Cantin","feur","https://edt-api.univ-avignon.fr/api/exportAgenda/tdoption/def502001eafd38c6be9b62798de135592ecdecec8d8f8c6dc24d4a29b99ab29b20b16c500d11e5fb9815a21952b048a697e9b8cd43bb905521c03c03793609b386e8fb17197b0ccafd5d21d3b2332e91120c702d6c26ed8");
        //userDB.insert("admin","admin","https://edt-api.univ-avignon.fr/api/exportAgenda/enseignant/def5020014cf744f63f7181931e243c5139c5d8427de488f3da5b30b52905edfe9de85e8da750e291f852c095f6fd05f93658cbbf3260bf1308a84c444accdb9ab8f67de5f5758e0b59200e3c78068a677fc5055644c4635");
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

    public static void main(String[] args) throws IOException {
        launch();
    }
}