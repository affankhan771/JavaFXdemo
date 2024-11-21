package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Label clickMeLabel;

    // Call this when a button is clicked
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        // Attempt to connect to the database when the button is clicked
        Connection connection = DatabaseConnection.connect();
        if (connection != null) {
            System.out.println("Database connected successfully.");
        } else {
            System.out.println("Database connection failed.");
        }
    }

    // Call this when the "Click Me" button is clicked
    @FXML
    protected void onClickMeButtonClick() {
        clickMeLabel.setText("Hello Duniya, kia hal chal!");

        // Create the table in the database
        DatabaseSetup.createTable();
    }
}
