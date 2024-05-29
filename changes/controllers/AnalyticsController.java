package username.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import username.model.Navigate;
import username.model.User;
import username.model.UserDAO;
import javafx.scene.transform.Scale;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.BarChart;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsController implements IControllerPaths {

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private PieChart PvNPie;

    @FXML
    private PieChart AppPie;

    @FXML
    public BarChart<String, Number> DoWchart;

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
        }

        // Example data for PvNPie
        HashMap<String, Integer> pvnData = new HashMap<>();
        pvnData.put("productive", 70);
        pvnData.put("non_productive", 30);
        PvNPieint(pvnData, PvNPie);

        // Example data for CustomPie
        HashMap<String, Integer> customData = new HashMap<>();
        customData.put("Google", 40);
        customData.put("Youtube", 35);
        customData.put("Steam", 25);
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
            // Load and apply the CSS stylesheet to the new scene
            String css = Navigate.class.getResource("/username/Stylesheet.css").toExternalForm();
            scene.getStylesheets().add(css);
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
            // Load and apply the CSS stylesheet to the new scene
            String css = Navigate.class.getResource("/username/Stylesheet.css").toExternalForm();
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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



}