package com.example.javafxdemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataOperations {

    // Method to insert a user into the database
    public static void insertUser(String name, String email) {
        String insertSQL = "INSERT INTO users (name, email) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("User inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
        }
    }

    // Method to verify if the given username and password match any in the database
    public static boolean verifyUserCredentials(String name, String password) {
        String selectSQL = "SELECT * FROM users WHERE name = ? AND password = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {

            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            // If a matching user is found, return true
            if (rs.next()) {
                return true;  // User found with matching credentials
            }
        } catch (SQLException e) {
            System.out.println("Error verifying user credentials: " + e.getMessage());
        }

        // Return false if no match is found
        return false;
    }

    public static void main(String[] args) {
        insertUser("John Doe", "johndoe@example.com");  // Insert a user into the database

        // Test the user credentials verification
        boolean isValidUser = verifyUserCredentials("John Doe", "incorrectPassword");
        System.out.println("User verification result: " + isValidUser);  // Should print false if the password is wrong

        isValidUser = verifyUserCredentials("John Doe", "correctPassword");
        System.out.println("User verification result: " + isValidUser);  // Should print true if the password matches
    }
}
