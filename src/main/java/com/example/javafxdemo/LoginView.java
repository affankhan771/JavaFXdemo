/*
package com.example.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView {

    private final DashboardView dashboardView; // Reference to DashboardView for navigation

    public LoginView(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    public void showLoginScreen(Stage primaryStage) {
        // Title Label
        Label titleLabel = new Label("Login Page");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Username Field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);
        usernameField.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                "-fx-prompt-text-fill: gray; -fx-border-color: #555555; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                "-fx-prompt-text-fill: gray; -fx-border-color: #555555; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-border-radius: 5px; -fx-background-radius: 5px;");
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;"));

        // Signup Button
        Button signupButton = new Button("Signup");
        signupButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-border-radius: 5px; -fx-background-radius: 5px;");
        signupButton.setOnMouseEntered(e -> signupButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;"));
        signupButton.setOnMouseExited(e -> signupButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;"));

        // Message Label
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        // Login Button Action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            boolean isValidUser = DataOperations.verifyUserCredentials(username, password);

            if (isValidUser) {
                // Navigate to the Dashboard
                dashboardView.openDashboard(primaryStage, new NavBarView().createNavBar(primaryStage),
                        new SidebarView().createSidebar(primaryStage, (stage, index) -> {}));
            } else {
                messageLabel.setText("Invalid credentials. Please try again.");
            }
        });

        // Layout
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, signupButton, messageLabel);
        layout.setAlignment(Pos.CENTER);

        // Background Image
        Image backgroundImage = new Image("file:C:\\Users\\admin\\IdeaProjects\\JavaFXdemo\\src\\main\\resources\\bg.png");
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        layout.setBackground(new Background(bgImage));

        // Scene
        Scene scene = new Scene(layout, 900, 600);

        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
*/
