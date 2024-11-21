package com.example.javafxdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:voxatech.db";  // Database file in the root directory

    public static Connection connect() {
        try {
            // Create a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connection to SQLite has been established.");
            return connection;
        } catch (SQLException e) {
            System.out.println("Connection to SQLite failed: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        connect();  // Test the connection
    }
}
