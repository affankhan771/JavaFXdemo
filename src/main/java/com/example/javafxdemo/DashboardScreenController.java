package com.example.javafxdemo;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.Button;


public class DashboardScreenController {

    @FXML
    private BarChart<String, Number> ideasBarChart;

    @FXML
    private CategoryAxis barChartXAxis;

    @FXML
    private NumberAxis barChartYAxis;

    @FXML
    private Label pendingApprovalsCount;

    @FXML
    private Label totalIdeasCount;

    @FXML
    private Label approvedIdeasCount;

    @FXML
    private Button submitIdeaButton;

    @FXML
    private Button pendingApprovalsButton;

    @FXML
    private Button regulatoryComplianceButton;

    // Initialize method should be properly closed
    public void initialize() {
        // Check if BarChart and axes are properly initialized
        if (ideasBarChart == null || barChartXAxis == null || barChartYAxis == null) {
            System.out.println("BarChart or Axes are not initialized properly");
            return; // Prevent any further actions if initialization fails
        }

        // Sample Data for BarChart
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Ideas Per Month");

        // Add data points to the series
        dataSeries.getData().add(new XYChart.Data<>("July", 10));
        dataSeries.getData().add(new XYChart.Data<>("August", 20));
        dataSeries.getData().add(new XYChart.Data<>("September", 30));
        dataSeries.getData().add(new XYChart.Data<>("October", 40));
        dataSeries.getData().add(new XYChart.Data<>("November", 50));

        // Add the series to the BarChart
        ideasBarChart.getData().add(dataSeries);

        // Set up sample data for metrics
        pendingApprovalsCount.setText("15");
        totalIdeasCount.setText("120");
        approvedIdeasCount.setText("85");

        // Set up the PieChart (You can add similar checks for PieChart as needed)
//        ideasPieChart.getData().addAll(
//                new PieChart.Data("Approved", 85),
//                new PieChart.Data("Pending", 15),
//                new PieChart.Data("Disapproved", 20)
//        );

        // Button Actions
        submitIdeaButton.setOnAction(event -> {
            // Logic for navigating to Submit Idea screen
        });

        pendingApprovalsButton.setOnAction(event -> {
            // Logic for navigating to Pending Approvals screen
        });

        regulatoryComplianceButton.setOnAction(event -> {
            // Logic for navigating to Regulatory Compliance screen
        });
    }

    // Make sure the class is properly closed
}
