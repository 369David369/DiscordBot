package me.david.discordbot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import me.david.discordbot.Cons;
import me.david.discordbot.DiscordBot;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;

public class GuildPlayer extends AudioEventAdapter {

    private VoiceChannel vc;
    private TextChannel tc;
    private final AudioPlayer player;

    private Track now;
    private final AudioQueue queue;

    public GuildPlayer(AudioPlayer player, TextChannel tc) {
        this.player = player;
        this.tc = tc;
        queue = new AudioQueue();
        now = new Track();
    }

    public void nextTrack() {
        if(queue.isEmpty()) {
            stopPlayer();
            tc.sendMessage("Queued empty!").queue();
        }
        else {
            player.startTrack(queue.peek().getTrack(), false);
            now = queue.poll();
        }
    }

    public void queue(Track track) {
        if (!player.startTrack(track.getTrack(), true)) {
            queue.offer(track);
            tc.sendMessage("Queued '" + track.getTrack().getInfo().title + "'").queue();
            return;
        }
        now = track;
    }

    public void queue(AudioPlaylist playlist, User user) {
        if(playlist.getTracks().isEmpty()){
            tc.sendMessage("Playlist im empty!").queue();
            return;
        }
        for(AudioTrack track : playlist.getTracks()) {
            Track newtrack = new Track(track, user);
            if (!player.startTrack(track, true)) {
                queue.offer(newtrack);
                continue;
            }
            now = newtrack;
        }
        tc.sendMessage("Loaded Playlist with " + playlist.getTracks().size() + " tracks!").queue();
    }

    public void play(String link, User author) {
        Matcher m = Cons.URL_PATTERN.matcher(link);
        if(m.find()){
            try {
                DiscordBot.instance.audiomanager.loadItemOrdered(DiscordBot.instance.audiomanager, link, new ResultHandler(this) {
                    @Override
                    public void trackLoaded(AudioTrack track) {
                        queue(new Track(track, author));
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist playlist) {
                        queue(playlist, author);
                    }
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            tc.sendMessage("No match found.").queue();
        }
    }

    public boolean connect(VoiceChannel vc) {
        if(getVc() != null && vc.getId().equals(getVc().getId())) return false;
        setVc(vc);
        AudioManager am = vc.getGuild().getAudioManager();
        am.setAutoReconnect(true);
        am.openAudioConnection(vc);
        return true;
    }

    public GuildPlayer disconnect() {
        vc.getGuild().getAudioManager().closeAudioConnection();
        return this;
    }

    public void pauseOrPlay() {
        if(now == null) return;
        if(player.isPaused()) player.setPaused(false);
        else player.setPaused(true);
    }

    public void setPaused(boolean paused){
        if(now == null) return;
        player.setPaused(paused);
    }

    public void jump(long position) {
        if(now.getTrack().isSeekable())
            now.getTrack().setPosition(position);
    }

    public boolean isPlaying() {
        return now != null && player.getPlayingTrack() != null;
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        if(tc != null) tc.sendMessage("Now playing `" + track.getInfo().title + "`").queue();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason == AudioTrackEndReason.REPLACED) tc.sendMessage("Skipped the current song `" + track.getInfo().title + "`").queue();
        else if (endReason == AudioTrackEndReason.STOPPED) tc.sendMessage("Stopped the player.").queue();

        if (endReason.mayStartNext) nextTrack();
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        tc.sendMessage("Player paused!").queue();
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        tc.sendMessage("Player resumed!").queue();
    }

    @Override
    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
        tc.sendMessage("Problem with Track! Skip to next...").queue();
        nextTrack();
    }

    @Override
    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
        tc.sendMessage("An error occurred: " + exception.getLocalizedMessage()).queue();
        nextTrack();
    }

    public GuildPlayer stopPlayer() {
        clearNow().clearQueue().player.stopTrack();
        return this;
    }

    public GuildPlayer clearQueue() {
        queue.clear();
        return this;
    }

    private GuildPlayer clearNow() {
        now = new Track();
        return this;
    }

    public VoiceChannel getVc() {
        return vc;
    }

    public void setVc(VoiceChannel vc) {
        this.vc = vc;
    }

    public TextChannel getTc() {
        return tc;
    }

    public void setTc(TextChannel tc) {
        this.tc = tc;
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public Track getNow() {
        return now;
    }

    public void setNow(Track now) {
        this.now = now;
    }

    public AudioQueue getQueue() {
        return queue;
    }
}
