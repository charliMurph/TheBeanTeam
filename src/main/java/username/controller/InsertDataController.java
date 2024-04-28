package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import username.model.User;
import username.model.UserDAO;

public class InsertDataController {
    @FXML
    private Label SignUp;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField ageField;

    private UserDAO userDAO;

    public InsertDataController() {
        userDAO = new UserDAO();
    }

    @FXML
    public void handleInsertButton(ActionEvent event) {
        if (usernameField.getText().isEmpty() ||
                passwordField.getText().isEmpty() ||
                firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() ||
                ageField.getText().isEmpty()) {
            SignUp.setText("Please fill in all required fields.");
            return; // Stop execution if any required field is empty
        }

        SignUp.setText("Details Entered");
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        int age = Integer.parseInt(ageField.getText());

        User newUser = new User(username, password, firstName, lastName, age);
        userDAO.addUser(newUser);

        // Clear the input fields
        usernameField.clear();
        passwordField.clear();
        firstNameField.clear();
        lastNameField.clear();
        ageField.clear();
    }


}