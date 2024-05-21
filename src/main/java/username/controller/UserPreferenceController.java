package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import org.controlsfx.control.ToggleSwitch;
import username.model.*;

import java.io.IOException;

public class UserPreferenceController {
    @FXML
    public ToggleSwitch WordSwitch;
    @FXML
    public ToggleSwitch ChromeSwitch;
    @FXML
    public ToggleSwitch ExcelSwitch;
    @FXML
    public ToggleSwitch SteamSwitch;
    @FXML
    public ToggleSwitch PPTSwitch;
    @FXML
    public ToggleSwitch DiscordSwitch;
    @FXML
    public ToggleSwitch OfficeSwitch;
    @FXML
    public ToggleSwitch InstaSwitch;
    @FXML
    public ToggleSwitch FBSwitch;
    private User user;
    private int id;
    private final UserDAO userDAO;
    private Stage primaryStage;

    // Add more CheckBox fields for additional apps as needed
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public UserPreferenceController(){
        userDAO = new UserDAO();
    }

    public void setUser(User user) {
        this.user = user;
        this.id = user.getId();
    }
    public User getUser() {
        return user;
    }
    public void handleUpdatePreferences(ActionEvent actionEvent) {
        try {
            userDAO.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/AddAppPage-view.fxml"));
            Parent root = loader.load();
            AddAppController addAppController = loader.getController();
            addAppController.setPrimaryStage(primaryStage);
            addAppController.setUser(user);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
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
            homeController.setUserId(id);
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
}
