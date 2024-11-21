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

        if (optionIndex == 0) {  // Option 1 - Idea Submission screen
            contentArea.getChildren().add(createIdeaSubmissionForm(primaryStage));
        } else if (optionIndex == 1) {
            contentArea.getChildren().add(createIdeaTrackingScreen(primaryStage));
        } else {
            // Default content for other options
            Label screenLabel = new Label("Content for option " + (optionIndex + 1));
            screenLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
            contentArea.getChildren().add(screenLabel);
        }

        // Main Layout with Sidebar
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(getNavBar(primaryStage));  // Reuse NavBar
        mainLayout.setLeft(getSidebar(primaryStage));  // Reuse Sidebar
        mainLayout.setCenter(contentArea);  // Update content area

        // Update Scene with new content
        Scene newScene = new Scene(mainLayout, 900, 600);
        primaryStage.setScene(newScene);
    }

    private VBox createIdeaSubmissionForm(Stage primaryStage) {
        // Form container
        VBox formArea = new VBox(15);
        formArea.setStyle("-fx-background-color: black; -fx-padding: 20px;");
        formArea.setAlignment(Pos.CENTER);

        // Title Label
        Label titleLabel = new Label("Drug Submission Form");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Drug Name
        Label drugNameLabel = new Label("Drug Name:");
        drugNameLabel.setStyle("-fx-text-fill: white;");
        TextField drugNameField = new TextField();
        drugNameField.setPromptText("Enter the drug name");

        // Description
        Label descriptionLabel = new Label("Description:");
        descriptionLabel.setStyle("-fx-text-fill: white;");
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Enter a brief description of the drug");
        descriptionField.setWrapText(true);
        descriptionField.setPrefHeight(100);

        // Dropdown for Categories
        Label categoryLabel = new Label("Drug Category:");
        categoryLabel.setStyle("-fx-text-fill: white;");
        ComboBox<String> categoryDropdown = new ComboBox<>();
        categoryDropdown.getItems().addAll("Analgesic", "Antibiotic", "Antiseptic", "Vaccine", "Others");
        categoryDropdown.setPromptText("Select a category");

        // Chemical Formula
        Label formulaLabel = new Label("Chemical Formula:");
        formulaLabel.setStyle("-fx-text-fill: white;");
        TextField formulaField = new TextField();
        formulaField.setPromptText("Enter the chemical formula");

        // Estimated Price
        Label priceLabel = new Label("Estimated Price (USD):");
        priceLabel.setStyle("-fx-text-fill: white;");
        TextField priceField = new TextField();
        priceField.setPromptText("Enter the estimated price");
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                priceField.setText(newValue.replaceAll("[^\\d.]", "")); // Allow only numbers and a single decimal
            }
        });

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 14px;");
        submitButton.setOnAction(e -> {
            // Collect input values
            String drugName = drugNameField.getText();
            String description = descriptionField.getText();
            String category = categoryDropdown.getValue();
            String formula = formulaField.getText();
            String price = priceField.getText();

            // Basic Validation
            if (drugName.isEmpty() || description.isEmpty() || category == null || formula.isEmpty() || price.isEmpty()) {
                System.out.println("Please fill in all fields.");
            } else {
                System.out.println("Drug Submitted Successfully:");
                System.out.println("Name: " + drugName);
                System.out.println("Description: " + description);
                System.out.println("Category: " + category);
                System.out.println("Formula: " + formula);
                System.out.println("Estimated Price: " + price);
            }
        });

        // Adding components to form
        formArea.getChildren().addAll(
                titleLabel,
                drugNameLabel, drugNameField,
                descriptionLabel, descriptionField,
                categoryLabel, categoryDropdown,
                formulaLabel, formulaField,
                priceLabel, priceField,
                submitButton
        );

        return formArea;
    }

    private VBox createIdeaTrackingScreen(Stage primaryStage) {
        // Container for the Idea Tracking Screen
        VBox trackingArea = new VBox(20);
        trackingArea.setStyle("-fx-background-color: black; -fx-padding: 20px;");
        trackingArea.setAlignment(Pos.TOP_CENTER);

        // Title Label
        Label titleLabel = new Label("Idea Tracking");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Dropdown for Selecting an Idea
        Label selectIdeaLabel = new Label("Select Idea:");
        selectIdeaLabel.setStyle("-fx-text-fill: white;");
        ComboBox<String> ideaDropdown = new ComboBox<>();
        ideaDropdown.getItems().addAll("Idea 1", "Idea 2", "Idea 3"); // Replace with dynamic list of ideas
        ideaDropdown.setPromptText("Choose an idea to track");

        // Container for Roadmap Graph
        ScrollPane roadmapContainer = new ScrollPane();
        roadmapContainer.setFitToWidth(true);
        roadmapContainer.setStyle("-fx-background-color: black; -fx-border-color: #555555; -fx-border-width: 2px;");
        VBox roadmapGraph = new VBox(20);
        roadmapGraph.setAlignment(Pos.CENTER);
        roadmapGraph.setStyle("-fx-padding: 20px;");

        // Define the 7 stages for the roadmap
        String[] stages = {
                "Idea Generated",
                "Approval by M-Level Officers",
                "Approval by C-Level Officers",
                "Regulatory Compliance",
                "Sales Forecast",
                "Testing",
                "Launch"
        };

        // Dynamic Nodes for Each Stage
        for (int i = 0; i < stages.length; i++) {
            // Node Container
            HBox stageNode = new HBox(10);
            stageNode.setAlignment(Pos.CENTER_LEFT);
            stageNode.setStyle("-fx-padding: 10px;");

            // Stage Name
            Label stageLabel = new Label((i + 1) + ". " + stages[i]);
            stageLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

            // Checkbox for Completion
            CheckBox stageCheckbox = new CheckBox();
            stageCheckbox.setStyle("-fx-border-color: white; -fx-border-width: 2px;");
            stageCheckbox.setDisable(true); // Initially disabled; can be enabled dynamically

            // Add components to stage node
            stageNode.getChildren().addAll(stageCheckbox, stageLabel);

            // Add connecting line if not the last stage
            if (i < stages.length - 1) {
                VBox connection = new VBox();
                connection.setStyle("-fx-background-color: white; -fx-width: 2px; -fx-height: 20px;");
                roadmapGraph.getChildren().addAll(stageNode, connection);
            } else {
                roadmapGraph.getChildren().add(stageNode);
            }
        }

        // Add Roadmap Graph to ScrollPane
        roadmapContainer.setContent(roadmapGraph);

        // Handle Dropdown Selection
        ideaDropdown.setOnAction(e -> {
            String selectedIdea = ideaDropdown.getValue();
            if (selectedIdea != null) {
                System.out.println("Tracking progress for: " + selectedIdea);
                // Update stage checkboxes dynamically based on selected idea's progress
                updateRoadmapProgress(roadmapGraph, selectedIdea);
            }
        });

        // Add components to the Tracking Screen
        trackingArea.getChildren().addAll(titleLabel, selectIdeaLabel, ideaDropdown, roadmapContainer);

        return trackingArea;
    }

    private void updateRoadmapProgress(VBox roadmapGraph, String selectedIdea) {
        // Mock data for progress; replace with real data from database or backend
        int completedStages = switch (selectedIdea) {
            case "Idea 1" -> 3; // Idea 1 completed 3 stages
            case "Idea 2" -> 5; // Idea 2 completed 5 stages
            case "Idea 3" -> 7; // Idea 3 completed all stages
            default -> 0;
        };

        // Update roadmap UI
        for (int i = 0; i < roadmapGraph.getChildren().size(); i++) {
            if (roadmapGraph.getChildren().get(i) instanceof HBox stageNode) {
                CheckBox checkbox = (CheckBox) stageNode.getChildren().get(0); // Checkbox is the first child
                checkbox.setSelected(i / 2 < completedStages); // Divide by 2 since connecting lines are counted
            }
        }
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
        String[] sidebarOptions = {"Idea Submission", "Idea Tracking", "My Approvals", "Gandio", "Wasay ke Totay", "JKL"};
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
        DatabaseConnection.connect();
        DatabaseSetup.createTable();
        DataOperations.insertUser("Chandiyo", "Chandiyo");
        launch(args);
    }
}
