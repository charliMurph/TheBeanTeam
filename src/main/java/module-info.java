module com.example.the_bean_time_screen_time {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.sun.jna;
    requires com.sun.jna.platform;
    opens username to javafx.fxml;
    exports username;
    exports username.controller;
    opens username.controller to javafx.fxml;
    exports username.model;
    opens username.model to javafx.fxml;
}

