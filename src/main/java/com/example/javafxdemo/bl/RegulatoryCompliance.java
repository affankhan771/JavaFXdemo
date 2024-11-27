package com.example.javafxdemo.bl;

import java.util.Date;

public class RegulatoryCompliance {
    private int id;
    private int ideaID;
    private Date dateOfSubmission;
    private String status;
    private int approverID;
    private String reviewComments;

    // Constructors
    public RegulatoryCompliance() {
        // Default constructor
    }

    public RegulatoryCompliance(int id, int ideaID, Date dateOfSubmission, String status, int approverID, String reviewComments) {
        this.id = id;
        this.ideaID = ideaID;
        this.dateOfSubmission = dateOfSubmission;
        this.status = status;
        this.approverID = approverID;
        this.reviewComments = reviewComments;
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

    public Date getDateOfSubmission() {
        return dateOfSubmission;
    }
    public void setDateOfSubmission(Date dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getApproverID() {
        return approverID;
    }
    public void setApproverID(int approverID) {
        this.approverID = approverID;
    }

    public String getReviewComments() {
        return reviewComments;
    }
    public void setReviewComments(String reviewComments) {
        this.reviewComments = reviewComments;
    }

    // Methods
    public void approveIdea() {
        this.status = "Approved";
        // Additional logic for approving the idea can be added here
    }

    public void rejectIdea() {
        this.status = "Rejected";
        // Additional logic for rejecting the idea can be added here
    }

    // You can include additional methods if necessary
}