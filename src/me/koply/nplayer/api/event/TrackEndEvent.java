package me.koply.nplayer.api.event;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import me.koply.nplayer.sound.SoundManager;

public class TrackEndEvent extends AudioEvent {

    public final AudioTrack endTrack;
    public final AudioTrackEndReason reason;

    public TrackEndEvent(SoundManager soundManager, AudioTrack track, AudioTrackEndReason reason) {
        super(soundManager);
        this.endTrack = track;
        this.reason = reason;
    }

}