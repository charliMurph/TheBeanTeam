package username.model;

import java.util.List;

public interface IScreenTIme {
    public void getUserName(User user);
    /**
     * Updates an existing user in the database.
     * @param user The user to update.
     */
    public void beginSceenTracker(User user);
    /**
     * Begins screen tracker to insert into table UserAnalytics
     * @param user The user to store data
     */
    public void getDailyUsage(User user);
    /**
     * Retrieves a user from the database.
     * @param username The id of the user to retrieve.
     * @return The userAnalytics with the given id, or null if not found.
     */
    public User MonthyUsage(String username);
    /**
     * Retrieves all users from the database.
     * @return A list of all users in the database.
     */
    public List<User> getAllUsers();
}
