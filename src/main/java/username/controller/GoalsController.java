package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import username.model.Navigate;
import username.model.User;
import username.model.UserDAO;

import java.io.IOException;
import java.util.List;

public class GoalsController implements IControllerPaths {
    @FXML
    private ComboBox<String> appNameComboBox;  // Ensure this is typed with <String>
    @FXML
    public Label appLabel;

    @FXML
    private Spinner<Integer> weeklyLimitSpinner;

    @FXML
    private Spinner<Integer> monthlyLimitSpinner;

    private User user;
    private int id;
    private final UserDAO userDAO;
    private Stage primaryStage;

    public GoalsController() {
        userDAO = new UserDAO();
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
        if(selectedAppName == null){
            appLabel.setText("Enter App Name");
            System.out.println("values not set");
            return;
        }
        if (weeklyLimitSpinner.getValue() != null && monthlyLimitSpinner.getValue() != null) {
            saveFunction(selectedAppName);
        } else {
            appLabel.setText("Values are non enter limit value");
            // Handle the case where one or both spinner values are null
            // For example, display an error message to the user or set default values
            return;
        }
//        // Add the app name to the user preferences ComboBox if it's a new entry
//        if (!appNameComboBox.getItems().contains(appName)) {
//            appNameComboBox.getItems().add(appName);
//        }
    }

    public void saveFunction(String appName) {
        int weeklyLimit = weeklyLimitSpinner.getValue();
        int monthlyLimit = monthlyLimitSpinner.getValue();
        user = getUser();
        System.out.println("id name: " + id);
        System.out.println("user name: " + user.getFirstName());
        String username = user.getUsername();
        userDAO.addAppName(id, appName, weeklyLimit, monthlyLimit);
        // Now you can proceed with your logic that depends on weeklyLimit and monthlyLimit
    }

    @FXML
    public void onBackButton(MouseEvent event) {
        Navigate.caseGoto(event, user,  primaryStage, "/username/DataManagementPage-view.fxml", "DataMan");
    }
    @Override
    //(MouseEvent event, User user, Stage primaryStage)
    public void Home(MouseEvent event) {
        Navigate.caseGoto(event, user,  primaryStage, "/username/HomePage-view.fxml", "Home");
    }
    @Override
    public void DataMan(MouseEvent event) {
        Navigate.caseGoto(event, user, primaryStage,"/username/DataManagementPage-view.fxml", "DataMan");
    }

    @Override
    public void Profile(MouseEvent event) {
        Navigate.caseGoto(event, user, primaryStage,"/username/Profile-view.fxml", "Profile");
    }
    @Override
    public void Analytics(MouseEvent event){
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/Analytics-view.fxml", "Analytics");
    }
    @Override
    public void Settings(MouseEvent event){
        Navigate.goTo("/username/Settings-view.fxml", event);
    }
    @Override
    public void Notifications(MouseEvent event){
        Navigate.goTo("/username/Notifications-view.fxml", event);
    }
    @Override
    public void Resources(MouseEvent event){
        Navigate.goTo("/username/Resource-view.fxml", event);
    }

    public void setUserId(int id) {
        this.id = id;
        System.out.println("id name: " + id);
    }
}
