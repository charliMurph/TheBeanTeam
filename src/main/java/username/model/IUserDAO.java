package username.model;
import java.util.List;

/**
 * Interface for the Contact Data Access Object that handles
 * the CRUD operations for the Contact class with the database.
 */
public interface IUserDAO {
    /**
     * Adds a new user to the database.
     * @param user The user to add.
     */
    public void addUser(User user);
    /**
     * Updates an existing user in the database.
     * @param user The user to update.
     */
    public void updateUser(User user);
    /**
     * Deletes a user from the database.
     * @param user The user to delete.
     */
    public void deleteUser(User user);
    /**
     * Retrieves a user from the database.
     * @param id The id of the user to retrieve.
     * @return The user with the given id, or null if not found.
     */
    public User getUser(int id);
    /**
     * Retrieves all users from the database.
     * @return A list of all users in the database.
     */
    public List<User> getAllUsers();
    public void AddOrUpdateUserPref(int id , String appName, int weekHours, int monthHours);

    public double[] getLimitUsagePercentages(String appName, int userId);
    int getUserId(String username, String password);
    public int getHoursTracked(String appName, int userId);
}