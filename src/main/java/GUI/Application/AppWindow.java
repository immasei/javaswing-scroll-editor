package GUI.Application;

import Logic.User;
import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {

    User user;
    static JTabbedPane tabbedPane;
    ProfilePane profile;
    ListUserPane list;
    ViewScrollPane view;

    public AppWindow(User user) {
        this.user = user;
        setTitle(user.getUsername() + " ~ " + user.getPrivilege());
        setSize(800, 720);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        //NOT GUEST
        if (!user.getPrivilege().equals("Guest")) {
            //PROFILE
            profile = new ProfilePane(user);
            profile.setBackground(new Color(41, 41, 41));
            tabbedPane.addTab("User Profile", profile);
        }

        //ADMIN ONLY
        if (user.getPrivilege().equals("Admin")) {
            list = new ListUserPane();
            tabbedPane.addTab("View users", list);
        }

        view = new ViewScrollPane(user);
        view.setBackground(new Color(41, 41, 41));
        tabbedPane.addTab("View Scrolls", view);

        //ADMIN ONLY
        if (user.getPrivilege().equals("Admin")) {
            //ARCHIVE
            JButton archiveButton = new JButton("Archive Scrolls");
            archiveButton.addActionListener(e -> {
                ArchivePane.archiveScrolls();
                JOptionPane.showMessageDialog(null, "Scrolls have been archived!");
            });
            tabbedPane.addTab("Archive Scrolls", archiveButton);
        }

        tabbedPane.setFont(new Font("Consolas", 1, 19));

        getContentPane().add(tabbedPane);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
