package username;

public class Main {
    public static void main(String[] args) {
        // Establish a connection to the database
            UserDAO userDAO = new UserDAO();
            userDAO.createTable();
            // Create a new user
            userDAO.insert(new User("van@example", "pass", "Vannessa", "Hill", 24));

            // Update the user's name and age
            // Insert the new user into the database
            userDAO.close();
    }
}