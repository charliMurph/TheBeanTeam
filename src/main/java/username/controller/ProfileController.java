package username.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import username.model.Navigate;
import username.model.User;
import username.model.UserDAO;

import java.io.IOException;

public class ProfileController implements IControllerPaths{

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
    @FXML
    private void updateProfile() {
        String newFirstName = firstNameLabel.getText();
        String newLastName = lastNameLabel.getText();
        String newEmail = emailLabel.getText();
        String newUsername = usernameLabel.getText();
        String newPassword = passwordLabel.getText();

        boolean updated = false;

        if (!newFirstName.equals(user.getFirstName())) {
            user.setFirstName(newFirstName);
            updated = true;
        }
        if (!newLastName.equals(user.getLastName())) {
            user.setLastName(newLastName);
            updated = true;
        }
        if (!newEmail.equals(user.getEmail())) {
            user.setEmail(newEmail);
            updated = true;
        }
        if (!newUsername.equals(user.getUsername())) {
            user.setUsername(newUsername);
            updated = true;
        }
        if (!newPassword.equals(user.getPassword())) {
            user.setPassword(newPassword);
            updated = true;
        }
        if (updated) {
            userDAO.updateUser(user); // Replace with actual data saving logic
            System.out.println("Profile updated successfully");
        } else {
            System.out.println("No changes detected");
        }
    }

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

    @Override
    public void initialize() {
        initializeProfile(user);

    }
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
    @FXML
    private void handleLogoutButton() {
        try {
            userDAO.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/Login-view.fxml"));
            System.out.println("Sign Up FXML Path: " + getClass().getResource("Login-view.fxml")); // Logging statement
            Parent root = loader.load();
            LoginController controller = loader.getController();
            controller.setPrimaryStage(primaryStage); // Pass the primaryStage to the controller
            userDAO.close();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    @Override
    public void Profile(MouseEvent event) {
        return;
    }
    @Override
    public void Analytics(MouseEvent event) {
        Navigate.goTo("/username/Analytics-view.fxml", event);
    }
    @Override
    public void Settings(MouseEvent event) {
        Navigate.goTo("/username/Settings-view.fxml", event);
    }
    @Override
    public void Notifications(MouseEvent event) {
        Navigate.goTo("/username/Notifications-view.fxml", event);
    }

    @Override
    public void DataMan(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user,  primaryStage, "/username/DataManagementPage-view.fxml", "DataMan");
    }

    public void Resources(MouseEvent event) {
        Navigate.goTo("/username/Resource-view.fxml", event);
    }

    public void Goals(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user,  primaryStage, "/username/AppGoals-view.fxml", "Goals");
    }
    @Override
    //(MouseEvent event, User user, Stage primaryStage)
    public void Home(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user,  primaryStage, "/username/HomePage-view.fxml", "Home");
    }

}