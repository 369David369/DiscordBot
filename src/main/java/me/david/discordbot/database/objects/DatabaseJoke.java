package me.david.discordbot.database.objects;

import me.david.discordbot.database.DataBaseObj;
import org.bson.Document;

public class DatabaseJoke implements DataBaseObj {

    private String creator, joke;

    public DatabaseJoke(){}
    public DatabaseJoke(String creator, String joke) {
        this.creator = creator;
        this.joke = joke;
    }

    @Override
    public Document write() {
        return new Document("creator", creator).append("joke", joke);
    }

    @Override
    public void read(Document document) {
        creator = document.getString("creator");
        joke = document.getString("joke");
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }
}
