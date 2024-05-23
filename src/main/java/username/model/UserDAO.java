package username.model;

import java.security.Principal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private Connection connection;
    public DatabaseConnection dataconnect;


    public UserDAO() {

        connection = DatabaseConnection.getInstance();
        // Initialize dataconnect here
        dataconnect = new DatabaseConnection();
        dataconnect.createUserTable(connection);
        dataconnect.createUserPreferences(connection);
        System.out.println("Connected DAO");
    }

    @Override
    public void addUser(User User) {
        try {
            PreparedStatement insertAccount = connection.prepareStatement(
                    "INSERT INTO authentication (email, firstName, lastName, age, username, password) VALUES (?, ?, ?, ?, ?, ?)"
            );
            insertAccount.setString(1, User.getEmail());
            insertAccount.setString(2, User.getFirstName());
            insertAccount.setString(3, User.getLastName());
            insertAccount.setInt(4, User.getAge());
            insertAccount.setString(5, User.getUsername());
            insertAccount.setString(6, User.getPassword());
            insertAccount.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void updateUser(User User) {
        try {
            PreparedStatement updateAccount = connection.prepareStatement(
                    "UPDATE authentication SET username = ?, password = ?, firstName = ?, lastName = ?, age = ? WHERE id = ?"
            );
            updateAccount.setString(1, User.getUsername());
            updateAccount.setString(2, User.getPassword());
            updateAccount.setString(3, User.getFirstName());
            updateAccount.setString(4, User.getLastName());
            updateAccount.setInt(5, User.getAge());
            updateAccount.setInt(6, User.getId());
            updateAccount.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM authentication WHERE id = ?");
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> accounts = new ArrayList<>();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM authentication");
            while (rs.next()) {
                accounts.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("email"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getInt("age")
                        )
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return accounts;
    }

    @Override
    public User getUser(int id) {
        try {
            PreparedStatement getAccount = connection.prepareStatement("SELECT * FROM authentication WHERE id = ?");
            getAccount.setInt(1, id);
            ResultSet rs = getAccount.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("age")
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    @Override
    public int getUserId(String username, String password) {
        try {
            PreparedStatement getAccount = connection.prepareStatement("SELECT id FROM authentication WHERE username = ? AND password = ?");
            getAccount.setString(1, username);
            getAccount.setString(2, password);
            ResultSet rs = getAccount.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return -1; // Return a default value indicating failure (you may choose another value if -1 is not appropriate)
    }

    public boolean isValidLogin(String username, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM authentication WHERE username = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // If there's a row in the result set, the login is valid
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of any exception
        }
    }

    public boolean userExists(String username) {
        try {
            // Prepare a SQL statement to query the database
            String sql = "SELECT COUNT(*) FROM authentication WHERE username = ?";

            // Create a PreparedStatement with the SQL query
            PreparedStatement statement = connection.prepareStatement(sql);

            // Set the username parameter in the PreparedStatement
            statement.setString(1, username);

            // Execute the query and retrieve the result set
            ResultSet resultSet = statement.executeQuery();

            // Check if the result set has any rows (i.e., if the username exists)
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Return true if count > 0 (username exists)
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions (e.g., connection errors)
            e.printStackTrace();
        }

        // Return false if an error occurred or if the username doesn't exist
        return false;
    }

    @Override
    public void AddOrUpdateUserPref(int id, String appName, int weekHours, int monthHours) {
        try {
            PreparedStatement updateStmt = connection.prepareStatement(
                    "UPDATE userPreferences SET weeklyHourLimit = ?, monthlyHourLimit = ? WHERE authenticationId = ? AND applicationName = ?"
            );
            updateStmt.setInt(1, weekHours);
            updateStmt.setInt(2, monthHours);
            updateStmt.setInt(3, id);
            updateStmt.setString(4, appName);
            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated == 0) {
                // If no row was updated, insert a new row
                PreparedStatement insertStmt = connection.prepareStatement(
                        "INSERT INTO userPreferences (authenticationId, applicationName, weeklyHourLimit, monthlyHourLimit, isActive) VALUES (?, ?, ?, ?, 1)"
                );
                insertStmt.setInt(1, id);
                insertStmt.setString(2, appName);
                insertStmt.setInt(3, weekHours);
                insertStmt.setInt(4, monthHours);
                insertStmt.executeUpdate();
                System.out.println("Inserted user preferences successfully!");
            } else {
                System.out.println("Updated user preferences successfully!");
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public List<String> getListOfApps(int id) {
        try {
            System.out.println(id);
            PreparedStatement getApps = connection.prepareStatement(
                    "SELECT applicationName FROM userPreferences WHERE authenticationId = ?"
            );
            getApps.setInt(1, id);
            System.out.println("Executed successfully!");
            ResultSet rs = getApps.executeQuery();
            List<String> activeApps = new ArrayList<>();
            while (rs.next()) {
                String applicationName = rs.getString("applicationName");
                activeApps.add(applicationName);
            }
            return activeApps;
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    public int getIsActiveStatus(int id, String appName) {
        try {
            PreparedStatement getApps = connection.prepareStatement(
                    "SELECT isActive FROM userPreferences WHERE authenticationId = ? AND applicationName = ?"
            );
            getApps.setInt(1, id);
            getApps.setString(2, appName);
            ResultSet psychoactive = getApps.executeQuery();
            if (psychoactive.next()) {
                System.out.println("Existent app: " + appName);
                return psychoactive.getInt("isActive");
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return -1; // Default to inactive if there's an issue or no result found
    }

    public void setApplicationActiveStatus(int authenId, String appName, int isActive) {
        String query = "UPDATE userPreferences SET isActive = ? WHERE authenticationId = ? AND applicationName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, isActive);
            preparedStatement.setInt(2, authenId);
            preparedStatement.setString(3, appName);
            int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Application deactivated successfully.");
                } else {
                    System.out.println("No active application found to deactivate.");
                }
        }catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
    }
    public int countAppsListed(int id) {
        int count = 0;
        try {
            // Prepare the SQL query
            String sqlQuery = ("SELECT COUNT(*)" +
                    "FROM userPreferences " +
                    "WHERE authenticationId = ?");

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            // Set the parameters
            preparedStatement.setInt(1, id);
            // Execute the query
            ResultSet countedApps = preparedStatement.executeQuery();
            if(countedApps.next())
            {
                System.out.println("App count :" + countedApps.getInt(1));

                return countedApps.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving hours tracked: " + ex.getMessage());
        }
        return count;
    }

    //change the names of sql appdata once Isaiah inserts his table with his value names
    @Override
    public double[] getLimitUsagePercentages(String appName, int userId) {
        double[] percentages = new double[2]; // Array to store weekly and monthly percentages

        try {
            // Prepare the SQL query
            String sqlQuery = "SELECT " +
                    "CASE " +
                    "    WHEN userPreferences.weeklyHourLimit > 0 THEN (appData.hoursTracked * 100.0) / userPreferences.weeklyHourLimit " +
                    "    ELSE NULL " +
                    "END AS weeklyLimitUsedPercentage, " +
                    "CASE " +
                    "    WHEN userPreferences.monthlyHourLimit > 0 THEN (appData.hoursTracked * 100.0) / userPreferences.monthlyHourLimit " +
                    "    ELSE NULL " +
                    "END AS monthlyLimitUsedPercentage " +
                    "FROM " +
                    "appData " +
                    "JOIN " +
                    "userPreferences ON appData.userID = userPreferences.authenticationId " +
                    "WHERE " +
                    "appData.userID = ? " +
                    "AND appData.appName = ?;";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            // Set the parameters
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, appName);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Retrieve the percentages
            if (resultSet.next()) {
                percentages[0] = resultSet.getDouble("weeklyLimitUsedPercentage");
                percentages[1] = resultSet.getDouble("monthlyLimitUsedPercentage");
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving limit usage percentages: " + ex.getMessage());
        }

        return percentages;
    }

    // change the names of app data once Isaiah inserts his data.
    @Override
    public int getHoursTracked(String appName, int userId) {
        int hoursTracked = 0;

        try {
            // Prepare the SQL query
            String sqlQuery = "SELECT appData.hoursTracked " +
                    "FROM appData " +
                    "JOIN userPreferences ON appData.userID = userPreferences.authenticationId " +
                    "WHERE appData.userID = ? " +
                    "AND appData.appName = ?;";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            // Set the parameters
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, appName);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Retrieve the hours tracked
            if (resultSet.next()) {
                hoursTracked = resultSet.getInt("hoursTracked");
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving hours tracked: " + ex.getMessage());
        }

        return hoursTracked;
    }
    public void close() {
        System.out.println("Closed user DAO");
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            // Log or handle the exception
            ex.printStackTrace();
        }
    }

}

