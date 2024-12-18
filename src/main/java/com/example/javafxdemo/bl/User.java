package com.example.javafxdemo.bl;

import javafx.scene.control.Button;

public abstract class  User {
    private String id;
    private String name;
    private String email;
    protected int grade;
    private Button removeButton;

    // Constructor without removeButton
    public User(String id, String name, String email, int grade) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.grade = grade;
    }

    // Constructor without removeButton
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.grade = grade;
    }

    // Constructor with removeButton
    public User(String id, String name, String email, int grade, Button removeButton) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.grade = grade;
        this.removeButton = removeButton;
    }
    public User(String id, String name, String email, Button removeButton) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.grade = grade;
        this.removeButton = removeButton;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getGrade() {
        return grade;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(Button removeButton) {
        this.removeButton = removeButton;
    }
}