package Characters;

import WorkBehaviours.WorkAsNot;
import WorkBehaviours.WorkBehaviour;

import java.util.HashSet;
import java.util.Set;

public class Acc implements InstaSevice {
	private Set<Observer> subscribers = new HashSet<>();
	private String alias;
	private WorkBehaviour workBehaviour;
	private static Acc instance = new Acc("Default", new WorkAsNot());
	private int savings;
	
	private Acc(String alias, WorkBehaviour workBehaviour) {
		this.alias = alias;
		this.workBehaviour = workBehaviour;
		this.savings = 100000;
	}
	
	public String work() {
		this.setSavings(getSavings()+this.workBehaviour.getPaid());
		return this.workBehaviour.work();
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
            o.getNotification("New post from " + this.alias + ":" + m + "\n");
        }
	}
	
	public String seeAccDetails() {
		return "You have " + subscribers.size() + " subs.";
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

	public static Acc getInstance() {
		return instance;
	}

	public void customize(String alias) {
		instance.alias = alias;
	}
}
