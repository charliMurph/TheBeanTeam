package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import username.model.*;

import java.io.IOException;

public class UserPreferenceController {
    private User user;
    private UserDAO userDAO;
    private int id;
    private UserPreferences preference;
    private Stage primaryStage;
    @FXML
    private CheckBox app1Checkbox;

    @FXML
    private CheckBox app2Checkbox;

    // Add more CheckBox fields for additional apps as needed
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

        preference = new UserPreferences(user);

    }
    public void loadAppNames()
    {
        preference.addUserPreference(preference);
    }
    public void handleUpdatePreferences(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/AddAppPage-view.fxml"));
            Parent root = loader.load();
            AddAppController addAppController = loader.getController();
            addAppController.setPrimaryStage(primaryStage);
            addAppController.setUser(user); // Pass the user information to the home page controller
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
