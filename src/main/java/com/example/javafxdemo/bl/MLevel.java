package com.example.javafxdemo.bl;

import javafx.scene.control.Button;

public class MLevel extends User {
    public MLevel(String id, String name, String email, Button removeButton) {
        super(id, name, email, removeButton);
        this.grade = 2; // Grade for G-level executives
    }
}