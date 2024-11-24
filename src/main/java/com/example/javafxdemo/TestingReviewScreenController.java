package com.example.javafxdemo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class TestingReviewScreenController {

    @FXML
    private ComboBox<String> ideaSelectionComboBox;

    @FXML
    private TableView<Test> testsTableView;

    @FXML
    private TableColumn<Test, String> testNameColumn, testDetailsColumn;

    @FXML
    private TableColumn<Test, String> budgetColumn, numberOfTestsColumn, timeTakenColumn;

    @FXML
    private Button approveButton, rejectButton;

    private String selectedIdea = null;

    /**
     * Initializes the Testing Review Screen.
     */
    @FXML
    public void initialize() {
        // Populate the dropdown menu with idea IDs and names from the database
        populateIdeaComboBox();

        // Set up the TableView columns
        testNameColumn.setCellValueFactory(new PropertyValueFactory<>("testName"));
        testDetailsColumn.setCellValueFactory(new PropertyValueFactory<>("testDetails"));
        budgetColumn.setCellValueFactory(new PropertyValueFactory<>("budget"));
        numberOfTestsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfTests"));
        timeTakenColumn.setCellValueFactory(new PropertyValueFactory<>("timeTaken"));

        // Add listener to the dropdown menu
        ideaSelectionComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedIdea = newValue;
            displayTestingDetails(newValue);
        });

        // Set button actions
        approveButton.setOnAction(e -> approveIdea());
        rejectButton.setOnAction(e -> rejectIdea());

        // Disable buttons initially
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
    }

    /**
     * Populates the Idea ComboBox with ideas having status 5 from the database.
     */
    private void populateIdeaComboBox() {
        String sql = "SELECT ideaID, drugName FROM ideas WHERE status = 5";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String ideaDisplay = "ID: " + rs.getInt("ideaID") + " - " + rs.getString("drugName");
                ideaSelectionComboBox.getItems().add(ideaDisplay);
            }

        } catch (Exception e) {
            System.err.println("Error fetching ideas: " + e.getMessage());
        }
    }

    /**
     * Displays the testing details for the selected idea.
     *
     * @param idea The selected idea.
     */
    private void displayTestingDetails(String idea) {
        if (idea == null) {
            clearTestingDetails();
            return;
        }

        // Extract ideaID from the selected idea string
        int ideaID = extractIdeaID(idea);

        // Fetch and display the tests for the selected idea from the database
        ObservableList<Test> tests = fetchTestsFromDatabase(ideaID);
        if (tests.isEmpty()) {
            showAlert("No Testing Data", "No testing has been performed for the selected idea.", Alert.AlertType.INFORMATION);
            clearTestingDetails();
        } else {
            testsTableView.setItems(tests);
            approveButton.setDisable(false);
            rejectButton.setDisable(false);
        }
    }

    /**
     * Fetches testing data for a specific ideaID from the database.
     *
     * @param ideaID The ideaID for which testing data is to be fetched.
     * @return A list of Test objects.
     */
    private ObservableList<Test> fetchTestsFromDatabase(int ideaID) {
        ObservableList<Test> tests = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Testing WHERE ideaID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ideaID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tests.add(new Test(
                        rs.getString("testName"),
                        rs.getString("details"),
                        rs.getString("testingBudget"),
                        rs.getString("numberOfTests"),
                        rs.getString("timeTaken")
                ));
            }

        } catch (Exception e) {
            System.err.println("Error fetching testing data: " + e.getMessage());
        }
        return tests;
    }

    /**
     * Clears the testing details displayed on the screen.
     */
    private void clearTestingDetails() {
        testsTableView.getItems().clear();
        approveButton.setDisable(true);
        rejectButton.setDisable(true);
    }

    /**
     * Approves the selected idea by updating its status to 6 in the database.
     */
    private void approveIdea() {
        if (selectedIdea != null) {
            int ideaID = extractIdeaID(selectedIdea);
            updateIdeaStatus(ideaID, 6);
            showAlert("Approval Successful", selectedIdea + " has been approved.", Alert.AlertType.INFORMATION);
            ideaSelectionComboBox.getItems().remove(selectedIdea);
            clearTestingDetails();
        }
    }

    /**
     * Rejects the selected idea by updating its status to 0 in the database.
     */
    private void rejectIdea() {
        if (selectedIdea != null) {
            int ideaID = extractIdeaID(selectedIdea);
            updateIdeaStatus(ideaID, 0);
            showAlert("Rejection Successful", selectedIdea + " has been rejected.", Alert.AlertType.INFORMATION);
            ideaSelectionComboBox.getItems().remove(selectedIdea);
            clearTestingDetails();
        }
    }

    /**
     * Updates the status of an idea in the database.
     *
     * @param ideaID The ID of the idea to update.
     * @param status The new status value.
     */
    private void updateIdeaStatus(int ideaID, int status) {
        String sql = "UPDATE ideas SET status = ? WHERE ideaID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, status);
            pstmt.setInt(2, ideaID);
            pstmt.executeUpdate();
            System.out.println("Idea status updated to " + status + " for ideaID: " + ideaID);

        } catch (Exception e) {
            System.err.println("Error updating idea status: " + e.getMessage());
        }
    }

    /**
     * Extracts the ideaID from the selected idea string.
     *
     * @param idea The selected idea string.
     * @return The extracted ideaID.
     */
    private int extractIdeaID(String idea) {
        try {
            return Integer.parseInt(idea.split(" - ")[0].split(": ")[1].trim());
        } catch (Exception e) {
            System.err.println("Error extracting ideaID: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Displays an alert.
     *
     * @param title   The alert title.
     * @param message The alert message.
     * @param type    The alert type.
     */
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Represents testing data for an idea.
     */
    public static class Test {
        private final String testName;
        private final String testDetails;
        private final String budget;
        private final String numberOfTests;
        private final String timeTaken;

        public Test(String testName, String testDetails, String budget, String numberOfTests, String timeTaken) {
            this.testName = testName;
            this.testDetails = testDetails;
            this.budget = budget;
            this.numberOfTests = numberOfTests;
            this.timeTaken = timeTaken;
        }

        public String getTestName() {
            return testName;
        }

        public String getTestDetails() {
            return testDetails;
        }

        public String getBudget() {
            return budget;
        }

        public String getNumberOfTests() {
            return numberOfTests;
        }

        public String getTimeTaken() {
            return timeTaken;
        }
    }
}
