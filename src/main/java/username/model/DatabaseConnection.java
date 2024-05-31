package username.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static Connection instance = null;

    public DatabaseConnection() {
        String url = "jdbc:sqlite:Screen_Time_Bean_Time.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    public static synchronized Connection getInstance() {
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
                            + "email VARCHAR NOT NULL, "
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

    public void createUserPreferences(Connection connection) {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS userPreferences ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "authenticationId INTEGER NOT NULL, "
                            + "applicationName VARCHAR NOT NULL, "
                            + "weeklyHourLimit INTEGER NOT NULL, "
                            + "monthlyHourLimit INTEGER, "
                            + "isActive INTEGER NOT NULL, "
                            + "FOREIGN KEY (authenticationId) REFERENCES authentication(id)"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void createAppDataTable(Connection connection) {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS appData ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "authenticationId INTEGER NOT NULL, "
                            + "applicationName VARCHAR NOT NULL, "
                            + "hours INTEGER NOT NULL, "
                            + "start_time TIMESTAMP, "
                            + "stop_time TIMESTAMP, "
                            + "FOREIGN KEY (authenticationId) REFERENCES authentication(id)"
                            + ")"
            );
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void initializeDatabase() {
        Connection connection = getInstance();
        createUserTable(connection);
        createUserPreferences(connection);
        createAppDataTable(connection);
    }
}
