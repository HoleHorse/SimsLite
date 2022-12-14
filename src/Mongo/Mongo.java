package Mongo;

import Characters.Acc;
import Characters.Observer;
import GUI.GameWindow;
import GamePlay.GamePlay;
import RadioState.*;
import WorkBehaviours.*;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.*;

import static GamePlay.GamePlay.*;

public class Mongo {
    private static final MongoClient mongoClient = new MongoClient("localhost", 27017);
    private static final MongoDatabase database = mongoClient.getDatabase("sdp");

    public static void register(String username, String password) {
        try {
            MongoCollection<Document> users = database.getCollection("users");
            Document user = new Document();
            user.append("username", username);
            user.append("password", password);
            user.append("isadmin", false);
            user.append("games", new ArrayList<ObjectId>());
            users.insertOne(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isValid(String username, String password) {
        boolean isValid = false;
        try {
            MongoCollection<Document> users = database.getCollection("users");
            BasicDBObject query = new BasicDBObject();
            query.put("username", username);
            long count = users.countDocuments(query);
            isValid = count < 1 && username.length() > 4 && password.length() > 4 && password.length() < 17;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public static String specify(String username, String password) {
        if(password.length() > 16)
            return "Password too long (16 symbols max)";
        if(password.length() < 5)
            return "Password too short (5 symbols min)";
        if(username.length() < 5)
            return "Username too short (4 symbols min)";
        else return "This username is already occupied";
    }

    public static Document login(String username, String password) {
        try {
            MongoCollection<Document> users = database.getCollection("users");
            BasicDBObject query = new BasicDBObject();
            ArrayList<BasicDBObject> obj = new ArrayList<>();
            obj.add(new BasicDBObject("username", username));
            obj.add(new BasicDBObject("password", password));
            query.put("$and", obj);
            FindIterable<Document> cursor = users.find(query);
            return cursor.first();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Document();
    }

    public static List<ObjectId> getGames(ObjectId _id) {
        try {
            MongoCollection<Document> users = database.getCollection("users");
            BasicDBObject query = new BasicDBObject();
            query.put("_id", _id);
            FindIterable<Document> cursor = users.find(query);
            return Objects.requireNonNull(cursor.first()).getList("games", ObjectId.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void addNewGame(ObjectId _id, String name, ObjectId game_id) {
        List<String> el = new ArrayList<>();
        List<Integer> savings = new ArrayList<>();
        List<String> radio = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            radio.add("Off");
        }
        List<String> work = new ArrayList<>();
        work.add("None");
        work.add("Lawyer");
        work.add("Builder");
        work.add("None");
        List<String> subs = new ArrayList<>();
        subs.add("champ");
        subs.add("vik");
        subs.add("smash");
        try {
            MongoCollection<Document> users = database.getCollection("users");
            MongoCollection<Document> games = database.getCollection("games");
            Document game = new Document();
            appendToGame(game_id, name, el, el, el, el, el, el, savings, work, radio, subs, game);
            games.insertOne(game);
            Bson query = new Document("_id", _id);
            Bson update = new Document("$push", new Document("games", game_id));
            users.findOneAndUpdate(query, update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getGameName(ObjectId id) {
        String name = "";
        try {
            MongoCollection<Document> games = database.getCollection("games");
            BasicDBObject query = new BasicDBObject();
            query.put("_id", id);
            FindIterable<Document> cursor = games.find(query);
            name = (String) Objects.requireNonNull(cursor.first()).get("game_name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public static void getSubs(ObjectId id) {
        Set<Observer> subscribers = new HashSet<>();
        try {
            MongoCollection<Document> games = database.getCollection("games");
            BasicDBObject query = new BasicDBObject();
            query.put("_id", id);
            FindIterable<Document> cursor = games.find(query);
            List<String> subs = Objects.requireNonNull(cursor.first()).getList("subs", String.class);
            if (!subs.contains("champ"))
                champ.subscribe(mainChar);
            if (!subs.contains("vik"))
                vik.subscribe(mainChar);
            if (!subs.contains("smash"))
                smash.subscribe(mainChar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Acc.getInstance().setSubscribers(subscribers);
    }

    public static void saveSubs(ObjectId id) {
        List<String> subs = new ArrayList<>();
        if (champ.isSub())
            subs.add("champ");
        if (vik.isSub())
            subs.add("vik");
        if (smash.isSub())
            subs.add("smash");
        try {
            MongoCollection<Document> games = database.getCollection("games");
            Bson query = new Document("_id", id);
            Bson update = Updates.set("subs", subs);
            games.findOneAndUpdate(query, update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getUnreadRead(ObjectId id) {
        try {
            MongoCollection<Document> games = database.getCollection("games");
            BasicDBObject query = new BasicDBObject();
            query.put("_id", id);
            FindIterable<Document> cursor = games.find(query);
            List<String> champU = Objects.requireNonNull(cursor.first()).getList("champU", String.class);
            List<String> champR = Objects.requireNonNull(cursor.first()).getList("champR", String.class);
            List<String> vikU = Objects.requireNonNull(cursor.first()).getList("vikU", String.class);
            List<String> vikR = Objects.requireNonNull(cursor.first()).getList("vikR", String.class);
            List<String> smashU = Objects.requireNonNull(cursor.first()).getList("smashU", String.class);
            List<String> smashR = Objects.requireNonNull(cursor.first()).getList("smashR", String.class);
            champ.setUnread((ArrayList<String>) champU);
            champ.setRead((ArrayList<String>) champR);
            vik.setUnread((ArrayList<String>) vikU);
            vik.setRead((ArrayList<String>) vikR);
            smash.setUnread((ArrayList<String>) smashU);
            smash.setRead((ArrayList<String>) smashR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveUnreadRaed(ObjectId id) {
        try {
            MongoCollection<Document> games = database.getCollection("games");
            Bson query = new Document("_id", id);
            Bson update = Updates.set("champU", champ.getUnread());
            games.findOneAndUpdate(query, update);
            update = Updates.set("champR", champ.getRead());
            games.findOneAndUpdate(query, update);
            update = Updates.set("vikU", vik.getUnread());
            games.findOneAndUpdate(query, update);
            update = Updates.set("vikR", vik.getRead());
            games.findOneAndUpdate(query, update);
            update = Updates.set("smashU", smash.getUnread());
            games.findOneAndUpdate(query, update);
            update = Updates.set("smashR", smash.getRead());
            games.findOneAndUpdate(query, update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getSavings(ObjectId id) {
        try {
            MongoCollection<Document> games = database.getCollection("games");
            BasicDBObject query = new BasicDBObject();
            query.put("_id", id);
            FindIterable<Document> cursor = games.find(query);
            List<Integer> savings = Objects.requireNonNull(cursor.first()).getList("savings", Integer.class);
            champ.setSavings(savings.get(0));
            vik.setSavings(savings.get(1));
            smash.setSavings(savings.get(2));
            mainChar.setSavings(savings.get(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveSavings(ObjectId id) {
        List<Integer> savings = new ArrayList<>();
        savings.add(champ.getSavings());
        savings.add(vik.getSavings());
        savings.add(smash.getSavings());
        savings.add(mainChar.getSavings());
        try {
            MongoCollection<Document> games = database.getCollection("games");
            Bson query = new Document("_id", id);
            Bson update = Updates.set("savings", savings);
            games.findOneAndUpdate(query, update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getMcName(ObjectId id) {
        try {
            MongoCollection<Document> games = database.getCollection("games");
            BasicDBObject query = new BasicDBObject();
            query.put("_id", id);
            FindIterable<Document> cursor = games.find(query);
            String name = (String) Objects.requireNonNull(cursor.first()).get("mcName");
            mainChar.customize(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveMcName(ObjectId id) {
        try {
            MongoCollection<Document> games = database.getCollection("games");
            BasicDBObject query = new BasicDBObject();
            query.put("_id", id);
            Bson update = Updates.set("mcName", mainChar.getAlias());
            games.findOneAndUpdate(query, update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getRadio(ObjectId id) {
        try {
            MongoCollection<Document> games = database.getCollection("games");
            BasicDBObject query = new BasicDBObject();
            query.put("_id", id);
            FindIterable<Document> cursor = games.find(query);
            List<String> radioStates = Objects.requireNonNull(cursor.first()).getList("radio", String.class);
            if (radioStates.get(0).equals("Off")) {
                champ.radio.setState(new OffState());
            } else {
                champ.radio.setState(new OnState());
            }
            if (radioStates.get(1).equals("Off")) {
                vik.radio.setState(new OffState());
            } else {
                vik.radio.setState(new OnState());
            }
            if (radioStates.get(2).equals("Off")) {
                smash.radio.setState(new OffState());
            } else {
                smash.radio.setState(new OnState());
            }
            if (radioStates.get(3).equals("Off")) {
                mainChar.radio.setState(new OffState());
            } else {
                mainChar.radio.setState(new OnState());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveRadio(ObjectId id) {
        List<String> radioStates = new ArrayList<>();
        radioStates.add(champ.radio.getState().toString());
        radioStates.add(vik.radio.getState().toString());
        radioStates.add(smash.radio.getState().toString());
        radioStates.add(mainChar.radio.getState().toString());
        try {
            MongoCollection<Document> games = database.getCollection("games");
            Bson query = new Document("_id", id);
            Bson update = Updates.set("radio", radioStates);
            games.findOneAndUpdate(query, update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getWork(ObjectId id) {
        try {
            MongoCollection<Document> games = database.getCollection("games");
            BasicDBObject query = new BasicDBObject();
            query.put("_id", id);
            FindIterable<Document> cursor = games.find(query);
            List<String> work = Objects.requireNonNull(cursor.first()).getList("work", String.class);
            if (work.get(0).equals("Lawyer"))
                champ.setWorkBehaviour(new WorkAsLawyer());
            else if (work.get(0).equals("Builder"))
                champ.setWorkBehaviour(new WorkAsBuilder());
            else champ.setWorkBehaviour(new WorkAsNot());
            if (work.get(1).equals("Lawyer"))
                vik.setWorkBehaviour(new WorkAsLawyer());
            else if (work.get(1).equals("Builder"))
                vik.setWorkBehaviour(new WorkAsBuilder());
            else vik.setWorkBehaviour(new WorkAsNot());
            if (work.get(2).equals("Lawyer"))
                smash.setWorkBehaviour(new WorkAsLawyer());
            else if (work.get(2).equals("Builder"))
                smash.setWorkBehaviour(new WorkAsBuilder());
            else smash.setWorkBehaviour(new WorkAsNot());
            if (work.get(3).equals("Lawyer"))
                mainChar.setWorkBehaviour(new WorkAsLawyer());
            else if (work.get(3).equals("Builder"))
                mainChar.setWorkBehaviour(new WorkAsBuilder());
            else mainChar.setWorkBehaviour(new WorkAsNot());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveWork(ObjectId id) {
        List<String> work = new ArrayList<>();
        work.add(champ.getWorkBehaviour().toString());
        work.add(vik.getWorkBehaviour().toString());
        work.add(smash.getWorkBehaviour().toString());
        work.add(mainChar.getWorkBehaviour().toString());
        try {
            MongoCollection<Document> games = database.getCollection("games");
            Bson query = new Document("_id", id);
            Bson update = Updates.set("work", work);
            games.findOneAndUpdate(query, update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void appendToGame(ObjectId game_id, String game_name, List<String> champU,
                                     List<String> champR, List<String> vikU, List<String> vikR,
                                     List<String> smashU, List<String> smashR, List<Integer> savings,
                                     List<String> work, List<String> radio,
                                     List<String> subs, Document game) {
        game.append("_id", game_id);
        game.append("game_name", game_name);
        game.append("champU", champU);
        game.append("champR", champR);
        game.append("vikU", vikU);
        game.append("vikR", vikR);
        game.append("smashU", smashU);
        game.append("smashR", smashR);
        game.append("savings", savings);
        game.append("work", work);
        game.append("radio", radio);
        game.append("mcName", "Stranger");
        game.append("subs", subs);
    }

    public static void saveLog() {
        ArrayList<String> lines = GameWindow.getLines();
        try {
            MongoCollection<Document> logs = database.getCollection("logs");
            Document log = new Document();
            log.append("User_id", GamePlay.getUid());
            log.append("Game_id", GamePlay.getGid());
            log.append("Log", lines);
            logs.insertOne(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FindIterable<Document> getLog() {
        FindIterable<Document> all = null;
        try {
            MongoCollection<Document> logs = database.getCollection("logs");
            all = logs.find();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return all;
    }
}
