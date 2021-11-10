package me.koply.nplayer.api.event;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.koply.nplayer.sound.SoundManager;

public class PlayEvent extends AudioEvent {

    /**
     * the track now playing
     */
    public final AudioTrack track;
    public final boolean isAddedToQueue;

    public PlayEvent(SoundManager soundManager, AudioTrack track, boolean isAddedToQueue) {
        super(soundManager);
        this.track = track;
        this.isAddedToQueue = isAddedToQueue;
    }
}