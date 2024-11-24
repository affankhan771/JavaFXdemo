package com.example.javafxdemo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LaunchScreenController {

    @FXML
    private ComboBox<String> ideaSelectionComboBox;

    @FXML
    private TableView<InfoRow> infoTable;

    @FXML
    private TableColumn<InfoRow, String> parameterColumn;

    @FXML
    private TableColumn<InfoRow, String> valueColumn;

    @FXML
    private Button launchButton;

    private String selectedIdea = null;

    @FXML
    public void initialize() {
        // Populate the dropdown menu with ideas having status = 6
        populateIdeaComboBox();

        // Configure table columns
        parameterColumn.setCellValueFactory(new PropertyValueFactory<>("parameter"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        // Add listener to dropdown menu
        ideaSelectionComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedIdea = newValue;
            displayIdeaDetails();
        });

        // Set launch button action
        launchButton.setOnAction(e -> launchIdea());

        // Initially disable the launch button
        launchButton.setDisable(true);
    }

    /**
     * Populates the Idea ComboBox with ideas having status = 6.
     */
    private void populateIdeaComboBox() {
        String sql = "SELECT ideaID, drugName FROM ideas WHERE status = 6";
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
     * Displays the idea and testing details for the selected idea in the table.
     */
    private void displayIdeaDetails() {
        if (selectedIdea != null) {
            int ideaID = extractIdeaID(selectedIdea);
            ObservableList<InfoRow> details = fetchIdeaAndTestingDetails(ideaID);
            infoTable.setItems(details);
            launchButton.setDisable(false); // Enable the launch button when an idea is selected
        } else {
            infoTable.getItems().clear();
            launchButton.setDisable(true); // Disable the launch button if no idea is selected
        }
    }

    /**
     * Fetches idea and testing details for a specific ideaID from the database.
     *
     * @param ideaID The ideaID for which details are to be fetched.
     * @return A list of InfoRow objects containing the details.
     */
    private ObservableList<InfoRow> fetchIdeaAndTestingDetails(int ideaID) {
        ObservableList<InfoRow> details = FXCollections.observableArrayList();

        String ideaSql = "SELECT * FROM ideas WHERE ideaID = ?";
        String testingSql = "SELECT * FROM Testing WHERE ideaID = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ideaStmt = conn.prepareStatement(ideaSql);
             PreparedStatement testingStmt = conn.prepareStatement(testingSql)) {

            // Fetch idea details
            ideaStmt.setInt(1, ideaID);
            ResultSet ideaRs = ideaStmt.executeQuery();
            if (ideaRs.next()) {
                details.add(new InfoRow("Drug Name", ideaRs.getString("drugName")));
                details.add(new InfoRow("Description", ideaRs.getString("description")));
                details.add(new InfoRow("Category", ideaRs.getString("category")));
                details.add(new InfoRow("Chemical Formula", ideaRs.getString("chemicalFormula")));
                details.add(new InfoRow("Price", ideaRs.getString("price") + " USD"));
            }

            // Fetch testing details
            testingStmt.setInt(1, ideaID);
            ResultSet testingRs = testingStmt.executeQuery();
            while (testingRs.next()) {
                details.add(new InfoRow("Test Name", testingRs.getString("testName")));
                details.add(new InfoRow("Test Details", testingRs.getString("details")));
                details.add(new InfoRow("Testing Budget", testingRs.getString("testingBudget") + " USD"));
                details.add(new InfoRow("Number of Tests", testingRs.getString("numberOfTests")));
                details.add(new InfoRow("Time Taken", testingRs.getString("timeTaken") + " days"));
            }

        } catch (Exception e) {
            System.err.println("Error fetching details: " + e.getMessage());
        }

        return details;
    }

    /**
     * Updates the status of the selected idea to 7 in the database.
     */
    private void launchIdea() {
        if (selectedIdea != null) {
            int ideaID = extractIdeaID(selectedIdea);
            String sql = "UPDATE ideas SET status = 7 WHERE ideaID = ?";

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, ideaID);
                pstmt.executeUpdate();
                showAlert("Launch Successful", "The idea has been successfully launched!", Alert.AlertType.INFORMATION);

                // Remove launched idea from dropdown and clear the table
                ideaSelectionComboBox.getItems().remove(selectedIdea);
                infoTable.getItems().clear();
                launchButton.setDisable(true);

            } catch (Exception e) {
                System.err.println("Error launching idea: " + e.getMessage());
            }
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
     * Displays an alert dialog.
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

    /**
     * Inner class to represent a row in the table.
     */
    public static class InfoRow {
        private final String parameter;
        private final String value;

        public InfoRow(String parameter, String value) {
            this.parameter = parameter;
            this.value = value;
        }

        public String getParameter() {
            return parameter;
        }

        public String getValue() {
            return value;
        }
    }
}
