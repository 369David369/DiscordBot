package me.david.discordbot.database;

import org.bson.Document;

public interface DataBaseObj {

    Document write();
    void read(Document document);

}
