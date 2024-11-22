package com.example.javafxdemo;

// A simple class to hold Idea details
class Idea {
    private int id;
    private String name;
    private String description;
    private String category;
    private String chemicalFormula;
    private String estimatedPrice;
    private String submittedby;
    private int status; // New field

    public Idea(int id,String name, String description, String category, String chemicalFormula, String estimatedPrice,String submittedby,int status) {
        this.name = name;
        this.status = status;
        this.id = id;
        this.description = description;
        this.category = category;
        this.chemicalFormula = chemicalFormula;
        this.estimatedPrice = estimatedPrice;
        this.submittedby = submittedby;
    }

    public String getName() {
        return name;
    }
    public int getIdeaId() {return id;}

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
    public String getSubmittedby() {return submittedby;}
    public int getStatus() {return status;}
    public void setStatus(int status) {this.status = status;}
    public void setIdeaId(int id) {this.id = id;}
    public void setDescription(String description) {this.description = description;}
    public void setCategory(String category) {this.category = category;}
}

