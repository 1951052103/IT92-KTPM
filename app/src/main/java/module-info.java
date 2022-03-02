module com.nbl.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.nbl.app to javafx.fxml;
    exports com.nbl.app;
}
