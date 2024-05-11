package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import username.model.UserDAO;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginStatus;

    private Stage primaryStage; // Reference to the primaryStage

    private UserDAO user;

    // Constructor
    public LoginController() {
        // Initialize the UserDAO object
        user = new UserDAO();
    }

    @FXML
    private void initialize() {
        // You can put initialization code here if needed
    }
    public void setLoginStatusStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Perform login authentication logic here
        if (isValidLogin(username, password)) {
            loginStatus.setText("Login successful!");
            // Redirect to main application or dashboard
            int userId = user.getUserId(username, password); // Retrieve the user ID
            redirectToHomePage(userId);
        } else {
            loginStatus.setText("Invalid username or password.");
        }
    }

    private boolean isValidLogin(String username, String password) {
        return user.isValidLogin(username, password);
    }

    @FXML
    private void handleSignUpLink(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/SignUp-view.fxml"));
            System.out.println("Sign Up FXML Path: " + getClass().getResource("SignUp-view.fxml")); // Logging statement
            Parent root = loader.load();
            SignUpController controller = loader.getController();
            controller.setPrimaryStage(primaryStage); // Pass the primaryStage to the controller
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    private void redirectToHomePage(int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/HomePage.fxml"));
            Parent root = loader.load();
            // Get the controller for the home page
            HomePageController homeController = loader.getController();
            // Pass any necessary data to the home controller if needed
            // For example, you can pass the username:
            // homeController.setUsername(username);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
