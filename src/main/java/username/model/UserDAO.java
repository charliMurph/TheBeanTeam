package username.model;

import java.security.Principal;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void addLimits(User user) {
        try {
            PreparedStatement updateStmt = connection.prepareStatement(
                            "UPDATE authentication SET userGoalWeekly = ?, userGoalMonthly = ? WHERE id = ?"
            );
            updateStmt.setInt(1, user.getWeeklyLimit());
            updateStmt.setInt(2, user.getMonthlyLimit());
            updateStmt.setInt(3, user.getId());
            updateStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        System.out.println("set app id : " + id);
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

    public void setApplicationActiveStatus(int id, String appName, int isActive) {
        System.out.println("set app id : " + id);
        String query = "UPDATE userPreferences SET isActive = ? WHERE authenticationId = ? AND applicationName = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, isActive);
            preparedStatement.setInt(2, id);
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

    public List<String> getActiveApplications(int id) {
        System.out.println("set app id: " + id);
        String query = "SELECT DISTINCT userPreferences.applicationName AS app " +
                "FROM appData " +
                "JOIN userPreferences " +
                "ON appData.authenticationId = userPreferences.authenticationId " +
                "AND appData.applicationName LIKE '%' || userPreferences.applicationName || '%' " +
                "WHERE appData.authenticationId = ? " +
                "AND userPreferences.isActive = 1";
        List<String> activeApps = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String appName = resultSet.getString("app");
                    activeApps.add(appName);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return activeApps;
    }
    @Override
    public double[] getLimitUsagePercentages(String appName, int userId) {
        System.out.println("Id :" + userId);
        String app = "%" + appName + "%";
        System.out.println("appname: " + app);
        double[] percentages = new double[2]; // Array to store weekly and monthly percentages
        try {
            // Prepare the SQL query
            String sqlQuery = "SELECT " +
                    "ROUND((SUM(appData.seconds) * 100.0) / (userPreferences.weeklyHourLimit * 3600), 2) AS weeklyLimitUsedPercentage, " +
                    "ROUND((SUM(appData.seconds) * 100.0) / (userPreferences.monthlyHourLimit * 3600), 2) AS monthlyLimitUsedPercentage " +
                    "FROM appData " +
                    "JOIN " +
                    "userPreferences ON appData.authenticationId = userPreferences.authenticationId " +
                    "WHERE " +
                    "appData.authenticationId = ? " +
                    "AND userPreferences.applicationName LIKE ? " +
                    "AND appData.applicationName LIKE ? " +
                    "GROUP BY " +
                    "userPreferences.weeklyHourLimit, userPreferences.monthlyHourLimit";
            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            // Set the parameters
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, app);
            preparedStatement.setString(3, app);
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
    public int getTimeTracked(String appName, int userId) {
        int timeTracked = 0;
        try {
            // Prepare the SQL query
            String sqlQuery = "SELECT SUM(seconds) AS timeTracked " +
                    "FROM appData " +
                    "WHERE appData.applicationName LIKE ? " +
                    "AND authenticationId = ? ";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            // Set the parameters
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(1, "%" + appName + "%");

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Retrieve the hours tracked
            if (resultSet.next()) {
                timeTracked = resultSet.getInt("timeTracked");
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving hours tracked: " + ex.getMessage());
        }

        return timeTracked;
    }
    public void trackApps(int id, String appName, Long duration, String startTimeStr, String endTimeStr) {
        System.out.println("Duration is: " + duration);
        System.out.println("Duration is: " + duration / 3600);
        String query = "INSERT INTO appData (authenticationId, applicationName, start_time, stop_time, seconds) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.setString(2, appName);
            statement.setString(3, startTimeStr);
            statement.setString(4, endTimeStr);
            statement.setLong(5, duration);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public HashMap<String, Integer> TopApps(int id, String fromDate, String toDate) {
        String rankquery = "SELECT userPreferences.applicationName, SUM(appData.seconds) AS timeTracked " +
                "FROM appData " +
                "JOIN userPreferences " +
                "ON appData.authenticationId = userPreferences.authenticationId " +
                "AND appData.applicationName LIKE '%' || userPreferences.applicationName || '%' " +
                "WHERE userPreferences.isActive = 1 " +
                "AND appData.authenticationId = ? " +
                "AND appData.stop_time BETWEEN ? AND ?"+
                "GROUP BY userPreferences.applicationName " +
                "ORDER BY timeTracked DESC";

        HashMap<String, Integer> appHoursMap = new HashMap<>();

        try (PreparedStatement rankStatement = connection.prepareStatement(rankquery)) {
            rankStatement.setInt(1, id);
            rankStatement.setString(2, fromDate + " 00:00:00"); // Start of the day 7 days ago
            rankStatement.setString(3, toDate + " 23:59:59"); // End of the current day
            ResultSet resultSet = rankStatement.executeQuery();

            while (resultSet.next()) {
                String appName = resultSet.getString("applicationName");
                int hoursTracked = resultSet.getInt("timeTracked");
                appHoursMap.put(appName, hoursTracked);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return appHoursMap;
    }
    public HashMap<String, Integer> DoWUsage(int id, String fromDate, String toDate) {
        HashMap<String, Integer> appHoursMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(fromDate, formatter);
        LocalDate endDate = LocalDate.parse(toDate, formatter);

        while (!startDate.isAfter(endDate)) {
            String currentDate = startDate.format(formatter);
            String rankquery = "SELECT " +
                    "    MAX(timeTracked) AS timeTracked " +
                    "FROM (" +
                    "    SELECT " +
                    "        SUM(appData.seconds) AS timeTracked " +
                    "    FROM " +
                    "        appData " +
                    "    JOIN " +
                    "        userPreferences ON appData.authenticationId = userPreferences.authenticationId " +
                    "    WHERE " +
                    "        userPreferences.isActive = 1 " +
                    "        AND appData.authenticationId = ? " +
                    "        AND DATE(appData.stop_time) = ? " + // Filter by current date
                    "    GROUP BY " +
                    "        userPreferences.applicationName" +
                    ") AS subquery";

            try (PreparedStatement rankStatement = connection.prepareStatement(rankquery)) {
                rankStatement.setInt(1, id);
                rankStatement.setString(2, currentDate);
                ResultSet resultSet = rankStatement.executeQuery();

                if (resultSet.next()) {
                    int hoursTracked = resultSet.getInt("timeTracked");
                    appHoursMap.put(currentDate, hoursTracked);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            startDate = startDate.plusDays(1); // Move to the next day
        }

        return appHoursMap;
    }

    public HashMap<String, Double> getPvNP(int id, String fromDate, String toDate) {
        // Get the current date and date 7 days ago in Brisbane timezone
        String sqlQuery = "WITH total_seconds AS ( " +
                "SELECT SUM(appData.seconds) AS sec " +
                "FROM appData " +
                "WHERE authenticationId = ? " +
                "AND appData.stop_time BETWEEN ? AND ? " + // Filter by Brisbane date range
                ") " +
                "SELECT " +
                "ROUND((total_seconds.sec * 100.0) / (authentication.userGoalWeekly * 3600), 2) AS UsedPercentage, " +
                "ROUND(100 - ((total_seconds.sec * 100.0) / (authentication.userGoalWeekly * 3600)), 2) AS NotUsedPercentage " +
                "FROM " +
                "total_seconds, " +
                "authentication " +
                "WHERE " +
                "authentication.id = ?";

        HashMap<String, Double> appHoursMap = new HashMap<>();

        try (PreparedStatement rankStatement = connection.prepareStatement(sqlQuery)) {
            rankStatement.setInt(1, id);
            rankStatement.setString(2, fromDate + " 00:00:00"); // Start of the day 7 days ago
            rankStatement.setString(3, toDate + " 23:59:59"); // End of the current day
            rankStatement.setInt(4, id);
            ResultSet resultSet = rankStatement.executeQuery();

            if (resultSet.next() && resultSet.getObject("UsedPercentage") != null) {
                double usedPercentage = resultSet.getDouble("UsedPercentage");
                double notUsedPercentage = resultSet.getDouble("NotUsedPercentage");
                appHoursMap.put("non productive", usedPercentage);
                appHoursMap.put("productive", notUsedPercentage);
            } else {
                System.out.println("isnull");
                appHoursMap.put("productive", 100.0);
                appHoursMap.put("non productive", 0.0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appHoursMap;
    }
    public HashMap<String, Integer> appByMinutesTop(int id, String fromDate, String toDate) {
        java.lang.String rankquery = "SELECT userPreferences.applicationName, (SUM(appData.seconds) * 0.0166667) AS timeTracked " +
                "FROM appData " +
                "JOIN userPreferences " +
                "ON appData.authenticationId = userPreferences.authenticationId " +
                "AND appData.applicationName LIKE '%' || userPreferences.applicationName || '%' " +
                "WHERE userPreferences.isActive = 1 " +
                "AND appData.authenticationId = ? " +
                "AND appData.stop_time BETWEEN ? AND ?"+
                "GROUP BY userPreferences.applicationName " +
                "ORDER BY timeTracked DESC";

        HashMap<java.lang.String, java.lang.Integer> appHoursMap = new HashMap<>();

        try (PreparedStatement rankStatement = connection.prepareStatement(rankquery)) {
            rankStatement.setInt(1, id);
            rankStatement.setString(2, fromDate + " 00:00:00"); // Start of the day 7 days ago
            rankStatement.setString(3, toDate + " 23:59:59"); // End of the current day
            ResultSet resultSet = rankStatement.executeQuery();

            while (resultSet.next()) {
                java.lang.String appName = resultSet.getString("applicationName");
                int hoursTracked = resultSet.getInt("timeTracked");
                appHoursMap.put(appName, hoursTracked);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appHoursMap;
    }
    public HashMap<String, Number> approundNum(int id, String fromDate, String toDate) {
        HashMap<String, Number> appHoursMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(fromDate, formatter);
        LocalDate endDate = LocalDate.parse(toDate, formatter);

        while (!startDate.isAfter(endDate)) {
            String currentDate = startDate.format(formatter);
            String rankquery = "SELECT " +
                    "    MAX(timeTracked)  * 0.0166667 AS timeTracked " +
                    "FROM (" +
                    "    SELECT " +
                    "        SUM(appData.seconds) AS timeTracked " +
                    "    FROM " +
                    "        appData " +
                    "    JOIN " +
                    "        userPreferences ON appData.authenticationId = userPreferences.authenticationId " +
                    "    WHERE " +
                    "        userPreferences.isActive = 1 " +
                    "        AND appData.authenticationId = ? " +
                    "        AND DATE(appData.stop_time) = ? " + // Filter by current date
                    "    GROUP BY " +
                    "        userPreferences.applicationName" +
                    ") AS subquery";

            try (PreparedStatement rankStatement = connection.prepareStatement(rankquery)) {
                rankStatement.setInt(1, id);
                rankStatement.setString(2, currentDate);
                ResultSet resultSet = rankStatement.executeQuery();

                if (resultSet.next()) {
                    int minTracked = resultSet.getInt("timeTracked");
                    appHoursMap.put(currentDate, minTracked);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            startDate = startDate.plusDays(1); // Move to the next day
        }

        return appHoursMap;
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
    public void open() {
        connection = DatabaseConnection.getInstance();
        // Initialize dataconnect here
        dataconnect = new DatabaseConnection();
        dataconnect.createUserTable(connection);
        dataconnect.createUserPreferences(connection);
        System.out.println("Connected DAO");


    }


}
