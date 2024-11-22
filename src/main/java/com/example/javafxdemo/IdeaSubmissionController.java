package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class IdeaSubmissionController implements Initializable{

    @FXML
    private TextField drugNameField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private ComboBox<String> categoryDropdown;

    @FXML
    private TextField formulaField;

    @FXML
    private TextField priceField;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate the dropdown menu with items
        categoryDropdown.getItems().addAll("Analgesic", "Antibiotic", "Antiseptic", "Vaccine", "Others");
    }

    @FXML
    private void handleSubmit() {
        String drugName = drugNameField.getText();
        String description = descriptionField.getText();
        String category = categoryDropdown.getValue();
        String formula = formulaField.getText();
        String price = priceField.getText();

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
}
