package com.example.javafxdemo.ui;

import com.example.javafxdemo.bl.Idea;
import com.example.javafxdemo.db.DataOperations;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.List;

public class RegulatoryComplianceController {

    @FXML
    private Button submitButton;

    @FXML
    private ComboBox<String> ideaSelectionComboBox;

    @FXML
    private CheckBox safetyAssessmentToggle, clinicalTrialsToggle, packagingApprovedToggle,
            labelContainsInfoToggle, productApprovedToggle, agreeTermsCheckbox, finalComplianceToggle;

    @FXML
    private Label progressStatusLabel;

    private int selectedIdeaID = -1; // To store the ID of the selected Idea

    @FXML
    public void initialize() {
        // Initialize the progress checker
        updateProgressStatus();

        // Populate the Idea ComboBox with data from the database
        populateIdeaComboBox();

        // Add listeners to all checkboxes to monitor changes
        safetyAssessmentToggle.selectedProperty().addListener((observable, oldValue, newValue) -> updateProgressStatus());
        clinicalTrialsToggle.selectedProperty().addListener((observable, oldValue, newValue) -> updateProgressStatus());
        packagingApprovedToggle.selectedProperty().addListener((observable, oldValue, newValue) -> updateProgressStatus());
        labelContainsInfoToggle.selectedProperty().addListener((observable, oldValue, newValue) -> updateProgressStatus());
        productApprovedToggle.selectedProperty().addListener((observable, oldValue, newValue) -> updateProgressStatus());
        agreeTermsCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> updateProgressStatus());
        finalComplianceToggle.selectedProperty().addListener((observable, oldValue, newValue) -> updateProgressStatus());

        // Initially disable the Submit button
        submitButton.setDisable(true);

        // Set action for the Submit button
        submitButton.setOnAction(e -> submitComplianceDetails());

        // Add listener to Idea ComboBox to monitor selection changes
        ideaSelectionComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateProgressStatus();
        });
    }

    private void populateIdeaComboBox() {
        List<Idea> ideas = DataOperations.getIdeasByStatus(3); // Fetch ideas from the database
        if (ideas != null) {
            for (Idea idea : ideas) {
                ideaSelectionComboBox.getItems().add("ID: " + idea.getIdeaId() + " - " + idea.getName());
            }
        } else {
            System.err.println("Failed to fetch ideas from the database.");
        }

        ideaSelectionComboBox.setPromptText("Select an Idea");
    }

    private void updateProgressStatus() {
        if (areAllCheckboxesSelected() && isIdeaSelected()) {
            progressStatusLabel.setText("Completed");

            progressStatusLabel.setTextFill(Color.GREEN);
            submitButton.setDisable(false);
        } else {
            progressStatusLabel.setText("Pending");
            progressStatusLabel.setTextFill(Color.RED);
            submitButton.setDisable(true);
        }
    }

    private boolean areAllCheckboxesSelected() {
        return safetyAssessmentToggle.isSelected()
                && clinicalTrialsToggle.isSelected()
                && packagingApprovedToggle.isSelected()
                && labelContainsInfoToggle.isSelected()
                && productApprovedToggle.isSelected()
                && agreeTermsCheckbox.isSelected()
                && finalComplianceToggle.isSelected();
    }

    private boolean isIdeaSelected() {
        String selectedIdea = ideaSelectionComboBox.getValue();
        if (selectedIdea == null || selectedIdea.isEmpty()) {
            selectedIdeaID = -1;
            return false;
        }
        selectedIdeaID = extractIdeaID(selectedIdea);
        return selectedIdeaID != -1;
    }

    private int extractIdeaID(String selectedIdea) {
        try {
            String[] parts = selectedIdea.split(" - ")[0].split(": ");
            return Integer.parseInt(parts[1].trim());
        } catch (Exception e) {
            System.err.println("Failed to extract Idea ID: " + e.getMessage());
            return -1;
        }
    }

    private void submitComplianceDetails() {
        if (!areAllCheckboxesSelected() || !isIdeaSelected()) {
            showAlert("Submission Error", "Please complete all compliance checks and select an Idea before submitting.", Alert.AlertType.ERROR);
            return;
        }

        boolean safetyAssessment = safetyAssessmentToggle.isSelected();
        boolean clinicalTrials = clinicalTrialsToggle.isSelected();
        boolean packagingApproved = packagingApprovedToggle.isSelected();
        boolean labelContainsInfo = labelContainsInfoToggle.isSelected();
        boolean productApproved = productApprovedToggle.isSelected();
        boolean agreeTerms = agreeTermsCheckbox.isSelected();
        boolean finalCompliance = finalComplianceToggle.isSelected();

        boolean success = simulateSubmission(selectedIdeaID, safetyAssessment, clinicalTrials,
                packagingApproved, labelContainsInfo, productApproved, agreeTerms, finalCompliance);

        if (success) {
            showAlert("Submission Successful", "Compliance details have been submitted successfully.", Alert.AlertType.INFORMATION);
            resetForm();
        } else {
            showAlert("Submission Failed", "An error occurred while submitting compliance details.", Alert.AlertType.ERROR);
        }
    }

    private boolean simulateSubmission(int ideaID, boolean safetyAssessment, boolean clinicalTrials,
                                       boolean packagingApproved, boolean labelContainsInfo,
                                       boolean productApproved, boolean agreeTerms,
                                       boolean finalCompliance) {
        DataOperations.updateIdeaStatus(ideaID,4);
        System.out.println("Submitting Compliance Details:");
        System.out.println("Idea ID: " + ideaID);
        System.out.println("Safety Assessment: " + safetyAssessment);
        System.out.println("Clinical Trials: " + clinicalTrials);
        System.out.println("Packaging Approved: " + packagingApproved);
        System.out.println("Label Contains Info: " + labelContainsInfo);
        System.out.println("Product Approved: " + productApproved);
        System.out.println("Agreed to Terms: " + agreeTerms);
        System.out.println("Final Compliance: " + finalCompliance);
        return true;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetForm() {
        ideaSelectionComboBox.getSelectionModel().clearSelection();
        safetyAssessmentToggle.setSelected(false);
        clinicalTrialsToggle.setSelected(false);
        packagingApprovedToggle.setSelected(false);
        labelContainsInfoToggle.setSelected(false);
        productApprovedToggle.setSelected(false);
        agreeTermsCheckbox.setSelected(false);
        finalComplianceToggle.setSelected(false);
    }
}
