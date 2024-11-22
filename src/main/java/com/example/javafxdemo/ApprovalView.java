package com.example.javafxdemo;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class ApprovalView {

    // Method to generate the VBox containing idea approval boxes
    private VBox createApprovalBox(Stage primaryStage) {
        VBox ideasBox = new VBox(20);
        ideasBox.setPadding(new Insets(20));
        ideasBox.setStyle("-fx-background-color: #333333;");

        // Fetch pending ideas from the database
        List<Idea> pendingIdeas = DataOperations.getPendingIdeas();

        for (Idea idea : pendingIdeas) {
            VBox ideaBox = new VBox(10);
            ideaBox.setPadding(new Insets(15));
            ideaBox.setStyle("-fx-background-color: #555555; -fx-border-radius: 5px; -fx-background-radius: 5px;");

            Label ideaName = new Label("Idea Name: " + idea.getName());
            ideaName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
            Label ideaDesc = new Label("Description: " + idea.getDescription());
            ideaDesc.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
            Label drugDetails = new Label("Category: " + idea.getCategory() + ", Formula: " + idea.getChemicalFormula() +
                    ", Price: $" + idea.getEstimatedPrice());
            drugDetails.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
            Label submittedBy = new Label("Submitted By: " + idea.getSubmittedby());
            submittedBy.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

            HBox buttons = new HBox(10);
            Button approveButton = new Button("Approve");
            approveButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
            Button disapproveButton = new Button("Reject");
            disapproveButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");

            // Button actions
            approveButton.setOnAction(e -> {
                boolean success = DataOperations.updateIdeaApproval(idea.getIdeaId(), "Approved");
                if (success) {
                    ideaBox.setStyle("-fx-background-color: green; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                    approveButton.setDisable(true);
                    disapproveButton.setDisable(true);
                }
            });
            disapproveButton.setOnAction(e -> {
                boolean success = DataOperations.updateIdeaApproval(idea.getIdeaId(), "Rejected");
                if (success) {
                    ideaBox.setStyle("-fx-background-color: red; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                    approveButton.setDisable(true);
                    disapproveButton.setDisable(true);
                }
            });

            buttons.getChildren().addAll(approveButton, disapproveButton);
            ideaBox.getChildren().addAll(ideaName, ideaDesc, drugDetails, submittedBy, buttons);
            ideasBox.getChildren().add(ideaBox);
        }

        return ideasBox;
    }

    // Method to display the approval screen
    public void showApprovalScreen(Stage primaryStage, HBox navBar, VBox sidebar) {
        BorderPane layout = new BorderPane();
        layout.setTop(navBar);
        layout.setLeft(sidebar);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(createApprovalBox(primaryStage)); // Populate the VBox
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #333333;");

        layout.setCenter(scrollPane);

        Scene approvalScene = new Scene(layout, 900, 600);
        primaryStage.setScene(approvalScene);
        primaryStage.show(); // Ensure the stage displays
    }
}
