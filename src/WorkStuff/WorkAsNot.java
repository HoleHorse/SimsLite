package WorkStuff;

import WorkStuff.WorkBehaviour;

public class WorkAsNot implements WorkBehaviour {

	@Override
	public int work() {
		System.out.println("I am unemployed");
		return 0;
	}

}
