package com.example.javafxdemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataOperations {

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

    public static void main(String[] args) {
        insertUser("John Doe", "johndoe@example.com");  // Insert a user into the database
    }
}
