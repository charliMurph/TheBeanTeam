package username.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import username.controller.*;

import java.io.IOException;

public class Navigate {

    public static void goTo(String fxmlFileName, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Navigate.class.getResource(fxmlFileName));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential IOException, such as FXML file not found
        }
    }
    public static void setScene(Scene scene, Stage primaryStage) {
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void caseGoto(MouseEvent event, User user, Stage primaryStage, String fxmlFileName, String pageName)
    {
        try {

            FXMLLoader loader = new FXMLLoader(Navigate.class.getResource(fxmlFileName));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            switch (pageName){
                case "Home":
                    HomePageController homeController = loader.getController();
                    // Pass any necessary data to the home controller if needed
                    // For example, you can pass the username:
                    homeController.setUser(user);
                    homeController.setPrimaryStage(primaryStage);
                    homeController.initialize();
                    break;
                case "DataMan":
                    DataManagementController preferences = loader.getController();
                    System.out.println("Button clicked. Navigating to user preferences page...");
                    preferences.setUser(user);
                    preferences.setPrimaryStage(primaryStage);
                    break;
                case "Profile":
                    ProfileController profileController = loader.getController();
                    profileController.setPrimaryStage(primaryStage); // Pass the primaryStage to the controller
                    System.out.println("user is :" + user.getUsername());
                    profileController.setUser(user);
                    profileController.initializeProfile(user);
                    break;
                case "Analytics":
                    AnalyticsController Analytics = loader.getController();
                    Analytics.setPrimaryStage(primaryStage); // Pass the primaryStage to the controller
                    System.out.println("user is :" + user.getUsername());
                    Analytics.setUser(user);
                    break;
                case "Login":
                    LoginController controller = loader.getController();
                    controller.setPrimaryStage(primaryStage); // Pass the primaryStage to the controller
                    break;
                case "SignUp":
                    break;
                case "Goals":
                    GoalsController goalsController = loader.getController();
                    goalsController.setPrimaryStage(primaryStage);
                    goalsController.setUser(user);
                    break;
                case "Notifications":
                    break;
                case "Resources":
                    break;
                default:
                    FXMLLoader defaultloader = new FXMLLoader(Navigate.class.getResource("Login-view.fxml"));
                    root = defaultloader.load();
                    LoginController loginController = defaultloader.getController();
                    loginController.setPrimaryStage(primaryStage); // Pass the primaryStage to the controller
                    primaryStage.setTitle("Screen Time Bean Time!");
                    break;

            }
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}