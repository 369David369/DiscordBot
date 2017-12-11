package me.david.discordbot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.TextChannel;

public abstract class ResultHandler implements AudioLoadResultHandler {


    private final GuildPlayer scheduler;
    private TextChannel tc;

    public ResultHandler (GuildPlayer scheduler) {
        this.scheduler = scheduler;
        this.tc = scheduler.getTc();
    }

    @Override public abstract void trackLoaded(AudioTrack track);
    @Override public abstract void playlistLoaded(AudioPlaylist playlist);

    @Override
    public void noMatches() {
        tc.sendMessage("No results for the provided url.").queue();
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        tc.sendMessage("Fail to load the song!").queue();
    }
}
