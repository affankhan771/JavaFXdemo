package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegulatoryComplianceController {

    @FXML
    private Button saveButton, submitButton, saveDraftButton, uploadSafetyDocsButton, uploadCertDocsButton;

    @FXML
    private TextField drugNameField, drugCodeField, certificateNumberField, approvalNumberField;

    @FXML
    private ComboBox<String> countryDropdown, gmpComplianceDropdown;

    @FXML
    private CheckBox safetyAssessmentToggle, clinicalTrialsToggle, packagingApprovedToggle, labelContainsInfoToggle,
            productApprovedToggle, agreeTermsCheckbox, finalComplianceToggle;

    @FXML
    private TextArea additionalCommentsField;

    public void initialize() {
        // Populate dropdowns with mock data
        countryDropdown.getItems().addAll("USA", "Canada", "UK", "Germany", "India");
        gmpComplianceDropdown.getItems().addAll("Yes", "No", "Not Applicable");

        // Set default actions for buttons (implement behavior as needed)
        saveButton.setOnAction(e -> saveComplianceDetails());
        submitButton.setOnAction(e -> submitComplianceDetails());
        saveDraftButton.setOnAction(e -> saveDraftDetails());
        uploadSafetyDocsButton.setOnAction(e -> mockUpload("Safety Documents"));
        uploadCertDocsButton.setOnAction(e -> mockUpload("Certification Documents"));
    }

    private void saveComplianceDetails() {
        System.out.println("Saving compliance details...");
    }

    private void submitComplianceDetails() {
        System.out.println("Submitting compliance details...");
    }

    private void saveDraftDetails() {
        System.out.println("Saving draft compliance details...");
    }

    private void mockUpload(String docType) {
        System.out.println("Uploading " + docType + "...");
    }
}
