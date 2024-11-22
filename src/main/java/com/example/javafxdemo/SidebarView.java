package com.example.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SidebarView {

    public VBox createSidebar(Stage primaryStage, SidebarActionHandler handler) {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20, 10, 10, 10));
        sidebar.setStyle("-fx-background-color: #333333; -fx-padding: 20px;");
        sidebar.setPrefWidth(200);
        sidebar.setAlignment(Pos.TOP_CENTER);

        String[] sidebarOptions = {"Idea Submission", "Idea Tracking", "My Approvals", "Gandio", "Wasay ke Totay", "JKL"};
        Button[] optionButtons = new Button[6];

        for (int i = 0; i < sidebarOptions.length; i++) {
            Button optionButton = new Button(sidebarOptions[i]);
            optionButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 14px; " +
                    "-fx-font-weight: bold; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-min-width: 160px;");
            optionButton.setOnMouseEntered(e -> optionButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;"));
            optionButton.setOnMouseExited(e -> optionButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;"));

            final int index = i;
            optionButton.setOnAction(e -> handler.handleSidebarOption(primaryStage, index));

            optionButtons[i] = optionButton;
        }

        sidebar.getChildren().addAll(optionButtons);

        return sidebar;
    }

    // Functional interface for handling sidebar actions
    @FunctionalInterface
    public interface SidebarActionHandler {
        void handleSidebarOption(Stage stage, int optionIndex);
    }
}
