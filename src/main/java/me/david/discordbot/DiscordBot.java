package me.david.discordbot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import me.david.discordbot.audio.GuildPlayer;
import me.david.discordbot.commands.CommandManager;
import me.david.discordbot.constants.Cons;
import me.david.discordbot.constants.PrivateCons;
import me.david.discordbot.database.AsyncDatabaseConnection;
import me.david.discordbot.database.DatabaseConnection;
import me.david.discordbot.logger.MLogger;
import net.dv8tion.jda.core.entities.Guild;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class DiscordBot implements Runnable {

    public MLogger logger;
    public final long START_TIME = System.currentTimeMillis();
    public final String prefix;
    public static DiscordBot instance;
    public CommandManager commandmanager;
    public List<Shard> shards = new ArrayList<>();
    public DatabaseConnection connection;
    public AsyncDatabaseConnection asyncconnection;
    public AudioPlayerManager audiomanager;

    private DiscordBot(String prefix){
        this.prefix = prefix;
    }

    public static void main(String[] args){
        new DiscordBot(args.length == 1 ?args[0]:"[Krüger] ").run();
    }

    public void run() {
        instance = this;
        logger = new MLogger(Level.ALL);
        logger.printbanner(prefix);
        commandmanager = new CommandManager();
        startmusic();
        connection = new DatabaseConnection("localhost", 27017);
        asyncconnection = new AsyncDatabaseConnection(connection);
        shards.add(new Shard(0, PrivateCons.AUTH_TOKEN));
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    private void startmusic(){
        audiomanager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(audiomanager);
        AudioSourceManagers.registerLocalSource(audiomanager);
    }

    private void stop(){
        for(Shard shard : shards)
            shard.jda.shutdown();
        connection.shutdown();
    }

    public GuildPlayer getPlayer(Guild guild){
        return getGuild(guild).guildPlayer;
    }

    public GuildWrapper getGuild(Guild guild){
        return shards.get(0).getGuilds().get(guild.getId());
    }

}
