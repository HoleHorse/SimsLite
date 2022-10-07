import java.util.ArrayList;

public class Subscriber implements Observer {
	private String username;
	private ArrayList<String> unread = new ArrayList<>();
	private ArrayList<String> read = new ArrayList<>();
	private WorkBehaviour workBehaviour;
	private int savings;
	
	public Subscriber(String username, WorkBehaviour workBehaviour) {
		this.username = username;
		this.workBehaviour = workBehaviour;
		this.savings = 10000;
	}
	
	public void work() {
		this.setSavings(getSavings()+this.workBehaviour.work());
	}

	@Override
	public void getNotification(String msg) {
		unread.add(msg);
	}
	
	@Override
	public void readNewNotifications() {
		for (String i : unread) {
			System.out.println(this.getUsername() + ",");
			System.out.println(i);
		}
		this.read.addAll(unread);
		this.unread.removeAll(unread);
	}

	@Override
	public void readOldNotifications() {
		for (String i : read) {
			System.out.println(this.getUsername() + ", " + i);
		}
	}
	
	public void subscribe(InstaAcc acc) {
		acc.subscribe(this);
	}
	
	public void unsubscribe(InstaAcc acc) {
		acc.unsubscribe(this);
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

}
