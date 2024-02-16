package com.example.edtunivavignon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.MalformedURLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        ICSParser.ReadICS("https://edt-api.univ-avignon.fr/api/exportAgenda/salle/def502006b4030c83b0dfb3f68a7e2a6332163ebfe44f271bbbbfe7058026a23b378b3013879ba0eb3e74a96646b57e71540596ec871eb9e64826f5be474ea236e675a2cb8569dd7871641722e5a18e24b1cd24fae5a764c16028a");
        launch();
    }
}