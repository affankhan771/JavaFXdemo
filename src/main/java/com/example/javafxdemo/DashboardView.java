package com.example.javafxdemo;

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
