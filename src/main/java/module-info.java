module com.tango {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.tango.controller to javafx.fxml;
    exports com.tango;
}