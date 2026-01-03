module com.cgvsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires tools.jackson.databind;


    opens com.cgvsu to javafx.fxml;
    exports com.cgvsu;
}