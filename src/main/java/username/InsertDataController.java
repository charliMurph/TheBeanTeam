package username;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class InsertDataController {

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
    private void handleInsertButton(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        int age = Integer.parseInt(ageField.getText());

        User newUser = new User(username, password, firstName, lastName, age);
        userDAO.insert(newUser);

        // Clear the input fields
        usernameField.clear();
        passwordField.clear();
        firstNameField.clear();
        lastNameField.clear();
        ageField.clear();
    }
}