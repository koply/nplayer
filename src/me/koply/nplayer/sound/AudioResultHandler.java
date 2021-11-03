package me.koply.nplayer.sound;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.koply.nplayer.Main;
import me.koply.nplayer.util.Util;

public class AudioResultHandler implements AudioLoadResultHandler {

    private final TrackManager scheduler;
    public AudioResultHandler(TrackManager scheduler) {
        this.scheduler = scheduler;
    }

    private boolean isOrderPlaylist = false;
    public void setOrderPlaylist(boolean x) {
        isOrderPlaylist = x;
    }

    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        Main.log.info("TrackLoaded: " + audioTrack.getInfo().title);
    }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        if (isOrderPlaylist) {
            long totalDuration = 0;
            scheduler.queue(audioPlaylist.getTracks().get(0));
            totalDuration += audioPlaylist.getTracks().get(0).getInfo().length;
            for (int i = 1; i<audioPlaylist.getTracks().size(); ++i) {
                scheduler.getQueue().offer(audioPlaylist.getTracks().get(i));
                totalDuration += audioPlaylist.getTracks().get(i).getInfo().length;
            }
            Main.log.info("Playlist added to queue.");
            Main.log.info(audioPlaylist.getTracks().size() + " tracks with " + Util.getKalanSure(totalDuration));
        } else {
            Main.log.info("Found tracks:");

            for (int i = 0; i<audioPlaylist.getTracks().size(); ++i) {
                AudioTrack currentTrack = audioPlaylist.getTracks().get(i);
                Main.log.info("[ " + i + " ] " + currentTrack.getInfo().title + " - " + Util.getKalanSure(currentTrack.getInfo().length));
            }

            scheduler.queue(audioPlaylist.getTracks().get(0));
            /*
            Main.log.info("Enter your choice:");
            int choice = Math.abs(OrderHandler.SC.nextInt());
            if (audioPlaylist.getTracks().size() - choice < 0) choice = 0;
            scheduler.queue(audioPlaylist.getTracks().get(choice));
             */
        }
    }

    @Override
    public void noMatches() {
        Main.log.info("I cant find that entry.");
    }

    @Override
    public void loadFailed(FriendlyException e) {
        Main.log.info("Something happened. " + e.getMessage());
    }
}