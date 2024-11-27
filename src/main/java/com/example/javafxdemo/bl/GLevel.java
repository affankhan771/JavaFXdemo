package com.example.javafxdemo.bl;

import javafx.scene.control.Button;

public class GLevel extends User {
    public GLevel(String id, String name, String email, Button removeButton) {
        super(id, name, email, removeButton);
        this.grade = 3; // Grade for G-level executives
    }
}