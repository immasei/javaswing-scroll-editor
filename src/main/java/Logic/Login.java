package Logic;

import org.mindrot.jbcrypt.BCrypt;
import static Logic.ReadWrite.Reader;
import static Logic.ReadWrite.ReaderOptPwd;
import static Logic.ReadWrite.ReaderOptEnabled;

public class Login {

    public static String hashPassword(String plainPassword, String salt) {
        // Hash the password with the salt
        return BCrypt.hashpw(plainPassword, salt);
    }

    // Function to verify a password
    public static boolean verifyPassword(String plainPassword, String username) {

        String storeHashed = Reader( username);

        // Check if the provided plain password matches the hashed password
        if (storeHashed == null){return false;}

        return BCrypt.checkpw(plainPassword, storeHashed);
    }

    public static boolean verifyOptPassword(String plainOptPassword, String username) {

        String storeHashed = ReaderOptPwd( username);

        // Check if the provided plain password matches the hashed password
        if (storeHashed == null){return false;}

        return BCrypt.checkpw(plainOptPassword, storeHashed);
    }

    public static boolean checkIfOptPwdExist(String username) {
        return ReaderOptEnabled( username);
    }
}
