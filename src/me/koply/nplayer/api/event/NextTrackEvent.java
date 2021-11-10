package me.koply.nplayer.api.event;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.koply.nplayer.sound.SoundManager;

public class NextTrackEvent extends AudioEvent {

    public final AudioTrack pastTrack;
    public final AudioTrack nextTrack;

    public NextTrackEvent(SoundManager soundManager, AudioTrack pastTrack, AudioTrack nextTrack) {
        super(soundManager);
        this.pastTrack = pastTrack;
        this.nextTrack = nextTrack;
    }
}