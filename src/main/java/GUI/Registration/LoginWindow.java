package GUI.Registration;

import Logic.User;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {

    LoginPane login;
    SignupPane signup;
    JTabbedPane tabbedPane;

    public LoginWindow(User user) {
        FlatLightLaf.setup();
        setTitle("CC-A24-G02-Ankit");
        setSize(699, 550);
        setResizable(false);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        //not admin adding pp
        if (user == null) {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            login = new LoginPane();
            tabbedPane.addTab("                       Log in                     ", login);
        }

        signup = new SignupPane(user);
        tabbedPane.addTab("                      Sign up                    ", signup);

        tabbedPane.setFont(new Font("Consolas", 1, 19));
        getContentPane().add(tabbedPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}