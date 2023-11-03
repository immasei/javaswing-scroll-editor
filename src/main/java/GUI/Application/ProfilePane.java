package GUI.Application;


import GUI.Registration.LoginWindow;
import Logic.User;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static GUI.Application.AppWindow.tabbedPane;
import static GUI.Application.ListUserPane.updateAllUserTable;
import static Logic.ReadWrite.*;

public class ProfilePane extends JPanel implements ActionListener {

    private User user;
    private static DefaultTableModel model;
    private static JTable table;

    private JTextField fid, fpass, uname, fname, flast, femail, ftel, usernameField;
    private JLabel pass, userid, name, email, first, last, tel, spectatorMode;
    private JButton save, view, edit, add, delete, spectator;
    private static JLabel totalUploads, totalDownloads;

    public ProfilePane(User user) {
        this.user = user;
        setLayout(null);

        Font font1 = new Font("Roboto", 1, 17);
        Font font2 = new Font("Roboto", 0, 16);
        System.out.println(": " + user);

        //NOT GUEST
        if (!user.getPrivilege().equals("Guest")) {

            //1 PERSONAL INFO && CREDENTIAL
            userid = new JLabel("User ID");
            userid.setBounds(50, 296, 100, 40);
            userid.setFont(font1);
            userid.setForeground(Color.WHITE);
            fid = new JTextField(user.getUserid());
            fid.setBounds(55, 344, 270, 35);
            fid.setFont(font2);

            pass = new JLabel("Password");
            pass.setBounds(50, 386, 100, 40);
            pass.setFont(font1);
            pass.setForeground(Color.WHITE);
            fpass = new JTextField();
            fpass.setBounds(55, 434, 270, 35);
            fpass.setFont(font2);

            name = new JLabel("User Name");
            name.setBounds(50, 476, 100, 40);
            name.setFont(font1);
            name.setForeground(Color.WHITE);
            uname = new JTextField(user.getUsername());
            uname.setBounds(55, 524, 270, 35);
            uname.setFont(font2);

            first = new JLabel("First Name");
            first.setBounds(50, 10, 100, 40);
            first.setFont(font1);
            first.setForeground(Color.WHITE);
            fname = new JTextField(user.getFirstname());
            fname.setBounds(55, 60, 270, 35);
            fname.setFont(font2);

            last = new JLabel("Last Name");
            last.setBounds(50, 105, 100, 40);
            last.setFont(font1);
            last.setForeground(Color.WHITE);
            flast = new JTextField(user.getLastname());
            flast.setBounds(155, 110, 170, 35);
            flast.setFont(font2);

            tel = new JLabel("Tel");
            tel.setBounds(50, 155, 100, 40);
            tel.setFont(font1);
            tel.setForeground(Color.WHITE);
            ftel = new JTextField(String.valueOf(user.getTel()));
            ftel.setBounds(90, 160, 235, 35);
            ftel.setFont(font2);

            email = new JLabel("Email");
            email.setBounds(50, 203, 100, 40);
            email.setFont(font1);
            email.setForeground(Color.WHITE);
            femail = new JTextField(user.getEmail());
            femail.setBounds(55, 251, 270, 35);
            femail.setFont(font2);

            save = new JButton("Save Changes");
            save.setBounds(55, 590, 270, 40);
            save.setFont(new Font("Consolas", 0, 16));
            save.setBackground(new Color(217, 136, 128));
            save.setBorderPainted(false);
            save.setFont(font1);

            add(userid);
            add(pass);
            add(fid);
            add(fpass);

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

            add(save);
            save.addActionListener(this);

            //2 PROFILE IMG
            ImageIcon originalIcon = new ImageIcon(user.getPhoto());
            JLabel profileImageLabel = new JLabel();
            profileImageLabel.setBounds(400, 100, 200, 200);
            // Scale the original image to fit within the JLabel's dimensions.
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            // Set the scaled image to the JLabel.
            profileImageLabel.setIcon(scaledIcon);
            // Add the JLabel to the JFrame.
            add(profileImageLabel);
            profileImageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("src/main/resources/photo"));
                    int returnValue = fileChooser.showOpenDialog(null);

                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        // Get the selected file.
                        File selectedFile = fileChooser.getSelectedFile();
                        // Load the selected image file.
                        ImageIcon newImageIcon = new ImageIcon(selectedFile.getAbsolutePath());
                        // Scale the new image to fit within the JLabel's dimensions.
                        Image newImage = newImageIcon.getImage();
                        Image scaledNewImage = newImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        ImageIcon scaledNewIcon = new ImageIcon(scaledNewImage);
                        // Update the profile image with the new image.
                        profileImageLabel.setIcon(scaledNewIcon);
                        user.setPhoto("src/main/resources/photo/" + selectedFile.getName());
                        updateUsers();
                    }
                }
            });
        }

        //3 ADMIN PRIVILEGE
        if (user.getPrivilege().equals("Admin")) {

            //TOTAL SCROLL UPLOADS
            totalUploads = new JLabel("Total Uploads: " + readScrollStats("scrollStats.txt").get(0));
            totalUploads.setBounds(400, 330, 300, 50);
            totalUploads.setFont(new Font("Consolas", 1, 15));
            totalUploads.setForeground(new Color(191, 200, 212));

            //TOTAL SCROLL DOWNLOADS
            totalDownloads = new JLabel("Total Downloads: " + readScrollStats("scrollStats.txt").get(1));
            totalDownloads.setBounds(400, 370, 300, 50);
            totalDownloads.setFont(new Font("Consolas", 1, 15));
            totalDownloads.setForeground(new Color(191, 200, 212));

            //spectator name field
            usernameField = new JTextField("Enter user name");
            usernameField.setBounds(400, 458, 187, 35);
            usernameField.setFont(font2);

            //spectator label
            spectatorMode = new JLabel("SPECTATOR mode: ");
            spectatorMode.setBounds(400, 415, 150, 50);
            spectatorMode.setFont(new Font("Consolas", 1, 12));
            spectatorMode.setForeground(new Color(160, 215, 154));

            view = new JButton("View");
            view.setBounds(520, 540, 110, 40);
            view.setBackground(new Color(127, 157, 185));

            add = new JButton("Add");
            add.setBounds(400, 540, 110, 40);
            add.setBackground(new Color(239, 213, 149));

            delete = new JButton("Delete");
            delete.setBounds(400, 590, 110, 40);
            delete.setBackground(new Color(239, 180, 149));

            edit = new JButton("Admin");
            edit.setBounds(520, 590, 110, 40);
            edit.setBackground(new Color(146, 157, 190));

            Icon icon = new ImageIcon("src/main/resources/photo/spy.png");
            Image resize = ((ImageIcon) icon).getImage().getScaledInstance(27, 27,  java.awt.Image.SCALE_SMOOTH);

            spectator = new JButton(new ImageIcon(resize));
            spectator.setBounds(595, 458, 35, 35);
            spectator.setBackground(new Color(192, 215, 154));

            JButton[] button = {view, add, delete, edit, spectator};
            for (int i = 0; i < button.length; i++) {
                button[i].setFocusable(false);
                button[i].addActionListener(this);
                button[i].setFont(font1);
                button[i].setOpaque(true);
                button[i].setBorderPainted(false);
            }

            //TABLE OF USERS
            FlatMacDarkLaf.setup();
            model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            table = new JTable(model);
            table.setRowHeight(35);
            // add columns
            model.addColumn("User name");        //0
            // insert user data
            for (User u : users) {
                model.insertRow(model.getRowCount(), new Object[]{
                        u.getUsername(),
                });
            }
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnModel colModel = table.getColumnModel();
            colModel.getColumn(0).setPreferredWidth(153);

            JScrollPane tabScroll = new JScrollPane(table);

            tabScroll.setBounds(640, 20, 155, 615);
            tabScroll.setWheelScrollingEnabled(true);
            tabScroll.setEnabled(false);

            add(tabScroll);
            add(view);
            add(add);
            add(delete);
            add(edit);
            add(totalUploads);
            add(totalDownloads);
            add(usernameField);
            add(spectator);
            add(spectatorMode);
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent x) {
        FlatMacLightLaf.setup();
        // save changes to info & cred
        if (x.getSource() == save) {
            // Credentials
            String Username = uname.getText();
            String Password = fpass.getText();
            // Personal info
            String UserID = fid.getText();
            String FirstName = fname.getText();
            String LastName = flast.getText();
            String Email = femail.getText();
            String Tel = ftel.getText();

            if (FirstName.trim().isEmpty())
                JOptionPane.showMessageDialog(this, "First name can't be empty", "Unable to change", JOptionPane.ERROR_MESSAGE);
            try {
                Integer.parseInt(Tel);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Telephone is an int", "Unable to change", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (Email.trim().isEmpty() || Email.contains(" ") || !Email.contains("@")) {
                JOptionPane.showMessageDialog(this, "Invalid email", "Unable to change", JOptionPane.ERROR_MESSAGE);
            } else if (!UserID.equals(user.getUserid()) && !checkUniqueID(UserID)) {
                JOptionPane.showMessageDialog(this, "UserID exists", "Unable to change", JOptionPane.ERROR_MESSAGE);
            } else if (!Username.equals(user.getUsername()) && !checkUniqueUserName(Username)) {
                JOptionPane.showMessageDialog(this, "Username exists", "Unable to change", JOptionPane.ERROR_MESSAGE);
            } else {
                user.setFirstname(FirstName);
                user.setLastname(LastName);
                user.setEmail(Email);
                user.setTel(Integer.parseInt(Tel));
                user.setUserid(UserID);
                user.setUsername(Username);
                if (!Password.trim().isEmpty()) {
                    user.setUserpass(Password);
                }

                updateUsers();
                if (user.getPrivilege().equals("Admin"))
                    updateAllUserTable(); //update table view in view all users
                JOptionPane.showMessageDialog(this, "Change confirmed :D");
            }
            System.out.println(user);
        }

        //add normal user (basically another sign up from admin window)
        if (x.getSource() == add) {
            new LoginWindow(user);
            updateAllUserTable();
        }

        //make someone an admin
        if (x.getSource() == edit) {
            int rind = table.getSelectedRow();

            if (rind != -1) {
                User u = findUser((String) model.getValueAt(rind, 0));
                u.setPrivilege(0);
                JOptionPane.showMessageDialog(this, "New admin: " + u.getUsername() + " :D");
                updateUsers();
                updateAllUserTable();
            } else {
                JOptionPane.showMessageDialog(this, "Choose 1 user first", "Unable to create admin", JOptionPane.ERROR_MESSAGE);
            }
        }

        //delete users
        if (x.getSource() == delete) {
            int rind = table.getSelectedRow();

            if (rind != -1) {
                User u = findUser((String) model.getValueAt(rind, 0));
                model.removeRow(rind);
                if (users.contains(u)) {
                    users.remove(u);
                }
                JOptionPane.showMessageDialog(this, "Deleted: " + u.getUsername() + " D:");
                updateUsers();
                updateAllUserTable(); //update table view in view all users
            } else {
                JOptionPane.showMessageDialog(this, "Choose 1 user to delete", "Unable to delete", JOptionPane.ERROR_MESSAGE);
            }
        }

        //view all users profile
        if (x.getSource() == view) {
            System.out.println(123);
            tabbedPane.setSelectedIndex(1);
        }

        // admin in spectator mode
        if (x.getSource() == spectator) {
            String newUsername = usernameField.getText().trim();
            User newUser = findUser(newUsername);

            if (newUser != null) {
                // A new AppWindow will pop up as the specified user that admin choose
                new AppWindow(newUser);
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
                if (window != null)
                    window.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username not found. Please enter\na valid username.", "Unable to enter as Spectator", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public static void updateUserTable(String usrname) {
        model.addRow(new Object[]{usrname});
        model.fireTableDataChanged();
    }

    public static void updateStats(int type) {
        String[] currUp = totalUploads.getText().split("\\s+");
        String[] currDown = totalDownloads.getText().split("\\s+");
        int uploadsCount = Integer.parseInt(currUp[currUp.length-1]);
        int downloadsCount = Integer.parseInt(currDown[currDown.length-1]);
        if (type == 0) {
            uploadsCount++;
            totalUploads.setText("Total Uploads: " + uploadsCount);
        } else {
            downloadsCount++;
            totalDownloads.setText("Total Downloads: " + downloadsCount);
        }
        writeScrollStats("scrollStats.txt", uploadsCount + ", " + downloadsCount);
    }

    public static void writeScrollStats(String filename, String data) {
        try {
            String filepath = "src/main/resources/";
            FileWriter f = new FileWriter(filepath + filename);
            f.write(data);
            f.close();
        }catch (IOException e) {
            System.out.println("Error");
        }
    }

    //re-add* due to ed 781
    public static java.util.List<Integer> readScrollStats(String filename) {
        List<Integer> content = new ArrayList<>();
        try {
            String filepath = "src/main/resources/";
            File f = new File(filepath + filename);
            Scanner sc = new Scanner(f);

            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals("")) {
                    continue;
                }
                String[] arr = line.split(",");
                content.add(Integer.parseInt(arr[0].trim()));
                content.add(Integer.parseInt(arr[1].trim()));
            }
            return content;
        } catch (FileNotFoundException e) {
            System.out.println("No such file");
            return null;
        }
    }
}