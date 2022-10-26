package GamePlay;

import GUI.GameWindow;
import DrinkFactory.DrinkFact;
import DrinkFactory.DrinkFactory;
import FoodDecorators.*;
import Characters.InstaAcc;
import Characters.Subscriber;
import WorkBehaviours.*;

import java.util.ArrayList;
import java.util.Objects;

public class GamePlay {
    static Subscriber champ = new Subscriber("Champ123", new WorkAsNot());
    static Subscriber vik = new Subscriber("Viktor228", new WorkAsLawyer());
    static Subscriber smash = new Subscriber("Smasher520", new WorkAsBuilder());
    static InstaAcc mainChar = InstaAcc.getInstance();
    static String currCharType = "";
    static Subscriber currCharSub = new Subscriber("Default", new WorkAsNot());

    public static void play() {
        GameWindow.createWindow();
        startGame();
        champ.subscribe(mainChar);
        vik.subscribe(mainChar);
        smash.subscribe(mainChar);
        GameWindow.createWindow();
        while(true) {
            String currCharName = insertCharName();
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
            }
            if(currCharName.equals(mainChar.getAlias()))
                currCharType = "acc";
            if(Objects.equals(currCharType, "sub")) {
                boolean wantSwitch = false;
                while(!wantSwitch) {
                    GameWindow.appendText("Enter number of the action to perform:\n");
                    GameWindow.appendText("1 = Read new, 2 = Read old, 3 = Switch, 4 = Sub., 5 = Unsub., 6 = Exit, 7 = Work, 8 = Eat, 9 = See savings, 10 = get drink\n");
                    String action = GameWindow.getText();
                    switch (action) {
                        case "1" -> {
                            GameWindow.appendText("New posts:\n");
                            ArrayList<String> list = currCharSub.readNewNotifications();
                            for (String i : list) {
                                GameWindow.appendText(i + "\n");
                            }
                            makeSame(currCharSub);
                        }
                        case "2" -> {
                            GameWindow.appendText("Old posts:\n");
                            ArrayList<String> list = currCharSub.readOldNotifications();
                            for (String i : list) {
                                GameWindow.appendText(i + "\n");
                            }
                            makeSame(currCharSub);
                        }
                        case "6" -> {
                            return;
                        }
                        case "3" -> wantSwitch = true;
                        case "4" -> sub(currCharName);
                        case "5" -> unsub(currCharName);
                        case "7" -> {
                            GameWindow.appendText(currCharSub.work() + "\n");
                            makeSame(currCharSub);
                        }
                        case "8" -> {
                            Food food = chooseFood();
                            GameWindow.appendText(food.getFoodName() + "\n");
                            GameWindow.appendText("Price: " + food.getFoodPrice() + "\n");
                            if (currCharSub.getSavings() < food.getFoodPrice()) {
                                GameWindow.appendText("You don't have enough money\n");
                            } else {
                                currCharSub.setSavings(currCharSub.getSavings() - food.getFoodPrice());
                                GameWindow.appendText("Savings: " + currCharSub.getSavings() + "\n");
                                makeSame(currCharSub);
                            }
                        }
                        case "9" -> GameWindow.appendText(currCharSub.getSavings() + "\n");
                        case "10" -> {
                            DrinkFact drink = chooseDrink();
                            GameWindow.appendText(drink.getDrink() + "\n");
                        }
                        default -> GameWindow.appendText("I can't " + action + "\n");
                    }
                }
            } else if(Objects.equals(currCharType, "acc")) {
                boolean wantSwitch = false;
                while(!wantSwitch) {
                    GameWindow.appendText("Enter number of the action to perform:\n");
                    GameWindow.appendText("1 = Post, 2 = Details, 3 = Switch, 4 = Eat, 5 = Unsub., 6 = Exit, 7 = Work, 8 = See savings, 9 = get drink\n");
                    String action = GameWindow.getText();
                    switch (action) {
                        case "1" -> {
                            GameWindow.appendText("Enter your message\n");
                            String msg = GameWindow.getText();
                            mainChar.notify(msg);
                        }
                        case "5" -> {
                            GameWindow.appendText("Enter who to unsub.\n");
                            String name = GameWindow.getText();
                            mainChar.unsubscribe(getSub(name));
                        }
                        case "6" -> {
                            return;
                        }
                        case "3" -> wantSwitch = true;
                        case "2" -> GameWindow.appendText(mainChar.seeAccDetails() + "\n");
                        case "7" -> GameWindow.appendText(mainChar.work() + "\n");
                        case "4" -> {
                            Food food = chooseFood();
                            GameWindow.appendText(food.getFoodName() + "\n");
                            GameWindow.appendText("Price: " + food.getFoodPrice() + "\n");
                            if (mainChar.getSavings() < food.getFoodPrice()) {
                                GameWindow.appendText("You don't have enough money\n");
                            } else {
                                mainChar.setSavings(mainChar.getSavings() - food.getFoodPrice());
                                GameWindow.appendText("Savings: " + mainChar.getSavings() + "\n");
                            }
                        }
                        case "8" -> GameWindow.appendText(mainChar.getSavings() + "\n");
                        case "9" -> {
                            DrinkFact drink = chooseDrink();
                            GameWindow.appendText(drink.getDrink() + "\n");
                        }
                        default -> GameWindow.appendText("I can't " + action + "\n");
                    }
                }
            }
        }
    }

    public static Food chooseFood() {
        GameWindow.appendText("Choose toppings for your dish: 1 = Mashed potato, 2 = Pasta, 3 = Sausage, 4 = Pattie, 5 = Rice, Any other key = End\n");
        String topping = GameWindow.getText();;
        return switch (topping) {
            case "1" -> new MashedPotato(chooseFood());
            case "2" -> new Pasta(chooseFood());
            case "3" -> new Sausage(chooseFood());
            case "4" -> new Pattie(chooseFood());
            case "5" -> new Rice(chooseFood());
            default -> new Dish();
        };
    }

    public static DrinkFact chooseDrink() {
        DrinkFactory drinkFactory = new DrinkFactory();
        while(true) {
            GameWindow.appendText("Choose your poison: 1 = Coffee, 2 = Tea, 3 = Booze\n");
            String drink = GameWindow.getText();
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
                default -> GameWindow.appendText("No such drink\n");
            }
        }
    }

    public static String insertCharName() {
        boolean isChar = false;
        String charName = null;
        while(!isChar) {
            GameWindow.appendText("Choose character (Enter '1 = Champ123', '2 = Viktor228', '3 = Smasher520' or '4 = " + mainChar.getAlias() + "')" + "\n");
            GameWindow.appendText("Enter 'exit' to end the game\n");
            charName = GameWindow.getText();
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
                    charName = mainChar.getAlias();
                    isChar = true;
                }
            }
            if(!isChar) {
                GameWindow.appendText("No such charachter, try again\n");
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

    public static void startGame() {
        GameWindow.appendText("Welcome, please insert your name (can be changed later)\n");
        String name = GameWindow.getText();;
        mainChar.customize(name);
    }
    public static void exitGame() {
        System.exit(0);
    }
}

