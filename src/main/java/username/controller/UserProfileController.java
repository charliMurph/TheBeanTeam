package username.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import username.model.User;

public class UserProfileController {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label ageLabel;

    // Method to initialize the profile with user data
    public void initializeProfile(User user) {
        // Populate the UI elements with user data
        usernameLabel.setText(user.getUsername());
        emailLabel.setText(user.getEmail());
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
        ageLabel.setText(String.valueOf(user.getAge()));
    }
}
