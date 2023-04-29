module com.example.skyprivate {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.skyprivate to javafx.fxml;
    exports com.example.skyprivate;
}