module com.example.skyprivate {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;


    opens com.example.skyprivate to javafx.fxml;
    exports com.example.skyprivate;
}