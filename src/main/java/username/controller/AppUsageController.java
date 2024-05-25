package username.controller;

import username.model.DatabaseConnection;
import username.model.User;
import username.model.UserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class AppUsageController {

    private final User user;


    private String currentApp;
    private LocalDateTime startTime;
    private final Connection connection;



    public AppUsageController(User user) {
        this.user = user;

        this.connection = DatabaseConnection.getInstance(); // Use a single connection
        startMonitoring();
    }

    private void startMonitoring() {
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTimeStr = start.format(formatter);
        String endTimeStr = end.format(formatter);
        long duration = java.time.Duration.between(start, end).getSeconds();

        String query = "INSERT INTO appData (authenticationId, applicationName, hours, start_time, stop_time) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.setString(2, appName);
            statement.setLong(3, duration / 3600); // Convert seconds to hours
            statement.setString(4, startTimeStr);
            statement.setString(5, endTimeStr);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void stopMonitoring() {

        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
