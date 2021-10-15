package me.koply.nplayer;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import java.net.URI;
import java.net.URISyntaxException;

public final class Util {

    public static boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    public static void printInformation(AudioTrack track) {
        AudioTrackInfo info = track.getInfo();
        Main.log.info(info.author + " - " + info.title + " ["+ Util.getKalanSure(info.length) +"]");
    }

    // shitcode i know
    public static String getKalanSure(final long ms) {
        final long millis = ms % 1000;
        final long second = (ms / 1000) % 60;
        final long minute = (ms / 60_000) % 60;
        final long hour = (ms / 3_600_000) % 24;
        final long day = (ms/3_600_000) / 24;

        final StringBuilder sb = new StringBuilder();
        short k = 0;
        if (day != 0) {
            sb.append(day).append(" gün");
            k++;
        }
        if (hour != 0) {
            if (k!=0) sb.append(", ");
            sb.append(hour).append(" saat");
            k++;
        }
        if (minute != 0) {
            if (k!=0) sb.append(", ");
            sb.append(minute).append(" dakika");
            k++;
        }
        if (second != 0) {
            if (k!=0) sb.append(", ");
            sb.append(second).append(" saniye");
            k++;
        }
        if (millis != 0) {
            if (k!=0) sb.append(", ");
            sb.append(millis).append(" milisaniye");
        }
        return sb.toString();
    }

}