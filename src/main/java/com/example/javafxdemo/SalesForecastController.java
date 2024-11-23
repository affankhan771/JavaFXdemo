package com.example.javafxdemo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

public class SalesForecastController {

    @FXML
    private ComboBox<String> ideaIdComboBox;
    @FXML
    private TextField marketSizeTextField;
    @FXML
    private TextField growthRateTextField;
    @FXML
    private TextField pricePerUnitTextField;
    @FXML
    private ComboBox<String> salesPeriodComboBox;
    @FXML
    private ComboBox<String> seasonalityComboBox;
    @FXML
    private ChoiceBox<String> forecastPeriodChoiceBox;
    @FXML
    private ChoiceBox<String> seasonalityChoiceBox;
    @FXML
    private TextField marketingBudgetTextField;
    @FXML
    private Button generateForecastButton;
    @FXML
    private Button saveForecastButton;
    @FXML
    private Label totalSalesLabel;
    @FXML
    private Label forecastBreakdownLabel;
    @FXML
    private BarChart<String, Number> salesChart;
    @FXML
    private ProgressBar confidenceLevelProgress;

    @FXML
    public void initialize() {
        forecastPeriodChoiceBox.setItems(FXCollections.observableArrayList("1 Year", "2 Years", "5 Years"));
        seasonalityChoiceBox.setItems(FXCollections.observableArrayList("Winter", "Summer", "No Seasonality"));

        XYChart.Series<String, Number> forecastSeries = new XYChart.Series<>();
        forecastSeries.setName("Forecast");

        forecastSeries.getData().add(new XYChart.Data<>("Q1", 5000)); // Example data
        forecastSeries.getData().add(new XYChart.Data<>("Q2", 7000));
        forecastSeries.getData().add(new XYChart.Data<>("Q3", 6000));

        salesChart.getData().add(forecastSeries);
    }

    private void generateForecast() {
        // Sample data; replace with actual logic for forecast calculation
        double marketSize = Double.parseDouble(marketSizeTextField.getText());
        double growthRate = Double.parseDouble(growthRateTextField.getText());
        double pricePerUnit = Double.parseDouble(pricePerUnitTextField.getText());
        String period = salesPeriodComboBox.getValue(); // Get selected period
        String seasonality = seasonalityComboBox.getValue(); // Get selected seasonality
        double marketingBudget = Double.parseDouble(marketingBudgetTextField.getText());

        // Basic forecast calculation
        double forecastedSales = marketSize * (1 + growthRate / 100) * pricePerUnit;

        // Update the total sales label
        totalSalesLabel.setText(String.format("$%.2f", forecastedSales));

        // Generate a simple forecast breakdown based on selected period
        StringBuilder breakdown = new StringBuilder();
        int months = 12;  // Default is 12 months (annual forecast)
        if (period != null) {
            months = switch (period) {
                case "1 Year" -> 12;
                case "2 Years" -> 24;
                case "5 Years" -> 60;
                default -> months;
            };
        }

        breakdown.append("Forecast Breakdown:\n");
        for (int i = 1; i <= months; i++) {
            double monthlySales = forecastedSales * (i / (double) months);
            breakdown.append(String.format("Month %d: $%.2f\n", i, monthlySales));
        }
        forecastBreakdownLabel.setText(breakdown.toString());

        // Display the sales chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Forecasted Sales");
        for (int i = 1; i <= months; i++) {
            double monthlySales = forecastedSales * (i / (double) months);
            series.getData().add(new XYChart.Data<>("Month " + i, monthlySales));
        }
        salesChart.getData().clear(); // Clear any previous data
        salesChart.getData().add(series); // Add the updated forecast series

        // Apply seasonality adjustments if any
        if (seasonality != null) {
            applySeasonalityAdjustments(series, seasonality);
        }

        // Set the confidence level (example progress value)
        confidenceLevelProgress.setProgress(0.75); // Example confidence value, update this as needed
    }

    // Method to apply seasonality adjustments
    private void applySeasonalityAdjustments(XYChart.Series<String, Number> series, String seasonality) {
        if (seasonality.equals("Winter")) {
            // Apply winter seasonality (example logic)
            for (XYChart.Data<String, Number> data : series.getData()) {
                int month = Integer.parseInt(data.getXValue().split(" ")[1]);
                double adjustedValue = data.getYValue().doubleValue();

                if (month >= 12 && month <= 2) { // Winter months (Dec, Jan, Feb)
                    adjustedValue *= 0.9; // Example: reduce sales in winter by 10%
                }

                data.setYValue(adjustedValue); // Apply adjusted value
            }
        } else if (seasonality.equals("Summer")) {
            // Apply summer seasonality (example logic)
            for (XYChart.Data<String, Number> data : series.getData()) {
                int month = Integer.parseInt(data.getXValue().split(" ")[1]);
                double adjustedValue = data.getYValue().doubleValue();

                if (month >= 6 && month <= 8) { // Summer months (Jun, Jul, Aug)
                    adjustedValue *= 1.2; // Example: increase sales in summer by 20%
                }

                data.setYValue(adjustedValue); // Apply adjusted value
            }
        }
        // If there's "No Seasonality", we don't adjust the forecast.
    }

    @FXML
    private void saveForecast() {
        // Logic to save the forecast data (not implemented here)
        System.out.println("Forecast saved.");
    }
}
