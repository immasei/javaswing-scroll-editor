package GUI.Application;

import Logic.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

import static Logic.ReadWrite.users;

public class ListUserPane extends JPanel {
    private JTable table;
    private static DefaultTableModel model;

    public ListUserPane() {
        setLayout(new BorderLayout());

        //  Create the table model (not editable)
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Username");
        model.addColumn("UserID");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Email");
        model.addColumn("Telephone");
        model.addColumn("Privilege");

        // Create the JTable
        table = new JTable(model);
        // Resize
        table.setRowHeight(35);
        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(70);
        colModel.getColumn(1).setPreferredWidth(70);
        colModel.getColumn(2).setPreferredWidth(100);
        colModel.getColumn(3).setPreferredWidth(100);
        colModel.getColumn(4).setPreferredWidth(120);
        colModel.getColumn(5).setPreferredWidth(120);
        colModel.getColumn(6).setPreferredWidth(40);

        // Create a scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(30, 20, 720, 400);
        tableScrollPane.setWheelScrollingEnabled(true);
        tableScrollPane.setEnabled(false);

        //insert data
        for (User u : users) {
            model.insertRow(model.getRowCount(), new Object[] {
                    u.getUsername(),
                    u.getUserid(),
                    u.getFirstname(),
                    u.getLastname(),
                    u.getEmail(),
                    u.getTel(),
                    u.getPrivilege()
            });
        }

        add(tableScrollPane);
    }

    public static void updateAllUserTable() {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        for (User u : users) {
            model.insertRow(model.getRowCount(), new Object[] {
                    u.getUsername(),
                    u.getUserid(),
                    u.getFirstname(),
                    u.getLastname(),
                    u.getEmail(),
                    u.getTel(),
                    u.getPrivilege()
            });
        }
        model.fireTableDataChanged();
    }

}
