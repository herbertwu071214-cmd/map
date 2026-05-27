module com.herb.macondo.map {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.herb.macondo.map to javafx.fxml;
    exports com.herb.macondo.map;
    exports com.herb.macondo.map.model;
    exports com.herb.macondo.map.view;
    exports com.herb.macondo.map.controller;
    exports com.herb.macondo.map.input;
}
