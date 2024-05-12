package username.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserPreferences {
    private int id;
    private String appName;

    private int weekHours;
    private int monthHours;
    private Connection connection;
    public DatabaseConnection dataconnect;
    public UserPreferences(int id) {

        connection = DatabaseConnection.getInstance();
        // Initialize dataconnect here
        dataconnect = new DatabaseConnection();
        dataconnect.createUserPreferences(connection);
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }
    public int getWeekHours()
    {
        return weekHours;
    }
    public void setWeekHours(int weekHours)
    {
        this.weekHours = weekHours;
    }
    public int getMonthHours()
    {
        return monthHours;
    }
    public void setMonthHours(int monthHours)
    {
        this.monthHours = monthHours;
    }
    public UserPreferences(int id, String appName, int weekHours, int monthHours)
    {
        connection = DatabaseConnection.getInstance();
        // Initialize dataconnect here
        dataconnect = new DatabaseConnection();
        dataconnect.createUserPreferences(connection);
        this.id = id;
        this.appName = appName;
        this.weekHours = weekHours;
        this.monthHours = monthHours;
    }
    public void addUserPreference(UserPreferences userpref) {
        try {
            PreparedStatement insertPreference = connection.prepareStatement(
                    "INSERT INTO userPreferences (authenticationId, applicationName, " +
                            "weeklyHourLimit, monthlyHourLimit) VALUES (?, ?, ?, ?)"
            );
            insertPreference.setInt(1, getId());
            insertPreference.setString(2, userpref.getAppName());
            insertPreference.setInt(3, userpref.getWeekHours());
            insertPreference.setInt(4, userpref.getMonthHours());
            insertPreference.execute();
            System.out.println("Executed successfully!");
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
