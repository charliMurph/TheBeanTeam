package username;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import username.controller.HomePageController;
import username.model.User;
import username.model.UserDAO;

import java.io.IOException;

public class HomePage_TestApplication extends Application {
    private int userId;
    // (String email, String username, String password, String firstName, String lastName, int age)
    private UserDAO userDAO = new UserDAO();
    private User testuser = userDAO.getUser(5);

    @Override
    public void start(Stage primaryStage) {
        userDAO.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage-view.fxml"));
            Parent root = loader.load();
            HomePageController homeController = loader.getController();
            Scene scene = new Scene(root);

            homeController.setUser(testuser);
            homeController.setPrimaryStage(primaryStage);
            homeController.initialize();
            primaryStage.setTitle("Screen Time Bean Time!");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            // Log the exception for debugging purposes
            e.printStackTrace();

            // Display an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load FXML file");
            alert.setContentText("An error occurred while loading the FXML file.");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(HomePage_TestApplication.class, args); // Launch the JavaFX application
    }
}
