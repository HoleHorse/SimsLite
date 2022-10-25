package GamePlay;

import FactoryStuff.DrinkFact;
import FactoryStuff.DrinkFactory;
import FoodStuff.*;
import ObservSinglStuff.InstaAcc;
import ObservSinglStuff.Subscriber;
import WorkStuff.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class GamePlay {
    static Subscriber champ = new Subscriber("Champ123", new WorkAsNot());
    static Subscriber vik = new Subscriber("Viktor228", new WorkAsLawyer());
    static Subscriber smash = new Subscriber("Smasher520", new WorkAsBuilder());
    static InstaAcc mainChar = InstaAcc.getInstance();
    static String currCharType = "";
    static Subscriber currCharSub = new Subscriber("Default", new WorkAsNot());
    static Scanner input = new Scanner(System.in);
    static ArrayList<Subscriber> sublist = new ArrayList<>();

    public static void play() {
        startGame(input);
        champ.subscribe(mainChar);
        vik.subscribe(mainChar);
        smash.subscribe(mainChar);
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
                    System.out.println("1 = Read new, 2 = Read old, 3 = Switch, 4 = Sub., 5 = Unsub., 6 = Exit, 7 = Work, 8 = Eat, 9 = See savings, 10 = get drink");
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
                        case "6" -> {
                            return;
                        }
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
                            System.out.println("Price: " + food.getFoodPrice());
                            if (currCharSub.getSavings() < food.getFoodPrice()) {
                                System.out.println("You don't have enough money");
                            } else {
                                currCharSub.setSavings(currCharSub.getSavings() - food.getFoodPrice());
                                System.out.println("Savings: " + currCharSub.getSavings());
                                makeSame(currCharSub);
                            }
                        }
                        case "9" -> System.out.println(currCharSub.getSavings());
                        case "10" -> {
                            DrinkFact drink = chooseDrink(input);
                            drink.getDrink();
                        }
                        default -> System.out.println("I can't " + action);
                    }
                }
            } else if(Objects.equals(currCharType, "acc")) {
                boolean wantSwitch = false;
                while(!wantSwitch) {
                    System.out.println("Enter number of the action to perform:");
                    System.out.println("1 = Post, 2 = Details, 3 = Switch, 4 = Eat, 5 = Unsub., 6 = Exit, 7 = Work, 8 = See savings, 9 = get drink");
                    String action = input.nextLine();
                    switch (action) {
                        case "1" -> {
                            System.out.println("Enter your message");
                            String msg = input.nextLine();
                            mainChar.notify(msg);
                        }
                        case "5" -> {
                            System.out.println("Enter who to unsub.");
                            String name = input.nextLine();
                            mainChar.unsubscribe(getSub(name));
                        }
                        case "6" -> {
                            return;
                        }
                        case "3" -> wantSwitch = true;
                        case "2" -> mainChar.seeAccDetails();
                        case "7" -> mainChar.work();
                        case "4" -> {
                            Food food = chooseFood(input);
                            System.out.println(food.getFoodName());
                            System.out.println("Price: " + food.getFoodPrice());
                            if (mainChar.getSavings() < food.getFoodPrice()) {
                                System.out.println("You don't have enough money");
                            } else {
                                mainChar.setSavings(mainChar.getSavings() - food.getFoodPrice());
                                System.out.println("Savings: " + mainChar.getSavings());
                            }
                        }
                        case "8" -> System.out.println(mainChar.getSavings());
                        case "9" -> {
                            DrinkFact drink = chooseDrink(input);
                            drink.getDrink();
                        }
                        default -> System.out.println("I can't " + action);
                    }
                }
            }
        }
    }

    public static Food chooseFood(Scanner input) {
        System.out.println("Choose toppings for your dish: 1 = Mashed potato, 2 = Pasta, 3 = Sausage, 4 = Pattie, 5 = Rice, Any other key = End");
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

    public static DrinkFact chooseDrink(Scanner input) {
        DrinkFactory drinkFactory = new DrinkFactory();
        while(true) {
            System.out.println("Choose your poison: 1 = Coffee, 2 = Tea, 3 = Booze");
            String drink = input.nextLine();
            switch (drink) {
                case "1" -> {
                    return drinkFactory.getDrink("Coffee");
                }
                case "2" -> {
                    return drinkFactory.getDrink("Tea");
                }
                case "3" -> {
                    return drinkFactory.getDrink("Booze");
                }
                default -> System.out.println("No such drink");
            }
        }
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
            case "Champ123" -> champ.unsubscribe(mainChar);
            case "Viktor228" -> vik.unsubscribe(mainChar);
            case "Smasher520" -> smash.unsubscribe(mainChar);
            default -> {
                System.err.println("Unforseen error occured, the game ends now");
                exitGame();
            }
        }
    }

    public static void sub(String currChar) {
        switch (currChar) {
            case "Champ123" -> champ.subscribe(mainChar);
            case "Viktor228" -> vik.subscribe(mainChar);
            case "Smasher520" -> smash.subscribe(mainChar);
            default -> {
                System.err.println("Unforseen error occured, the game ends now");
                exitGame();
            }
        }
    }

    public static void startGame(Scanner input) {
        System.out.println("Welcome, please insert your name (can be changed later)");
        String name = input.nextLine();
        mainChar.customize(name);
    }
    public static void exitGame() {
        System.exit(0);
    }
}

