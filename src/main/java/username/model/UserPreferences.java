package username.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserPreferences {
    private int id;
    private Connection connection;
    public DatabaseConnection dataconnect;
    public UserPreferences(int id) {

        connection = DatabaseConnection.getInstance();
        // Initialize dataconnect here
        dataconnect = new DatabaseConnection();
        dataconnect.createUserPreferences(connection);
        this.id = id;
    }
    public void addUserPreference(User user) {
        try {
            PreparedStatement insertPreference = connection.prepareStatement(
                    "INSERT INTO userPreferences (authentication_id, application_name, hours_tracked) VALUES (?, ?, ?)"
            );
            insertPreference.setInt(1, user.getId());
            insertPreference.setString(2, user.getApplicationName());
            insertPreference.setInt(3, user.getHoursTracked());
            insertPreference.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
