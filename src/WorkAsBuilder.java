
public class WorkAsBuilder implements WorkBehaviour {

	@Override
	public int work() {
		System.out.println("I am building things");
		return 10000;
	}

}
