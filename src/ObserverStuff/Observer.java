package ObserverStuff;

public interface Observer {
    public void getNotification(String msg);
    public void readNewNotifications();
    public void readOldNotifications();
}
