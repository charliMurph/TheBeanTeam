package username.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO{
    private Connection connection;
    public DatabaseConnection dataconnect;

    public UserDAO() {
        connection = DatabaseConnection.getInstance();
        // Initialize dataconnect here
        dataconnect = new DatabaseConnection();
        dataconnect.createTable(connection);
    }
    @Override
    public void addUser(User User) {
        try {
            PreparedStatement insertAccount = connection.prepareStatement(
                    "INSERT INTO authentication (firstName, lastName, age, username, password) VALUES (?, ?, ?, ?, ?)"
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
    public void close() {
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

