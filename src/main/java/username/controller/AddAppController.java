package username.controller;

import javafx.event.ActionEvent;
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
        this.id = user.getId();
    }
    public User getUser() {

        return user;
    }

    public void onSaveButton(ActionEvent actionEvent) {
        appNameTextField.setText("Done!");
    }
}