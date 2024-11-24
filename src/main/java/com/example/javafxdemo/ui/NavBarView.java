package com.example.javafxdemo.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class NavBarView {

    public HBox createNavBar(Stage primaryStage) {
        HBox navBar = new HBox(20);
        navBar.setPadding(new Insets(10));
        navBar.setStyle("-fx-background-color: #333333; -fx-padding: 10px;");
        navBar.setAlignment(Pos.CENTER_LEFT);

        Label logo = new Label("MyCompany");
        logo.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        Button profileButton = new Button("V");
        profileButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-font-weight: bold; -fx-border-radius: 50px; -fx-background-radius: 50px; -fx-min-width: 40px; -fx-min-height: 40px;");
        profileButton.setOnMouseEntered(e -> profileButton.setStyle("-fx-background-color: #777777; -fx-text-fill: white;"));
        profileButton.setOnMouseExited(e -> profileButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;"));

        ContextMenu dropdownMenu = new ContextMenu();
        MenuItem logoutItem = new MenuItem("Logout");
        logoutItem.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
        logoutItem.setOnAction(e -> primaryStage.close());
        dropdownMenu.getItems().add(logoutItem);

        profileButton.setOnAction(e -> dropdownMenu.show(profileButton, javafx.geometry.Side.BOTTOM, 0, 0));

        navBar.getChildren().addAll(logo, new Region(), profileButton);
        HBox.setHgrow(navBar.getChildren().get(1), Priority.ALWAYS);

        return navBar;
    }
}
