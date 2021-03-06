package me.koply.nplayer.sound;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import me.koply.nplayer.Main;
import me.koply.nplayer.api.event.NextTrackEvent;
import me.koply.nplayer.api.event.PlayEvent;
import me.koply.nplayer.api.event.TrackEndEvent;
import me.koply.nplayer.event.EventManager;
import me.koply.nplayer.util.Util;

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

        EventManager.pushEvent(
                new PlayEvent(Main.soundManager, track, !isStarted));
    }

    public void nextTrack(AudioTrack lastTrack) {
        AudioTrack poll = queue.poll();
        if (poll == null) {
            Main.log.info("Empty queue..");
            return;
        }

        player.startTrack(poll, false);
        Main.log.info("Playing now:");
        Util.printInformation(poll);

        EventManager.pushEvent(
                new NextTrackEvent(Main.soundManager, lastTrack, poll));
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // player.destroy();
        Main.log.info("OnTrackEnd called. " + endReason.name());
        if (queue.size() == 0) {
            Main.log.info("Last track is over. Empty queue.");
            EventManager.pushEvent(
                    new TrackEndEvent(Main.soundManager, track, endReason));
        } else if (endReason.mayStartNext) {
            nextTrack(track);
        }
     }
}