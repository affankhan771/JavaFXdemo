package com.example.javafxdemo.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginFormView {

    public VBox createLoginForm(Stage primaryStage, LoginActionHandler loginHandler, SignupActionHandler signupHandler) {
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
        loginButton.setOnAction(e -> loginHandler.handleLogin(usernameField.getText(), passwordField.getText(), primaryStage));

        // Signup Button
        Button signupButton = new Button("Signup");
        signupButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-border-radius: 5px; -fx-background-radius: 5px;");
        signupButton.setOnMouseEntered(e -> signupButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;"));
        signupButton.setOnMouseExited(e -> signupButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;"));
        signupButton.setOnAction(e -> signupHandler.handleSignup(primaryStage));

        // Message Label
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");

        // Layout
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(40));
        layout.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, signupButton, messageLabel);
        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    @FunctionalInterface
    public interface LoginActionHandler {
        void handleLogin(String username, String password, Stage primaryStage);
    }

    @FunctionalInterface
    public interface SignupActionHandler {
        void handleSignup(Stage primaryStage);
    }
}
