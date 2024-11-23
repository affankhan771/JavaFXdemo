package com.example.javafxdemo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardView {

    public void openDashboard(Stage primaryStage, HBox navBar, VBox sidebar) {

        // Load the Dashboard Screen FXML
        BorderPane dashboardContent;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxdemo/DashboardScreen.fxml"));
            dashboardContent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load Dashboard Screen FXML: " + e.getMessage());
        }

        // Main Layout (BorderPane with sidebar and navBar)
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(navBar);      // Top: NavBar
        mainLayout.setLeft(sidebar);   // Left: Sidebar
        mainLayout.setCenter(dashboardContent); // Center: Dashboard Content from FXML

        // Scene for Dashboard
        Scene dashboardScene = new Scene(mainLayout, 900, 600);

        // Update Stage with Dashboard Scene
        primaryStage.setScene(dashboardScene);
        primaryStage.setTitle("Dashboard");
    }
}
/*

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DashboardView {

    public void openDashboard(Stage primaryStage, HBox navBar, VBox sidebar) {

        // Main Content Area (initial placeholder)
        Label welcomeLabel = new Label("Welcome to the Dashboard!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        VBox contentArea = new VBox(welcomeLabel);
        contentArea.setAlignment(Pos.CENTER);
        contentArea.setStyle("-fx-background-color: black;");
        VBox.setVgrow(contentArea, Priority.ALWAYS);

        // Main Layout (BorderPane with sidebar)
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(navBar);  // Top: NavBar
        mainLayout.setLeft(sidebar);  // Left: Sidebar
        mainLayout.setCenter(contentArea);  // Center: Content Area

        // Scene for Dashboard
        Scene dashboardScene = new Scene(mainLayout, 900, 600);

        // Update Stage with Dashboard Scene
        primaryStage.setScene(dashboardScene);
        primaryStage.setTitle("Dashboard");


    }
}
*/