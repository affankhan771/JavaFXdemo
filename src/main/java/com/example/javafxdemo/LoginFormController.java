package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginFormController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    @FXML
    private Label messageLabel;

    // Reference to the primary stage (set from LoginPage)
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {
        // Configure Login Button Action
        loginButton.setOnAction(e -> handleLogin());

        // Configure Signup Button Action
        signupButton.setOnAction(e -> handleSignup());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isValidUser = DataOperations.verifyUserCredentials(username, password);

        if (isValidUser) {
            messageLabel.setText("");
            System.out.println("Login successful. Navigating to dashboard...");
            openDashboard();
        } else {
            messageLabel.setText("Invalid credentials. Please try again.");
        }
    }

    private void handleSignup() {
        System.out.println("Signup button clicked. Implement signup logic here.");
    }

    private void openDashboard() {
        System.out.println("Navigating to dashboard...");
        // Logic to navigate to the dashboard (similar to openDashboard in LoginPage)
        new DashboardView().openDashboard(primaryStage, new NavBarView().createNavBar(primaryStage), new SidebarView().createSidebar(primaryStage, (stage, index) -> {}));
    }
}
