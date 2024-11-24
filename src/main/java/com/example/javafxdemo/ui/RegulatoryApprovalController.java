package com.example.javafxdemo.ui;

import com.example.javafxdemo.bl.Idea;
import com.example.javafxdemo.db.DataOperations;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.List;

public class RegulatoryApprovalController {

    @FXML
    private ComboBox<String> ideaSelectionComboBox;

    @FXML
    private Label ideaIDLabel, ideaNameLabel, userIDLabel, userNameLabel;

    @FXML
    private CheckBox safetyAssessmentToggle, clinicalTrialsToggle, packagingApprovedToggle,
            labelContainsInfoToggle, productApprovedToggle, agreeTermsCheckbox, finalComplianceToggle;

    @FXML
    private Button approveButton, rejectButton;

    @FXML
    private Label feedbackLabel;

    // Currently selected Idea Submission
    private Idea selectedIdea = null;

    @FXML
    public void initialize() {
        // Populate the Idea ComboBox with real data from the database
        populateIdeaComboBox();

        // Set up ComboBox listener
        ideaSelectionComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            displayIdeaDetails(newValue);
        });

        // Set up Approve button action
        approveButton.setOnAction(e -> approveIdea());

        // Set up Reject button action
        rejectButton.setOnAction(e -> rejectIdea());

        // Initially disable Approve and Reject buttons
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
    }

    /**
     * Populates the Idea ComboBox with ideas having status 4.
     */
    private void populateIdeaComboBox() {
        List<Idea> ideas = DataOperations.getIdeasByStatus(4); // Fetch ideas with status 4 from the database
        for (Idea idea : ideas) {
            String displayText = "ID: " + idea.getIdeaId() + " - " + idea.getName();
            ideaSelectionComboBox.getItems().add(displayText);
        }
        ideaSelectionComboBox.setPromptText("Select an Idea");
    }

    /**
     * Displays the details of the selected Idea.
     *
     * @param selectedIdea The selected Idea string from the ComboBox.
     */
    private void displayIdeaDetails(String selectedIdea) {
        if (selectedIdea == null || selectedIdea.isEmpty()) {
            clearIdeaDetails();
            return;
        }

        // Extract Idea ID from the selected string
        int ideaID = extractIdeaID(selectedIdea);

        // Fetch the idea details from the database
        this.selectedIdea = DataOperations.getIdeaById(ideaID);

        if (this.selectedIdea != null) {
            // Display Idea Details
            ideaIDLabel.setText(String.valueOf(this.selectedIdea.getIdeaId()));
            ideaNameLabel.setText(this.selectedIdea.getName());
            userIDLabel.setText(String.valueOf(this.selectedIdea.getSubmittedby()));
            userNameLabel.setText(DataOperations.getUserById(String.valueOf(this.selectedIdea.getSubmittedby())).getName());

            // Display Compliance Check statuses
            safetyAssessmentToggle.setSelected(true); // Simulating as all compliance checks are done.
            clinicalTrialsToggle.setSelected(true);
            packagingApprovedToggle.setSelected(true);
            labelContainsInfoToggle.setSelected(true);
            productApprovedToggle.setSelected(true);
            agreeTermsCheckbox.setSelected(true);
            finalComplianceToggle.setSelected(true);

            // Enable Approve and Reject buttons
            approveButton.setDisable(false);
            rejectButton.setDisable(false);

            // Clear previous feedback
            feedbackLabel.setText("");
        } else {
            clearIdeaDetails();
        }
    }

    /**
     * Extracts the Idea ID from the ComboBox selection string.
     *
     * @param selectedIdea The selected Idea string in the format "ID: <IdeaID> - <Idea Name>"
     * @return The extracted Idea ID, or -1 if extraction fails.
     */
    private int extractIdeaID(String selectedIdea) {
        try {
            String[] parts = selectedIdea.split(" - ")[0].split(": ");
            return Integer.parseInt(parts[1].trim());
        } catch (Exception e) {
            System.err.println("Error extracting Idea ID: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Clears the displayed Idea details.
     */
    private void clearIdeaDetails() {
        ideaIDLabel.setText("N/A");
        ideaNameLabel.setText("N/A");
        userIDLabel.setText("N/A");
        userNameLabel.setText("N/A");
        safetyAssessmentToggle.setSelected(false);
        clinicalTrialsToggle.setSelected(false);
        packagingApprovedToggle.setSelected(false);
        labelContainsInfoToggle.setSelected(false);
        productApprovedToggle.setSelected(false);
        agreeTermsCheckbox.setSelected(false);
        finalComplianceToggle.setSelected(false);
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
        feedbackLabel.setText("");
    }

    /**
     * Approves the selected Idea.
     */
    private void approveIdea() {
        if (selectedIdea == null) {
            showFeedback("No Idea selected for approval.", Color.RED);
            return;
        }

        // Update the status of the idea to 5 in the database
        boolean success = DataOperations.updateIdeaStatus(selectedIdea.getIdeaId(), 5);

        if (success) {
            showFeedback("Idea ID " + selectedIdea.getIdeaId() + " approved successfully.", Color.GREEN);

            // Remove the approved idea from the dropdown
            ideaSelectionComboBox.getItems().removeIf(item -> item.contains("ID: " + selectedIdea.getIdeaId() + " - "));
            clearIdeaDetails();
        } else {
            showFeedback("Failed to approve Idea ID " + selectedIdea.getIdeaId() + ".", Color.RED);
        }
    }

    /**
     * Rejects the selected Idea (Optional Implementation).
     */
    private void rejectIdea() {
        if (selectedIdea == null) {
            showFeedback("No Idea selected for rejection.", Color.RED);
            return;
        }


        DataOperations.updateIdeaStatus(selectedIdea.getIdeaId(), 0);
        showFeedback("Idea ID " + selectedIdea.getIdeaId() + " rejected. Action Pending.", Color.RED);
        clearIdeaDetails();
    }

    /**
     * Displays feedback messages to the user.
     *
     * @param message The feedback message.
     * @param color   The color of the message text.
     */
    private void showFeedback(String message, Color color) {
        feedbackLabel.setText(message);
        feedbackLabel.setTextFill(color);
    }
}
