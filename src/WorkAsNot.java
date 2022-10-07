
public class WorkAsNot implements WorkBehaviour {

	@Override
	public int work() {
		System.out.println("I am unemployeed");
		return 0;
	}

}
