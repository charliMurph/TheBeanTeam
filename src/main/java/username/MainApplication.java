package username;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import username.controller.LoginController;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login-view.fxml"));
            Parent root = loader.load();
            LoginController controller = loader.getController();
            controller.setPrimaryStage(primaryStage); // Pass the primaryStage to the controller
            Scene scene = new Scene(root);
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
        launch(MainApplication.class, args); // Launch the JavaFX application
    }
}