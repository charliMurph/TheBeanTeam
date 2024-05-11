package username.model;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection instance = null;

    DatabaseConnection() {
        String url = "jdbc:sqlite:Screen_Time_Bean_Time.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            new DatabaseConnection();
        }
        return instance;
    }

    public void createUserTable(Connection connection) {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS authentication ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "username VARCHAR NOT NULL, "
                            + "password VARCHAR NOT NULL, "
                            + "firstName VARCHAR NOT NULL, "
                            + "lastName VARCHAR NOT NULL, "
                            + "age INTEGER NOT NULL"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    public void createScreenLogs(Connection connection){
        try{
            Statement ScreenLogs = connection.createStatement();
            ScreenLogs.execute(
                    "CREATE TABLE IF NOT EXISTS screenLogs ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "authentication_id INTEGER NOT NULL, "
                            + "application_name VARCHAR NOT NULL, "
                            + "hours_tracked INTEGER NOT NULL, "
                            + "FOREIGN KEY (authentication_id) REFERENCES authentication(id)"
                            + ")"
            );
        }
        catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    public void createUserPreferences(Connection connection) {
        try {
            Statement createUserPreferences = connection.createStatement();
            createUserPreferences.execute(
                    "CREATE TABLE IF NOT EXISTS userPreferences ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "authentication_id INTEGER NOT NULL, "
                            + "application_name VARCHAR NOT NULL, "
                            + "hours_per_week INTEGER NOT NULL, "
                            + "hours_per_month INTEGER NOT NULL, "
                            + "FOREIGN KEY (authentication_id) REFERENCES authentication(id)"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

}