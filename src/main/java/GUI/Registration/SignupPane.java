package GUI.Registration;

import GUI.Main;
import Logic.Login;
import Logic.Signup;
import Logic.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static GUI.Application.ListUserPane.updateAllUserTable;
import static GUI.Application.ProfilePane.updateUserTable;
import static Logic.ReadWrite.*;

public class SignupPane extends JPanel implements ActionListener {
    private JTextField fid, fpass, uname, fname, flast, femail, ftel;
    private JLabel pass, opass, userid, wall, name, email, first, last, tel;
    private JButton signup;
    private JCheckBox cbopass;
    private User admin;
    private String fopass;

    public SignupPane(User admin) {
        this.admin = admin;
        setLayout(null);

        Font font1 = new Font("Roboto", 1, 17);
        Font font2 = new Font("Roboto", 0, 16);

        wall = new JLabel();
        wall.setBounds(0, 0, 348, 800);
        wall.setBackground(new Color(41, 41, 41));
        wall.setOpaque(true);

        userid = new JLabel("User ID");
        userid.setBounds(380, 50, 100, 40);
        userid.setFont(font1);
        fid = new JTextField();
        fid.setBounds(385, 100, 270, 35);
        fid.setFont(font2);

        pass = new JLabel("Password");
        pass.setBounds(380, 145, 100, 40);
        pass.setFont(font1);
        fpass = new JTextField();
        fpass.setBounds(385, 196, 270, 35);
        fpass.setFont(font2);

        cbopass = new JCheckBox("");
        cbopass.setBounds(378, 330, 30, 40);
        cbopass.setFont(font1);
        opass = new JLabel("Optional Password");
        opass.setBounds(405, 325, 200, 50);
        opass.setFont(new Font("Consolas", 1, 12));
        opass.setForeground(new Color(255, 86, 80));

        name = new JLabel("User Name");
        name.setBounds(380, 245, 100, 40);
        name.setFont(font1);
        uname = new JTextField();
        uname.setBounds(385, 291, 270, 35);
        uname.setFont(font2);

        first = new JLabel("First Name");
        first.setBounds(30, 50, 100, 40);
        first.setFont(font1);
        first.setForeground(Color.WHITE);
        fname = new JTextField();
        fname.setBounds(35, 100, 270, 30);
        fname.setFont(font2);

        last = new JLabel("Last Name");
        last.setBounds(30, 145, 100, 40);
        last.setFont(font1);
        last.setForeground(Color.WHITE);
        flast = new JTextField();
        flast.setBounds(135, 150, 170, 30);
        flast.setFont(font2);

        tel = new JLabel("Tel");
        tel.setBounds(30, 195, 100, 40);
        tel.setFont(font1);
        tel.setForeground(Color.WHITE);
        ftel = new JTextField();
        ftel.setBounds(70, 200, 235, 30);
        ftel.setFont(font2);

        email = new JLabel("Email");
        email.setBounds(30, 245, 100, 40);
        email.setFont(font1);
        email.setForeground(Color.WHITE);
        femail = new JTextField();
        femail.setBounds(35, 295, 270, 30);
        femail.setFont(font2);

        signup = new JButton("Sign up");
        signup.setBounds(230, 390, 230, 40);
        signup.setBackground(new Color(244, 162, 97));
        signup.setBorderPainted(false);
        signup.setFont(font1);

        add(userid);
        add(pass);
        add(fid);
        add(fpass);
        add(signup);

        add(first);
        add(last);
        add(name);
        add(email);
        add(tel);

        add(fname);
        add(uname);
        add(flast);
        add(femail);
        add(ftel);

        add(opass);
        add(cbopass);

        add(wall);

        cbopass.addActionListener(this);
        signup.addActionListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signup){
            // Credentials
            String Username = uname.getText();
            String Password = fpass.getText();
            String salt = BCrypt.gensalt(12);  // Generate a salt (a random value used during hashing)
            String hashedPwd = Login.hashPassword(Password, salt);
            // Personal info
            String UserID = fid.getText();
            String FirstName = fname.getText();
            String LastName = flast.getText();
            String Email = femail.getText();
            String Tel = ftel.getText();
            // Create New User
            Signup s = new Signup().setUser(Username, hashedPwd, UserID)
                    .setName(FirstName, LastName)
                    .setEmail(Email)
                    .setTel(Tel);

            if (cbopass.isSelected() && !fopass.trim().isEmpty()) {
                String hashedOptPwd = Login.hashPassword(fopass, salt);
                s.setHasOptPwd(true).setOptPwd(hashedOptPwd);
            } else {
                cbopass.setSelected(false);
            }

            User user = s.createUser();

            // No New User
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Retry :D", "Signup Failed", JOptionPane.ERROR_MESSAGE);
                // Store new user data
            } else {
                users.add(user);
                updateUsers();
                if (admin == null) {
                    Main.setUser(user);
                } else {
                    JOptionPane.showMessageDialog(this, "Admin power done :D");
                    updateUserTable(Username);
                    updateAllUserTable();
                }
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (window != null)
                    window.dispose();
            }
        }

        if (e.getSource() == cbopass) {
            fopass = JOptionPane.showInputDialog(this, "Enter optional password:");
        }
    }
}
