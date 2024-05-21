package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.ToggleSwitch;
import username.model.*;

import java.io.IOException;

public class DataManagementController implements IControllerPaths {
    @FXML
    public ToggleSwitch WordSwitch;
    @FXML
    public ToggleSwitch ChromeSwitch;
    @FXML
    public ToggleSwitch ExcelSwitch;
    @FXML
    public ToggleSwitch SteamSwitch;
    @FXML
    public ToggleSwitch PPTSwitch;
    @FXML
    public ToggleSwitch DiscordSwitch;
    @FXML
    public ToggleSwitch OfficeSwitch;
    @FXML
    public ToggleSwitch InstaSwitch;
    @FXML
    public ToggleSwitch FBSwitch;
    private User user;
    private int id;
    private final UserDAO userDAO;
    private Stage primaryStage;

    // Add more CheckBox fields for additional apps as needed
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public DataManagementController(){
        userDAO = new UserDAO();
    }

    public void setUser(User user) {
        this.user = user;
        this.id = user.getId();
    }
    public User getUser() {
        return user;
    }
    @Override
    //(MouseEvent event, User user, Stage primaryStage)
    public void Home(MouseEvent event) {
        Navigate.caseGoto(event, user, primaryStage, "/username/HomePage-view.fxml", "Home");
    }
    @Override
    public void DataMan(MouseEvent event) {
        Navigate.caseGoto(event, user, primaryStage, "/username/DataManagementPage-view.fxml", "DataMan");
    }

    @Override
    public void Profile(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage,"/username/Profile-view.fxml", "Profile");
    }
    @Override
    public void Analytics(MouseEvent event){
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/Analytics-view.fxml", "Analytics");
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
    public void Resources(MouseEvent event){
        Navigate.goTo("/username/Resource-view.fxml", event);
    }

    public void Goals(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/username/Goals-view.fxml"));
            Parent root = loader.load();
            GoalsController goalsController = loader.getController();
            System.out.println(" user: " + goalsController);
            goalsController.setUserId(id);
            goalsController.setUser(user);
            goalsController.setPrimaryStage(primaryStage);
            goalsController.initialize();
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
