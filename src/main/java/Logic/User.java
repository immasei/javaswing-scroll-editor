package Logic;

import org.mindrot.jbcrypt.BCrypt;
import static Logic.Login.hashPassword;

public class User {
    private int tel;
    private String email;
    private String username;
    private String userid;
    private String userpass;
    private String firstname;
    private String lastname;
    private int privilege;
    private String photo;

    private boolean has_opt_pwd;
    private String opt_pwd;

    public User(String username, String userpass, String userid, String firstname, String lastname, String email, int tel, int privilege, String photo, boolean has_opt_pwd, String opt_pwd) {
        this.username = username;
        this.userid = userid;
        this.userpass = userpass;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.tel = tel;
        this.privilege = privilege; //0 = admin, 1 = user, 2 = guest
        this.photo = photo;

        this.has_opt_pwd = has_opt_pwd;
        this.opt_pwd = opt_pwd;
    }

    public User() {
        this.username = "";
        this.userid = "NoID";
        this.firstname = "No";
        this.lastname = "Name";
        //no profile?
        this.privilege = 2;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUserid() {
        return userid;
    }

    public String getUserpass() {
        return userpass;
    }

    public String getEmail() {
        return email;
    }

    public int getTel() {
        return tel;
    }

    public String getPrivilege() {
        if (privilege == 0)
            return "Admin";
        else if (privilege == 1)
            return "User";
        else
            return "Guest";
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUserpass(String pass) {
        String salt = BCrypt.gensalt(12);
        this.userpass = hashPassword(pass, salt);
        System.out.println("new pass" + this.userpass);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public void setPhoto(String photo){
        this.photo = photo;
    }

    @Override
    public String toString() {
        return
                username
                        + ", " + userpass
                        + ", " + userid
                        + ", " + firstname
                        + ", " + lastname
                        + ", " + email
                        + ", " + tel
                        + ", " + privilege
                        + ", " + photo
                        + ", " + has_opt_pwd
                        + ", " + opt_pwd;
    }
}
