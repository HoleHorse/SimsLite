package ObservSinglStuff;

public interface Observer {
    public void getNotification(String msg);
    public void readNewNotifications();
    public void readOldNotifications();
}