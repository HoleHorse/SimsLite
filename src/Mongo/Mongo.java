package Mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

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
            users.insertOne(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Document login(String username, String password) {
        try {
            MongoCollection<Document> users = database.getCollection("users");
            BasicDBObject andQuery = new BasicDBObject();
            ArrayList<BasicDBObject> obj = new ArrayList<>();
            obj.add(new BasicDBObject("username", username));
            obj.add(new BasicDBObject("password", password));
            andQuery.put("$and", obj);
            FindIterable<Document> cursor = users.find(andQuery);
            return cursor.first();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
