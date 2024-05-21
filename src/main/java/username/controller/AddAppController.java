package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import username.model.User;
import username.model.UserDAO;

import java.io.IOException;
import java.util.List;

public class AddAppController{
    @FXML
    private ComboBox<String> appNameComboBox;  // Ensure this is typed with <String>
    @FXML
    private TextField appNameTextField;

    @FXML
    private Spinner<Integer> weeklyLimitSpinner;

    @FXML
    private Spinner<Integer> monthlyLimitSpinner;

    private User user;
    private int id;
    private final UserDAO userDAO;
    private Stage primaryStage;

    public AddAppController() {
        userDAO = new UserDAO();
    }
    @FXML
    public void onbuttonBacktoHome(ActionEvent actionEvent)
    {
        try {
            userDAO.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/HomePage-view.fxml"));
            Parent root = loader.load();
            // Get the controller for the home page
            HomePageController homeController = loader.getController();
            // Pass any necessary data to the home controller if needed
            // For example, you can pass the username:
            homeController.setUser(user);
            homeController.setPrimaryStage(primaryStage);
            homeController.initialize();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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
    private List<String> getAppNamesFromUserPreferences() {
        // This method should return a list of app names retrieved from user preferences.
        // This is a placeholder implementation.
        return List.of("Google Chrome", "Microsoft Word", "Discord", "Steam", "Microsoft PowerPoint", "Facebook", "Instagram", "Microsoft Office", "Microsoft Excel");
    }

    @FXML
    public void initialize() {
        // Initialize spinners
        weeklyLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 168, 0));
        weeklyLimitSpinner.setEditable(true);

        monthlyLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 720, 0));
        monthlyLimitSpinner.setEditable(true);

        // Populate ComboBox with app names from user preferences
        List<String> appNames = getAppNamesFromUserPreferences();
        appNameComboBox.getItems().addAll(appNames);
    }

    @FXML
    public void onSaveButton(ActionEvent event) {
        // Read the input from the ComboBox and TextField for app name
        String selectedAppName = appNameComboBox.getValue();
        String appName = appNameTextField.getText().isEmpty() ? selectedAppName : appNameTextField.getText();

        if (weeklyLimitSpinner.getValue() != null && monthlyLimitSpinner.getValue() != null) {
            saveFunction(appName);
        } else {
            appNameTextField.setText("Values are non enter limit value");
            // Handle the case where one or both spinner values are null
            // For example, display an error message to the user or set default values
            return;
        }

        // Clear the TextFields after saving
        appNameTextField.clear();
        // Add the app name to the user preferences ComboBox if it's a new entry
        if (!appNameComboBox.getItems().contains(appName)) {
            appNameComboBox.getItems().add(appName);
        }
    }

    public void saveFunction(String appName) {
        int weeklyLimit = weeklyLimitSpinner.getValue();
        int monthlyLimit = monthlyLimitSpinner.getValue();
        user = getUser();
        id = user.getId();
        System.out.println("id name: " + id);
        System.out.println("user name: " + user.getFirstName());
        String username = user.getUsername();
        userDAO.addAppName(id, appName, weeklyLimit, monthlyLimit);
        // Now you can proceed with your logic that depends on weeklyLimit and monthlyLimit
    }

    @FXML
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
