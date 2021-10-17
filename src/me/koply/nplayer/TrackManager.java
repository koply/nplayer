package me.koply.nplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackManager extends AudioEventAdapter {

    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;
    public BlockingQueue<AudioTrack> getQueue()  { return queue; }
    private final OutputHandler outputHandler;

    public TrackManager(AudioPlayer player, OutputHandler outputHandler) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.outputHandler = outputHandler;
    }

    public void queue(AudioTrack track) {
        boolean isStarted = player.startTrack(track, true);
        if (!isStarted) {
            // çalan müzik yok ise direkt başlar ve sıraya eklenmez
            // çalan müzik var ise sıraya eklenir
            queue.offer(track);
            Main.log.info("Added to queue:");
        } else {
            Main.log.info("Playing now:");
            outputHandler.prepareAndRun();
        }
        Util.printInformation(track);
    }

    public void nextTrack() {
        AudioTrack poll = queue.poll();
        if (poll == null) {
            Main.log.info("Empty queue..");
            return;
        }

        player.startTrack(poll, false);
        Main.log.info("Playing now:");
        Util.printInformation(poll);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // player.destroy();
        Main.log.info("OnTrackEnd called. " + endReason.name());
        if (queue.size() == 0) {
            Main.log.info("Last track is over. Empty queue.");
        } else if (endReason.mayStartNext) {
            nextTrack();
        }
     }
}