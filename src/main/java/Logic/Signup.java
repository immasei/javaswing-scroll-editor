package Logic;

import static Logic.ReadWrite.checkUniqueID;
import static Logic.ReadWrite.checkUniqueUserName;

public class Signup {
    private int tel;
    private String email;
    private String username;
    private String userid;
    private String userpass;
    private String firstname;
    private String lastname;

    private boolean has_opt_pwd;
    private String opt_pwd;

    public Signup() {
        this.username = null;
        this.userid= null;
        this.userpass = null;
        this.firstname = null;
        this.lastname = null;
        this.email = null;
        this.tel = -1;

        this.has_opt_pwd = false;
        this.opt_pwd = null;
    }

    public Signup setTel(String tel) {
        try {
            this.tel = Integer.parseInt(tel);
        } catch (Exception e) {
            this.tel = -1;
        }
        return this;
    }

    public Signup setEmail(String email) {
        if (!email.trim().isEmpty() || !email.contains(" ")) {
            if (email.contains("@"))
                this.email = email;
        }
        return this;
    }

    public Signup setID(String userid) {
        if (!userid.trim().isEmpty())
            this.userid = userid;
        return this;
    }

    public Signup setName(String name, String last) {
        if (!name.trim().isEmpty() && !last.trim().isEmpty()) {
            this.firstname = name;
            this.lastname = last;
        }
        return this;
    }

    public Signup setHasOptPwd(boolean hasOptPwd) {
        this.has_opt_pwd = hasOptPwd;
        return this;
    }

    public Signup setOptPwd(String pwd) {
        if (!pwd.contains(" ") && !pwd.trim().isEmpty()) {
            if (checkUniqueID(userid) && checkUniqueUserName(username)){
                this.opt_pwd = pwd;
            }
        }
        return this;
    }

    public Signup setUser(String username, String userpass, String userid) {
        if (!username.trim().isEmpty() && !userid.trim().isEmpty() && !userpass.trim().isEmpty()) {
            if (!userpass.contains(" ") && !userid.contains(" ") && !username.contains(" ")) {
                if (checkUniqueID(userid) && checkUniqueUserName(username)){
                    this.username = username;
                    this.userid = userid;
                    this.userpass = userpass;
                }
            }
        }
        return this;
    }

    public User createUser() {
        if (username != null && userpass != null && userid != null && firstname != null && lastname != null) {
            if (email != null && tel != -1) {
                System.out.println((!has_opt_pwd || opt_pwd != null));
                if (!has_opt_pwd || opt_pwd != null) {
                    return new User(
                            username,
                            userpass,
                            userid,
                            firstname,
                            lastname,
                            email,
                            tel,
                            1,
                            "src/main/resources/photo/cat.png",
                            has_opt_pwd,
                            opt_pwd
                    );
                }
            }
        }
        return null;
    }

    public int getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public String getUserid() {
        return userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getUserpass() {
        return userpass;
    }

}
