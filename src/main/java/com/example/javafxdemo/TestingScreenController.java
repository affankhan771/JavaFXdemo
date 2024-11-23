package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.ProgressBar;

public class TestingScreenController {

    @FXML
    private TextField testNameField;
    @FXML
    private TextArea testDescriptionArea;
    @FXML
    private TextField testParamsField;
    @FXML
    private Button submitTestButton;
    @FXML
    private Label testStatusLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label testOutcomeLabel;
    @FXML
    private ListView<String> testHistoryList;
    @FXML
    private VBox historySection;

    // Handle the submit button click event
    @FXML
    private void handleSubmitTest() {
        String testName = testNameField.getText();
        String testDescription = testDescriptionArea.getText();
        String testParams = testParamsField.getText();

        // Validate the fields before submission
        if (testName.isEmpty() || testDescription.isEmpty() || testParams.isEmpty()) {
            showError("Please fill all the fields.");
            return;
        }

        // Update test status to "In Progress"
        testStatusLabel.setText("Status: Test in Progress");
        testOutcomeLabel.setText("Outcome: N/A");
        progressBar.setProgress(0.0);

        // Simulate test progress and completion
        simulateTestProgress();
    }

    // Simulate the test progress
    private void simulateTestProgress() {
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                final int progress = i;
                try {
                    Thread.sleep(100);  // Simulate the test running
                    progressBar.setProgress(progress / 100.0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            updateTestResult("Test Complete", "Passed");
        }).start();
    }

    // Update the test outcome
    private void updateTestResult(String status, String outcome) {
        // Update the status and outcome labels
        testStatusLabel.setText("Status: " + status);
        testOutcomeLabel.setText("Outcome: " + outcome);
    }

    // Handle the Test History hyperlink click event
    @FXML
    private void handleTestHistory() {
        // Toggle the visibility of the Test History section
        historySection.setVisible(!historySection.isVisible());
    }

    // Show error message
    private void showError(String message) {
        // You can show an error message using an alert
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}
