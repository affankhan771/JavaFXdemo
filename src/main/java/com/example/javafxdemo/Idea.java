package com.example.javafxdemo;

// A simple class to hold Idea details
class Idea {
    private String name;
    private String description;
    private String category;
    private String chemicalFormula;
    private String estimatedPrice;

    public Idea(String name, String description, String category, String chemicalFormula, String estimatedPrice) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.chemicalFormula = chemicalFormula;
        this.estimatedPrice = estimatedPrice;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getChemicalFormula() {
        return chemicalFormula;
    }

    public String getEstimatedPrice() {
        return estimatedPrice;
    }
}

