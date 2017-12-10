package me.david.discordbot.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.User;

public class Track {

    private final AudioTrack track;
    private final User requester;

    public Track(AudioTrack track, User requester) {
        this.track = track;
        this.requester = requester;
    }

    public Track(){
        track = null;
        requester = null;
    }

    public boolean isEmpty() {
        return track == null;
    }

    public Track clone() {
        return new Track(track.makeClone(), requester);
    }

    public AudioTrack getTrack() {
        return track;
    }

    public User getRequester() {
        return requester;
    }
}
