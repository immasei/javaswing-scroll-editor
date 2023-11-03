package GUI.Application;

import Logic.Scroll;
import Logic.User;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static GUI.Application.ProfilePane.updateStats;
import static Logic.ReadWrite.*;

public class ViewScrollPane extends JPanel implements ActionListener {

    private JButton preview, download, upload, edit, remove, rename;
    private JLabel search, scrollName, scrollID;
    private JTextField searchbar, name, id;
    public static User user;
    private JTable table;
    private static DefaultTableModel model;
    private TableRowSorter<TableModel> sortModel;

    public ViewScrollPane(User user) {
        FlatMacDarkLaf.setup();
        setLayout(null);
        this.user = user;

        Font font1 = new Font("Roboto", 1, 17);
        Font font2 = new Font("Roboto", 0, 16);

        preview = new JButton("Preview");
        preview.setBounds(165, 10, 110, 40);
        preview.setBackground(new Color(127, 157, 185));
        preview.setFocusable(false);
        preview.setFont(font1);
        preview.setOpaque(true);
        preview.setBorderPainted(false);
        preview.setForeground(Color.BLACK);
        preview.addActionListener(this);

        search = new JLabel("Search Bar:");
        search.setBounds(25, 70, 200, 40);
        search.setFont(font1);
        search.setForeground(Color.WHITE);

        if (!user.getPrivilege().equals("Guest")) {
            edit = new JButton("Edit");
            edit.setBounds(45, 10, 110, 40);
            edit.setBackground(new Color(146, 157, 190));

            download = new JButton("Save");
            download.setBounds(285, 10, 110, 40);
            download.setBackground(new Color(239, 213, 149));

            upload = new JButton("Upload");
            upload.setBounds(405, 10, 110, 40);
            upload.setBackground(new Color(239, 180, 149));

            remove = new JButton("Remove");
            remove.setBounds(525, 10, 110, 40);
            remove.setBackground(new Color(217, 136, 128));

            //rename section
            rename = new JButton("Rename");
            rename.setBounds(645, 10, 110, 40);
            rename.setBackground(new Color(160, 134, 119));

            scrollName = new JLabel("Name :");
            scrollName.setBounds(387, 70, 100, 40);
            scrollName.setFont(new Font("Roboto", 1, 15));
            scrollName.setForeground(Color.WHITE);

            name = new JTextField();
            name.setBounds(445, 70, 165, 40);
            name.setFont(font2);

            scrollID = new JLabel("ID :");
            scrollID.setBounds(620, 70, 100, 40);
            scrollID.setFont(new Font("Roboto", 1, 15));
            scrollID.setForeground(Color.WHITE);

            id = new JTextField();
            id.setBounds(650, 70, 130, 40);
            id.setFont(font2);

            add(name);
            add(scrollName);
            add(scrollID);
            add(id);

            JButton[] button = {download, upload, edit, remove, rename};
            for (int i = 0; i < button.length; i++) {
                button[i].setFocusable(false);
                button[i].setFont(font1);
                button[i].setOpaque(true);
                button[i].setBorderPainted(false);
                button[i].setForeground(Color.BLACK);
                button[i].addActionListener(this);
                add(button[i]);
            }
        }

        FlatMacDarkLaf.setup();
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Scroll Name");
        model.addColumn("Scroll ID");
        model.addColumn("Username");
        model.addColumn("Date");
        model.addColumn("Uploads");
        model.addColumn("Downloads");

        // Create the JTable
        table = new JTable(model);
        // Resize
        table.setRowHeight(35);
        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(150);
        colModel.getColumn(1).setPreferredWidth(100);
        colModel.getColumn(2).setPreferredWidth(100);
        colModel.getColumn(3).setPreferredWidth(70);
        colModel.getColumn(4).setPreferredWidth(65);
        colModel.getColumn(5).setPreferredWidth(65);

        // Create a scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(20, 140, 760, 510);
        tableScrollPane.setWheelScrollingEnabled(true);
        tableScrollPane.setEnabled(false);

        // Search bar
        sortModel= new TableRowSorter<>(model);
        table.setRowSorter(sortModel);

        searchbar = new JTextField();
        searchbar.setBounds(127, 70, 250, 40);
        searchbar.setFont(font2);
        searchbar.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = searchbar.getText();
                if (text.trim().length() == 0) {
                    sortModel.setRowFilter(null);
                } else {
                    System.out.println(text);
                    sortModel.setRowFilter(RowFilter.regexFilter("(?i)" + text));
//                    sortModel.setSortsOnUpdates(true);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = searchbar.getText();
                if (text.trim().length() == 0) {
                    sortModel.setRowFilter(null);
                } else {
                    sortModel.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        //insert data
        if (user.getPrivilege().equals("Admin")) {
            for (Scroll s : scrolls) {
                model.insertRow(model.getRowCount(), new Object[]{
                        s.getScrollName(),
                        s.getScrollID(),
                        s.getOwner().getUsername(),
                        s.getDate(),
                        s.getUploadCount(),
                        s.getDownloadCount()
                });
            }
        } else {
            for (Scroll s : scrolls) {
                model.insertRow(model.getRowCount(), new Object[]{
                        s.getScrollName(),
                        s.getScrollID(),
                        s.getOwner().getUsername(),
                        s.getDate(),
                        "Admin Only",
                        "Admin Only"
                });
            }
        }

        add(tableScrollPane);

        add(search);
        add(searchbar);
        add(preview);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent x) {
        FlatMacLightLaf.setup();
        if (x.getSource() == preview) {
            int rind = table.getSelectedRow();

            if (rind == -1) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setCurrentDirectory(new File("src/main/resources/scroll"));
                fileChooser.setAcceptAllFileFilterUsed(false);
                Action details = fileChooser.getActionMap().get("viewTypeDetails");
                details.actionPerformed(null);
                //choose .bin file only
                fileChooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".bin");
                    }

                    @Override
                    public String getDescription() {
                        return "Binary Files (*.bin)";
                    }
                });

                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile != null)
                        new PreviewWindow(selectedFile.getName(), false);
                }
            } else {
                String fileName = (String) model.getValueAt(rind, 0);
                //open preview
                new PreviewWindow(fileName, false);
            }
        }

        if (x.getSource() == download) {
            int rind = table.getSelectedRow();

            if (rind == -1) {
                JOptionPane.showMessageDialog(this, "Choose 1 file to download", ":D", JOptionPane.ERROR_MESSAGE);
            } else {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                Action details = fileChooser.getActionMap().get("viewTypeDetails");
                details.actionPerformed(null);
                //choose dir to download
                fileChooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory();
                    }
                    @Override
                    public String getDescription() {
                        return "Download to Dir";
                    }
                });

                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedDir = fileChooser.getSelectedFile();
                    String filename = (String) model.getValueAt(rind, 0);

                    //create dir /scroll in local
                    File localScrollDirectory = new File(selectedDir.getAbsolutePath() + "/scroll");
                    if (!localScrollDirectory.exists()){
                        localScrollDirectory.mkdirs();
                    }
                    //copy source file and destination path
                    Path src = Path.of("src/main/resources/scroll/" + filename);
                    Path des = Path.of(selectedDir.getAbsolutePath() + "/scroll/" + filename);
                    try {
                        Files.copy(src, des);
                        findScroll(filename).downloaded();
                        updateStats(1);
                        updateScrolls("scrollDB.txt");
                        updateScrollTable();
                        JOptionPane.showMessageDialog(this, "Downloaded");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "File exists :(", "Unable to download", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (returnValue == JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(this, "Cancelled");
                }
            }
        }

        if (x.getSource() == upload) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            //choose .bin file only
            fileChooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getName().toLowerCase().endsWith(".bin");
                }

                @Override
                public String getDescription() {
                    return "Binary Files (*.bin)";
                }
            });

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                Path src = Path.of(selectedFile.getAbsolutePath());
                Path des = Path.of("src/main/resources/scroll/" + selectedFile.getName());

                Scroll duplicatedVersion = checkReupScroll(selectedFile.getAbsolutePath());

                //havent upload once
                if (duplicatedVersion == null) {
                    String sid = JOptionPane.showInputDialog(this, "Enter scroll id:");

                    while (sid==null || sid.trim().isEmpty() || !checkUniqueScrollID(sid)) {
                        JOptionPane.showMessageDialog(this, "Scroll ID exists!", "Retry :D", JOptionPane.WARNING_MESSAGE);
                        sid = JOptionPane.showInputDialog(this, "Enter scroll id:");
                    }
                    try {
                        Files.copy(src, des);

                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        Date date = new Date();
                        scrolls.add(new Scroll(user, selectedFile.getName(), sid, dateFormat.format(date), 1, 0));

                        updateStats(0);
                        updateScrolls("scrollDB.txt");
                        updateScrollTable();

                        JOptionPane.showMessageDialog(this, "Uploaded");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Scroll name exists :(", "Unable to upload", JOptionPane.ERROR_MESSAGE);
                    }
                //re-upload a scroll with same content in history
                } else {
                    String sid = JOptionPane.showInputDialog(this, "Enter scroll i8d:");

                    boolean isUnique = true;

                    while (sid==null || sid.trim().isEmpty() || !checkUniqueScrollID(sid)) {
                        isUnique = false;
                        JOptionPane.showMessageDialog(this, "Scroll ID exists!", "Retry :D", JOptionPane.WARNING_MESSAGE);
                        sid = JOptionPane.showInputDialog(this, "Enter scroll id:");

                        //same sid as before but must unique within current scrolls
                        if (sid!=null) {
                            if (checkUniqueScrollID(sid) && findReupScroll(selectedFile.getAbsolutePath(), sid) != null) {
                                try {
                                    Files.copy(src, des);

                                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                    Date date = new Date();
                                    scrolls.add(new Scroll(user, selectedFile.getName(), sid, dateFormat.format(date),
                                            findReupScroll(selectedFile.getAbsolutePath(), sid).getUploadCount() + 1,
                                            findReupScroll(selectedFile.getAbsolutePath(), sid).getDownloadCount()));

                                    updateStats(0);
                                    updateScrolls("scrollDB.txt");
                                    updateScrollTable();

                                    JOptionPane.showMessageDialog(this, "Uploaded");
                                    return;
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(this, "Scroll name exists :(", "Unable to upload", JOptionPane.ERROR_MESSAGE);
                                }
                                //new file with same content and different id
                            } else if (checkUniqueScrollID(sid)) {
                                isUnique = true;
                            }
                        }
                    } if (isUnique) {
                        try {
                            Files.copy(src, des);

                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                            Date date = new Date();
                            scrolls.add(new Scroll(user, selectedFile.getName(), sid, dateFormat.format(date), 1, 0));

                            updateStats(0);
                            updateScrolls("scrollDB.txt");
                            updateScrollTable();
                            JOptionPane.showMessageDialog(this, "Uploaded");
                            return;
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Scroll name exists :(", "Unable to upload", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } else if (returnValue == JFileChooser.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(this, "Cancelled");
            }
        }

        if (x.getSource() == edit) {
            int rind = table.getSelectedRow();

            if (rind == -1) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setCurrentDirectory(new File("src/main/resources/scroll"));
                fileChooser.setAcceptAllFileFilterUsed(false);
                Action details = fileChooser.getActionMap().get("viewTypeDetails");
                details.actionPerformed(null);
                //choose .bin file only
                fileChooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".bin");
                    }

                    @Override
                    public String getDescription() {
                        return "Binary Files (*.bin)";
                    }
                });

                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();
                    User fileOwner = findScroll(fileName).getOwner();
                    //open editor
                    PreviewWindow editor = new PreviewWindow(selectedFile.getName(), user == fileOwner);

                    if (user != fileOwner)
                        JOptionPane.showMessageDialog(editor, "You are not the file owner", "Preview only", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                String fileName = (String) model.getValueAt(rind, 0);
                String fileOwner = (String) model.getValueAt(rind, 2);
                //open editor
                PreviewWindow editor = new PreviewWindow(fileName, fileOwner.equals(user.getUsername()));

                if (!fileOwner.equals(user.getUsername()))
                    JOptionPane.showMessageDialog(editor, "You are not the file owner", "Preview only", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (x.getSource() == remove) {
            int rind = table.getSelectedRow();

            if (rind == -1) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setCurrentDirectory(new File("src/main/resources/scroll"));
                fileChooser.setAcceptAllFileFilterUsed(false);
                Action details = fileChooser.getActionMap().get("viewTypeDetails");
                details.actionPerformed(null);
                //choose .bin file only
                fileChooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".bin");
                    }
                    @Override
                    public String getDescription() {
                        return "Binary Files (*.bin)";
                    }
                });

                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();
                    User fileOwner = findScroll(fileName).getOwner();

                    if (user != fileOwner) {
                        JOptionPane.showMessageDialog(this, "You are not the file owner", "Unable to delete", JOptionPane.ERROR_MESSAGE);
                    } else {
                        //update table view of scroll information
                        Scroll s = findScroll(fileName);
                        if (scrolls.contains(s)) {
                            scrolls.remove(s);
                        }
                        //save in history scroll dir
                        Scroll oldScroll = findOldScroll(fileName);
                        if (oldScroll != null) {
                            archives.remove(oldScroll);
                            new File("src/main/resources/Archive/"+selectedFile.getName()).delete();
                        }
                        archives.add(s);

                        Path src = Path.of(selectedFile.getAbsolutePath());
                        Path des = Path.of("src/main/resources/Archive/"+selectedFile.getName());
                        try {
                            Files.copy(src, des);
                        } catch (Exception e) {
                            ;;
                        }
                        //delete from current scroll dir
                        selectedFile.delete();
                        JOptionPane.showMessageDialog(this, fileName + " deleted :D");
                        //update txt
                        updateScrolls("scrollDB.txt");
                        updateScrolls("archive.txt");
                        updateScrollTable();
                    }
                }
            } else {
                String fileName = (String) model.getValueAt(rind, 0);
                String fileOwner = (String) model.getValueAt(rind, 2);

                if (!fileOwner.equals(user.getUsername())) {
                    JOptionPane.showMessageDialog(this, "You are not the file owner", "Unable to delete", JOptionPane.ERROR_MESSAGE);
                } else {
                    //update table view of scroll information
                    Scroll s = findScroll(fileName);
                    model.removeRow(rind);
                    if (scrolls.contains(s)) {
                        scrolls.remove(s);
                    }
                    Scroll oldScroll = findOldScroll(fileName);
                    if (oldScroll != null) {
                        archives.remove(oldScroll);
                        new File("src/main/resources/Archive/"+oldScroll.getScrollName()).delete();
                    }
                    archives.add(s);

                    Path src = Path.of("src/main/resources/scroll/"+fileName);
                    Path des = Path.of("src/main/resources/Archive/"+fileName);
                    try {
                        Files.copy(src, des);
                    } catch (Exception e) {
                    }
                    //delete from current scroll dir
                    new File("src/main/resources/scroll/"+fileName).delete();
                    JOptionPane.showMessageDialog(this, fileName + " deleted :D");

                    updateScrolls("scrollDB.txt");
                    updateScrolls("Archive.txt");
                    updateScrollTable();
                }
            }
        }

        if (x.getSource() == rename) {
            int rind = table.getSelectedRow();

            if (rind == -1) {
                JOptionPane.showMessageDialog(this, "Choose 1 scroll to change ID and name", "Unable to rename", JOptionPane.ERROR_MESSAGE);
            } else {
                if (name.getText().trim().equals("") && id.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(this, "You haven't insert new name\nor new id into textfield!", "Unable to rename", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String fileName = (String) model.getValueAt(rind, 0);
                String fileID = (String) model.getValueAt(rind, 1);
                String fileOwner = (String) model.getValueAt(rind, 2);

                Scroll s = findScroll(fileName);

                if (!fileOwner.equals(user.getUsername())) {
                    JOptionPane.showMessageDialog(this, "You are not the file owner", "Unable to rename", JOptionPane.ERROR_MESSAGE);
                } else {
                    String newName = name.getText();

                    String nameNew = "";

                    if (newName.endsWith(".bin"))
                        nameNew= newName;
                    else if (!newName.endsWith(".bin") && !newName.contains(".") && !newName.trim().isEmpty())
                        nameNew = newName+".bin";
                    else if (newName.contains(".") || newName.trim().isEmpty() || newName == null)
                        nameNew = fileName;

                    if (!newName.equals(fileName) && !newName.trim().isEmpty()) {
                        if (checkUniqueScrollName(nameNew)) {
                            s.setScrollName(nameNew);

                            //copy source file and destination path
                            Path src = Path.of("src/main/resources/scroll/" + fileName);
                            Path des = Path.of("src/main/resources/scroll/" + nameNew);
                            try {
                                Files.copy(src, des);
                                new File("src/main/resources/scroll/" + fileName).delete();
                                updateScrolls("scrollDB.txt");
                                updateScrollTable();
                                JOptionPane.showMessageDialog(this, "Rename scroll name successful");
                            } catch (Exception e) {
                                ;;
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Scroll name exists", "Unable to rename", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    String newID = id.getText();
                    if (!newID.isEmpty() && newID != null && !newID.equals(fileID)) {
                        if (checkUniqueScrollID(newID)) {
                            s.setScrollID(newID);
                            JOptionPane.showMessageDialog(this, "Rename scroll ID successful");
                        } else {
                            JOptionPane.showMessageDialog(this, "Scroll ID exists", "Unable to rename ID", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    updateScrolls("scrollDB.txt");
                    updateScrollTable();
                }
            }
        }
    }

    public static void updateScrollTable() {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        if (user.getPrivilege().equals("Admin")) {
            for (Scroll s : scrolls) {
                model.insertRow(model.getRowCount(), new Object[]{
                        s.getScrollName(),
                        s.getScrollID(),
                        s.getOwner().getUsername(),
                        s.getDate(),
                        s.getUploadCount(),
                        s.getDownloadCount()
                });
            }
        } else {
            for (Scroll s : scrolls) {
                model.insertRow(model.getRowCount(), new Object[]{
                        s.getScrollName(),
                        s.getScrollID(),
                        s.getOwner().getUsername(),
                        s.getDate(),
                        "Admin Only",
                        "Admin Only"
                });
            }
        }
        model.fireTableDataChanged();
    }
}