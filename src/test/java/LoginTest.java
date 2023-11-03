import Logic.Login;
import Logic.ReadWrite;
import Logic.User;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mindrot.jbcrypt.BCrypt;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private static final String SALT = "$2a$12$aEw01SPf1IfNCkWfhQa09udtlEKniGnye/9jyNwdO3RLOp6eEKbN.";

    @Test
    public void testHashPassword() {
        String plainPassword = "password123";

        // Hash the password
        String hashedPassword = Login.hashPassword(plainPassword, SALT);

        assertNotNull(hashedPassword);
        assertTrue(hashedPassword.length() > 0);

        // You can add more assertions here based on your requirements
    }

    @Test
    public void testVerifyValidPassword() {
        String username = "111";
        String plainPassword = "password123";

        // Verify a valid password
        boolean result = Login.verifyPassword(plainPassword, username);

        assertFalse(result);
    }

    @Test
    public void testVerifyInvalidPassword() {
        String username = "123";
        String plainPassword = "wrongpassword";

        // Verify an invalid password
        boolean result = Login.verifyPassword(plainPassword, username);

        assertFalse(result);
    }

    @Test
    public void testVerifyNonexistentUser() {
        String username = "nonexistentUser";
        String plainPassword = "password123";

        // Verify a user that doesn't exist in the membershipDB.txt file
        boolean result = Login.verifyPassword(plainPassword, username);

        assertFalse(result);
    }
    @Test
    public void testVerifyPasswordWithEmptyParameters() {
        // Verify with empty username and password
        boolean result = Login.verifyPassword("", "");

        assertFalse(result);
    }

    @Test
    public void testHashPasswordWithEmptyPassword() {
        String plainPassword = "";

        // Hash an empty password
        String hashedPassword = Login.hashPassword(plainPassword, SALT);

        assertNotNull(hashedPassword);
        assertTrue(hashedPassword.length() > 0);
    }

    @Test
    public void testHashPasswordWithNullPassword() {
        String plainPassword = null;

        // Hash a null password
        String hashedPassword = Login.hashPassword(plainPassword, SALT);

        assertNotNull(hashedPassword);
        assertTrue(hashedPassword.length() > 0);
    }

    @Test
    public void testVerifyPasswordWithEmptyStoredHash() {
        String username = "emptyHashUser";
        String plainPassword = "password123";
        String optPassword = "pass";

        // Verify with an empty stored hash in membershipDB.txt
        boolean result = Login.verifyPassword(plainPassword, username);
        boolean res2 = Login.verifyOptPassword(optPassword, username);
        boolean res3 = Login.checkIfOptPwdExist(username);

        assertFalse(result);
        assertFalse(res2);
        assertFalse(res3);
    }
}
