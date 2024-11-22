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

        // Mock ideas data
        List<String[]> mockIdeas = List.of(
                new String[]{"Idea 1", "Description for idea 1", "Drug X", "Category A", "C6H12O6", "120"},
                new String[]{"Idea 2", "Description for idea 2", "Drug Y", "Category B", "H2O", "200"},
                new String[]{"Idea 3", "Description for idea 3", "Drug Z", "Category C", "NaCl", "300"},
                new String[]{"Idea 4", "Description for idea 4", "Drug A", "Category D", "CH4", "400"}
        );

        for (String[] idea : mockIdeas) {
            VBox ideaBox = new VBox(10);
            ideaBox.setPadding(new Insets(15));
            ideaBox.setStyle("-fx-background-color: #555555; -fx-border-radius: 5px; -fx-background-radius: 5px;");

            Label ideaName = new Label("Idea Name: " + idea[0]);
            ideaName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
            Label ideaDesc = new Label("Description: " + idea[1]);
            ideaDesc.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
            Label drugDetails = new Label("Drug Name: " + idea[2] + ", Category: " + idea[3] +
                    ", Formula: " + idea[4] + ", Price: $" + idea[5]);
            drugDetails.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

            HBox buttons = new HBox(10);
            Button approveButton = new Button("Approve");
            approveButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
            Button disapproveButton = new Button("Disapprove");
            disapproveButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");

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
