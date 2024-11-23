package com.example.javafxdemo;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginPage extends Application {

    private final LoginFormView loginFormView = new LoginFormView();
    @Override
    public void start(Stage primaryStage) {
        // Create the login form using LoginFormView
        VBox loginForm = loginFormView.createLoginForm(primaryStage,
                (username, password, stage) -> {
                    // Login Button Action
                    boolean isValidUser = DataOperations.verifyUserCredentials(username, password);
                    if (isValidUser) {
                        // Retrieve additional user info (email and grade)
                        try (Connection conn = DatabaseConnection.connect();
                             PreparedStatement pstmt = conn.prepareStatement("SELECT email, grade FROM users WHERE userid = ?")) {

                            pstmt.setString(1, username);
                            ResultSet rs = pstmt.executeQuery();

                            if (rs.next()) {
                                String email = rs.getString("email");
                                int grade = rs.getInt("grade");

                                // Set the user session
                                UserSession.createSession(username, email, grade);

                                // Open the dashboard
                                openDashboard(stage);
                            } else {
                                Label messageLabel = new Label();
                                messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                                messageLabel.setText("User data retrieval failed.");
                            }

                        } catch (SQLException ex) {
                            Label messageLabel = new Label();
                            messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                            messageLabel.setText("Database error: " + ex.getMessage());
                        }
                    } else {
                        Label messageLabel = new Label();
                        messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                        messageLabel.setText("Invalid credentials. Please try again.");
                    }
                    /*if (isValidUser) {
                        //System.out.println("Login Succedded Here \n");
                        openDashboard(stage);
                    } else {
                        System.out.println("Invalid credentials. Please try again.");
                    }*/
                },
                stage -> {
                    // Signup Button Action (for now, just a placeholder)
                    System.out.println("Signup button clicked. Implement signup logic here.");
                }
        );



        // Background Image
        Image backgroundImage = new Image("file:C:\\Users\\admin\\IdeaProjects\\JavaFXdemo\\src\\main\\resources\\bg.png");
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        loginForm.setBackground(new Background(bgImage));

        // Scene and Stage
        Scene scene = new Scene(loginForm, 900, 600);
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    private DashboardView dashboardView = new DashboardView();
    private void openDashboard(Stage primaryStage) {
        dashboardView.openDashboard(primaryStage, getNavBar(primaryStage), getSidebar(primaryStage));
    }
    private NavBarView navBarView = new NavBarView();
    private SidebarView sidebarView = new SidebarView();
    public HBox getNavBar(Stage primaryStage) {
        return navBarView.createNavBar(primaryStage);
    }

    public VBox getSidebar(Stage primaryStage) {
        return sidebarView.createSidebar(primaryStage, (stage, index) -> showScreenContent(stage, index));
    }


    // Function to show screen content based on clicked option
    private void showScreenContent(Stage primaryStage, int optionIndex) {
        VBox contentArea = new VBox();
        contentArea.setAlignment(Pos.CENTER);
        contentArea.setStyle("-fx-background-color: black; -fx-padding: 20px;");

        if (optionIndex == 1) {  // Option 1 - Idea Submission screen
            // Create instance of IdeaSubmissionView and use it
            IdeaSubmissionView ideaSubmissionView = new IdeaSubmissionView();
            contentArea.getChildren().add(ideaSubmissionView.createIdeaSubmissionForm(primaryStage));
        } else if (optionIndex == 2) {
           // contentArea.getChildren().add(createIdeaTrackingScreen(primaryStage));
            IdeaTrackingView ideaTrackingView = new IdeaTrackingView();
            contentArea.getChildren().add(ideaTrackingView.createIdeaTrackingScreen(primaryStage));

        } else if (optionIndex == 3) {
            // Use ScrollPane to make ideas scrollable
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(createApprovalBox(primaryStage));
            scrollPane.setFitToWidth(true); // Ensure content fits horizontally
            scrollPane.setStyle("-fx-background-color: black;");

            contentArea.getChildren().add(scrollPane);
           // approvalView.showApprovalScreen(primaryStage, getNavBar(primaryStage), getSidebar(primaryStage));
        } else if (optionIndex == 4) { // Regulatory Compliance
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxdemo/RegulatoryCompliance.fxml"));
            VBox regulatoryComplianceScreen;
            try {
                regulatoryComplianceScreen = loader.load();
            } catch (IOException e) {
                throw new RuntimeException("Error loading Regulatory Compliance screen: " + e.getMessage(), e);
            }

            // Wrap it in a ScrollPane for scrolling
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(regulatoryComplianceScreen);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color: black;");

            // Add to the content area
            contentArea.getChildren().add(scrollPane);
        }else if (optionIndex == 5) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxdemo/ApprovalsScreen.fxml"));
            BorderPane approvalsScreen = null;
            try {
                approvalsScreen = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Add to the content area
            contentArea.getChildren().add(approvalsScreen);
        }else if (optionIndex == 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxdemo/DashboardScreen.fxml"));
            BorderPane dashboardScreen = null;
            try {
                dashboardScreen = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Add to the content area
            contentArea.getChildren().add(dashboardScreen);
        } else if (optionIndex == 6) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxdemo/TestingScreen.fxml"));
            AnchorPane testingScreen = null;
            try {
                testingScreen = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error loading Testing Screen: " + e.getMessage(), e);
            }

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(testingScreen);
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color: black;");
            contentArea.getChildren().add(scrollPane);

        } else if (optionIndex == 7) { // Sales Forecast Screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxdemo/SalesForecastScreen.fxml"));
            BorderPane salesForecastScreen = null;
            try {
                salesForecastScreen = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error loading Sales Forecast Screen: " + e.getMessage(), e);
            }

            contentArea.getChildren().add(salesForecastScreen);
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


    private VBox createApprovalBox(Stage primaryStage) {
        // VBox to hold idea entries
        VBox ideasBox = new VBox(20);
        ideasBox.setPadding(new Insets(20));
        ideasBox.setStyle("-fx-background-color: #333333;");

        // Mock ideas data
        List<String[]> mockIdeas = List.of(
                new String[]{"Idea 1", "Description for idea 1", "Drug X", "Category A", "C6H12O6", "120"},
                new String[]{"Idea 2", "Description for idea 2", "Drug Y", "Category B", "H2O", "200"},
                new String[]{"Idea 3", "Description for idea 3", "Drug Z", "Category C", "NaCl", "300"},
                new String[]{"Idea 4", "Description for idea 4", "Drug A", "Category D", "CH4", "400"}
                // Add as many ideas as required
        );

        for (String[] idea : mockIdeas) {
            // Each idea box
            VBox ideaBox = new VBox(10);
            ideaBox.setPadding(new Insets(15));
            ideaBox.setStyle("-fx-background-color: #555555; -fx-border-radius: 5px; -fx-background-radius: 5px;");

            // Idea details
            Label ideaName = new Label("Idea Name: " + idea[0]);
            ideaName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
            Label ideaDesc = new Label("Description: " + idea[1]);
            ideaDesc.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
            Label drugDetails = new Label("Drug Name: " + idea[2] + ", Category: " + idea[3] + ", Formula: " + idea[4] + ", Price: $" + idea[5]);
            drugDetails.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

            // Buttons for approval
            HBox buttons = new HBox(10);
            Button approveButton = new Button("Approve");
            approveButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
            Button disapproveButton = new Button("Disapprove");
            disapproveButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");

            // Button actions
            approveButton.setOnAction(e -> {
                ideaBox.setStyle("-fx-background-color: green; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                approveButton.setDisable(true);
                disapproveButton.setDisable(true);
            });
            disapproveButton.setOnAction(e -> {
                ideaBox.setStyle("-fx-background-color: red; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                approveButton.setDisable(true);
                disapproveButton.setDisable(true);
            });

            buttons.getChildren().addAll(approveButton, disapproveButton);
            ideaBox.getChildren().addAll(ideaName, ideaDesc, drugDetails, buttons);
            ideasBox.getChildren().add(ideaBox);
        }

        return ideasBox;
    }


    public static void main(String[] args) {
        DatabaseConnection.connect();
        DatabaseSetup.createTable();
       // DataOperations.insertUser("Chandiyo", "Chandiyo");
        launch(args);
    }
}
