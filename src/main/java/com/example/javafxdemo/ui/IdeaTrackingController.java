package com.example.javafxdemo.ui;

import com.example.javafxdemo.bl.Idea;
import com.example.javafxdemo.db.DataOperations;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class IdeaTrackingController {

    @FXML
    private ComboBox<String> ideaDropdown;

    @FXML
    private ScrollPane roadmapContainer;

    private VBox roadmapGraph;

    public void initialize() {
        // Create the initial roadmap graph
        roadmapGraph = createRoadmapGraph();
        roadmapContainer.setContent(roadmapGraph);

        // Fetch ideas from the database
        List<Idea> allIdeas = DataOperations.getAllIdeas();

        // Populate the dropdown with idea IDs and names
        for (Idea idea : allIdeas) {
            ideaDropdown.getItems().add("ID: " + idea.getIdeaId() + " - " + idea.getName());
        }

        // Add listener for dropdown selection
        ideaDropdown.setOnAction(e -> {
            String selectedItem = ideaDropdown.getValue();
            if (selectedItem != null) {
                // Extract the ideaID from the selected item
                int ideaID = extractIdeaID(selectedItem);
                // Fetch the idea's status from the database
                int ideaStatus = DataOperations.getIdeaStatusById(ideaID);
                if (ideaStatus != -1) {
                    System.out.println("Tracking progress for Idea ID: " + ideaID + " with status: " + ideaStatus);
                    updateRoadmapProgress(ideaStatus);
                } else {
                    System.err.println("Idea not found or error retrieving status.");
                }
            }
        });
    }

    private VBox createRoadmapGraph() {
        VBox graph = new VBox(10); // Root container for the roadmap graph
        graph.setStyle("-fx-padding: 20px; -fx-background-color: #333333;");

        // Define the 7 stages for the roadmap
        String[] stages = {
                "Idea Generated",
                "Approval by M-Level Officers",
                "Approval by C-Level Officers",
                "Regulatory Compliance",
                "Regulatory Approval",
                "Testing",
                "Launch"
        };

        for (int i = 0; i < stages.length; i++) {
            HBox stageNode = new HBox(10); // Container for each stage
            stageNode.setAlignment(Pos.CENTER_LEFT);
            stageNode.setStyle("-fx-padding: 10px;");

            // Checkbox for the stage
            CheckBox checkbox = new CheckBox(); // Standalone checkbox
            checkbox.setStyle("-fx-text-fill: white;");
            checkbox.setDisable(true); // Disabled, as users should not manually change it

            // Stage Label
            Label stageLabel = new Label(stages[i]); // Descriptive label for the stage
            stageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

            // Add the checkbox and label to the HBox
            stageNode.getChildren().addAll(checkbox, stageLabel);

            // Add the stage node to the graph
            graph.getChildren().add(stageNode);

            // Add a connecting line if it's not the last stage
            if (i < stages.length - 1) {
                VBox connection = new VBox();
                connection.setStyle("-fx-background-color: white; -fx-width: 2px; -fx-height: 20px;");
                graph.getChildren().add(connection);
            }
        }

        return graph; // Return the completed roadmap graph
    }

    private void updateRoadmapProgress(int ideaStatus) {
        int stageIndex = 1; // Status starts from 1

        for (var node : roadmapGraph.getChildren()) {
            if (node instanceof HBox stageNode) {
                CheckBox checkbox = (CheckBox) stageNode.getChildren().get(0); // First child is the checkbox
                if (stageIndex <= ideaStatus) {
                    checkbox.setSelected(true);
                } else {
                    checkbox.setSelected(false);
                }
                stageIndex++;
            }
        }
    }

    private int extractIdeaID(String selectedItem) {
        // Assumes the format is "ID: <ideaID> - <ideaName>"
        try {
            String[] parts = selectedItem.split(" - ")[0].split(": ");
            return Integer.parseInt(parts[1].trim());
        } catch (Exception e) {
            System.err.println("Failed to extract ideaID: " + e.getMessage());
            return -1;
        }
    }
}
