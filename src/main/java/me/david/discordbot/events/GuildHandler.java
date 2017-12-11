package me.david.discordbot.events;

import me.david.discordbot.DiscordBot;
import me.david.discordbot.GuildWrapper;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class GuildHandler extends ListenerAdapter {


    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        if(ChronoUnit.SECONDS.between(event.getGuild().getSelfMember().getJoinDate(), ZonedDateTime.now())<10) {
            Guild guild = event.getGuild();
            String id = guild.getId();

            if(guild.getDefaultChannel().canTalk()) {
               guild.getDefaultChannel().sendMessage("Hey im new! Do !help for help!").queue();
            }
            GuildWrapper newguild = new GuildWrapper(event.getJDA(), id);
            guild.getAudioManager().setSendingHandler(newguild.getSendHandler());
            DiscordBot.instance.logger.info("Joined guild: " + id + " " + guild.getName());
            DiscordBot.instance.shards.get(DiscordBot.instance.shards.size()-1).getGuilds().put(id, newguild);
            DiscordBot.instance.shards.get(DiscordBot.instance.shards.size()-1).updateguidls();
        }
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        DiscordBot.instance.shards.get(DiscordBot.instance.shards.size()-1).updateguidls();
    }
}
