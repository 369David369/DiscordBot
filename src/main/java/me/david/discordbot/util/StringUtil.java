package me.david.discordbot.util;

import java.util.concurrent.TimeUnit;

public final class StringUtil {

    public static String fromTime(Long duration) {
        TimeUnit u = TimeUnit.MILLISECONDS;
        long hours = u.toHours(duration) % 24;
        long minutes = u.toMinutes(duration) % 60;
        long seconds = u.toSeconds(duration) % 60;
        if (hours > 0) return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        else return String.format("%02d:%02d", minutes, seconds);
    }
}
