package com.example.edtunivavignon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Button sendButton;
    @FXML
    private StackPane topSpacer;
    @FXML
    private Label userNameLabel;
    @FXML
    private TextField usernameInput;
    @FXML
    private Pane middleSpacer;
    @FXML
    private Label passwordLabel;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private StackPane bottomSpacer;
    @FXML
    private AnchorPane root;
    @FXML
    private HBox userNameHbox;
    @FXML
    private HBox passwordHbox;
    @FXML
    private ImageView logo;
    @FXML
    private Label errorMsg;
    private Stage stage;
    private User user;

    @FXML
    public void initialize() {
        topSpacer.prefHeightProperty().bind(root.heightProperty().divide(10).multiply(5));
        bottomSpacer.prefHeightProperty().bind(root.heightProperty().divide(10).multiply(2));
        middleSpacer.prefHeightProperty().bind(root.heightProperty().divide(20));
        userNameHbox.prefHeightProperty().bind(root.heightProperty().divide(10));
        userNameHbox.prefWidthProperty().bind(root.widthProperty());
        passwordHbox.prefHeightProperty().bind(root.heightProperty().divide(10));
        passwordHbox.prefWidthProperty().bind(root.widthProperty());
        passwordLabel.prefHeightProperty().bind(passwordHbox.prefHeightProperty());
        passwordLabel.prefWidthProperty().bind(passwordHbox.prefWidthProperty().divide(10).multiply(3));
        passwordInput.prefHeightProperty().bind(passwordHbox.prefHeightProperty());
        passwordInput.prefWidthProperty().bind(passwordHbox.prefWidthProperty().divide(10).multiply(6));
        usernameInput.prefHeightProperty().bind(userNameHbox.prefHeightProperty());
        usernameInput.prefWidthProperty().bind(userNameHbox.prefWidthProperty().divide(10).multiply(6));
        userNameLabel.prefHeightProperty().bind(userNameHbox.prefHeightProperty());
        userNameLabel.prefWidthProperty().bind(userNameHbox.prefWidthProperty().divide(10).multiply(3));
        StackPane.setAlignment(logo, Pos.CENTER);
        StackPane.setAlignment(errorMsg,Pos.BOTTOM_RIGHT);
        passwordInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                login();
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onButtonClick(ActionEvent event) {
        login();
    }

    public void login() {
        UserDB userDB = new UserDB();
        user = userDB.getUser(usernameInput.getText(),passwordInput.getText());
        if (user == null) {
            errorMsg.setText("Identifiant ou mot de passe incorrect");
            return;
        }
        stage.setOnCloseRequest(null);
        stage.close();
    }

    public User getUser() {
        return user;
    }
}
