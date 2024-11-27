package com.example.javafxdemo.bl;

import java.util.Date;

public class ProductLaunch {
    private int id;
    private int ideaID;
    private Date launchDate;
    private String productName;
    private String marketingPlan;

    // Constructors
    public ProductLaunch() {
        // Default constructor
    }

    public ProductLaunch(int id, int ideaID, Date launchDate, String productName, String marketingPlan) {
        this.id = id;
        this.ideaID = ideaID;
        this.launchDate = launchDate;
        this.productName = productName;
        this.marketingPlan = marketingPlan;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdeaID() {
        return ideaID;
    }
    public void setIdeaID(int ideaID) {
        this.ideaID = ideaID;
    }

    public Date getLaunchDate() {
        return launchDate;
    }
    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMarketingPlan() {
        return marketingPlan;
    }
    public void setMarketingPlan(String marketingPlan) {
        this.marketingPlan = marketingPlan;
    }

    // Methods
    public String getLaunchDetails() {
        return "Product Launch Details:\n" +
                "ID: " + id + "\n" +
                "Idea ID: " + ideaID + "\n" +
                "Launch Date: " + launchDate + "\n" +
                "Product Name: " + productName + "\n" +
                "Marketing Plan: " + marketingPlan;
    }

    // You can include additional methods if necessary
}