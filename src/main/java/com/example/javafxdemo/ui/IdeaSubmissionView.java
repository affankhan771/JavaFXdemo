/*
package com.example.javafxdemo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IdeaSubmissionView {

    public VBox createIdeaSubmissionForm(Stage primaryStage) {
        // Form container
        VBox formArea = new VBox(15);
        formArea.setStyle("-fx-background-color: black; -fx-padding: 20px;");
        formArea.setAlignment(Pos.CENTER);

        // Title Label
        Label titleLabel = new Label("Drug Submission Form");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Drug Name
        Label drugNameLabel = new Label("Drug Name:");
        drugNameLabel.setStyle("-fx-text-fill: white;");
        TextField drugNameField = new TextField();
        drugNameField.setPromptText("Enter the drug name");

        // Description
        Label descriptionLabel = new Label("Description:");
        descriptionLabel.setStyle("-fx-text-fill: white;");
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Enter a brief description of the drug");
        descriptionField.setWrapText(true);
        descriptionField.setPrefHeight(100);

        // Dropdown for Categories
        Label categoryLabel = new Label("Drug Category:");
        categoryLabel.setStyle("-fx-text-fill: white;");
        ComboBox<String> categoryDropdown = new ComboBox<>();
        categoryDropdown.getItems().addAll("Analgesic", "Antibiotic", "Antiseptic", "Vaccine", "Others");
        categoryDropdown.setPromptText("Select a category");

        // Chemical Formula
        Label formulaLabel = new Label("Chemical Formula:");
        formulaLabel.setStyle("-fx-text-fill: white;");
        TextField formulaField = new TextField();
        formulaField.setPromptText("Enter the chemical formula");

        // Estimated Price
        Label priceLabel = new Label("Estimated Price (USD):");
        priceLabel.setStyle("-fx-text-fill: white;");
        TextField priceField = new TextField();
        priceField.setPromptText("Enter the estimated price");
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                priceField.setText(newValue.replaceAll("[^\\d.]", "")); // Allow only numbers and a single decimal
            }
        });

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 14px;");
        submitButton.setOnAction(e -> handleSubmission(
                drugNameField.getText(),
                descriptionField.getText(),
                categoryDropdown.getValue(),
                formulaField.getText(),
                priceField.getText()
        ));

        // Adding components to form
        formArea.getChildren().addAll(
                titleLabel,
                drugNameLabel, drugNameField,
                descriptionLabel, descriptionField,
                categoryLabel, categoryDropdown,
                formulaLabel, formulaField,
                priceLabel, priceField,
                submitButton
        );

        return formArea;
    }

    private void handleSubmission(String drugName, String description, String category,
                                  String formula, String price) {
        // Basic Validation
        if (drugName.isEmpty() || description.isEmpty() || category == null ||
                formula.isEmpty() || price.isEmpty()) {
            showAlert("Validation Error", "Please fill in all fields.");
            return;
        }

        // Here you would typically save to database
        // For now, just print to console
        System.out.println("Drug Submitted Successfully:");
        System.out.println("Name: " + drugName);
        System.out.println("Description: " + description);
        System.out.println("Category: " + category);
        System.out.println("Formula: " + formula);
        System.out.println("Estimated Price: " + price);

        // Show success message
        showAlert("Success", "Drug idea submitted successfully!");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}*/
package com.example.javafxdemo.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class IdeaSubmissionView {

    public VBox createIdeaSubmissionForm(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IdeaSubmissionView.fxml"));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return new VBox(); // Return an empty VBox in case of error
        }
    }
}
