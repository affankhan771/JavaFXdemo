package com.example.javafxdemo.bl;
import com.example.javafxdemo.db.*;
import com.example.javafxdemo.ui.*;
import com.example.javafxdemo.bl.*;
public class SalesPlan {
    private int planID;
    private int ideaID;
    private int marketSize;
    private float growthRate;
    private int pricePerUnit;
    private int salesPeriod;
    private String seasonality;
    private int marketingBudget;

    // Constructor without planID (for new entries)
    public SalesPlan(int ideaID, int marketSize, float growthRate, int pricePerUnit,
                     int salesPeriod, String seasonality, int marketingBudget) {
        this.ideaID = ideaID;
        this.marketSize = marketSize;
        this.growthRate = growthRate;
        this.pricePerUnit = pricePerUnit;
        this.salesPeriod = salesPeriod;
        this.seasonality = seasonality;
        this.marketingBudget = marketingBudget;
    }

    // Constructor with all fields (for existing entries)
    public SalesPlan(int planID, int ideaID, int marketSize, float growthRate, int pricePerUnit,
                     int salesPeriod, String seasonality, int marketingBudget) {
        this.planID = planID;
        this.ideaID = ideaID;
        this.marketSize = marketSize;
        this.growthRate = growthRate;
        this.pricePerUnit = pricePerUnit;
        this.salesPeriod = salesPeriod;
        this.seasonality = seasonality;
        this.marketingBudget = marketingBudget;
    }

    // Getters and Setters
    public int getPlanID() { return planID; }
    public void setPlanID(int planID) { this.planID = planID; }

    public int getIdeaID() { return ideaID; }
    public void setIdeaID(int ideaID) { this.ideaID = ideaID; }

    public int getMarketSize() { return marketSize; }
    public void setMarketSize(int marketSize) { this.marketSize = marketSize; }

    public float getGrowthRate() { return growthRate; }
    public void setGrowthRate(float growthRate) { this.growthRate = growthRate; }

    public int getPricePerUnit() { return pricePerUnit; }
    public void setPricePerUnit(int pricePerUnit) { this.pricePerUnit = pricePerUnit; }

    public int getSalesPeriod() { return salesPeriod; }
    public void setSalesPeriod(int salesPeriod) { this.salesPeriod = salesPeriod; }

    public String getSeasonality() { return seasonality; }
    public void setSeasonality(String seasonality) { this.seasonality = seasonality; }

    public int getMarketingBudget() { return marketingBudget; }
    public void setMarketingBudget(int marketingBudget) { this.marketingBudget = marketingBudget; }
}

