package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import username.model.UserDAO;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginStatus;

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

    @FXML
    private void handleLoginButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Perform login authentication logic here
        if (isValidLogin(username, password)) {
            loginStatus.setText("Login successful!");
            // Redirect to main application or dashboard
        } else {
            loginStatus.setText("Invalid username or password.");
        }
    }

    private boolean isValidLogin(String username, String password) {
        return user.isValidLogin(username, password);
    }

    @FXML
    private void handleSignUpLink(ActionEvent event) {
        // Navigate to the sign-up page
        // You can implement navigation using your application's framework
    }
}