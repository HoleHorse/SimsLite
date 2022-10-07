
public interface InstaSevice {
	public void subscribe(Observer o);
    public void unsubscribe(Observer o);
    public void notify(String msg);
}
