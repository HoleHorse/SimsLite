package Mongo;

import Characters.Acc;
import Characters.Observer;
import GUI.GameWindow;
import GamePlay.GamePlay;
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
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ObjectId> getGames(ObjectId _id) {
        try {
            MongoCollection<Document> users = database.getCollection("users");
            BasicDBObject query = new BasicDBObject();
            query.put("_id", _id);
            FindIterable<Document> cursor = users.find(query);
            return Objects.requireNonNull(cursor.first()).getList("games", ObjectId.class);
        } catch(Exception e) {
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
            appendToGame(game_id, name, el, el, el, el, el, el, savings, work, radio, "Stranger", subs, game);
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
            if(!subs.contains("champ"))
                champ.subscribe(mainChar);
            if(!subs.contains("vik"))
                vik.subscribe(mainChar);
            if(!subs.contains("smash"))
                smash.subscribe(mainChar);
        } catch(Exception e) {
            e.printStackTrace();
        }
        Acc.getInstance().setSubscribers(subscribers);
    }

    public static void saveSubs(ObjectId id) {
        List<String> subs = new ArrayList<>();
        if(champ.isSub())
            subs.add("champ");
        if(vik.isSub())
            subs.add("vik");
        if(smash.isSub())
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
            champ.setUnread((ArrayList<String>)champU);
            champ.setRead((ArrayList<String>)champR);
            vik.setUnread((ArrayList<String>)vikU);
            vik.setRead((ArrayList<String>)vikR);
            smash.setUnread((ArrayList<String>)smashU);
            smash.setRead((ArrayList<String>)smashR);
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

    private static void appendToGame(ObjectId game_id, String game_name, List<String> champU,
                                     List<String> champR, List<String> vikU, List<String> vikR,
                                     List<String> smashU, List<String> smashR, List<Integer> savings,
                                     List<String> work, List<String> radio, String mcName,
                                     List<String> subs,Document game) {
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
        game.append("mcName", mcName);
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
