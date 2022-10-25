package WorkStuff;

import WorkStuff.WorkBehaviour;

public class WorkAsLawyer implements WorkBehaviour {

	@Override
	public String work() {
		return "I am doing lawyer stuff";
	}

	@Override
	public int getPaid() {
		return 20000;
	}

}
