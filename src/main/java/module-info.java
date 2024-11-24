module com.example.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.formsfx;

    opens com.example.javafxdemo to javafx.fxml;
    exports com.example.javafxdemo;
    exports com.example.javafxdemo.db;
    opens com.example.javafxdemo.db to javafx.fxml;
    exports com.example.javafxdemo.ui;
    opens com.example.javafxdemo.ui to javafx.fxml;
    exports com.example.javafxdemo.bl;
    opens com.example.javafxdemo.bl to javafx.fxml;
}