import java.util.Scanner;

public class Main {
	static Subscriber champ = new Subscriber("Champ123", new WorkAsNot());
	static Subscriber vik = new Subscriber("Viktor228", new WorkAsLawyer());
	static Subscriber smash = new Subscriber("Smasher520", new WorkAsBuilder());
	static InstaAcc shkipC = new InstaAcc("Captain_SHkiper", new WorkAsNot());
			
	static String currCharType = "";
	static Subscriber currCharSub = new Subscriber("Default", new WorkAsNot());
			
	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		champ.subscribe(shkipC);
		vik.subscribe(shkipC);
		smash.subscribe(shkipC);
		play();
	}
	
	public static void play() {
		while(true) {
			String currCharName = insertCharName(input);
			if(currCharName.equals("Champ123")) {
				currCharSub = champ;
				currCharType = "sub";
			} else if(currCharName.equals("Viktor228")) {
				currCharSub = vik;
				currCharType = "sub";
			} else if(currCharName.equals("Smasher520")) {
				currCharSub = smash;
				currCharType = "sub";
			} else if(currCharName.equals("Captain_SHkiper")) {
				currCharType = "acc";
			}
			if(currCharType == "sub") {
				boolean wantSwitch = false;
				while(!wantSwitch) {
					System.out.println("Enter number of the action to perform:");
					System.out.println("1 = Read new, 2 = Read old, 3 = Switch, 4 = Sub., 5 = Unsub., 6 = Exit, 7 = Work, 8 = Eat, 9 = See savings");
					String action = input.nextLine();
					if(action.equals("1")) {
						System.out.println("New posts:");
						currCharSub.readNewNotifications();
						makeSame(currCharSub);
					} else if(action.equals("2")) {
						System.out.println("Old posts:");
						currCharSub.readOldNotifications();
						makeSame(currCharSub);
					} else if(action.equals("6")) {
						exitGame();
					} else if (action.equals("3")) {
						wantSwitch = true;
					} else if (action.equals("4")) {
						sub(currCharName);
					} else if (action.equals("5")) {
						unsub(currCharName);
					} else if (action.equals("7")) {
						currCharSub.work();
						makeSame(currCharSub);
					} else if (action.equals("8")) {
						Food food = chooseFood(input);
						System.out.println(food.getFoodName());
						System.out.println("Price" + food.getFoodPrice());
						if(currCharSub.getSavings() < food.getFoodPrice()) {
							System.out.println("You don't have enough money");
						} else {
							currCharSub.setSavings(currCharSub.getSavings()-food.getFoodPrice());
							System.out.println("Savings: " + currCharSub.getSavings());
							makeSame(currCharSub);
						}
					} else if (action.equals("9")) {
						System.out.println(currCharSub.getSavings());
					} else {
						System.out.println("I can't " + action);
					}
				}
			} else if(currCharType == "acc") {
				boolean wantSwitch = false;
				while(!wantSwitch) {
					System.out.println("Enter number of the action to perform:");
					System.out.println("1 = Post, 2 = Details, 3 = Switch, 4 = Eat, 5 = Unsub., 6 = Exit, 7 = Work, 8 = See savings");
					String action = input.nextLine();
					if(action.equals("1")) {
						System.out.println("Enter your message");
						String msg = input.nextLine();
						shkipC.notify(msg);
					} else if(action.equals("5")) {
						System.out.println("Enter who to unsub.");
						String name = input.nextLine();
						shkipC.unsubscribe(getSub(name));
					} else if(action.equals("6")) {
						exitGame();
					} else if (action.equals("3")) {
						wantSwitch = true;
					} else if(action.equals("2")) {
						shkipC.seeAccDetails();
					} else if(action.equals("7")) {
						shkipC.work();
					} else if (action.equals("4")) {
						Food food = chooseFood(input);
						System.out.println(food.getFoodName());
						System.out.println("Price" + food.getFoodPrice());
						if(shkipC.getSavings() < food.getFoodPrice()) {
							System.out.println("You don't have enough money");
						} else {
							shkipC.setSavings(shkipC.getSavings()-food.getFoodPrice());
							System.out.println("Savings: " + shkipC.getSavings());
						}
					} else if (action.equals("8")) {
						System.out.println(shkipC.getSavings());
					} else {
						System.out.println("I can't " + action);
					}
				}
			}
		}
	}
	
	public static Food chooseFood(Scanner input) {
		System.out.println("Choose toppings for your dish: 1 = Mashed potato, 2 = Pasta, 3 = Sausage, 4 = Pattie, 5 = Rice, 6 = End");
		String topping = input.nextLine();
		if(topping.equals("1")) {
			return new MashedPotato(chooseFood(input));
		} else if(topping.equals("2")) {
			return new Pasta(chooseFood(input));
		} else if(topping.equals("3")) {
			return new Sausage(chooseFood(input));
		} else if(topping.equals("4")) {
			return new Pattie(chooseFood(input));
		} else if(topping.equals("5")) {
			return new Rice(chooseFood(input));
		} else if(topping.equals("6")) {
			return new Dish();
		}
		return new Dish();
	}
	
	public static String insertCharName(Scanner input) {
		boolean isChar = false;
		String charName = null;
		while(!isChar) {
			System.out.println("Choose character (Enter '1 = Champ123', '2 = Viktor228', '3 = Smasher520' or '4 = Captain_SHkiper')");
			System.out.println("Enter 'exit' to end the game");
			charName = input.nextLine();
			if(charName.equals("exit")) {
				exitGame();
			}
			if(charName.equals("1")) {
				charName = "Champ123";
				isChar = true;
			} else if(charName.equals("2")) {
				charName = "Viktor228";
				isChar = true;
			} else if(charName.equals("3")) {
				charName = "Smasher520";
				isChar = true;
			} else if(charName.equals("4")) {
				charName = "Captain_SHkiper";
				isChar = true;
			}
			if(!isChar) {
				System.out.println("No such charachter, try again");
			}
		}
		return charName;
	}
	
	public static Subscriber getSub(String name) {
		if(name.equals("Champ123")) {
			return champ;
		} else if(name.equals("Viktor228")) {
			return vik;
		} else if(name.equals("Smasher520")) {
			return smash;
		} else {
			return new Subscriber(name, new WorkAsNot());
		}
	}
	
	public static void makeSame(Subscriber currChar) {
		if(currChar.getUsername().equals("Champ123")) {
			champ = currChar;
		} else if(currChar.getUsername().equals("Viktor228")) {
			vik = currChar;
		} else if(currChar.getUsername().equals("Smasher520")) {
			smash = currChar;
		} else {
			System.err.println("Unforseen error occured, the game ends now");
			exitGame();
		}
	}
	
	public static void unsub(String currChar) {
		if(currChar.equals("Champ123")) {
			champ.unsubscribe(shkipC);
		} else if(currChar.equals("Viktor228")) {
			vik.unsubscribe(shkipC);
		} else if(currChar.equals("Smasher520")) {
			smash.unsubscribe(shkipC);
		} else {
			System.err.println("Unforseen error occured, the game ends now");
			exitGame();
		}
	}
	
	public static void sub(String currChar) {
		if(currChar.equals("Champ123")) {
			champ.subscribe(shkipC);
		} else if(currChar.equals("Viktor228")) {
			vik.subscribe(shkipC);
		} else if(currChar.equals("Smasher520")) {
			smash.subscribe(shkipC);
		} else {
			System.err.println("Unforseen error occured, the game ends now");
			exitGame();
		}
	}
	
	public static void exitGame() {
		System.exit(0);
	}
}
