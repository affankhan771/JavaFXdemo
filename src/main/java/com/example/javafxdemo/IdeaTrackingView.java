package com.example.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IdeaTrackingView {

    public VBox createIdeaTrackingScreen(Stage primaryStage) {
        // Container for the Idea Tracking Screen
        VBox trackingArea = new VBox(20);
        trackingArea.setStyle("-fx-background-color: black; -fx-padding: 20px;");
        trackingArea.setAlignment(Pos.TOP_CENTER);

        // Title Label
        Label titleLabel = new Label("Idea Tracking");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Dropdown for Selecting an Idea
        Label selectIdeaLabel = new Label("Select Idea:");
        selectIdeaLabel.setStyle("-fx-text-fill: white;");
        ComboBox<String> ideaDropdown = new ComboBox<>();
        ideaDropdown.getItems().addAll("Idea 1", "Idea 2", "Idea 3"); // Replace with dynamic list of ideas
        ideaDropdown.setPromptText("Choose an idea to track");

        // Container for Roadmap Graph
        ScrollPane roadmapContainer = new ScrollPane();
        roadmapContainer.setFitToWidth(true);
        roadmapContainer.setStyle("-fx-background-color: black; -fx-border-color: #555555; -fx-border-width: 2px;");
        VBox roadmapGraph = createRoadmapGraph();

        // Add Roadmap Graph to ScrollPane
        roadmapContainer.setContent(roadmapGraph);

        // Handle Dropdown Selection
        ideaDropdown.setOnAction(e -> {
            String selectedIdea = ideaDropdown.getValue();
            if (selectedIdea != null) {
                System.out.println("Tracking progress for: " + selectedIdea);
                // Update stage checkboxes dynamically based on selected idea's progress
                updateRoadmapProgress(roadmapGraph, selectedIdea);
            }
        });

        // Add components to the Tracking Screen
        trackingArea.getChildren().addAll(titleLabel, selectIdeaLabel, ideaDropdown, roadmapContainer);

        return trackingArea;
    }

    private VBox createRoadmapGraph() {
        VBox roadmapGraph = new VBox(20);
        roadmapGraph.setAlignment(Pos.CENTER);
        roadmapGraph.setStyle("-fx-padding: 20px;");

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

        // Dynamic Nodes for Each Stage
        for (int i = 0; i < stages.length; i++) {
            // Node Container
            HBox stageNode = new HBox(10);
            stageNode.setAlignment(Pos.CENTER_LEFT);
            stageNode.setStyle("-fx-padding: 10px;");

            // Stage Name
            Label stageLabel = new Label((i + 1) + ". " + stages[i]);
            stageLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

            // Checkbox for Completion
            CheckBox stageCheckbox = new CheckBox();
            stageCheckbox.setStyle("-fx-border-color: white; -fx-border-width: 2px;");
            stageCheckbox.setDisable(true); // Initially disabled; can be enabled dynamically

            // Add components to stage node
            stageNode.getChildren().addAll(stageCheckbox, stageLabel);

            // Add connecting line if not the last stage
            if (i < stages.length - 1) {
                VBox connection = new VBox();
                connection.setStyle("-fx-background-color: white; -fx-width: 2px; -fx-height: 20px;");
                roadmapGraph.getChildren().addAll(stageNode, connection);
            } else {
                roadmapGraph.getChildren().add(stageNode);
            }
        }

        return roadmapGraph;
    }

    private void updateRoadmapProgress(VBox roadmapGraph, String selectedIdea) {
        // Mock data for progress; replace with real data from database or backend
        int completedStages = switch (selectedIdea) {
            case "Idea 1" -> 3; // Idea 1 completed 3 stages
            case "Idea 2" -> 5; // Idea 2 completed 5 stages
            case "Idea 3" -> 7; // Idea 3 completed all stages
            default -> 0;
        };

        // Update roadmap UI
        for (int i = 0; i < roadmapGraph.getChildren().size(); i++) {
            if (roadmapGraph.getChildren().get(i) instanceof HBox stageNode) {
                CheckBox checkbox = (CheckBox) stageNode.getChildren().get(0); // Checkbox is the first child
                checkbox.setSelected(i / 2 < completedStages); // Divide by 2 since connecting lines are counted
            }
        }
    }
}