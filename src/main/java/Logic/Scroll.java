package Logic;

public class Scroll {
    private User owner;
    private String scrollName, scrollID, date;
    private int uploadCount, downloadCount;

    public Scroll(User owner, String scrollName, String scrollID, String date, int uploadCount, int downloadCount) {
        this.owner = owner;
        this.scrollName = scrollName;
        this.scrollID = scrollID;
        this.date = date;
        this.uploadCount = uploadCount;
        this.downloadCount = downloadCount;
        System.out.println(this);
    }

    public String getScrollID() {
        return scrollID;
    }

    public String getScrollName() {
        return scrollName;
    }

    public User getOwner() {
        return owner;
    }

    public void downloaded() {
        this.downloadCount++;
    }

    public int getUploadCount() {
        return uploadCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public String getDate() {
        return date;
    }

    public void setScrollID(String sid) {
        scrollID = sid;
    }

    public void setScrollName(String sname) {
        scrollName = sname;
    }

    @Override
    public String toString() {
        return scrollName + ", " + scrollID +  ", " + owner.getUsername() + ", " + date + ", " + uploadCount + ", " + downloadCount;
    }
}
