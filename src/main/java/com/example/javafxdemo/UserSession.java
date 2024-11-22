package com.example.javafxdemo;

public class UserSession {
    private static UserSession instance;
    private String userid;
    private String email;
    private int grade;

    private UserSession(String userid, String email, int grade) {
        this.userid = userid;
        this.email = email;
        this.grade = grade;
    }

    public static void createSession(String userid, String email, int grade) {
        if (instance == null) {
            instance = new UserSession(userid, email, grade);
        }
    }

    public static UserSession getInstance() {
        return instance;
    }

    public String getUserid() {
        return userid;
    }

    public String getEmail() {
        return email;
    }

    public int getGrade() {
        return grade;
    }

    public static void clearSession() {
        instance = null;
    }
}
