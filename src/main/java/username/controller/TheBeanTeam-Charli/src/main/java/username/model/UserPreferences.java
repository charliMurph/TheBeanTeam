//package username.model;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class UserPreferences {
//    private int id;
//    private String appName;
//    private User user;
//    private int weekHours;
//    private int monthHours;
//    private Connection connection;
//    public DatabaseConnection dataconnect;
//
//    public UserPreferences() {
//
//        connection = DatabaseConnection.getInstance();
//        System.out.println("Connect to user pref");
//        // Initialize dataconnect here
//        dataconnect = new DatabaseConnection();
//        dataconnect.createUserPreferences(connection);
//    }
//    public void addUser(User user) {
//        this.user = user;
//    }
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getAppName() {
//        return appName;
//    }
//
//    public void setAppName(String appName) {
//        this.appName = appName;
//    }
//
//    public int getWeekHours() {
//        return weekHours;
//    }
//
//    public void setWeekHours(int weekHours) {
//        this.weekHours = weekHours;
//    }
//
//    public int getMonthHours() {
//        return monthHours;
//    }
//
//    public void setMonthHours(int monthHours) {
//        this.monthHours = monthHours;
//    }
//    public void addUserPreference(UserPreferences userpref) {
//        try {
//            PreparedStatement insertPreference = connection.prepareStatement(
//                    "INSERT INTO userPreferences (authenticationId, applicationName, " +
//                            "weeklyHourLimit, monthlyHourLimit) VALUES (?, ?, ?, ?)"
//            );
//            insertPreference.setInt(1, getId());
//            insertPreference.setString(2, userpref.getAppName());
//            insertPreference.setInt(3, userpref.getWeekHours());
//            insertPreference.setInt(4, userpref.getMonthHours());
//            insertPreference.execute();
//            System.out.println("Executed successfully!");
//        } catch (SQLException ex) {
//            System.err.println(ex);
//        }
//
//    }
//
//    public void addAppName(int id, String appName, int weekHours,int monthHours) {
//        System.out.println("user id: " + id);
//        try {
//            PreparedStatement insertPreference = connection.prepareStatement(
//                    "INSERT INTO userPreferences (authenticationId, applicationName, " +
//                            "weeklyHourLimit, monthlyHourLimit) VALUES (?, ?, ?, ?)"
//            );
//            insertPreference.setInt(1, id);
//            insertPreference.setString(2, appName);
//            insertPreference.setInt(3, weekHours);
//            insertPreference.setInt(4, monthHours);
//            insertPreference.execute();
//            System.out.println("Executed successfully!");
//        } catch (SQLException ex) {
//            System.err.println(ex);
//        }
//    }
//    public void close() {
//        try {
//            if (connection != null) {
//                connection.close();
//            }
//        } catch (SQLException ex) {
//            // Log or handle the exception
//            ex.printStackTrace();
//        }
//        System.out.println("Closed user pref");
//    }
//
//
//}
//
