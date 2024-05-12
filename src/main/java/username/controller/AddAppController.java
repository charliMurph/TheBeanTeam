package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import username.model.User;
import username.model.UserDAO;
import username.model.UserPreferences;

public class AddAppController {
    public TextField appNameTextField;
    public Spinner weeklyLimitSpinner;
    public Spinner monthlyLimitSpinner;
    private User user;
    private UserDAO userDAO;
    private int id;
    private UserPreferences preferences;
    private Stage primaryStage;
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    @FXML
    public void onSaveButton(ActionEvent event) {
        // Read the input from the TextField for app name
        String appName = appNameTextField.getText();
        int weeklyLimit = (int) weeklyLimitSpinner.getValue();
        int monthlyLimit = (int) monthlyLimitSpinner.getValue();
        preferences.setMonthHours(monthlyLimit);
        preferences.setWeekHours(weeklyLimit);
        // Add the app name and limits to the user preferences
        preferences.addAppName(id, appName);
        // Clear the TextFields after saving
        appNameTextField.clear();
        weeklyLimitSpinner.getValueFactory().setValue(null);
        monthlyLimitSpinner.getValueFactory().setValue(null);
        // Add the app name to the user preferences
        // Clear the TextField after saving
        appNameTextField.clear();
    }
}