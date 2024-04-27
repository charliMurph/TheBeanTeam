package username;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {

    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    public User(int id, String username, String password, String firstName, String lastName, int age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    public User(String username, String password, String firstName, String lastName, int age) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
    }
    

    public void insertData(Connection connection) {
        String insertQuery = "INSERT INTO users (username, password, first_name, last_name, age) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, getUsername());
            statement.setString(2, getPassword());
            statement.setString(3, getFirstName());
            statement.setString(4, getLastName());
            statement.setInt(5, getAge());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}