package GamePlay;

import GUI.GameWindow;
import DrinkFactory.DrinkFact;
import DrinkFactory.DrinkFactory;
import FoodDecorators.*;
import Characters.Acc;
import Characters.Subscriber;
import Mongo.Mongo;
import RadioState.*;
import WorkBehaviours.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamePlay {
    private static ObjectId uid;
    private static ObjectId gid;
    public static Subscriber champ = new Subscriber("Champ123", new WorkAsNot());
    public static Subscriber vik = new Subscriber("Viktor228", new WorkAsLawyer());
    public static Subscriber smash = new Subscriber("Smasher520", new WorkAsBuilder());
    public static Acc mainChar = Acc.getInstance();
    static String currCharType = "";
    static Subscriber currCharSub = new Subscriber("Default", new WorkAsNot());

    public static void play() {
        getGames();
        champ.subscribe(mainChar);
        vik.subscribe(mainChar);
        smash.subscribe(mainChar);
        while (true) {
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
            if (currCharName.equals(mainChar.getAlias()))
                currCharType = "acc";
            if (Objects.equals(currCharType, "sub")) {
                boolean wantSwitch = false;
                while (!wantSwitch) {
                    GameWindow.appendText("Enter number of the action to perform:\n");
                    GameWindow.appendText("1 = Read new, 2 = Read old, 3 = Switch, 4 = Sub., 5 = Unsub., 6 = Exit, 7 = Work\n");
                    GameWindow.appendText("8 = Eat, 9 = See savings, 10 = get drink, 11 = turn radio on/off, 12 = change job\n");
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
                            saveGame();
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
                        case "11" -> {
                            if (currCharSub.radio.getState().toString().equals("Off")) {
                                currCharSub.radio.setState(new OnState());
                                GameWindow.appendText(currCharSub.radio.getState().switchRadio() + "\n");
                            } else {
                                currCharSub.radio.setState(new OffState());
                                GameWindow.appendText(currCharSub.radio.getState().switchRadio() + "\n");
                            }
                        }
                        case "12" -> {
                            WorkBehaviour newJob = changeJobs();
                            currCharSub.setWorkBehaviour(newJob);
                            makeSame(currCharSub);
                        }
                        default -> GameWindow.appendText("I can't " + action + "\n");
                    }
                }
            } else if (Objects.equals(currCharType, "acc")) {
                boolean wantSwitch = false;
                while (!wantSwitch) {
                    GameWindow.appendText("Enter number of the action to perform:\n");
                    GameWindow.appendText("1 = Post, 2 = Details, 3 = Switch, 4 = Eat, 5 = Unsub., 6 = Exit, 7 = Work\n");
                    GameWindow.appendText("8 = See savings, 9 = get drink, 10 = turn radio on/off, 11 = change job\n");
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
                            getSub(name).unsubscribe(mainChar);
                        }
                        case "6" -> {
                            saveGame();
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
                        case "10" -> {
                            if (mainChar.radio.getState().toString().equals("Off")) {
                                mainChar.radio.setState(new OnState());
                                GameWindow.appendText(mainChar.radio.getState().switchRadio() + "\n");
                            } else {
                                mainChar.radio.setState(new OffState());
                                GameWindow.appendText(mainChar.radio.getState().switchRadio() + "\n");
                            }
                        }
                        case "11" -> {
                            WorkBehaviour newJob = changeJobs();
                            mainChar.setWorkBehaviour(newJob);
                        }
                        default -> GameWindow.appendText("I can't " + action + "\n");
                    }
                }
            }
        }
    }

    private static Food chooseFood() {
        GameWindow.appendText("Choose toppings for your dish: 1 = Mashed potato, 2 = Pasta, 3 = Sausage, 4 = Pattie, 5 = Rice, Any other key = End\n");
        String topping = GameWindow.getText();
        return switch (topping) {
            case "1" -> new MashedPotato(chooseFood());
            case "2" -> new Pasta(chooseFood());
            case "3" -> new Sausage(chooseFood());
            case "4" -> new Pattie(chooseFood());
            case "5" -> new Rice(chooseFood());
            default -> new Dish();
        };
    }

    private static DrinkFact chooseDrink() {
        DrinkFactory drinkFactory = new DrinkFactory();
        while (true) {
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

    private static String insertCharName() {
        boolean isChar = false;
        String charName = null;
        while (!isChar) {
            GameWindow.appendText("Choose character (Enter '1 = Champ123', '2 = Viktor228', '3 = Smasher520' or '4 = " + mainChar.getAlias() + "')" + "\n");
            GameWindow.appendText("Enter 'exit' to end the game\n");
            charName = GameWindow.getText();
            if (charName.equals("exit")) {
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
            if (!isChar) {
                GameWindow.appendText("No such character, try again\n");
            }
        }
        return charName;
    }

    private static Subscriber getSub(String name) {
        return switch (name) {
            case "Champ123" -> champ;
            case "Viktor228" -> vik;
            case "Smasher520" -> smash;
            default -> new Subscriber(name, new WorkAsNot());
        };
    }

    private static void makeSame(Subscriber currChar) {
        switch (currChar.getUsername()) {
            case "Champ123" -> champ = currChar;
            case "Viktor228" -> vik = currChar;
            case "Smasher520" -> smash = currChar;
            default -> {
                System.err.println("Unforeseen error occurred, the game ends now");
                exitGame();
            }
        }
    }

    private static void unsub(String currChar) {
        switch (currChar) {
            case "Champ123" -> champ.unsubscribe(mainChar);
            case "Viktor228" -> vik.unsubscribe(mainChar);
            case "Smasher520" -> smash.unsubscribe(mainChar);
            default -> {
                System.err.println("Unforeseen error occurred, the game ends now");
                exitGame();
            }
        }
    }

    private static void sub(String currChar) {
        switch (currChar) {
            case "Champ123" -> champ.subscribe(mainChar);
            case "Viktor228" -> vik.subscribe(mainChar);
            case "Smasher520" -> smash.subscribe(mainChar);
            default -> {
                System.err.println("Unforeseen error occurred, the game ends now");
                exitGame();
            }
        }
    }

    private static void startGame() {
        loadGame();
    }

    private static void startNewGame() {
        GameWindow.appendText("Welcome to new game, please insert your main character's name (can not be changed later)\n");
        String name = GameWindow.getText();
        mainChar.customize(name);
        WorkBehaviour job = changeJobs();
        mainChar.setWorkBehaviour(job);
    }

    public static void setUid(ObjectId id) {
        uid = id;
    }

    private static void getGames() {
        List<ObjectId> games = Mongo.getGames(uid);
        assert games != null;
        if (games.size() > 0) {
            GameWindow.appendText("Choose your game session:\n");
            for (ObjectId id : games) {
                String name = Mongo.getGameName(id);
                GameWindow.appendText(games.indexOf(id) + " = " + name + " ");
                if (games.indexOf(id) > 0 && games.indexOf(id) % 7 == 0) {
                    GameWindow.appendText(games.indexOf(id) + " = " + name + "\n");
                }
            }
            GameWindow.appendText("\n (Or create a new one type -1)\n");
            String gameToLoad = GameWindow.getText();
            if (gameToLoad.equals("-1")) {
                GameWindow.appendText("Type name for the new game session\n");
                String name = GameWindow.getText();
                ObjectId id = new ObjectId();
                Mongo.addNewGame(uid, name, id);
                gid = id;
                startNewGame();
                return;
            }
            for (ObjectId id : games) {
                if (Integer.toString(games.indexOf(id)).equals(gameToLoad)) {
                    gid = id;
                }
            }
            startGame();
        } else {
            GameWindow.appendText("You have no game sessions, insert Name of the new game session to start a new one\n");
            String name = GameWindow.getText();
            ObjectId id = new ObjectId();
            Mongo.addNewGame(uid, name, id);
            gid = id;
            startNewGame();
        }
    }

    private static WorkBehaviour changeJobs() {
        while (true) {
            GameWindow.appendText("Choose the job you'd love to do 1 = No job, 2 = Builder, 3 = Lawyer\n");
            String job = GameWindow.getText();
            switch (job) {
                case "1" -> {
                    return new WorkAsNot();
                }
                case "2" -> {
                    return new WorkAsBuilder();
                }
                case "3" ->{
                    return new WorkAsLawyer();
                }
                default -> GameWindow.appendText("No representatives of this job are in demand\n");
            };
        }
    }

    private static void loadGame() {
        Mongo.getSubs(gid);
        Mongo.getUnreadRead(gid);
        Mongo.getSavings(gid);
        Mongo.getMcName(gid);
        Mongo.getRadio(gid);
        Mongo.getWork(gid);
    }

    public static void saveGame() {
        Mongo.saveSubs(gid);
        Mongo.saveUnreadRaed(gid);
        Mongo.saveSavings(gid);
        Mongo.saveMcName(gid);
        Mongo.saveRadio(gid);
        Mongo.saveWork(gid);
    }

    public static void exitGame() {
        saveGame();
        System.exit(0);
    }

    public static ObjectId getUid() {
        return uid;
    }

    public static ObjectId getGid() {
        return gid;
    }
}
