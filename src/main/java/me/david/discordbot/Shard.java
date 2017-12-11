package me.david.discordbot;

import me.david.discordbot.events.GuildHandler;
import me.david.discordbot.events.MessageHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.HashMap;

public class Shard {

    public JDA jda;
    private final int ID;
    private final String token;
    private HashMap<String, GuildWrapper> guilds = new HashMap<>();

    public Shard(int shardID, String token) {
        this.token = token;
        login(token);
        ID = shardID;
        loadShard();
    }

    void login(String token){
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .addEventListener(new MessageHandler(), new GuildHandler())
                    .setAutoReconnect(true)
                    .setMaxReconnectDelay(120)
                    .setEnableShutdownHook(true)
                    .setStatus(OnlineStatus.IDLE)
                    .buildBlocking();
        } catch (LoginException | InterruptedException | RateLimitedException e) {
            e.printStackTrace();
        }
    }

    void loadShard(){
        for(Guild g : jda.getGuilds()) {
            GuildWrapper newGuild = new GuildWrapper(jda, g.getId());
            guilds.put(g.getId(), newGuild);
            g.getAudioManager().setSendingHandler(newGuild.getSendHandler());
        }
        updateguidls();
    }

    public void updateguidls(){
        jda.getPresence().setGame(Game.playing(guilds.size() + " Guilds!"));
    }

    public HashMap<String, GuildWrapper> getGuilds() {
        return guilds;
    }
}
