package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import username.model.*;

import java.io.IOException;
import java.sql.Connection;

public class UserPreferenceController {
    private User user;
    private UserDAO userDAO;
    private int id;
    private UserPreferences userpreference;
    private Stage primaryStage;
    @FXML
    private CheckBox app1Checkbox;

    @FXML
    private CheckBox app2Checkbox;

    // Add more CheckBox fields for additional apps as needed

    @FXML
    private Slider monthlyScreenTimeSlider;

    @FXML
    private Label monthlyScreenTimeLabel;

    @FXML
    private Slider weeklyScreenTimeSlider;

    @FXML
    private Label weeklyScreenTimeLabel;
    public CheckBox getApp1Checkbox() {
        return app1Checkbox;
    }

    public CheckBox getApp2Checkbox() {
        return app2Checkbox;
    }

    // Add getter methods for additional CheckBox fields as needed

    public Slider getMonthlyScreenTimeSlider() {
        return monthlyScreenTimeSlider;
    }

    public Label getMonthlyScreenTimeLabel() {
        return monthlyScreenTimeLabel;
    }

    public Slider getWeeklyScreenTimeSlider() {
        return weeklyScreenTimeSlider;
    }

    public Label getWeeklyScreenTimeLabel() {
        return weeklyScreenTimeLabel;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setUser(User user) {

        this.user = user;
        this.id = user.getId();
    }
    public User getUser() {

        return user;
    }
    public void initialize() {

        userpreference = new UserPreferences(id);

    }
    public void loadAppNames()
    {
        userpreference.addUserPreference(user);
    }
    public void handleUpdatePreferences(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/HomePage-view.fxml"));
            Parent root = loader.load();
            HomePageController homepageController = loader.getController();
            homepageController.setPrimaryStage(primaryStage);
            homepageController.setUser(user); // Pass the user information to the home page controller

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
