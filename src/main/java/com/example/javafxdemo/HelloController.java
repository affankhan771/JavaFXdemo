package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Label clickMeLabel;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onClickMeButtonClick() {clickMeLabel.setText("Hello Duniya, kia hal chal!");
    }
}