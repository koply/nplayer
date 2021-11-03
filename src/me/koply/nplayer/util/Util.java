package me.koply.nplayer.util;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.koply.nplayer.Main;

import java.net.URI;
import java.net.URISyntaxException;

public final class Util {

    public static final StringBuilder sb = new StringBuilder();

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
        Main.log.info(info.author + " - " + info.title + " ["+ Util.getKalanSure(info.length) +"]");
    }

    // shitcode i know
    public static String getKalanSure(final long ms) {
        final long millis = ms % 1000;
        final long second = (ms / 1000) % 60;
        final long minute = (ms / 60_000) % 60;
        final long hour = (ms / 3_600_000) % 24;
        final long day = (ms/3_600_000) / 24;

        short k = 0;

        appendDateTime(day != 0, k, day," gün", "DA");
        appendDateTime(hour != 0, k, hour, " saat", "HO");
        appendDateTime(minute != 0, k, minute, " dakika", "MI");
        appendDateTime(second != 0, k, second, " saniye", "SE");
        appendDateTime(millis != 0, k, millis, " milisaniye", "MIL");

        return sb.toString();
    }

    public static void appendDateTime(boolean condition, int k, long dateTime, String suffix, String dateTimeCode){
        if(condition){

            if (k!=0 && dateTimeCode != "DA"){
                sb.append(", ");
            }

            sb.append(dateTime).append(suffix);

            if(dateTimeCode != "MIL"){
                k++;
            }
        }
    }

}