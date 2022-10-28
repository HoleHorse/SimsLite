package Characters;

import RadioState.OffState;
import RadioState.Radio;
import WorkBehaviours.WorkBehaviour;

import java.util.ArrayList;

public class Subscriber implements Observer {
    private String username;
    private ArrayList<String> unread = new ArrayList<>();
    private ArrayList<String> read = new ArrayList<>();
    private WorkBehaviour workBehaviour;
    private int savings;
    private boolean isSub;
    public Radio radio = new Radio(new OffState());

    public Subscriber(String username, WorkBehaviour workBehaviour) {
        this.username = username;
        this.workBehaviour = workBehaviour;
        this.savings = 10000;
    }

    public String work() {
        this.setSavings(getSavings() + this.workBehaviour.getPaid());
        return this.workBehaviour.work();
    }

    @Override
    public void getNotification(String msg) {
        unread.add(msg);
    }

    @Override
    public ArrayList<String> readNewNotifications() {
        this.read.addAll(unread);
        this.unread.removeAll(unread);
        return read;
    }

    @Override
    public ArrayList<String> readOldNotifications() {
        return read;
    }

    public void subscribe(Acc acc) {
        acc.subscribe(this);
        this.isSub = true;
    }

    public void unsubscribe(Acc acc) {
        acc.unsubscribe(this);
        this.isSub = false;
    }

    public String getUsername() {
        return username;
    }

    public void setWorkBehaviour(WorkBehaviour workBehaviour) {
        this.workBehaviour = workBehaviour;
    }

    public int getSavings() {
        return savings;
    }

    public void setSavings(int savings) {
        this.savings = savings;
    }

    public boolean isSub() {
        return isSub;
    }

    public ArrayList<String> getRead() {
        return read;
    }

    public ArrayList<String> getUnread() {
        return unread;
    }

    public void setRead(ArrayList<String> read) {
        this.read = read;
    }

    public void setUnread(ArrayList<String> unread) {
        this.unread = unread;
    }

    public WorkBehaviour getWorkBehaviour() {
        return workBehaviour;
    }

    public void setSub(boolean sub) {
        this.isSub = sub;
    }
}
