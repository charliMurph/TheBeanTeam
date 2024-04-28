package username;

import username.model.User;
import username.model.UserDAO;

public class MainApplication {
    public static void main(String[] args) {
        // Establish a connection to the database
            UserDAO userDAO = new UserDAO();
            // Create a new user
            userDAO.close();
    }
}