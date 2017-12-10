package me.david.discordbot.database;

import me.david.discordbot.database.objects.DatabaseJoke;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AsyncDatabaseConnection {

    private final DatabaseConnection connection;
    private ExecutorService exec;

    public AsyncDatabaseConnection(DatabaseConnection connection){
        this.connection = connection;
        exec = Executors.newCachedThreadPool();
    }


    public void addJoke(DatabaseJoke joke, Runnable run){
        exec.submit(() -> {
            AsyncDatabaseConnection.this.connection.addJoke(joke);
            run.run();
        });
    }

    public void getRandomJoke(Consumer<DatabaseJoke> cb){
        exec.submit(() -> cb.accept(AsyncDatabaseConnection.this.connection.getRandomJoke()));
    }

    public void getJokeCount(Consumer<Long> cb){
        exec.submit(() -> cb.accept(AsyncDatabaseConnection.this.connection.getJokeCount()));
    }


}
