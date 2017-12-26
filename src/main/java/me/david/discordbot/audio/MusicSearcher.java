package me.david.discordbot.audio;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MusicSearcher {

    public static String getUrlbyKeyword(String input){
        try {
            Document doc = Jsoup.connect("https://www.youtube.com/results?search_query=" + input).timeout(0).get();
            Elements main = doc.select("div#results").select("ol.item-section").select("li:not([class])").first().select("div.yt-lockup-content");
            return "https://www.youtube.com" + main.select(".yt-lockup-title").select(".yt-uix-tile-link").select("a").attr("href");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
