package GUI;

import GUI.Application.AppWindow;
import GUI.Registration.LoginWindow;
import Logic.Scroll;
import Logic.User;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static Logic.ReadWrite.*;


public class Main {
    public static User curr;

    public static void main(String[] args) {
        setupUsers();
        setupScrolls("scrollDB.txt");
        setupScrolls("archive.txt");
        System.out.println("\ncurrent scrolls in list scrolls");
        for (Scroll scroll:scrolls){
            System.out.println(scroll.toString());
        }

        LoginWindow login = new LoginWindow(null);
        login.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent we) {
                AppWindow app = new AppWindow(curr);
            }
        });
    }

    public static void setUser(User user) {
        curr = user;
    }
}
