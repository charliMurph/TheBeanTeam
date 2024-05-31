package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import username.model.Navigate;
import username.model.User;
import username.model.UserDAO;

import java.io.IOException;
import java.util.List;

public class UserGoalsController implements IControllerPaths {
    @FXML
    private Spinner<Integer> weeklyLimitSpinner;

    @FXML
    private Spinner<Integer> monthlyLimitSpinner;

    private int id;
    private final UserDAO userDAO;
    private User user;
    private Stage primaryStage;

    // Constructor to inject UserDAO dependency
    public UserGoalsController() {
        userDAO = new UserDAO();
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    @Override
    public void initialize() {
        // Initialize spinners
        weeklyLimitSpinner.getValueFactory().setValue(0);
        weeklyLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 168, 0));
        weeklyLimitSpinner.setEditable(true);

        monthlyLimitSpinner.getValueFactory().setValue(0);
        monthlyLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 720, 0));
        monthlyLimitSpinner.setEditable(true);


    }
    @FXML
    public void onSaveButton(MouseEvent event) {
        if (weeklyLimitSpinner.getValue() != null && monthlyLimitSpinner.getValue() != null) {
            user.setGoalLimit(weeklyLimitSpinner.getValue(), monthlyLimitSpinner.getValue());
            System.out.println("values: " + weeklyLimitSpinner.getValue() + monthlyLimitSpinner.getValue());
            userDAO.addLimits(user);
            System.out.println("added limits");
            Home(event);
        } else {
            System.out.println("Enter limit values");
            // Handle the case where one or both spinner values are null
            // For example, display an error message to the user or set default values
            return;
        }
    }
    public void setUserId(int id) {
        this.id = id;
    }
    public void setUser(User user){
        this.user = user;
    }
    public User getUser()
    {
        return user;
    }
    public int getUserID()
    {
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
    public void AppUsageStart(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage,"/username/AppUsageStart-view.fxml", "Record" );
    }
    @Override
    public void Analytics(MouseEvent event) {
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
    public void Resources(MouseEvent event){Navigate.goTo("/username/Resource-view.fxml", event);
    }
    @Override
    public void Home(MouseEvent event){
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/HomePage-view.fxml", "Home");
    }
    @Override
    public void UserGoals(MouseEvent event) {
        return;
    }
}
