package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import username.model.Navigate;
import username.model.User;
import username.model.UserDAO;

import java.io.IOException;

public class SignUpController {

    public TextField emailField;
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

    private User homepageuser;

    private final UserDAO userDAO = new UserDAO();
    private Stage primaryStage; // Reference to the primaryStage
    // Constructor that receives the primaryStage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage; // Set the primaryStage reference
    }
    public void setUserAs(User user)
    {
        this.homepageuser = user;
    }
    @FXML
    public void handleInsertButton(ActionEvent event) {
        if (usernameField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                passwordField.getText().isEmpty() ||
                firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() ||
                ageField.getText().isEmpty()) {
            SignUp.setText("Please fill in all required fields.");
            return; // Stop execution if any required field is empty
        }
        if (userDAO.userExists(usernameField.getText()))
        {
            SignUp.setText("Username already exists");
            usernameField.clear();
            return;
        }
        SignUp.setText("Details Entered"); // Update label text
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        int age = Integer.parseInt(ageField.getText());

        User newUser = new User(email, username, password, firstName, lastName, age);
        userDAO.addUser(newUser);
        setUserAs(newUser);

        // Clear the input fields
        emailField.clear();
        usernameField.clear();
        passwordField.clear();
        firstNameField.clear();
        lastNameField.clear();
        ageField.clear();

        // Navigate to the home page
        navigateToHomePage();
    }
    @FXML
    private void handleBackToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/Login-view.fxml"));
            System.out.println("Login FXML Path: " + getClass().getResource("/username/Login-view.fxml")); // Logging statement
            Parent root = loader.load();
            LoginController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            Scene scene = new Scene(root);
            // Load and apply the CSS stylesheet to the new scene
            String css = Navigate.class.getResource("/username/Stylesheet.css").toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
    private void navigateToHomePage() {
        try {
            userDAO.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/HomePage-view.fxml"));
            Parent root = loader.load();
            HomePageController homeController = loader.getController();
            System.out.println("homepage user: " + homepageuser);
            homeController.setUser(homepageuser);
            homeController.setPrimaryStage(primaryStage);
            homeController.initialize();
            // You can pass any necessary data to the home controller here if needed
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

}
