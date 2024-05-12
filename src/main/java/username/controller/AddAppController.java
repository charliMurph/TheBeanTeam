package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import username.model.User;
import username.model.UserPreferences;

import java.io.IOException;

public class AddAppController implements IController {
    private Stage primaryStage;
    private User user;
    private int id;
    @FXML
    private TextField appNameTextField;
    @FXML
    private Spinner weeklyLimitSpinner;
    @FXML
    private Spinner monthlyLimitSpinner;


    private UserPreferences userpref;
    public void setUser(User user) {

        this.user = user;
        this.id = user.getId();
    }
    @Override
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @Override
    public void initialize() {
        // Set default values for Spinner controls
        weeklyLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 168, 0));
        monthlyLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 720, 0));

        // Configure behavior of Spinner controls
        weeklyLimitSpinner.setEditable(true);
        monthlyLimitSpinner.setEditable(true);
    }

    public void onSaveButton(ActionEvent actionEvent) {
        if (appNameTextField.getText().isEmpty())
        {
            appNameTextField.setText("Please fill in all required fields.");
            return; // Stop execution if any required field is empty
        }
        String appName = appNameTextField.getText();
        int weeklyLimit = (int) weeklyLimitSpinner.getValue();
        int monthlyLimit = (int) monthlyLimitSpinner.getValue();
        userpref = new UserPreferences(id, appName, weeklyLimit, monthlyLimit);
        userpref.addUserPreference(userpref);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/PreferencesPage-view.fxml"));
            Parent root = loader.load();
            UserPreferenceController preferenceController = loader.getController();
            preferenceController.setPrimaryStage(primaryStage);
            preferenceController.setUser(user);
            preferenceController.initialize();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onBackToHome(){

    }
}
