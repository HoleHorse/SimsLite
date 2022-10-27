package WorkBehaviours;

public class WorkAsNot implements WorkBehaviour {

	@Override
	public String work() {
		return "I am unemployed";
	}

	@Override
	public int getPaid() {
		return 0;
	}

	@Override
	public String toString() {
		return "None";
	}
}
