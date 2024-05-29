//package username.controller;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Spinner;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import username.model.User;
//import username.model.UserDAO;
//import username.model.UserPreferences;
//
//public class AddAppController {
//    public TextField appNameTextField;
//    public Spinner weeklyLimitSpinner;
//    public Spinner monthlyLimitSpinner;
//    private User user;
//    private int id;
//    private UserPreferences preferences;
//    private Stage primaryStage;
//
//    public void setPrimaryStage(Stage primaryStage) {
//        this.primaryStage = primaryStage;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//    public User getUser() {
//        return user;
//    }
//
//    public void initialize()
//    {
//        preferences = new UserPreferences();
//    }
//
//    @FXML
//    public void onSaveButton(ActionEvent event) {
//        // Read the input from the TextField for app name
//        String appName = appNameTextField.getText();
//        if (monthlyLimitSpinner.getValue() != null && monthlyLimitSpinner.getValue() != null) {
//
//            int weeklyLimit = (int) weeklyLimitSpinner.getValue();
//            int monthlyLimit = (int) monthlyLimitSpinner.getValue();
//            user = getUser();
//            id = user.getId();
//            System.out.println("id name: " + id);
//            System.out.println("user name: " + user.getFirstName());
//            preferences.addAppName(id, appName, weeklyLimit, monthlyLimit);
//            // Now you can proceed with your logic that depends on weeklyLimit and monthlyLimit
//        } else {
//            appNameTextField.setText("Values are non enter limit value");
//            // Handle the case where one or both spinner values are null
//            // For example, display an error message to the user or set default values
//            return;
//        }
//
//        // Add the app name and limits to the user preferences
//
//        // Clear the TextFields after saving
//        appNameTextField.clear();
//        // Add the app name to the user preferences
//        // Clear the TextField after saving
//    }
//
//
//}