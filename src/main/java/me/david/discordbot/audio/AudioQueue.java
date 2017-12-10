package me.david.discordbot.audio;

import java.util.Collections;
import java.util.LinkedList;

public class AudioQueue extends LinkedList<Track> {

    public AudioQueue() {}

    public int find(String keyword) {
        keyword = keyword.toLowerCase();
        int count = -1;
        for(Track each : this) {
            count++;
            if(each.getTrack().getInfo().title.toLowerCase().contains(keyword)) return count;
        }
        return -1;
    }

    public void shuffle() {
        Collections.shuffle(this);
    }

    public void skipTo(int index) {
        for(int i = 0; i < this.size(); i++)
            if(i <= index)
                this.remove();
    }

}
