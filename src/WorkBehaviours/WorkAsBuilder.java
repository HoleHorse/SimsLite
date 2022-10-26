package WorkBehaviours;

public class WorkAsBuilder implements WorkBehaviour {

	@Override
	public String work() {
		return "I am building things";
	}

	@Override
	public int getPaid() {
		return 10000;
	}

}
