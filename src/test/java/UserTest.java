import Logic.User;
import org.junit.Before;
import org.junit.Test;

import static Logic.ReadWrite.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;
    @Before
    public void setup(){
        setupUsers();
        user = new User("name", "pass", "id", "first", "last", "email", 123, 0, "photo", false, "pass2");
        users.add(user);
    }

    @Test
    public void testUserExist() {
        assertNotNull(user.toString());
    }
    @Test
    public void testUserPrivilege() {
        assertEquals(user.getPrivilege(), "Admin");
        user.setPrivilege(1);
        assertEquals(user.getPrivilege(), "User");
        user.setPrivilege(2);
        assertEquals(user.getPrivilege(), "Guest");
    }

    @Test
    public void testUserPhoto() {
        assertEquals(user.getPhoto(), "photo");
        user.setPhoto("phata");
        assertEquals(user.getPhoto(), "phata");
    }

    @Test
    public void testUserSetterGetter() {
        user.setTel(456);
        assertEquals(user.getTel(), 456);

        user.setFirstname("hihi");
        assertEquals(user.getFirstname(), "hihi");

        user.setLastname("hihi");
        assertEquals(user.getLastname(), "hihi");

        user.setUserid("sid");
        assertEquals(user.getUserid(), "sid");

        user.setUsername("uname");
        assertEquals(user.getUsername(), "uname");

        user.setEmail("a@");
        assertEquals(user.getEmail(), "a@");
        user.setUserpass("pwd");
    }

    @Test
    public void testfindUser() {
        assertNotNull(findUser("name"));
        assertNull(findUser(null));
        setupUsers();
        updateUsers();
    }
}
