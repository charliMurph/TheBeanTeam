package username.model;

import java.sql.Connection;
import java.util.List;

public class SceenAnalytics implements IScreenTIme{
    private Connection connection;
    public DatabaseConnection dataconnect;
    public SceenAnalytics() {
        connection = DatabaseConnection.getInstance();
        // Initialize dataconnect here
        dataconnect = new DatabaseConnection();
        dataconnect.createScreenLogs(connection);
    }
    @Override
    public void getUserName(User user) {

    }

    @Override
    public void beginSceenTracker(User user) {

    }

    @Override
    public void getDailyUsage(User user) {

    }

    @Override
    public User MonthyUsage(String username) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
