package com.example.javafxdemo.bl;

import javafx.scene.control.Button;

public class CLevel extends User {
    public CLevel(String id, String name, String email) {
        super(id, name, email);
        this.grade = 1; // Grade for C-level executives
    }
}