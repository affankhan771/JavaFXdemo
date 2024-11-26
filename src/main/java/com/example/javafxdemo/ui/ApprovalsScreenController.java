package com.example.javafxdemo.ui;
import com.example.javafxdemo.bl.Idea;
import com.example.javafxdemo.bl.UserSession;
import com.example.javafxdemo.db.DataOperations;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javafx.scene.control.Alert.AlertType;

import java.util.List;


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
        filterSortDropdown.getItems().clear();

        int userRank = UserSession.getInstance().getGrade();

        if (userRank == 1) {
            // C-level user
            filterSortDropdown.getItems().addAll("Pending Approval");
            loadIdeasForCLevel();
        } else if (userRank == 2) {
            // M-level user
            filterSortDropdown.getItems().addAll("Pending Approval");
            loadIdeasForMLevel();
        } else {
            // G-level user
            filterSortDropdown.getItems().addAll("No Approval Access");
            ideaCardsContainer.getChildren().clear();
            showAlert("Access Denied", "You do not have permission to approve ideas.", AlertType.WARNING);
        }

        filterSortDropdown.setOnAction(e -> {
            String selectedOption = filterSortDropdown.getValue();
            if (userRank == 1) {
                loadIdeasForCLevel();
            } else if (userRank == 2) {
                loadIdeasForMLevel();
            }
        });

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
                if (userRank == 1) {
                    loadIdeasForCLevel();
                } else if (userRank == 2) {
                    loadIdeasForMLevel();
                }
            }
        });

        // Disable approve/reject buttons initially
        approveButton.setDisable(true);
        disapproveButton.setDisable(true);
    }
    private void loadIdeasForMLevel() {
        ideaCardsContainer.getChildren().clear();
        List<Idea> ideas = DataOperations.getIdeasByStatus(1); // Status = 1 for M-level approval
        for (Idea idea : ideas) {
            if (idea.getStatus() == 1) { // Only load pending M-level approval ideas
                VBox card = createIdeaCard(idea);
                ideaCardsContainer.getChildren().add(card);
            }
        }
    }


    private void loadIdeasForCLevel() {
        ideaCardsContainer.getChildren().clear();
        List<Idea> ideas = DataOperations.getIdeasByStatus(2); // Status = 2 for C-level approval
        for (Idea idea : ideas) {
            VBox card = createIdeaCard(idea);
            ideaCardsContainer.getChildren().add(card);
        }
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
        Idea idea = DataOperations.getIdeaById(ideaID);
        if (idea != null) {
            int userRank = UserSession.getInstance().getGrade();
            int ideaStatus = idea.getStatus();

            // Check if the user is allowed to view this idea
            if ((userRank == 2 && ideaStatus == 1) || (userRank == 1 && ideaStatus == 2)) {
                ideaCardsContainer.getChildren().clear();
                VBox card = createIdeaCard(idea);
                ideaCardsContainer.getChildren().add(card);
            } else {
                showAlert("Access Denied", "You do not have permission to view this idea.", AlertType.WARNING);
            }
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
            int userRank = UserSession.getInstance().getGrade();
            int currentStatus = selectedIdea.getStatus();

            // Determine if the user has permission to approve this idea
            if ((userRank == 2 && currentStatus == 1) || (userRank == 1 && currentStatus == 2)) {
                Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Approval");
                confirmAlert.setHeaderText(null);
                confirmAlert.setContentText("Are you sure you want to approve this idea?");

                confirmAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        int newStatus = selectedIdea.getStatus() + 1; // Increment the status
                        boolean success = DataOperations.updateIdeaStatus(selectedIdea.getIdeaId(), newStatus);
                        if (success) {
                            showAlert("Success", "Idea approved successfully.", AlertType.INFORMATION);

                            // Refresh based on rank
                            if (userRank == 1) {
                                loadIdeasForCLevel();
                            } else if (userRank == 2) {
                                loadIdeasForMLevel(); // Ensure this reloads ideas with updated status
                            }
                            approveButton.setDisable(true);
                            disapproveButton.setDisable(true);
                        } else {
                            showAlert("Error", "Failed to approve the idea.", AlertType.ERROR);
                        }
                    }
                });
            } else {
                showAlert("Access Denied", "You do not have permission to approve this idea.", AlertType.WARNING);
            }
        }
    }



    public void onDisapproveButtonClicked() {
        if (selectedIdea != null) {
            int userRank = UserSession.getInstance().getGrade();
            int currentStatus = selectedIdea.getStatus();

            // Determine if the user has permission to disapprove this idea
            if ((userRank == 2 && currentStatus == 1) || (userRank == 1 && currentStatus == 2)) {
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
                            if (userRank == 1) {
                                loadIdeasForCLevel();
                            } else if (userRank == 2) {
                                loadIdeasForMLevel();
                            }
                            approveButton.setDisable(true);
                            disapproveButton.setDisable(true);
                        } else {
                            showAlert("Error", "Failed to reject the idea.", AlertType.ERROR);
                        }
                    }
                });
            } else {
                showAlert("Access Denied", "You do not have permission to reject this idea.", AlertType.WARNING);
            }
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
