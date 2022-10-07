package WorkStuff;

import WorkStuff.WorkBehaviour;

public class WorkAsLawyer implements WorkBehaviour {

	@Override
	public int work() {
		System.out.println("I am doing lawyer stuff");
		return 20000;
	}

}
