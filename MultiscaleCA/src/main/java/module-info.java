module com.example.multiscaleca {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports com.example.multiscaleca.ui;
    exports com.example.multiscaleca.model;
    exports com.example.multiscaleca.seeding;
    exports com.example.multiscaleca.neighborhood;

    opens com.example.multiscaleca.ui to javafx.fxml;
}