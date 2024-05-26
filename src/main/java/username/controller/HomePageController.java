package username.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import username.model.Navigate;
import username.model.UserDAO;
import username.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HomePageController implements IControllerPaths {

    @FXML
    private Label greetingLabel;

    // Getter methods for accessing UI elements

    private int id;
    private final UserDAO userDAO;
    private User user;
    private Stage primaryStage;

    // Constructor to inject UserDAO dependency
    public HomePageController() {
        userDAO = new UserDAO();
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @Override
    public void initialize() {
        // Initialize the greeting label with a default message
        int userID = getUserID();
        if (getUser() != null) {
            setGreetingMessage(user.getFirstName());
        }
        else {
            if (userDAO != null) {
                System.out.println("UserDAO not null");
                User user = userDAO.getUser(userID);
                setUser(user);
                if (user != null) {
                    setGreetingMessage(user.getFirstName());
                } else {
                    greetingLabel.setText("Hello, User!");
                }
            }
        }
    }

    // Method to update the greeting message
    public void setGreetingMessage(String firstName) {

        greetingLabel.setText("Hello, " + firstName + "!");
    }

    public void setUserId(int id) {
        this.id = id;
    }
    public void setUser(User user){
        this.user = user;
        System.out.println("Set User as : " + user);
    }
    public User getUser()
    {
        return user;
    }
    public int getUserID()
    {
//        System.out.println("Id2: " + id); // Print user DAO
        return id;
    }
    @Override
    public void DataMan(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user,  primaryStage, "/username/DataManagementPage-view.fxml", "DataMan");
    }

    @Override
    public void Profile(MouseEvent event) {
        Navigate.caseGoto(event, user,  primaryStage,"/username/Profile-view.fxml", "Profile");
    }
    @Override
    public void Analytics(MouseEvent event){
        userDAO.close();
        Navigate.caseGoto(event, user,  primaryStage, "/username/Analytics-view.fxml", "Analytics");
    }
    @FXML
    public void startStop(MouseEvent event) {
        try {
            userDAO.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/AppUsageStart-view.fxml"));
            Parent root = loader.load();
            // Get the controller for the home page
            AppUsageController appUsageController = loader.getController();
            appUsageController.setUser(user);
            appUsageController.setPrimaryStage(primaryStage);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    @Override
    public void Settings(MouseEvent event){
        Navigate.goTo("/username/Settings-view.fxml", event);
    }
    @Override
    public void Notifications(MouseEvent event){
        Navigate.goTo("/username/Notifications-view.fxml", event);
    }
    @Override
    public void Resources(MouseEvent event){Navigate.goTo("/username/Resource-view.fxml", event);
    }
    @Override
    public void Home(MouseEvent event){
        return;
    }
}
