module com.example.the_bean_time_screen_time {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    opens username to javafx.fxml;
    exports username;
    exports username.controller;
    opens username.controller to javafx.fxml;
    exports username.model;
    opens username.model to javafx.fxml;
}

