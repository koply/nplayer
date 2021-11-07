package me.koply.nplayer.util;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.koply.nplayer.Main;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public final class Util {

    private static final long DAY = 86_400_000L;
    private static final long HOUR = 3_600_000L;
    private static final long MINUTE = 60_000L;
    private static final long SECOND = 1_000L;

    public static boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    public static Integer parseInt(String entry) {
        try {
            return Integer.parseInt(entry);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Float parseFloat(String entry) {
        try {
            return Float.parseFloat(entry);
        } catch (Exception ex) {
            return null;
        }
    }

    public static void printInformation(AudioTrack track) {
        AudioTrackInfo info = track.getInfo();
        Main.log.info(info.author + " - " + info.title + " ["+ Util.formatMilliSecond(info.length) +"]");
    }

    public static String formatMilliSecond(long millis) {
        long days = millis / DAY;
        millis -= days * DAY;

        long hours = millis / HOUR;
        millis -= hours * HOUR;

        long minutes = millis / MINUTE;
        millis -= minutes * MINUTE;

        long seconds = millis / SECOND;
        millis -= seconds * SECOND;

        StringBuilder builder = new StringBuilder();

        if (days > 0) {
            builder.append(days).append(" gün");
        }
        if (hours > 0) {
            builder.append(" ").append(hours).append(" saat");
        }
        if (minutes > 0) {
            builder.append(" ").append(minutes).append(" dakika");
        }
        if (seconds > 0) {
            builder.append(" ").append(seconds).append(" saniye");
        }

        return builder.toString();
    }

}