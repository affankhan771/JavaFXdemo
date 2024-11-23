package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ApprovalsScreenController {

    @FXML
    private TextField searchBar;

    @FXML
    private ComboBox<String> filterSortDropdown;

    @FXML
    private VBox ideaCardsContainer;

    @FXML
    private VBox ideaDetailsPanel;

    @FXML
    private Label ideaTitleLabel;

    @FXML
    private Label ideaDetailedDescription;

    @FXML
    private Label ideaCreatorDetails;

    @FXML
    private Button approveButton;

    @FXML
    private Button disapproveButton;

    @FXML
    private TextArea commentBox;

    public void initialize() {
        // Initialize filter/sort options
        filterSortDropdown.getItems().addAll("Pending", "Approved", "Disapproved", "Sort by Submission Date");

        // Mock ideas data
        for (int i = 1; i <= 5; i++) {
            VBox card = createIdeaCard("Idea Title " + i, "Pending", "Short description of Idea " + i, "Creator " + i, "2024-11-21");
            ideaCardsContainer.getChildren().add(card);
        }
    }

    private VBox createIdeaCard(String title, String status, String summary, String creator, String date) {
        VBox card = new VBox(10);
        card.setStyle("-fx-padding: 10; -fx-background-color: #555555; -fx-border-radius: 5; -fx-background-radius: 5;");
        card.setOnMouseClicked(event -> loadIdeaDetails(title, summary, creator, status)); // Add click event

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;");
        Label statusLabel = new Label("Status: " + status);
        statusLabel.setStyle("-fx-font-size: 14; -fx-text-fill: yellow;");
        Label summaryLabel = new Label(summary);
        summaryLabel.setStyle("-fx-font-size: 12; -fx-text-fill: white;");
        Label creatorLabel = new Label("By: " + creator + " on " + date);
        creatorLabel.setStyle("-fx-font-size: 12; -fx-text-fill: white;");

        card.getChildren().addAll(titleLabel, statusLabel, summaryLabel, creatorLabel);
        return card;
    }

    private void loadIdeaDetails(String title, String description, String creator, String status) {
        ideaTitleLabel.setText(title);
        ideaDetailedDescription.setText(description);
        ideaCreatorDetails.setText("Creator: " + creator + "\nStatus: " + status);
        commentBox.setText("");
        approveButton.setDisable(false);
        disapproveButton.setDisable(false);
    }
}
