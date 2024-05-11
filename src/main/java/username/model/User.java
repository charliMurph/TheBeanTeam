package username.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String applicationName;
    private int hoursTracked;

    public User(int id, String email, String username, String password, String firstName, String lastName, int age) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    public User(String email, String username, String password, String firstName, String lastName, int age) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
    public User(String applicationName, int hoursTracked){
        this.applicationName = applicationName;
        this.hoursTracked = hoursTracked;
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
    public String getEmail() { return email; }

    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public int getHoursTracked() {
        return hoursTracked;
    }

    public void setHoursTracked(int hoursTracked) {
        this.hoursTracked = hoursTracked;
    }
}