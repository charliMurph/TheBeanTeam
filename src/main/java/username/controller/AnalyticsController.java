package username.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import username.model.Navigate;
import username.model.User;
import username.model.UserDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsController implements IControllerPaths {
    private final UserDAO userDAO;
    private User user;
    private int id;
    private Stage primaryStage;
    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private PieChart PvNPie;

    @FXML
    private PieChart AppPie;

    @FXML
    public BarChart<String, Number> DoWchart;

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
            int hoursTracked = userDAO.getTimeTracked(appName, id);
            System.out.println("Hours tracked for " + appName + ": " + hoursTracked);
            double[] percentages = userDAO.getLimitUsagePercentages(appName, id);
            for(double percentage : percentages)
            {
                System.out.println("Percentage:" + appName + ": " + percentage);
            }
        }

        Map<String, Integer> topRankedApps = userDAO.rankTopApps(id);
        System.out.println("Top apps order: " + topRankedApps);
        // Example data for PvNPie
        HashMap<String, Integer> pvnData = userDAO.getWeekPvNP(id);
        PvNPieint(pvnData, PvNPie);

        // Example data for CustomPie
        HashMap<String, Integer> customData = userDAO.rankTopApps(id);
        AppPieint(customData, AppPie);

        // Example data for CustomPie
        HashMap<String, Integer> dayData = new HashMap<>();
        dayData.put("Google", 3);
        dayData.put("Youtube", 7);
        dayData.put("Steam", 6);
        dayBoxChart(dayData, DoWchart);

        // return get hours tracked
        return;

    }
    public void PvNPieint(HashMap<String, Integer> data, PieChart chart) {
        PieChart.Data productiveData = new PieChart.Data("Productive", data.getOrDefault("productive", 0));
        PieChart.Data nonProductiveData = new PieChart.Data("Non Productive", data.getOrDefault("non_productive", 0));

        chart.getData().clear();
        chart.getData().addAll(productiveData, nonProductiveData);
        chart.setLabelsVisible(false);

    }

    public void AppPieint(HashMap<String, Integer> data, PieChart chart) {
        chart.getData().clear();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            chart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        chart.setLabelsVisible(false);

    }

    public void dayBoxChart(HashMap<String, Integer> data, BarChart chart) {
        // Clear previous data
        chart.getData().clear();

        // Set chart title
        chart.setTitle("Daily Usage");

        // Create a series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Hours");

        // Add data points to the series
        for (String app : data.keySet()) {
            series.getData().add(new XYChart.Data<>(app, data.get(app)));
        }

        // Add the series to the chart
        chart.getData().add(series);

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