package com.example.javafxdemo.ui;

import com.example.javafxdemo.db.DataOperations;
import javafx.fxml.FXML;
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
        setupBarChart();

        // Add the series to the BarChart
        //ideasBarChart.getData().add(dataSeries);

        // Set up sample data for metrics
                int totalIdeas = DataOperations.getTotalIdeasCount();
        int approvedIdeas = DataOperations.getApprovedIdeasCount();
        int rejectedIdeas = DataOperations.getRejectedIdeasCount();
        int pendingApprovals = DataOperations.getPendingApprovalsCount();

        totalIdeasCount.setText(String.valueOf(totalIdeas));
        approvedIdeasCount.setText(String.valueOf(approvedIdeas));
       // rejectedIdeasCount.setText(String.valueOf(rejectedIdeas));
        pendingApprovalsCount.setText(String.valueOf(pendingApprovals));

        /*pendingApprovalsCount.setText("15");
        totalIdeasCount.setText("120");
        approvedIdeasCount.setText("85");*/

        // Set up the PieChart (You can add similar checks for PieChart as needed)
//        ideasPieChart.getData().addAll(
//                new PieChart.Data("Approved", 85),
//                new PieChart.Data("Pending", 15),
//                new PieChart.Data("Disapproved", 20)
//        );

        // Button Actions
        /*submitIdeaButton.setOnAction(event -> {
            // Logic for navigating to Submit Idea screen
        });

        pendingApprovalsButton.setOnAction(event -> {
            // Logic for navigating to Pending Approvals screen
        });

        regulatoryComplianceButton.setOnAction(event -> {
            // Logic for navigating to Regulatory Compliance screen
        });*/
    }

    private void setupBarChart() {
        // Check if BarChart and axes are properly initialized
        if (ideasBarChart == null || barChartXAxis == null || barChartYAxis == null) {
            System.out.println("BarChart or Axes are not initialized properly");
            return;
        }

        // Fetch data from the database for the bar chart
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Ideas Per Month");

        dataSeries.getData().addAll(DataOperations.getIdeasPerMonthData());

        // Clear existing data and add the new series
        ideasBarChart.getData().clear();
        ideasBarChart.getData().add(dataSeries);

        // Customize the axes if needed
        barChartXAxis.setLabel("Month");
        barChartYAxis.setLabel("Number of Ideas");
    }

    // Make sure the class is properly closed
}
