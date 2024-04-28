import username.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("lily@gmail.com", "lilypads", "lil", "Lily", "Pads", 25);
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
}
