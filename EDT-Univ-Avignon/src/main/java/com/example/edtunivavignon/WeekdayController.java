package com.example.edtunivavignon;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class WeekdayController {
    @FXML
    private Text horaire;
    @FXML
    private Text salles;
    @FXML
    private Text info;

    @FXML
    public void initialize() {
        horaire.setText("feur");
        salles.setText("feur");
        info.setText("feur");
    }

    public void setHoraire(Text horaire) {
        this.horaire = horaire;
    }

    public void setSalles(Text salles) {
        this.salles = salles;
    }

    public void setInfo(Text info) {
        this.info = info;
    }

    public void setAll(String hours, String rooms, String other) {
        horaire.setText(hours);
        salles.setText(rooms);
        info.setText(other);
    }
}
