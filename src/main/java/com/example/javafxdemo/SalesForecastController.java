package com.example.javafxdemo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SalesForecastController {

    @FXML
    private ComboBox<String> ideaIdComboBox;
    @FXML
    private TextField marketSizeField, growthRateField, pricePerUnitField, marketingBudgetField;
    @FXML
    private ComboBox<String> salesPeriodComboBox, seasonalityComboBox;
    @FXML
    private Label totalForecastLabel;
    @FXML
    private TableView<ForecastBreakdown> forecastBreakdownTable;
    @FXML
    private TableColumn<ForecastBreakdown, String> timePeriodColumn;
    @FXML
    private TableColumn<ForecastBreakdown, Double> expectedSalesColumn;
    @FXML
    private TableColumn<ForecastBreakdown, Double> cumulativeSalesColumn;
    @FXML
    private BarChart<String, Number> salesForecastChart;
    @FXML
    private ProgressBar confidenceMeter;

    @FXML
    private TableView<SalesPlan> planHistoryTable;
    @FXML
    private TableColumn<SalesPlan, Integer> planIDColumn;
    @FXML
    private TableColumn<SalesPlan, Integer> ideaIDColumn;
    @FXML
    private TableColumn<SalesPlan, Integer> marketSizeColumn;
    @FXML
    private TableColumn<SalesPlan, Float> growthRateColumn;
    @FXML
    private TableColumn<SalesPlan, Integer> pricePerUnitColumn;
    @FXML
    private TableColumn<SalesPlan, Integer> salesPeriodColumn;
    @FXML
    private TableColumn<SalesPlan, String> seasonalityColumn;
    @FXML
    private TableColumn<SalesPlan, Integer> marketingBudgetColumn;

    // Navigation Handlers
    @FXML
    private void navigateToHome() {
        // Implement navigation to Dashboard
        System.out.println("Navigating to Home...");
    }

    @FXML
    private void navigateToSalesHistory() {
        // Implement navigation to Sales History Screen
        System.out.println("Navigating to Sales History...");
    }

    // Initialization
    @FXML
    private void initialize() {
        initializeIdeaIdComboBox();
        initializeForecastBreakdownTable();
        initializePlanHistoryTable();

        // Load all sales plans into planHistoryTable
        loadPlanHistory();
    }

    // Initialize Idea ID ComboBox
    private void initializeIdeaIdComboBox() {
        List<Idea> ideas = DataOperations.getAllIdeas();
        ObservableList<String> ideaOptions = FXCollections.observableArrayList();
        for (Idea idea : ideas) {
            ideaOptions.add("ID: " + idea.getIdeaId() + " - " + idea.getName());
        }
        ideaIdComboBox.setItems(ideaOptions);
    }

    // Initialize Forecast Breakdown Table
    private void initializeForecastBreakdownTable() {
        timePeriodColumn.setCellValueFactory(new PropertyValueFactory<>("timePeriod"));
        expectedSalesColumn.setCellValueFactory(new PropertyValueFactory<>("expectedSales"));
        cumulativeSalesColumn.setCellValueFactory(new PropertyValueFactory<>("cumulativeSales"));
        forecastBreakdownTable.setItems(FXCollections.observableArrayList());
    }

    // Initialize Plan History Table
    private void initializePlanHistoryTable() {
        planIDColumn.setCellValueFactory(new PropertyValueFactory<>("planID"));
        ideaIDColumn.setCellValueFactory(new PropertyValueFactory<>("ideaID"));
        marketSizeColumn.setCellValueFactory(new PropertyValueFactory<>("marketSize"));
        growthRateColumn.setCellValueFactory(new PropertyValueFactory<>("growthRate"));
        pricePerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
        salesPeriodColumn.setCellValueFactory(new PropertyValueFactory<>("salesPeriod"));
        seasonalityColumn.setCellValueFactory(new PropertyValueFactory<>("seasonality"));
        marketingBudgetColumn.setCellValueFactory(new PropertyValueFactory<>("marketingBudget"));
        planHistoryTable.setItems(FXCollections.observableArrayList());
    }

    // Load Plan History from Database
    private void loadPlanHistory() {
        List<SalesPlan> salesPlans = DataOperations.getAllSalesPlans();
        ObservableList<SalesPlan> salesPlanList = FXCollections.observableArrayList(salesPlans);
        planHistoryTable.setItems(salesPlanList);
    }

    // Generate Forecast Logic
    @FXML
    private void generateForecast() {
        String selectedIdea = ideaIdComboBox.getValue();
        if (selectedIdea == null || selectedIdea.isEmpty()) {
            showAlert("Input Error", "Please select an Idea ID.", AlertType.WARNING);
            return;
        }

        int ideaID = extractIdeaID(selectedIdea);
        if (ideaID == -1) {
            showAlert("Input Error", "Invalid Idea ID selected.", AlertType.ERROR);
            return;
        }

        // Retrieve input values
        String marketSizeText = marketSizeField.getText().trim();
        String growthRateText = growthRateField.getText().trim();
        String pricePerUnitText = pricePerUnitField.getText().trim();
        String marketingBudgetText = marketingBudgetField.getText().trim();
        String salesPeriodText = salesPeriodComboBox.getValue();
        String seasonalityText = seasonalityComboBox.getValue();

        // Validate inputs
        if (marketSizeText.isEmpty() || growthRateText.isEmpty() || pricePerUnitText.isEmpty()
                || marketingBudgetText.isEmpty() || salesPeriodText == null || seasonalityText == null) {
            showAlert("Input Error", "Please fill in all fields.", AlertType.WARNING);
            return;
        }

        int marketSize;
        float growthRate;
        int pricePerUnit;
        int marketingBudget;

        try {
            marketSize = Integer.parseInt(marketSizeText);
            growthRate = Float.parseFloat(growthRateText);
            pricePerUnit = Integer.parseInt(pricePerUnitText);
            marketingBudget = Integer.parseInt(marketingBudgetText);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numerical values.", AlertType.WARNING);
            return;
        }

        // Perform Forecast Calculation
        // Example Formula:
        // Total Forecast = Market Size * (1 + Growth Rate) * Price Per Unit + Marketing Budget
        double totalForecast = marketSize * (1 + (growthRate / 100)) * pricePerUnit + marketingBudget;

        // Update Total Forecast Label
        totalForecastLabel.setText(String.format("Total Forecasted Sales: $%,.2f", totalForecast));

        // Update Confidence Meter (Assuming confidence is proportional to growth rate)
        double confidence = Math.min(growthRate / 100.0, 1.0); // Cap at 1.0
        confidenceMeter.setProgress(confidence);

        // Populate the BarChart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Forecasted Sales");

        // Example Quarterly Breakdown
        double quarterlyForecast = totalForecast / 4;
        series.getData().add(new XYChart.Data<>("Q1", quarterlyForecast));
        series.getData().add(new XYChart.Data<>("Q2", quarterlyForecast));
        series.getData().add(new XYChart.Data<>("Q3", quarterlyForecast));
        series.getData().add(new XYChart.Data<>("Q4", quarterlyForecast));

        salesForecastChart.getData().clear();
        salesForecastChart.getData().add(series);

        // Populate Forecast Breakdown Table
        forecastBreakdownTable.getItems().clear();
        double cumulative = 0;
        for (int i = 1; i <= 4; i++) {
            cumulative += quarterlyForecast;
            forecastBreakdownTable.getItems().add(new ForecastBreakdown("Q" + i, quarterlyForecast, cumulative));
        }
    }

    // Save Forecast Logic
    @FXML
    private void saveForecast() {
        String selectedIdea = ideaIdComboBox.getValue();
        if (selectedIdea == null || selectedIdea.isEmpty()) {
            showAlert("Input Error", "Please select an Idea ID.", AlertType.WARNING);
            return;
        }

        int ideaID = extractIdeaID(selectedIdea);
        if (ideaID == -1) {
            showAlert("Input Error", "Invalid Idea ID selected.", AlertType.ERROR);
            return;
        }

        // Retrieve input values
        String marketSizeText = marketSizeField.getText().trim();
        String growthRateText = growthRateField.getText().trim();
        String pricePerUnitText = pricePerUnitField.getText().trim();
        String marketingBudgetText = marketingBudgetField.getText().trim();
        String salesPeriodText = salesPeriodComboBox.getValue();
        String seasonalityText = seasonalityComboBox.getValue();

        // Validate inputs
        if (marketSizeText.isEmpty() || growthRateText.isEmpty() || pricePerUnitText.isEmpty()
                || marketingBudgetText.isEmpty() || salesPeriodText == null || seasonalityText == null) {
            showAlert("Input Error", "Please fill in all fields.", AlertType.WARNING);
            return;
        }

        int marketSize;
        float growthRate;
        int pricePerUnit;
        int marketingBudget;
        int salesPeriod; // in years

        try {
            marketSize = Integer.parseInt(marketSizeText);
            growthRate = Float.parseFloat(growthRateText);
            pricePerUnit = Integer.parseInt(pricePerUnitText);
            marketingBudget = Integer.parseInt(marketingBudgetText);
            salesPeriod = parseSalesPeriod(salesPeriodText);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numerical values.", AlertType.WARNING);
            return;
        }

        // Perform Forecast Calculation
        double totalForecast = marketSize * (1 + (growthRate / 100)) * pricePerUnit + marketingBudget;

        // Insert into SalesPlan table
        SalesPlan newSalesPlan = new SalesPlan(ideaID, marketSize, growthRate, pricePerUnit, salesPeriod, seasonalityText, marketingBudget);
        boolean success = DataOperations.insertSalesPlan(newSalesPlan);

        if (success) {
            showAlert("Success", "Forecast saved successfully!", AlertType.INFORMATION);
            loadPlanHistory(); // Refresh the plan history table
        } else {
            showAlert("Error", "Failed to save the forecast. Please try again.", AlertType.ERROR);
        }
    }

    // Helper Method to Parse Sales Period Text to Integer (Years)
    private int parseSalesPeriod(String salesPeriodText) {
        switch (salesPeriodText) {
            case "1 Year":
                return 1;
            case "2 Years":
                return 2;
            case "5 Years":
                return 5;
            default:
                return 1; // Default to 1 year if unrecognized
        }
    }

    // Helper Method to Extract Idea ID from ComboBox Selection
    private int extractIdeaID(String selectedIdea) {
        // Assumes the format "ID: <ideaID> - <ideaName>"
        try {
            String[] parts = selectedIdea.split(" - ")[0].split(": ");
            return Integer.parseInt(parts[1].trim());
        } catch (Exception e) {
            System.err.println("Failed to extract ideaID: " + e.getMessage());
            return -1;
        }
    }

    // Helper Method to Show Alerts
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Inner Class for Forecast Breakdown Table
    public static class ForecastBreakdown {
        private final String timePeriod;
        private final double expectedSales;
        private final double cumulativeSales;

        public ForecastBreakdown(String timePeriod, double expectedSales, double cumulativeSales) {
            this.timePeriod = timePeriod;
            this.expectedSales = expectedSales;
            this.cumulativeSales = cumulativeSales;
        }

        public String getTimePeriod() {
            return timePeriod;
        }

        public double getExpectedSales() {
            return expectedSales;
        }

        public double getCumulativeSales() {
            return cumulativeSales;
        }
    }
}
