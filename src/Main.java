import FoodStuff.*;
import ObserverStuff.InstaAcc;
import ObserverStuff.Subscriber;
import WorkStuff.*;

import java.util.Objects;
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
			switch (currCharName) {
				case "Champ123" -> {
					currCharSub = champ;
					currCharType = "sub";
				}
				case "Viktor228" -> {
					currCharSub = vik;
					currCharType = "sub";
				}
				case "Smasher520" -> {
					currCharSub = smash;
					currCharType = "sub";
				}
				case "Captain_SHkiper" -> currCharType = "acc";
			}
			if(Objects.equals(currCharType, "sub")) {
				boolean wantSwitch = false;
				while(!wantSwitch) {
					System.out.println("Enter number of the action to perform:");
					System.out.println("1 = Read new, 2 = Read old, 3 = Switch, 4 = Sub., 5 = Unsub., 6 = Exit, 7 = Work, 8 = Eat, 9 = See savings");
					String action = input.nextLine();
					switch (action) {
						case "1" -> {
							System.out.println("New posts:");
							currCharSub.readNewNotifications();
							makeSame(currCharSub);
						}
						case "2" -> {
							System.out.println("Old posts:");
							currCharSub.readOldNotifications();
							makeSame(currCharSub);
						}
						case "6" -> exitGame();
						case "3" -> wantSwitch = true;
						case "4" -> sub(currCharName);
						case "5" -> unsub(currCharName);
						case "7" -> {
							currCharSub.work();
							makeSame(currCharSub);
						}
						case "8" -> {
							Food food = chooseFood(input);
							System.out.println(food.getFoodName());
							System.out.println("Price" + food.getFoodPrice());
							if (currCharSub.getSavings() < food.getFoodPrice()) {
								System.out.println("You don't have enough money");
							} else {
								currCharSub.setSavings(currCharSub.getSavings() - food.getFoodPrice());
								System.out.println("Savings: " + currCharSub.getSavings());
								makeSame(currCharSub);
							}
						}
						case "9" -> System.out.println(currCharSub.getSavings());
						default -> System.out.println("I can't " + action);
					}
				}
			} else if(Objects.equals(currCharType, "acc")) {
				boolean wantSwitch = false;
				while(!wantSwitch) {
					System.out.println("Enter number of the action to perform:");
					System.out.println("1 = Post, 2 = Details, 3 = Switch, 4 = Eat, 5 = Unsub., 6 = Exit, 7 = Work, 8 = See savings");
					String action = input.nextLine();
					switch (action) {
						case "1" -> {
							System.out.println("Enter your message");
							String msg = input.nextLine();
							shkipC.notify(msg);
						}
						case "5" -> {
							System.out.println("Enter who to unsub.");
							String name = input.nextLine();
							shkipC.unsubscribe(getSub(name));
						}
						case "6" -> exitGame();
						case "3" -> wantSwitch = true;
						case "2" -> shkipC.seeAccDetails();
						case "7" -> shkipC.work();
						case "4" -> {
							Food food = chooseFood(input);
							System.out.println(food.getFoodName());
							System.out.println("Price" + food.getFoodPrice());
							if (shkipC.getSavings() < food.getFoodPrice()) {
								System.out.println("You don't have enough money");
							} else {
								shkipC.setSavings(shkipC.getSavings() - food.getFoodPrice());
								System.out.println("Savings: " + shkipC.getSavings());
							}
						}
						case "8" -> System.out.println(shkipC.getSavings());
						default -> System.out.println("I can't " + action);
					}
				}
			}
		}
	}
	
	public static Food chooseFood(Scanner input) {
		System.out.println("Choose toppings for your dish: 1 = Mashed potato, 2 = FoodStuff.Pasta, 3 = FoodStuff.Sausage, 4 = FoodStuff.Pattie, 5 = FoodStuff.Rice, Any other key = End");
		String topping = input.nextLine();
		return switch (topping) {
			case "1" -> new MashedPotato(chooseFood(input));
			case "2" -> new Pasta(chooseFood(input));
			case "3" -> new Sausage(chooseFood(input));
			case "4" -> new Pattie(chooseFood(input));
			case "5" -> new Rice(chooseFood(input));
			default -> new Dish();
		};
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
			switch (charName) {
				case "1" -> {
					charName = "Champ123";
					isChar = true;
				}
				case "2" -> {
					charName = "Viktor228";
					isChar = true;
				}
				case "3" -> {
					charName = "Smasher520";
					isChar = true;
				}
				case "4" -> {
					charName = "Captain_SHkiper";
					isChar = true;
				}
			}
			if(!isChar) {
				System.out.println("No such charachter, try again");
			}
		}
		return charName;
	}
	
	public static Subscriber getSub(String name) {
		return switch (name) {
			case "Champ123" -> champ;
			case "Viktor228" -> vik;
			case "Smasher520" -> smash;
			default -> new Subscriber(name, new WorkAsNot());
		};
	}
	
	public static void makeSame(Subscriber currChar) {
		switch (currChar.getUsername()) {
			case "Champ123" -> champ = currChar;
			case "Viktor228" -> vik = currChar;
			case "Smasher520" -> smash = currChar;
			default -> {
				System.err.println("Unforseen error occured, the game ends now");
				exitGame();
			}
		}
	}
	
	public static void unsub(String currChar) {
		switch (currChar) {
			case "Champ123" -> champ.unsubscribe(shkipC);
			case "Viktor228" -> vik.unsubscribe(shkipC);
			case "Smasher520" -> smash.unsubscribe(shkipC);
			default -> {
				System.err.println("Unforseen error occured, the game ends now");
				exitGame();
			}
		}
	}
	
	public static void sub(String currChar) {
		switch (currChar) {
			case "Champ123" -> champ.subscribe(shkipC);
			case "Viktor228" -> vik.subscribe(shkipC);
			case "Smasher520" -> smash.subscribe(shkipC);
			default -> {
				System.err.println("Unforseen error occured, the game ends now");
				exitGame();
			}
		}
	}
	
	public static void exitGame() {
		System.exit(0);
	}
}
