package com.example.javafxdemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
        String selectSQL = "SELECT * FROM users WHERE userid = ? AND password = ?";

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
    public static boolean insertIdea(String drugName, String description, String category,
                                     String formula, double price, String submittedBy) {
        int ideaID = getNextIdeaID(); // Get the next ideaID
        String userid = "aw";

        String sql = "INSERT INTO ideas(ideaID, drugName, description, category, chemicalFormula, price, submittedBy) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ideaID);
            pstmt.setString(2, drugName);
            pstmt.setString(3, description);
            pstmt.setString(4, category);
            pstmt.setString(5, formula);
            pstmt.setDouble(6, price);
            pstmt.setString(7, submittedBy);

            pstmt.executeUpdate();
            System.out.println("Idea inserted successfully with ideaID: " + ideaID);
            return true;

        } catch (SQLException e) {
            System.err.println("Failed to insert idea: " + e.getMessage());
            return false;
        }
    }

    public static int getNextIdeaID() {
        String sql = "SELECT MAX(ideaID) FROM ideas";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int maxID = rs.getInt(1);
                return maxID + 1;
            } else {
                return 1; // No records yet
            }

        } catch (SQLException e) {
            System.err.println("Failed to get next ideaID: " + e.getMessage());
            return 1;
        }
    }
    public static boolean insertIdeaApproval(int ideaID, String status) {
        String sql = "INSERT INTO IdeaApprovals(ideaID, status) VALUES(?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ideaID);
            pstmt.setString(2, status);

            pstmt.executeUpdate();
            System.out.println("Approval record inserted for ideaID: " + ideaID);
            return true;

        } catch (SQLException e) {
            System.err.println("Failed to insert approval record: " + e.getMessage());
            return false;
        }
    }
    public static boolean updateIdeaApproval(int ideaID, String status) {
        String sql = "UPDATE IdeaApprovals SET status = ? WHERE ideaID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, ideaID);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Approval status updated for ideaID: " + ideaID);
                return true;
            } else {
                System.err.println("No approval record found for ideaID: " + ideaID);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Failed to update approval status: " + e.getMessage());
            return false;
        }
    }

    public static String getIdeaApprovalStatus(int ideaID) {
        String sql = "SELECT status FROM IdeaApprovals WHERE ideaID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ideaID);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("status");
            } else {
                return null; // No record found
            }

        } catch (SQLException e) {
            System.err.println("Failed to retrieve approval status: " + e.getMessage());
            return null;
        }
    }
    public static List<Idea> getPendingIdeas() {
        List<Idea> ideas = new ArrayList<>();
        String sql = "SELECT i.* FROM ideas i LEFT JOIN IdeaApprovals ia ON i.ideaID = ia.ideaID WHERE ia.status = 'Pending' OR ia.status IS NULL";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Idea idea = new Idea(
                        rs.getInt("ideaID"),
                        rs.getString("drugName"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getString("chemicalFormula"),
                        rs.getString("price"),
                        rs.getString("submittedBy")
                );
                ideas.add(idea);

                // If no approval record exists, insert one with status 'Pending'
                if (!ideaApprovalExists(idea.getIdeaId())) {
                    insertIdeaApproval(idea.getIdeaId(), "Pending");
                }
            }

        } catch (SQLException e) {
            System.err.println("Failed to retrieve pending ideas: " + e.getMessage());
        }
        return ideas;
    }

    private static boolean ideaApprovalExists(int ideaID) {
        String sql = "SELECT 1 FROM IdeaApprovals WHERE ideaID = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ideaID);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Failed to check if idea approval exists: " + e.getMessage());
            return false;
        }
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
