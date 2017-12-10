package me.david.discordbot.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.david.discordbot.database.objects.DatabaseJoke;
import org.bson.Document;

import java.util.Collections;

public class DatabaseConnection {

    private final MongoClient client;
    private MongoDatabase database;

    public DatabaseConnection(String host, int port){
        this.client = new MongoClient(host, port);
        this.database = client.getDatabase("krueger");
    }

    public void addJoke(DatabaseJoke joke){
        MongoCollection<Document> coll = database.getCollection("jokes");
        coll.insertOne(joke.write());
    }

    public DatabaseJoke getRandomJoke(){
        MongoCollection<Document> coll = database.getCollection("jokes");
        double count = Math.floor(Math.random() * coll.count());
        DatabaseJoke joke = new DatabaseJoke();
        Document document = coll.find().limit(1).skip((int) count).first();
        if(document == null) return null;
        joke.read(document);
        return joke;
    }

    public long getJokeCount(){
        return database.getCollection("jokes").count();
    }

    public void shutdown(){
        client.close();
    }
}
