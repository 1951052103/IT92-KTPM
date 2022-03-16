module com.nbl.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.nbl.app to javafx.fxml;
    exports com.nbl.app;
    exports com.nbl.pojo;
}
