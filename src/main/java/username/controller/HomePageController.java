package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import username.model.UserDAO;
import username.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HomePageController {

    @FXML
    private Label greetingLabel;

    // Getter methods for accessing UI elements

    private int id;
    private final UserDAO userDAO;
    private User user;
    private Stage primaryStage;


    // Constructor to inject UserDAO dependency
    public HomePageController() {
        userDAO = new UserDAO();
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public int getId(User user)
    {
        return user.getId();
    }
    public void initialize() {
        // Initialize the greeting label with a default message
        int userID = getUserID();
        if (getUser() != null) {
            setGreetingMessage(user.getFirstName());
        }
        else {
            if (userDAO != null) {
                System.out.println("UserDAO not null");
                User user = userDAO.getUser(userID);
                setUser(user);
                if (user != null) {
                    setGreetingMessage(user.getFirstName());
                } else {
                    greetingLabel.setText("Hello, User!");
                }
            } else {
                greetingLabel.setText("UserDAO is not initialized!");
            }
        }
    }

    // Method to update the greeting message
    public void setGreetingMessage(String firstName) {

        greetingLabel.setText("Hello, " + firstName + "!");
    }

    public void setUserId(int id) {
        this.id = id;
    }
    public void setUser(User user){
        this.user = user;
        System.out.println("Set User as : " + user);
    }
    public User getUser()
    {
        return user;
    }
    public int getUserID()
    {
//        System.out.println("Id2: " + id); // Print user DAO
        return id;
    }

    @FXML
    private void handleUserPreferencesButtonClick() {
        try {
            userDAO.close();
        // Implement the logic to navigate to the user preferences page here
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/PreferencesPage-view.fxml"));
            Parent root = loader.load();
        // Get the controller for the home page
            UserPreferenceController preferences = loader.getController();
            System.out.println("Button clicked. Navigating to user preferences page...");

            preferences.setUser(user);
            preferences.setUserDAO(userDAO);
            preferences.setPrimaryStage(primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
