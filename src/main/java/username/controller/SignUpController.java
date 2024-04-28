package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import username.model.User;
import username.model.UserDAO;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import username.controller.LoginController;

public class SignUpController {

    @FXML
    private Label signUpLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void handleRegisterButton(ActionEvent event) {
        if (usernameField.getText().isEmpty() ||
                passwordField.getText().isEmpty() ||
                firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() ||
                emailField.getText().isEmpty()) {
            signUpLabel.setText("Please fill in all required fields.");
            return; // Stop execution if any required field is empty
        }

        signUpLabel.setText("Details Entered"); // Update label text
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();

        // You might want to add validation for email format here

        User newUser = new User(username, password, firstName, lastName, email);
        userDAO.addUser(newUser);

        // Clear the input fields
        usernameField.clear();
        passwordField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        // Code to navigate back to the login screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login-View.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            // You can add any initialization logic here if needed

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential exceptions
        }
    }
}

