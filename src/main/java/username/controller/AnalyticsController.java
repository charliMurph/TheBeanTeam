package username.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import username.model.Navigate;
import username.model.User;
import username.model.UserDAO;

import java.io.IOException;

public class AnalyticsController implements IControllerPaths {
    private final UserDAO userDAO;
    private User user;
    private Stage primaryStage;
    public AnalyticsController() {
        userDAO = new UserDAO();
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void setUser(User user){
        this.user = user;
        System.out.println("Set User as : " + user);
    }
    public void Profile(MouseEvent event){
        Navigate.goTo("/username/Profile-view.fxml", event);
    }
    @Override
    public void Analytics(MouseEvent event){return;} // already on page
    @Override
    public void Settings(MouseEvent event){
        Navigate.goTo("/username/Settings-view.fxml", event);
    }
    @Override
    public void Notifications(MouseEvent event){
        Navigate.goTo("/username/Notifications-view.fxml", event);
    }
    @Override
    public void Resources(MouseEvent event){
        Navigate.goTo("/username/Resource-view.fxml", event);
    }
    @Override
    public void Home(MouseEvent event) {
        try {
            userDAO.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/HomePage-view.fxml"));
            Parent root = loader.load();
            // Get the controller for the home page
            HomePageController homeController = loader.getController();
            // Pass any necessary data to the home controller if needed
            // For example, you can pass the username:
            homeController.setUser(user);
            homeController.setPrimaryStage(primaryStage);
            homeController.initialize();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
    @Override
    public void DataMan(MouseEvent event)
    {
        try {
            userDAO.close();
            // Implement the logic to navigate to the user preferences page here
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/DataManagementPage-view.fxml"));
            Parent root = loader.load();
            // Get the controller for the home page
            DataManagementController preferences = loader.getController();
            System.out.println("Button clicked. Navigating to user preferences page...");

            preferences.setUser(user);
            preferences.setPrimaryStage(primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}