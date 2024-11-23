package com.example.javafxdemo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;

import java.util.List;
import java.util.stream.Collectors;


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
        filterSortDropdown.getItems().addAll("All", "Pending", "Approved", "Rejected", "Sort by Submission Date");

        // Fetch and display ideas
        loadIdeas("All");

        // Add listener for dropdown selection
        filterSortDropdown.setOnAction(e -> {
            String selectedOption = filterSortDropdown.getValue();
            loadIdeas(selectedOption);
        });

        // Add listener for search bar
        searchBar.setOnAction(e -> {
            String input = searchBar.getText();
            if (!input.isEmpty()) {
                try {
                    int ideaID = Integer.parseInt(input);
                    loadIdeaById(ideaID);
                } catch (NumberFormatException ex) {
                    showAlert("Invalid Input", "Please enter a valid idea ID.", AlertType.WARNING);
                }
            } else {
                loadIdeas("All");
            }
        });

        // Disable approve/reject buttons initially
        approveButton.setDisable(true);
        disapproveButton.setDisable(true);
    }
    private void loadIdeas(String filter) {
        ideaCardsContainer.getChildren().clear();

        List<Idea> ideas = DataOperations.getIdeasByFilter(filter);

        for (Idea idea : ideas) {
            VBox card = createIdeaCard(idea);
            ideaCardsContainer.getChildren().add(card);
        }
    }
    private void loadIdeaById(int ideaID) {
        ideaCardsContainer.getChildren().clear();

        Idea idea = DataOperations.getIdeaById(ideaID);
        if (idea != null) {
            VBox card = createIdeaCard(idea);
            ideaCardsContainer.getChildren().add(card);
        } else {
            showAlert("Idea Not Found", "No idea found with ID: " + ideaID, AlertType.INFORMATION);
        }
    }
    private VBox createIdeaCard(Idea idea) {
        VBox card = new VBox(10);
        card.setStyle("-fx-padding: 10; -fx-background-color: #555555; -fx-border-radius: 5; -fx-background-radius: 5;");
        card.setOnMouseClicked(event -> loadIdeaDetails(idea)); // Load idea details on click

        Label titleLabel = new Label("ID: " + idea.getIdeaId() + " - " + idea.getName());
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;");
        Label statusLabel = new Label("Status: " + getStatusText(idea.getStatus()));
        statusLabel.setStyle("-fx-font-size: 14; -fx-text-fill: yellow;");
        Label summaryLabel = new Label(idea.getDescription());
        summaryLabel.setStyle("-fx-font-size: 12; -fx-text-fill: white;");
        Label creatorLabel = new Label("By: " + idea.getSubmittedby());
        creatorLabel.setStyle("-fx-font-size: 12; -fx-text-fill: white;");

        card.getChildren().addAll(titleLabel, statusLabel, summaryLabel, creatorLabel);
        return card;
    }

    private Idea selectedIdea; // Keep track of the currently selected idea

    private void loadIdeaDetails(Idea idea) {
        selectedIdea = idea;
        ideaTitleLabel.setText("ID: " + idea.getIdeaId() + " - " + idea.getName());
        ideaDetailedDescription.setText(idea.getDescription());
        ideaCreatorDetails.setText("Creator: " + idea.getSubmittedby() + "\nStatus: " + getStatusText(idea.getStatus()));
        commentBox.setText("");
        approveButton.setDisable(false);
        disapproveButton.setDisable(false);
    }

    public void onApproveButtonClicked() {
        if (selectedIdea != null) {
            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Approval");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to approve this idea?");

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Increment the status by 1
                    int newStatus = selectedIdea.getStatus() + 1;
                    boolean success = DataOperations.updateIdeaStatus(selectedIdea.getIdeaId(), newStatus);
                    if (success) {
                        showAlert("Success", "Idea approved successfully.", AlertType.INFORMATION);
                        loadIdeas(filterSortDropdown.getValue());
                        approveButton.setDisable(true);
                        disapproveButton.setDisable(true);
                    } else {
                        showAlert("Error", "Failed to approve the idea.", AlertType.ERROR);
                    }
                }
            });
        }
    }

    public void onDisapproveButtonClicked() {
        if (selectedIdea != null) {
            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Rejection");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to reject this idea?");

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Set status to 0
                    boolean success = DataOperations.updateIdeaStatus(selectedIdea.getIdeaId(), 0);
                    if (success) {
                        showAlert("Success", "Idea rejected successfully.", AlertType.INFORMATION);
                        loadIdeas(filterSortDropdown.getValue());
                        approveButton.setDisable(true);
                        disapproveButton.setDisable(true);
                    } else {
                        showAlert("Error", "Failed to reject the idea.", AlertType.ERROR);
                    }
                }
            });
        }
    }
    private void showAlert(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private String getStatusText(int status) {
        switch (status) {
            case 0:
                return "Rejected";
            case 1:
                return "Idea Generated";
            case 2:
                return "Approved by M-Level Officers";
            case 3:
                return "Approved by C-Level Officers";
            case 4:
                return "Regulatory Compliance";
            case 5:
                return "Sales Forecast";
            case 6:
                return "Testing";
            case 7:
                return "Launch";
            default:
                return "Unknown Status";
        }
    }

}
