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

    public static boolean isNumber(String str){
        try {
            Integer.valueOf(str);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    public static boolean isLong(String str){
        try {
            Long.valueOf(str);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    public static int countMatches(String str, char find){
        return (str.length()-str.replace(find, '\0').length());
    }

    public static long toLong(String str){
        long hours = 0;
        long minutes = 0;
        long seconds;
        String[] split = str.split(":");
        if(split.length == 0 || split.length > 3) return -1;
        try {
            seconds = Long.valueOf(split[split.length-1]);
            if(split.length > 1) minutes = Long.valueOf(split[split.length-2]);
            if(split.length > 2) minutes = Long.valueOf(split[split.length-3]);
        }catch (NumberFormatException e){
            return -1;
        }
        long count = seconds*1000;
        count += minutes*60*1000;
        count += hours*60*60*1000;
        return count;
    }
}
