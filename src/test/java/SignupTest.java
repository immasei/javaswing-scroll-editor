import Logic.Signup;
import Logic.ReadWrite;
import Logic.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mindrot.jbcrypt.BCrypt;

import static Logic.ReadWrite.setupUsers;
import static org.junit.jupiter.api.Assertions.*;

public class SignupTest {


    @Test
    public void testSetTelValid() {

        Signup signup = new Signup();
        signup.setTel("1234567890");
        assertEquals(1234567890, signup.getTel());
    }

    @Test
    public void testSetTelInvalid() {
        Signup signup = new Signup();
        signup.setTel("abc123");
        assertEquals(-1, signup.getTel());
    }

    @Test
    public void testSetEmailValid() {
        Signup signup = new Signup();
        signup.setEmail("test@example.com");
        assertEquals("test@example.com", signup.getEmail());
    }

    @Test
    public void testSetEmailInvalid() {
        Signup signup = new Signup();
        signup.setEmail("invalid-email");
        assertNull(signup.getEmail());
    }

    @Test
    public void testSetIDValid() {
        Signup signup = new Signup();
        signup.setID("user123");
        assertEquals("user123", signup.getUserid());
    }

    @Test
    public void testSetIDInvalid() {
        Signup signup = new Signup();
        signup.setID("");
        assertNull(signup.getUserid());
    }

    @Test
    public void testSetNameValid() {
        Signup signup = new Signup();
        signup.setName("John", "Doe");
        assertEquals("John", signup.getFirstname());
        assertEquals("Doe", signup.getLastname());
    }

    @Test
    public void testSetNameInvalid() {
        Signup signup = new Signup();
        signup.setName("", "");
        assertNull(signup.getFirstname());
        assertNull(signup.getLastname());
    }

    @Test
    public void testSetUserValid() {
        setupUsers();
        Signup signup = new Signup();
        signup.setUser("johndoe", "password123", "user123");
        assertEquals("johndoe", signup.getUsername());
        assertEquals("user123", signup.getUserid());
        assertEquals("password123", signup.getUserpass());
    }

    @Test
    public void testSetUserInvalid() {
        Signup signup = new Signup();
        signup.setUser("user with space", "user with space", "password with space");
        assertNull(signup.getUsername());
        assertNull(signup.getUserid());
        assertNull(signup.getUserpass());
    }
    @Test
    public void testCreateUserValid() {
        setupUsers();
        Signup signup = new Signup();
        signup.setUser("johndoe", "password123", "user123");
        signup.setName("John", "Doe");
        signup.setEmail("test@example.com");
        signup.setTel("1234567890");
        User user = signup.createUser();

        assertNotNull(user);
        assertEquals("johndoe", user.getUsername());
        assertEquals("user123", user.getUserid());
        assertEquals("password123", user.getUserpass());
        assertEquals("John", user.getFirstname());
        assertEquals("Doe", user.getLastname());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(1234567890, user.getTel());
    }

    @Test
    public void testCreateUserIncompleteData() {
        setupUsers();
        Signup signup = new Signup();
        // Not setting all required fields
        signup.setUser("johndoe", "user123", "password123");
        User user = signup.createUser();

        assertNull(user);
    }
    @Test
    public void testSetUserAndCreateUser() {
        setupUsers();
        Signup signup = new Signup();
        signup.setUser("johndoe", "password123", "user123");
        signup.setName("John", "Doe");
        signup.setEmail("test@example.com");
        signup.setTel("1234567890");
        signup.setHasOptPwd(false);
        signup.setOptPwd("pass");
        User user = signup.createUser();

        assertNotNull(user);
        assertEquals("johndoe", user.getUsername());
        assertEquals("user123", user.getUserid());
        assertEquals("password123", user.getUserpass());
        assertEquals("John", user.getFirstname());
        assertEquals("Doe", user.getLastname());
        assertEquals("test@example.com", user.getEmail());
        assertEquals(1234567890, user.getTel());
    }

    @Test
    public void testCreateUserWithIncompleteData() {
        setupUsers();
        Signup signup = new Signup();
        // Not setting all required fields
        signup.setUser("johndoe", "user123", "password123");
        User user = signup.createUser();

        assertNull(user);
    }
    @Test
    public void testCreateUserWithInvalidEmail() {
        setupUsers();
        Signup signup = new Signup();
        signup.setUser("johndoe", "user123", "password123");
        signup.setName("John", "Doe");
        signup.setEmail("invalid-email"); // Invalid email address
        signup.setTel("1234567890");
        User user = signup.createUser();

        assertNull(user);
    }

    @Test
    public void testCreateUserWithInvalidUsername() {
        Signup signup = new Signup();
        signup.setUser("user with space", "user123", "password123"); // Invalid username with space
        signup.setName("John", "Doe");
        signup.setEmail("test@example.com");
        signup.setTel("1234567890");
        User user = signup.createUser();

        assertNull(user);
    }

    @Test
    public void testCreateUserWithInvalidUserid() {
        Signup signup = new Signup();
        signup.setUser("johndoe", "user with space", "password123"); // Invalid userid with space
        signup.setName("John", "Doe");
        signup.setEmail("test@example.com");
        signup.setTel("1234567890");
        User user = signup.createUser();

        assertNull(user);
    }

    @Test
    public void testCreateUserWithInvalidUserpass() {
        Signup signup = new Signup();
        signup.setUser("johndoe", "user123", "password with space"); // Invalid userpass with space
        signup.setName("John", "Doe");
        signup.setEmail("test@example.com");
        signup.setTel("1234567890");
        User user = signup.createUser();

        assertNull(user);
    }

    @Test
    public void testSetTelWithInvalidInput() {
        Signup signup = new Signup();
        signup.setTel("abc123"); // Invalid input for telephone
        assertEquals(-1, signup.getTel());
    }

    @Test
    public void testSetEmailWithEmptyInput() {

        Signup signup = new Signup();
        signup.setEmail(""); // Empty email
        assertEquals(-1, signup.getTel());
    }

    @Test
    public void testSetIDWithEmptyInput() {
        Signup signup = new Signup();
        signup.setID(""); // Empty userid
        assertNull(signup.getUserid());
    }

    @Test
    public void testSetNameWithEmptyInput() {
        Signup signup = new Signup();
        signup.setName("", ""); // Empty first name and last name
        assertNull(signup.getFirstname());
        assertNull(signup.getLastname());
    }

    @Test
    public void testSetUserWithEmptyInput() {
        Signup signup = new Signup();
        signup.setUser("", "", ""); // Empty username, userid, and userpass
        assertNull(signup.getUsername());
        assertNull(signup.getUserid());
        assertNull(signup.getUserpass());
    }

    @Test
    public void testCheckUniqueIDWithUniqueID() {
        Signup signup = new Signup();
        boolean isUnique = ReadWrite.checkUniqueID("newUser123");
        assertTrue(isUnique);
    }

    @Test
    public void testCheckUniqueUserNameWithUniqueUsername() {
        Signup signup = new Signup();
        boolean isUnique = ReadWrite.checkUniqueUserName("newUsername");
        assertTrue(isUnique);
    }

}
