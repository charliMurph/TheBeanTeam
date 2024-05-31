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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalyticsController implements IControllerPaths {
    private final UserDAO userDAO;
    private User user;
    private int id;
    private Stage primaryStage;
    @FXML
    private LineChart<String, Number> summaryLineChart;

    @FXML
    private PieChart PvNPie;

    @FXML
    private PieChart AppPie;

    @FXML
    private PieChart WeekAppPie;

    @FXML
    public BarChart<String, Number> DoWchart;
    @FXML
    public BarChart<String, Number> WoMchart;

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
            System.out.println("Secs tracked for " + appName + ": " + hoursTracked);
            double[] percentages = userDAO.getLimitUsagePercentages(appName, id);
            for(double percentage : percentages)
            {
                System.out.println("Percentage:" + appName + ": " + percentage);
            }
        }
        //Set up for daily usage
        DailyUsageSetUp();
        WeeklyUsageSetUp();


        // set up for weekly usage
        return;

    }
    public void DailyUsageSetUp()
    {
        ZoneId brisbaneZoneId = ZoneId.of("Australia/Brisbane");
        ZonedDateTime brisbaneNow = ZonedDateTime.now(brisbaneZoneId);
        String brisbaneDate = brisbaneNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Map<String, Integer> topRankedApps = userDAO.TopApps(id, brisbaneDate, brisbaneDate);
        System.out.println("Top apps order: " + topRankedApps);
        // Example data for PvNPie
        HashMap<String, Double> pvnData = userDAO.getPvNP(id, brisbaneDate, brisbaneDate);
        PvNPieint(pvnData, PvNPie);

        // Example data for CustomPie
        HashMap<String, Integer> customData = userDAO.TopApps(id, brisbaneDate, brisbaneDate);
        AppPieint(customData, AppPie);


        // Example data for Box chart
        HashMap<String, Integer> dayData = userDAO.appByMinutesTop(id, brisbaneDate, brisbaneDate);
        dayBoxChart(dayData, DoWchart);

        HashMap<String, Number> lineMonthData = userDAO.approundNum(id, "2024-05-20", brisbaneDate);
        mapWeeklyUsage(lineMonthData, summaryLineChart);
    }


    public void PvNPieint(HashMap<String, Double> data, PieChart chart) {
        PieChart.Data productiveData = new PieChart.Data("Productive", data.getOrDefault("productive", 0.0));
        PieChart.Data nonProductiveData = new PieChart.Data("Non Productive", data.getOrDefault("non productive", 0.0));

        chart.getData().clear();
        chart.getData().addAll(productiveData, nonProductiveData);
        chart.setLabelsVisible(false);

    }
    public void mapWeeklyUsage(HashMap<String, Number> weeklyData, LineChart<String, Number> lineChart) {
        // Clear existing data from the line chart
        System.out.println("Line Data: " + weeklyData);
        //        mapWeeklyUsage(lineMonthData, lineChart);
        // Sort the HashMap entries by keys (dates)
        List<Map.Entry<String, Number>> sortedEntries = weeklyData.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());

        // Print the sorted data
        System.out.println("Line Data:");
        for (Map.Entry<String, Number> entry : sortedEntries) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        lineChart.getData().clear();

        // Set the chart title
        lineChart.setTitle("Weekly Usage");

        // Create a series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Time Spent Per Day");
        // Add data points to the series
        for (String date : weeklyData.keySet()) {
            series.getData().add(new XYChart.Data<>(date, weeklyData.get(date)));
        }

        // Add the series to the chart
        lineChart.getData().add(series);
    }

    public void WeeklyUsageSetUp()
    {
        ZoneId brisbaneZoneId = ZoneId.of("Australia/Brisbane");
        ZonedDateTime brisbaneNow = ZonedDateTime.now(brisbaneZoneId);
        ZonedDateTime brisbaneWeekAgo = brisbaneNow.minusDays(7);
        String brisbaneNowDate = brisbaneNow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String brisbaneWeekAgoDate = brisbaneWeekAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, Integer> topRankedApps = userDAO.TopApps(id, brisbaneWeekAgoDate, brisbaneNowDate);
        System.out.println("Top apps order: " + topRankedApps);
        // Example data for PvNPie
        HashMap<String, Integer> weekData = userDAO.appByMinutesTop(id, brisbaneWeekAgoDate, brisbaneNowDate);
        weekBoxChart(weekData, WoMchart);
        // Example data for CustomPie
        HashMap<String, Integer> customData = userDAO.TopApps(id, brisbaneWeekAgoDate, brisbaneNowDate);
        AppPieint(customData, WeekAppPie);

    }
    public void AppPieint(HashMap<String, Integer> data, PieChart chart) {
        chart.getData().clear();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            chart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        chart.setLabelsVisible(false);

    }
    public void weekBoxChart(HashMap<String, Integer> data, BarChart chart) {
        // Clear previous data
        chart.getData().clear();

        // Set chart title
        chart.setTitle("Weekly Usage");

        // Create a series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Minutes");

        // Add data points to the series
        for (String app : data.keySet()) {
            series.getData().add(new XYChart.Data<>(app, data.get(app)));
        }

        // Add the series to the chart
        chart.getData().add(series);

    }
    public void dayBoxChart(HashMap<String, Integer> data, BarChart chart) {
        // Clear previous data
        chart.getData().clear();

        // Set chart title
        chart.setTitle("Daily Usage");

        // Create a series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Minutes");

        // Add data points to the series
        for (String app : data.keySet()) {
            series.getData().add(new XYChart.Data<>(app, data.get(app)));
        }

        // Add the series to the chart
        chart.getData().add(series);

    }
    @Override
    public void AppUsageStart(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage,"/username/AppUsageStart-view.fxml", "Record" );
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