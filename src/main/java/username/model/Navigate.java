package username.model;

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

            // Load and apply the CSS stylesheet to the new scene
            String css = Navigate.class.getResource("/username/Stylesheet.css").toExternalForm();
            scene.getStylesheets().add(css);
            switch (pageName){
                case "Home":
                    HomePageController homeController = loader.getController();
                    homeController.setUser(user);
                    homeController.setPrimaryStage(primaryStage);
                    homeController.initialize();
                    break;
                case "DataMan":
                    DataManagementController datamanController = loader.getController();
                    datamanController.setPrimaryStage(primaryStage);
                    System.out.println("Button clicked. Navigating to user preferences page...");
                    datamanController.setUser(user);
                    datamanController.init();
                    break;
                case "Profile":
                    ProfileController profileController = loader.getController();
                    profileController.setPrimaryStage(primaryStage); // Pass the primaryStage to the controller
                    System.out.println("user is :" + user.getUsername());
                    profileController.setUser(user);
                    profileController.initialize();
                    break;
                case "Analytics":
                    AnalyticsController Analytics = loader.getController();
                    System.out.println("user is :" + user.getUsername());
                    Analytics.setUserID(user.getId());
                    Analytics.setUser(user);
                    Analytics.setPrimaryStage(primaryStage); // Pass the primaryStage to the controller
                    Analytics.initialize();
                    break;
                case "Login":
                    LoginController controller = loader.getController();
                    controller.setPrimaryStage(primaryStage); // Pass the primaryStage to the controller
                    break;
                case "SignUp":
                    break;
                case "UserGoals":
                    UserGoalsController userGoalsController = loader.getController();
                    System.out.println(" user: " + userGoalsController);
                    userGoalsController.setUserId(user.getId());
                    userGoalsController.setUser(user);
                    userGoalsController.initialize();
                    userGoalsController.setPrimaryStage(primaryStage);
                    break;
                case "AppGoals":
                    AppGoalsController appGoalsController = loader.getController();
                    System.out.println(" user: " + appGoalsController);
                    appGoalsController.setUserId(user.getId());
                    appGoalsController.setUser(user);
                    appGoalsController.initialize();
                    appGoalsController.setPrimaryStage(primaryStage);
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