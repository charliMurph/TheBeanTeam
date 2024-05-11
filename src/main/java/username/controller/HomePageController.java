package username.controller;

import javafx.event.ActionEvent;
import username.model.UserDAO;
import username.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomePageController {

    @FXML
    private Label greetingLabel;
    private int id;
    private UserDAO userDAO;
    private User user;

    // Constructor to inject UserDAO dependency
    public HomePageController() {
        userDAO = new UserDAO();
    }
    public void initialize() {
        // Initialize the greeting label with a default message
        int userID = getUserID();
        System.out.println("Id1: " + userID); // Print user DAO
        System.out.println("UserDAO: " + userDAO); // Print user DAO
        if (userDAO != null) {
            User user = userDAO.getUser(userID);

            if (user != null) {
                setGreetingMessage(user.getFirstName());
            } else {
                greetingLabel.setText("Hello, User!");
            }
        } else {
            greetingLabel.setText("UserDAO is not initialized!");
        }
    }

    // Method to update the greeting message
    public void setGreetingMessage(String firstName) {

        greetingLabel.setText("Hello, " + firstName + "!");
    }

    public void setUserId(int id) {
        System.out.println("Id3: " + id); // Print user DAO
        this.id = id;
    }
    public int getUserID()
    {
        System.out.println("Id2: " + id); // Print user DAO
        return id;
    }

    @FXML
    private void handleUserPreferencesButtonClick() {
        // Implement the logic to navigate to the user preferences page here
        System.out.println("Button clicked. Navigating to user preferences page...");
        // For example, you can close the current window and open the user preferences window
        // Stage stage = (Stage) greetingLabel.getScene().getWindow();
        // stage.close();
        // Then open the user preferences window
        // Open user preferences window here
    }
}
