import username.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import username.model.UserDAO;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        user = new User("lily@gmail.com", "lilypads", "lil", "Lily", "Pads", 25);
        userDAO = new UserDAO(); // Initialize userDAO
    }

    @Test
    public void testGetUsername() {
        assertEquals("lilypads", user.getUsername());
    }

    @Test
    public void testSetUsername() {
        user.setUsername("jane");
        assertEquals("jane", user.getUsername());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("Lily", user.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        user.setFirstName("Jane");
        assertEquals("Jane", user.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Pads", user.getLastName());
    }

    @Test
    public void testSetLastName() {
        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("lily@gmail.com", user.getEmail());
    }

    @Test
    public void testSetEmail() {
        user.setEmail("jane@example.com");
        assertEquals("jane@example.com", user.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("lil", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newpassword456");
        assertEquals("newpassword456", user.getPassword());
    }
    @Test
    public void testUserExists() {

        // Test for an existing username
        assertTrue(userDAO.userExists("lilypads"));

        // Test for a non-existing username
        assertFalse(userDAO.userExists("nonexistinguser"));
    }
}
