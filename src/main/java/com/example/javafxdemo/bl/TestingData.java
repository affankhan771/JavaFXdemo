package com.example.javafxdemo.bl;
import com.example.javafxdemo.db.*;
import com.example.javafxdemo.ui.*;
import com.example.javafxdemo.bl.*;
public class TestingData {
    private int testID;
    private int ideaID;
    private String testName;
    private String testDetails;
    private double testingBudget;
    private int numberOfTests;
    private int timeTaken;

    public TestingData(int testID, int ideaID, String testName, String testDetails, double testingBudget, int numberOfTests, int timeTaken) {
        this.testID = testID;
        this.ideaID = ideaID;
        this.testName = testName;
        this.testDetails = testDetails;
        this.testingBudget = testingBudget;
        this.numberOfTests = numberOfTests;
        this.timeTaken = timeTaken;
    }

    public int getTestID() { return testID; }
    public int getIdeaID() { return ideaID; }
    public String getTestName() { return testName; }
    public String getTestDetails() { return testDetails; }
    public double getTestingBudget() { return testingBudget; }
    public int getNumberOfTests() { return numberOfTests; }
    public int getTimeTaken() { return timeTaken; }
}

