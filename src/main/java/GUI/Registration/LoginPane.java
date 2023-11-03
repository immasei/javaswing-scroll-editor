package GUI.Registration;

import Logic.Login;
import Logic.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static GUI.Main.setUser;
import static Logic.ReadWrite.findUser;

public class LoginPane extends JPanel implements ActionListener {
    private JPasswordField password, fopass;
    private JTextField username;
    private JLabel pass, user, wall;
    private JButton signin, guest;

    private JLabel opass;
    private JCheckBox cbopass;

    private boolean is_opass_enabled = false;

    public LoginPane() {
        setLayout(null);

        Font font1 = new Font("Roboto", 1, 17);
        Font font2 = new Font("Roboto", 0, 16);

        wall = new JLabel("Welcome back!", SwingConstants.CENTER);
        wall.setBounds(348, 0, 360, 800);
        wall.setBackground(new Color(41, 41, 41));
        wall.setOpaque(true);
        wall.setFont(new Font("Roboto", 1, 33));
        wall.setForeground(Color.WHITE);

        user = new JLabel("User Name");
        user.setBounds(30, 20, 100, 40);
        user.setFont(font1);

        pass = new JLabel("Password");
        pass.setBounds(30, 120, 100, 40);
        pass.setFont(font1);

        username = new JTextField();
        username.setBounds(35, 70, 270, 40);
        username.setFont(font2);

        password = new JPasswordField();
        password.setBounds(35, 170, 270, 40);
        password.setFont(font2);

        cbopass = new JCheckBox("");
        cbopass.setBounds(27, 220, 30, 40);
        cbopass.setFont(font1);

        opass = new JLabel("Optional Password");
        opass.setBounds(50, 214, 200, 50);
        opass.setFont(new Font("Consolas", 1, 12));
        opass.setForeground(new Color(255, 86, 80));

        fopass = new JPasswordField();
        fopass.setBounds(35, 264, 270, 40);
        fopass.setFont(font2);
        fopass.setVisible(false);

        signin = new JButton("Sign in");
        signin.setBounds(55, 340, 230, 40);
        signin.setBackground(new Color(108, 151, 194));
        signin.setBorderPainted(false);
        signin.setFont(font1);

        guest = new JButton("Guest");
        guest.setBounds(55, 410, 230, 40);
        guest.setFont(new Font("Consolas", 0, 16));
        guest.setBackground(new Color(212, 81, 76));
        guest.setBorderPainted(false);
        guest.setFont(font1);

        add(user);
        add(pass);
        add(username);
        add(password);
        add(signin);
        add(guest);
        add(wall);

        add(opass);
        add(cbopass);
        add(fopass);

        signin.addActionListener(this);
        guest.addActionListener(this);
        cbopass.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent x) {
        String msg;
        if (x.getSource() == signin){

            String Username = username.getText();

            char[] passwordChars = password.getPassword();
            String passwordString = new String(passwordChars); // Convert the char[] to a String
            Arrays.fill(passwordChars, ' '); // Clear the password from memory for security

            String optPwdString = "";
            if (cbopass.isSelected()) {
                char[] optPwdChars = fopass.getPassword();
                optPwdString = new String(optPwdChars); // Convert the char[] to a String
                Arrays.fill(optPwdChars, ' '); // Clear the password from memory for security
            }

            Boolean pass_pwd = Login.verifyPassword(passwordString, Username);
            Boolean pass_opt_pwd = !Login.checkIfOptPwdExist(Username) || Login.verifyOptPassword(optPwdString, Username);

            if (pass_pwd && pass_opt_pwd){
                System.out.println("Welcome Back " + Username + " :D!");
                // login successful, set current user in Main
                setUser(findUser(Username));
                // close window
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (window != null)
                    window.dispose();

            }else{
                msg = "invalid combination :( ";
                JOptionPane.showMessageDialog(this, msg, "Login Failed", JOptionPane.ERROR_MESSAGE);
                // Clear the input fields
                username.setText("");
                password.setText("");
            }

        } else if (x.getSource() == guest) {
            setUser(new User());
            JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (window != null)
                window.dispose();
        } else if (x.getSource() == cbopass){
            fopass.setVisible(cbopass.isSelected());
            is_opass_enabled = cbopass.isSelected();
        }
    }
}
