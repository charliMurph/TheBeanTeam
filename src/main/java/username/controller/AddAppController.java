package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import username.model.User;
import username.model.UserDAO;

import java.io.IOException;

public class AddAppController {
    public TextField appNameTextField;
    public Spinner weeklyLimitSpinner;
    public Spinner monthlyLimitSpinner;
    private User user;
    private int id;
    private final UserDAO userDAO;
    private Stage primaryStage;
    public AddAppController(){ userDAO = new UserDAO();}

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
        if (monthlyLimitSpinner.getValue() != null && monthlyLimitSpinner.getValue() != null) {
            saveFunction(appName);
        } else {
            appNameTextField.setText("Values are non enter limit value");
            // Handle the case where one or both spinner values are null
            // For example, display an error message to the user or set default values
            return;
        }

        // Clear the TextFields after saving
        appNameTextField.clear();
        // Add the app name to the user preferences
        // Clear the TextField after saving
    }
    public void saveFunction(String appName) {
            int weeklyLimit = (int) weeklyLimitSpinner.getValue();
            int monthlyLimit = (int) monthlyLimitSpinner.getValue();
            user = getUser();
            id = user.getId();
            System.out.println("id name: " + id);
            System.out.println("user name: " + user.getFirstName());
            String username = user.getUsername();
            userDAO.addAppName(id, appName, weeklyLimit, monthlyLimit);
            // Now you can proceed with your logic that depends on weeklyLimit and monthlyLimit

    }
    public void onBackButton(ActionEvent actionEvent) {
        try {
            System.out.println("Closed user DAO on back");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/PreferencesPage-view.fxml"));
            Parent root = loader.load();
            UserPreferenceController userPreferenceController = loader.getController();
            userPreferenceController.setPrimaryStage(primaryStage);
            userPreferenceController.setUser(user);
            userDAO.close();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}