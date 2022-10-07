import WorkStuff.WorkBehaviour;

import java.util.HashSet;
import java.util.Set;

public class InstaAcc implements InstaSevice {
	private Set<Observer> subscribers = new HashSet<>();
	private String alias;
	private WorkBehaviour workBehaviour;
	private int savings;
	
	public InstaAcc(String alias, WorkBehaviour workBehaviour) {
		this.alias = alias;
		this.workBehaviour = workBehaviour;
		this.savings = 100000;
	}
	
	public void work() {
		this.setSavings(getSavings()+this.workBehaviour.work());
	}

	@Override
	public void subscribe(Observer o) {
		subscribers.add(o);
	}

	@Override
	public void unsubscribe(Observer o) {
		subscribers.remove(o);
	}

	@Override
	public void notify(String m) {
		for(Observer o: subscribers) {
            o.getNotification("new post from " + this.alias + ":" + m + "\n");
        }
	}
	
	public void seeAccDetails() {
		System.out.println("You have " + subscribers.size() + " subs.");
	}
	
	public String getAlias() {
		return alias;
	}
	
	public int getSavings() {
		return savings;
	}
	
	public void setSavings(int savings) {
		this.savings = savings;
	}
}
