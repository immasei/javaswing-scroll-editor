package GUI.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Logic.ReadWrite.readScroll;
import static Logic.ReadWrite.writeScroll;

public class PreviewWindow extends JFrame {

    private JTextArea text;
    private boolean isOwner;
    private JLabel time;

    public PreviewWindow(String path, boolean isOwner) {
        this.isOwner = isOwner;
        setTitle(path);
        setSize(800, 720);
        setResizable(false);
        setLayout(null);

        List<String> content = readScroll(path, "curr");

        text = new JTextArea(String.join("\n", content));
        text.setFont(new Font("Roboto", 0, 16));

        JScrollPane scrollPane = new JScrollPane(text);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setEnabled(false);

        if (!isOwner) { //preview only
            scrollPane.setBounds(1, 50, 798, 640);
            //2 mins timer
            time = new JLabel();
            time.setBounds(10, 5, 500, 40);
            time.setFont(new Font("Consolas", 1, 17));
            time.setForeground(Color.BLACK);
            add(time);

            Timer timer = new Timer(1000, new ActionListener() {
                private int count = 120;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (count <= 0) {
                        ((Timer)e.getSource()).stop();
                        close();
                    } else {
                        int min = count/60;
                        int sec = count - min*60;
                        time.setText(String.format("Preview closed in: %02d:%02d", min, sec));
                        count--;
                    }
                }
            });
            timer.start();

            text.setEditable(false);
        } else { //owner edit scroll
            scrollPane.setBounds(1, 1, 798, 690);
            text.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    int caretPosition = text.getCaretPosition();

                    //ignore arrow key
                    if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                        System.out.println(e.getKeyCode());
                        return;
                    }
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT|| e.getKeyCode() == KeyEvent.VK_ENTER) {
                        System.out.println(e.getKeyCode());
                        return;
                    }

                    //can only input 1 and 0
                    if (e.getKeyCode() != KeyEvent.VK_0 || e.getKeyCode() != KeyEvent.VK_1) {
                        String input = text.getText();
                        String[] ls = input.split("");

                        int cursor = 0;
                        for (int i = 0; i < ls.length; i++) {
                            //remove
                            if (!ls[i].equals("0") && !ls[i].equals("1") && !ls[i].equals("\n")) {
                                ls[i] = "";
                                cursor = 1;
                            }
                        }
                        List<String> list = Arrays.asList(ls);
                        //rewrite textfield
                        text.setText(String.join("", list));
                        //reset cursor
                        try {
                            text.setCaretPosition(caretPosition - cursor);
                            text.requestFocus();
                        }catch (IllegalArgumentException ex) {
                            ;;
                        }
                        writeScroll(path, String.join("", list));
                    } else {
                        writeScroll(path, text.getText());
                    }
                }
            });
        }

        add(scrollPane);
        setVisible(true);
    }

    public void close() {
        this.setVisible(false);
        this.dispose();
    }
}