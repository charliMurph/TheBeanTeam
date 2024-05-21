package username.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import username.model.Navigate;
import username.model.User;
import username.model.UserDAO;

public class ProfileController {

    @FXML
    private TextField usernameLabel;

    @FXML
    private TextField emailLabel;

    @FXML
    private TextField firstNameLabel;

    @FXML
    private TextField lastNameLabel;

    @FXML
    private TextField passwordLabel;

    private final UserDAO userDAO;
    private User user;
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public ProfileController() { userDAO = new UserDAO();}
    public User getUser()
    {
        return user;
    }
    public void setUser(User user)
    {this.user = user;
    System.out.println("user is: " + user.getUsername());}
    // Method to initialize the profile with user data
    public void initializeProfile(User user) {
        // Populate the UI elements with user data
        if (user != null) {
            try {
                usernameLabel.setText(user.getUsername());
                emailLabel.setText(user.getEmail());
                firstNameLabel.setText(user.getFirstName());
                lastNameLabel.setText(user.getLastName());
                passwordLabel.setText(user.getPassword());
            }
            catch (Exception e) {
                System.out.println("error: " + e);
            }
        }
        else {
            System.out.println("user is null");
        }
    }

    public void Profile(MouseEvent event) {
        Navigate.goTo("/username/Profile-view.fxml", event);
    }

    public void Analytics(MouseEvent event) {
        Navigate.goTo("/username/Analytics-view.fxml", event);
    }

    public void Settings(MouseEvent event) {
        Navigate.goTo("/username/Settings-view.fxml", event);
    }

    public void Notifications(MouseEvent event) {
        Navigate.goTo("/username/Notifications-view.fxml", event);
    }

    public void DataMan(MouseEvent event) {
        Navigate.goTo("/username/Dmanagement-view.fxml", event);
    }

    public void Resources(MouseEvent event) {
        Navigate.goTo("/username/Resource-view.fxml", event);
    }

    public void Goals(MouseEvent event) {
        Navigate.goTo("/username/Home-view.fxml", event);
    }

    public void Home(MouseEvent event) {
        Navigate.goTo("/username/Home-view.fxml", event);
    }

}