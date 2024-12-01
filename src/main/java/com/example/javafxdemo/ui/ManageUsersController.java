package com.example.javafxdemo.ui;

import com.example.javafxdemo.bl.CLevel;
import com.example.javafxdemo.bl.GLevel;
import com.example.javafxdemo.bl.MLevel;
import com.example.javafxdemo.bl.User;
import com.example.javafxdemo.db.DataOperations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

//import com.example.javafxdemo.model.User;
//import com.example.javafxdemo.util.DataOperations;
import javafx.scene.control.*;

public class ManageUsersController {

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> colId;

    @FXML
    private TableColumn<User, String> colName;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, Integer> colGrade;

    @FXML
    private TableColumn<User, Button> colAction;

    @FXML
    private Button addUserButton;

    @FXML
    private TextField idField, nameField, emailField, gradeField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void initialize() {
        // Set up table columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeButton"));

        // Load user data from the database
        loadUserData();

        // Set up Add User button action
        addUserButton.setOnMouseClicked(this::handleAddUser);
    }

    // Method to load user data into the TableView
    private void loadUserData() {
        List<User> users = DataOperations.getAllUsers(); // Fetch users from the database
        ObservableList<User> userObservableList = FXCollections.observableArrayList();

        for (User user : users) {
            // Create a Remove button for each user
            Button removeButton = new Button("Remove");
            removeButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            removeButton.setOnAction(event -> removeUser(user.getId()));

            // Assign the button to the user
            user.setRemoveButton(removeButton);

            // Add the user to the observable list
            userObservableList.add(user);
        }

        // Set the items for the TableView
        userTable.setItems(userObservableList);
    }

    // Method to handle adding a new user
    private void handleAddUser(MouseEvent event) {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String gradeText = gradeField.getText().trim();
        String password = passwordField.getText().trim();

        // Validate inputs
        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || gradeText.isEmpty() || password.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields.", Alert.AlertType.WARNING);
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            showAlert("Input Error", "Please enter a valid email address.", Alert.AlertType.WARNING);
            return;
        }

        // Validate grade as integer
        int grade;
        try {
            grade = Integer.parseInt(gradeText);
            if (grade < 1 || grade > 3) { // Assuming grades 1-3 correspond to C, M, G levels
                showAlert("Input Error", "Grade must be between 1 and 3.", Alert.AlertType.WARNING);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Grade must be a valid integer.", Alert.AlertType.WARNING);
            return;
        }

        // Check if user ID already exists
        if (DataOperations.getUserById(id) != null) {
            showAlert("Input Error", "User ID already exists. Please choose a different ID.", Alert.AlertType.WARNING);
            return;
        }
        User newUser;
        if(grade==1){
             newUser = new CLevel(id,name,email);
            //CLevel newUser = new CLevel(id,name,email);
        }
        else if(grade==2){
             newUser = new MLevel(id,name,email);

        }
        else  {
             newUser = new GLevel(id,name,email);
        }

        // Create User object
      /// User newUser = new User(id, name, email);



        // Insert user into database
        boolean success = DataOperations.insertUser(newUser, password);

        if (success) {
            showAlert("Success", "User added successfully.", Alert.AlertType.INFORMATION);
            loadUserData(); // Refresh the table
            clearFields();   // Clear input fields
        } else {
            showAlert("Error", "Failed to add user. Please try again.", Alert.AlertType.ERROR);
        }
    }

    // Method to remove a user
    private void removeUser(String userId) {
        // Confirm deletion
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to remove this user?");
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = DataOperations.deleteUser(userId); // Delete user in the database
                if (success) {
                    showAlert("Success", "User removed successfully.", Alert.AlertType.INFORMATION);
                    loadUserData(); // Refresh the table
                } else {
                    showAlert("Error", "Failed to remove user.", Alert.AlertType.ERROR);
                }
            }
        });
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        // Simple regex for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    // Helper method to clear input fields
    private void clearFields() {
        idField.clear();
        nameField.clear();
        emailField.clear();
        gradeField.clear();
        passwordField.clear();
    }

    // Helper method to show alerts
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
