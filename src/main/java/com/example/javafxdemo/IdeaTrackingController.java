package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class IdeaTrackingController {

    @FXML
    private ComboBox<String> ideaDropdown;

    @FXML
    private ScrollPane roadmapContainer;

    @FXML
    private VBox roadmapGraph;

    public void initialize() {
        // Populate the dropdown with ideas (mock data for now)
        ideaDropdown.getItems().addAll("Idea 1", "Idea 2", "Idea 3");

        // Create the initial roadmap graph
        roadmapGraph = createRoadmapGraph();
        roadmapContainer.setContent(roadmapGraph);

        // Add listener for dropdown selection
        ideaDropdown.setOnAction(e -> {
            String selectedIdea = ideaDropdown.getValue();
            if (selectedIdea != null) {
                System.out.println("Tracking progress for: " + selectedIdea);
                updateRoadmapProgress(roadmapGraph, selectedIdea);
            }
        });
    }

    private VBox createRoadmapGraph() {
        VBox graph = new VBox(20); // Root container for the roadmap graph
        graph.setStyle("-fx-padding: 20px;");

        // Define the 7 stages for the roadmap
        String[] stages = {
                "Idea Generated",
                "Approval by M-Level Officers",
                "Approval by C-Level Officers",
                "Regulatory Compliance",
                "Sales Forecast",
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
            checkbox.setDisable(true); // Initially disabled

            // Stage Label
            Label stageLabel = new Label(stages[i]); // Descriptive label for the stage
            stageLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

            // Add the checkbox and label to the HBox
            stageNode.getChildren().addAll(checkbox, stageLabel);

            // Add the stage node to the graph
            graph.getChildren().add(stageNode);

            // Add a connecting line if it's not the last stage
            if (i < stages.length - 1) {
                VBox connection = new VBox(); // Connecting line between stages
                connection.setStyle("-fx-background-color: white; -fx-width: 2px; -fx-height: 20px;");
                graph.getChildren().add(connection);
            }
        }

        return graph; // Return the completed roadmap graph
    }

    private void updateRoadmapProgress(VBox graph, String selectedIdea) {
        // Mock data for progress; replace with real data from a database or backend
        int completedStages = switch (selectedIdea) {
            case "Idea 1" -> 3;
            case "Idea 2" -> 5;
            case "Idea 3" -> 7;
            default -> 0;
        };

        int stageIndex = 0;

        for (var node : graph.getChildren()) {
            if (node instanceof HBox stageNode) { // Ensure it's an HBox containing a stage
                CheckBox checkbox = (CheckBox) stageNode.getChildren().get(0); // First child is the checkbox
                checkbox.setSelected(stageIndex < completedStages);
                stageIndex++;
            }
        }
    }
} // <-- Make sure this closing brace exists
