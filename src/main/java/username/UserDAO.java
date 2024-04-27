package username;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        connection = DatabaseConnection.getInstance();
    }

    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            createTable.execute(
                    "CREATE TABLE IF NOT EXISTS Users ("
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

    public void insert(User User) {
        try {
            PreparedStatement insertAccount = connection.prepareStatement(
                    "INSERT INTO Users (firstName, lastName, age, username, password) VALUES (?, ?, ?, ?, ?)"
            );
            insertAccount.setString(1, User.getFirstName());
            insertAccount.setString(2, User.getLastName());
            insertAccount.setInt(3, User.getAge());
            insertAccount.setString(4, User.getUsername());
            insertAccount.setString(5, User.getPassword());
            insertAccount.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    public void update(User User) {
        try {
            PreparedStatement updateAccount = connection.prepareStatement(
                    "UPDATE Users SET username = ?, password = ?, firstName = ?, lastName = ?, age = ? WHERE id = ?"
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

    public void delete(int id) {
        try {
            PreparedStatement deleteAccount = connection.prepareStatement("DELETE FROM Users WHERE id = ?");
            deleteAccount.setInt(1, id);
            deleteAccount.execute();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    public List<User> getAll() {
        List<User> accounts = new ArrayList<>();
        try {
            Statement getAll = connection.createStatement();
            ResultSet rs = getAll.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                accounts.add(
                        new User(
                                rs.getInt("id"),
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

    public User getById(int id) {
        try {
            PreparedStatement getAccount = connection.prepareStatement("SELECT * FROM USERS WHERE id = ?");
            getAccount.setInt(1, id);
            ResultSet rs = getAccount.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
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

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
