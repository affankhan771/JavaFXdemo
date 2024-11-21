package com.example.javafxdemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginPage extends Application {
    @Override
    public void start(Stage primaryStage) {
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

            if (username.equals("admin") && password.equals("password")) {
                // Open the next screen
                openDashboard(primaryStage);
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
        Image backgroundImage = new Image("file:C:\\Users\\admin\\IdeaProjects\\JavaFXdemo\\src\\main\\resources\\bg.png"); // Replace with your image path
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

    private void openDashboard(Stage primaryStage) {
        // Navigation Bar (unchanged)
        HBox navBar = getNavBar(primaryStage);

        // Sidebar
        VBox sidebar = getSidebar(primaryStage);

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


    // Function to show screen content based on clicked option
    private void showScreenContent(Stage primaryStage, int optionIndex) {
        VBox contentArea = new VBox();
        contentArea.setAlignment(Pos.CENTER);
        contentArea.setStyle("-fx-background-color: black; -fx-padding: 20px;");
        Label screenLabel = new Label("Content for option " + (optionIndex + 1));
        screenLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        contentArea.getChildren().add(screenLabel);

        // Main Layout with Sidebar
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(getNavBar(primaryStage));  // Reuse NavBar
        mainLayout.setLeft(getSidebar(primaryStage));  // Reuse Sidebar
        mainLayout.setCenter(contentArea);  // Update content area

        // Update Scene with new content
        Scene newScene = new Scene(mainLayout, 900, 600);
        primaryStage.setScene(newScene);
    }

    // Reusable NavBar method (for reusing in showScreenContent)
    private HBox getNavBar(Stage primaryStage) {
        HBox navBar = new HBox(20);
        navBar.setPadding(new Insets(10));
        navBar.setStyle("-fx-background-color: #333333; -fx-padding: 10px;");
        navBar.setAlignment(Pos.CENTER_LEFT);

        // Company Logo
        Label logo = new Label("MyCompany");
        logo.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        // Profile Circle (V Button)
        Button profileButton = new Button("V");
        profileButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-font-weight: bold; -fx-border-radius: 50px; -fx-background-radius: 50px; -fx-min-width: 40px; -fx-min-height: 40px;");
        profileButton.setOnMouseEntered(e -> profileButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;"));
        profileButton.setOnMouseExited(e -> profileButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;"));

        // Dropdown Menu for Logout
        ContextMenu dropdownMenu = new ContextMenu();
        MenuItem logoutItem = new MenuItem("Logout");
        logoutItem.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
        logoutItem.setOnAction(e -> start(primaryStage)); // Return to login screen
        dropdownMenu.getItems().add(logoutItem);

        profileButton.setOnAction(e -> dropdownMenu.show(profileButton, javafx.geometry.Side.BOTTOM, 0, 0));

        // Add logo and profile button to the NavBar
        navBar.getChildren().addAll(logo, new Region(), profileButton);
        HBox.setHgrow(navBar.getChildren().get(1), Priority.ALWAYS); // Push profile button to the right

        return navBar;
    }


    // Reusable Sidebar method (for reusing in showScreenContent)
    private VBox getSidebar(Stage primaryStage) {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20, 10, 10, 10));
        sidebar.setStyle("-fx-background-color: #333333; -fx-padding: 20px;");
        sidebar.setPrefWidth(200);
        sidebar.setAlignment(Pos.TOP_CENTER);

        // Sidebar Options (6 random options)
        String[] sidebarOptions = {"ABC", "ABD", "XYZ", "DEF", "GHI", "JKL"};
        Button[] optionButtons = new Button[6];

        for (int i = 0; i < sidebarOptions.length; i++) {
            Button optionButton = new Button(sidebarOptions[i]);  // Extract the button into a local variable
            optionButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 14px; " +
                    "-fx-font-weight: bold; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-min-width: 160px;");

            // Hover effect for the button
            optionButton.setOnMouseEntered(e -> optionButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;"));
            optionButton.setOnMouseExited(e -> optionButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;"));

            // Setting action for each button (switch screen content)
            final int index = i;  // Capture the current index to use in the event handler
            optionButton.setOnAction(e -> showScreenContent(primaryStage, index));

            // Add the button to the array and the sidebar
            optionButtons[i] = optionButton;
        }

        // Add buttons to sidebar
        sidebar.getChildren().addAll(optionButtons);

        return sidebar;
    }




    public static void main(String[] args) {
        launch(args);
    }
}
