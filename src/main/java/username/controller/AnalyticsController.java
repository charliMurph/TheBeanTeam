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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsController implements IControllerPaths {
    private final UserDAO userDAO;
    private User user;
    private int id;
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
    public void setUserID(int id){
        this.id = id;
        System.out.println("Set User as : " + id);
    }

    @Override
    public void initialize(){
        // check is active
        List<String> activeApps = userDAO.getActiveApplications(id);
        System.out.println("Active applications: " + activeApps);
        // for all is active return string list of their names
        // match all names and user id to appdata
        for (String appName : activeApps) {
            // Fetch and print the hours tracked for each active application
            int hoursTracked = userDAO.getHoursTracked(appName, id);
            System.out.println("Hours tracked for " + appName + ": " + hoursTracked);
            double[] percentages = userDAO.getLimitUsagePercentages(appName, id);
            for(double percentage : percentages)
            {
                System.out.println("Percentage:" + appName + ": " + percentage);
            }
        }

        Map<String, Integer> topRankedApps = userDAO.rankTopApps(id);
        System.out.println("Top apps order: " + topRankedApps);

        // return get hours tracked
        return;
    }
    public void Profile(MouseEvent event){
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/Profile-view.fxml", "Profile");
    }
    @Override
    public void UserGoals(MouseEvent event) {
        Navigate.caseGoto(null, user, primaryStage, "/username/UserGoals-view.fxml", "UserGoals");
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
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/HomePage-view.fxml", "Home");
    }
    @Override
    public void DataMan(MouseEvent event)
    {
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/DataManagementPage-view.fxml", "DataMan");
    }

}