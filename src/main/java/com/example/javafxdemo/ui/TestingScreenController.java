package com.example.javafxdemo.ui;

import com.example.javafxdemo.bl.Idea;
import com.example.javafxdemo.db.DataOperations;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

/**
 * Controller class for the Testing Screen.
 */
public class TestingScreenController {

    @FXML
    private ComboBox<String> ideaSelectionComboBox;

    @FXML
    private TextField testNameField, estimatedBudgetField, numberOfTestsField, estimatedTimeField;

    @FXML
    private TextArea testDetailsField;

    @FXML
    private Label statusLabel;

    @FXML
    private Button submitButton;

    /**
     * Initializes the Testing Screen Controller.
     */
    @FXML
    public void initialize() {
        // Populate the Idea Selection ComboBox with ideas having status = 5
        populateIdeaComboBox();

        // Add listeners to validate the form dynamically
        ideaSelectionComboBox.valueProperty().addListener((observable, oldValue, newValue) -> validateForm());
        testNameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        testDetailsField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        estimatedBudgetField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        numberOfTestsField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        estimatedTimeField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());

        // Set up Submit Button action
        submitButton.setOnAction(e -> handleSubmit());
    }

    /**
     * Populates the Idea Selection ComboBox with ideas having status = 5.
     */
    private void populateIdeaComboBox() {
        List<Idea> ideas = DataOperations.getIdeasByStatus(5); // Fetch ideas from the database
        if (ideas != null) {
            for (Idea idea : ideas) {
                ideaSelectionComboBox.getItems().add("ID: " + idea.getIdeaId() + " - " + idea.getName());
            }
        } else {
            System.err.println("Failed to fetch ideas from the database.");
        }

        ideaSelectionComboBox.setPromptText("Select an Idea");
    }

    /**
     * Validates the form and updates the status label and submit button accordingly.
     */
    private void validateForm() {
        boolean isFormComplete = ideaSelectionComboBox.getValue() != null
                && !testNameField.getText().trim().isEmpty()
                && !testDetailsField.getText().trim().isEmpty()
                && !estimatedBudgetField.getText().trim().isEmpty()
                && !numberOfTestsField.getText().trim().isEmpty()
                && !estimatedTimeField.getText().trim().isEmpty();

        if (isFormComplete) {
            statusLabel.setText("Completed");
            statusLabel.setStyle("-fx-text-fill: green;");
            submitButton.setDisable(false);
        } else {
            statusLabel.setText("Pending");
            statusLabel.setStyle("-fx-text-fill: red;");
            submitButton.setDisable(true);
        }
    }

    /**
     * Handles the Submit Button action.
     */
    private void handleSubmit() {
        // Collect data from the input fields
        String selectedIdea = ideaSelectionComboBox.getValue();
        String testName = testNameField.getText().trim();
        String testDetails = testDetailsField.getText().trim();
        double estimatedBudget;
        int numberOfTests, estimatedTime;

        try {
            estimatedBudget = Double.parseDouble(estimatedBudgetField.getText().trim());
            numberOfTests = Integer.parseInt(numberOfTestsField.getText().trim());
            estimatedTime = Integer.parseInt(estimatedTimeField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please ensure numeric fields contain valid numbers.", Alert.AlertType.ERROR);
            return;
        }

        // Extract Idea ID from the selected string
        int ideaID = extractIdeaID(selectedIdea);

        // Insert data into the database
        boolean success = DataOperations.insertTestingData(ideaID, testName, testDetails, estimatedBudget, numberOfTests, estimatedTime);

        if (success) {
            // Show success alert
            showAlert("Submission Successful", "Testing details have been successfully submitted.", Alert.AlertType.INFORMATION);
            resetForm();
        } else {
            showAlert("Submission Failed", "An error occurred while saving testing details. Please try again.", Alert.AlertType.ERROR);
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
     * Resets the form fields and status label.
     */
    private void resetForm() {
        ideaSelectionComboBox.getSelectionModel().clearSelection();
        testNameField.clear();
        testDetailsField.clear();
        estimatedBudgetField.clear();
        numberOfTestsField.clear();
        estimatedTimeField.clear();
        statusLabel.setText("Pending");
        statusLabel.setStyle("-fx-text-fill: red;");
        submitButton.setDisable(true);
    }

    /**
     * Helper method to display an alert.
     *
     * @param title   The title of the alert.
     * @param message The message content of the alert.
     * @param type    The type of the alert.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
