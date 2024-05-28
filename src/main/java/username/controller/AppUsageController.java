package username.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import username.model.Navigate;
import username.model.User;
import username.model.UserDAO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class AppUsageController implements IControllerPaths{

    private User user;
    private final UserDAO userDAO;
    private String currentApp;
    private LocalDateTime startTime;
    private Boolean isclosed;
    private Stage primaryStage;
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public AppUsageController() {
        userDAO = new UserDAO(); // new connection
        isclosed = false;
    }
    @FXML
    private void setStartTime(ActionEvent event) {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String activeApp = WindowsAppMonitor.getActiveWindowTitle();
                if (!activeApp.equals(currentApp)) {
                    if (currentApp != null) {
                        recordUsageData(currentApp, startTime, LocalDateTime.now());
                    }
                    currentApp = activeApp;
                    startTime = LocalDateTime.now();
                }
            }
        }, 0, 1000); // Check every 5 seconds
    }

    private synchronized void recordUsageData(String appName, LocalDateTime start, LocalDateTime end) {
        if(isclosed)
        {
            userDAO.open(); // reopen if stopped
            isclosed = false;
        }
        System.out.println("Starting recording...");
        System.out.println("Id: " + user.getId());
        System.out.println("App Name: " + appName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTimeStr = start.format(formatter);
        String endTimeStr = end.format(formatter);
        long duration = java.time.Duration.between(start, end).getSeconds();
        userDAO.trackApps(user.getId(), appName, duration / 3600, startTimeStr, endTimeStr);
        String query = "INSERT INTO appData (authenticationId, applicationName, hours, start_time, stop_time) VALUES (?, ?, ?, ?, ?)";
    }
    @FXML
    private void stopMonitoring(ActionEvent event)
    {
        isclosed = true;
        userDAO.close();
    }
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public void Home(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/HomePage-view.fxml", "Home");
    }

    @Override
    public void DataMan(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/DataManagementPage-view.fxml", "DataMan");
    }

    @Override
    public void Profile(MouseEvent event) {
        Navigate.caseGoto(event, user,  primaryStage,"/username/Profile-view.fxml", "Profile");
    }

    @Override
    public void Analytics(MouseEvent event) {
        userDAO.close();
        Navigate.caseGoto(event, user, primaryStage, "/username/Analytics-view.fxml", "Analytics");
    }

    @Override
    public void Settings(MouseEvent event) {
        Navigate.goTo("/username/Settings-view.fxml", event);
    }

    @Override
    public void Notifications(MouseEvent event) {
        Navigate.goTo("/username/Notifications-view.fxml", event);
    }

    @Override
    public void Resources(MouseEvent event) {
        Navigate.goTo("/username/Resource-view.fxml", event);
    }

    public void Goals() {
        userDAO.close();
        Navigate.caseGoto(null, user, primaryStage, "/username/AppGoals-view.fxml", "Goals");
    }
    @Override
    public void initialize(){return;}




}
