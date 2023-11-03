package Logic;


import java.io.*;
import java.util.*;
import java.util.List;

public class ReadWrite {
    public static List<User> users;
    public static List<Scroll> scrolls;
    public static List<Scroll> archives;

    public static String Reader(String username) { // for checking pwd
        try {
            String filepath = "src/main/resources/membershipDB.txt";
            File f = new File(filepath);
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals("")) {
                    continue;
                }
                String[] arr = line.split(",");
                String user = arr[0].trim();
                String pass = arr[1].trim();
                if (username.equals(user)){
                    return pass;
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            return "no such file";
        }
    }

    public static String ReaderOptPwd(String username) { // for checking opt pwd
        try {
            String filepath = "src/main/resources/membershipDB.txt";
            File f = new File(filepath);
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals("")) {
                    continue;
                }
                String[] arr = line.split(",");
                String user = arr[0].trim();
                if (username.equals(user)){
                    String optpass = arr[10].trim();
                    return optpass;
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            return "no such file";
        }
    }

    public static boolean ReaderOptEnabled(String username) {
        try {
            String filepath = "src/main/resources/membershipDB.txt";
            File f = new File(filepath);
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals("")) {
                    continue;
                }
                String[] arr = line.split(",");
                String user = arr[0].trim();

                if (username.equals(user)){
                    Boolean hasoptpass = Boolean.parseBoolean(arr[9].trim());
                    return hasoptpass;
                }
            }
            return false;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static boolean checkUniqueID(String uid) {
        for (User user: users) {
            if (user.getUserid().equals(uid))
                return false;
        }
        return true;
    }

    public static boolean checkUniqueUserName(String uname) {
        for (User user: users) {
            if (user.getUsername().equals(uname))
                return false;
        }
        return true;
    }

    public static boolean checkUniqueScrollID(String sid) {
        for (Scroll scroll: scrolls) {
            if (scroll.getScrollID().equals(sid))
                return false;
        }
        return true;
    }

    public static boolean checkUniqueScrollName(String sname) {

        for (Scroll scroll: scrolls) {
            if (scroll.getScrollName().equals(sname))
                return false;
        }
        return true;
    }

    public static void setupUsers() {
        List<User> all = new ArrayList<>();
        try {
            String filepath = "src/main/resources/membershipDB.txt";
            File f = new File(filepath);
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals("")) {
                    continue;
                }
                String[] arr = line.split(",");
                // credentials
                String username = arr[0].trim();
                String password = arr[1].trim();
                // personal info
                String userid = arr[2].trim();
                String firstname = arr[3].trim();
                String lastname = arr[4].trim();
                String email = arr[5].trim();
                int tel = Integer.parseInt(arr[6].trim());
                int privilege = Integer.parseInt(arr[7].trim());
                String photo = arr[8].trim();

                Boolean has_opt_pwd = Boolean.parseBoolean(arr[9].trim());

                if (has_opt_pwd) {
                    String opt_pwd = arr[10].trim();
                    all.add(new User(username, password, userid, firstname, lastname, email, tel, privilege, photo, has_opt_pwd, opt_pwd));
                } else {
                    all.add(new User(username, password, userid, firstname, lastname, email, tel, privilege, photo, has_opt_pwd, null));
                }
            }
            users = all;
        } catch (FileNotFoundException e) {
        }
    }

    public static User findUser(String uname) {
        for (User account: users) {
            if (account.getUsername().equals(uname))
                return account;
        }
        return null;
    }

    public static Scroll findScroll(String scrollname) {
        for (Scroll scroll: scrolls) {
            if (scroll.getScrollName().equals(scrollname))
                return scroll;
        }
        return null;
    }

    public static Scroll findOldScroll(String scrollname) {
        for (Scroll scroll: archives) {
            if (scroll.getScrollName().equals(scrollname))
                return scroll;
        }
        return null;
    }

    public static void updateUsers() {
        try {
            File f = new File("src/main/resources/membershipDB.txt");
            PrintWriter w = new PrintWriter(f);

            for (User user: users) {
                w.println(user.toString());
            }
            w.close();
        } catch (FileNotFoundException e) {
        }
    }

    public static List<String> readScroll(String filename, String dir) { //scroll content
        List<String> content = new ArrayList<>();
        try {
            String filepath;
            if (dir.equals("curr"))
                filepath = "src/main/resources/scroll/";
            else if (dir.equals("old"))
                filepath = "src/main/resources/Archive/";
            else
                filepath = "";

            File f = new File(filepath+filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                content.add(line);
            }
            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeScroll(String filename, String content) { //scroll content
        try {
            String filepath = "src/main/resources/scroll/";
            FileWriter f = new FileWriter(filepath + filename);
            f.write(content);
            f.close();
        }catch (IOException e) {
        }
    }

    public static void setupScrolls(String filename) { //scroll info
        List<Scroll> all = new ArrayList<>();
        try {
            String filepath = "src/main/resources/";
            File f = new File(filepath+filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals("")) {
                    continue;
                }
                String[] arr = line.split(",");

                String scrollname = arr[0].trim();
                String scrollid = arr[1].trim();
                String username = arr[2].trim();    //owner
                String date = arr[3].trim();
                int uploadCount = Integer.parseInt(arr[4].trim());
                int downloadCount = Integer.parseInt(arr[5].trim());

                all.add(new Scroll(findUser(username), scrollname, scrollid, date, uploadCount, downloadCount));
            }
            if (filename.equals("scrollDB.txt"))
                scrolls = all;
            else
                archives = all;
        } catch (FileNotFoundException e) {
        }
    }

    public static void updateScrolls(String filename) { //scroll info
        try {
            File f = new File("src/main/resources/" + filename);
            PrintWriter w = new PrintWriter(f);

            if (filename.equals("scrollDB.txt")) {
                for (Scroll scroll : scrolls) {
                    w.println(scroll.toString());
                }
            } else {
                for (Scroll scroll : archives) {
                    w.println(scroll.toString());
                }
            }
            w.close();
        } catch (FileNotFoundException e) {
        }
    }


    public static Scroll checkReupScroll(String filename) {
        for (Scroll scroll: archives) {
            if (readScroll(scroll.getScrollName(), "old") != null && readScroll(filename, "abs") != null) {
                if (readScroll(scroll.getScrollName(), "old").equals(readScroll(filename, "abs")))
                    return scroll;
            }
        }
        return null;
    }

    public static Scroll findReupScroll(String filename, String sid) {
        boolean found = false;
        Scroll s = null;
        for (Scroll scroll: archives) {
            if (readScroll(scroll.getScrollName(), "old").equals(readScroll(filename, "abs")) && sid.equals(scroll.getScrollID())) {
                if (s != null) {
                    if ((scroll.getUploadCount() > s.getUploadCount()))
                        s = scroll;
                } else {
                    s = scroll;
                    found = true;
                }
            }
        }
        if (!found)
            return null;
        else
            return s;
    }
}
