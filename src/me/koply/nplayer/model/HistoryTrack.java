package me.koply.nplayer.model;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class HistoryTrack {

    public long id;
    public final AudioTrack track;
    public final long listeningMillis;

    public HistoryTrack(AudioTrack track, long listeningMillis) {
        this.track = track;
        this.listeningMillis = listeningMillis;
    }

    public HistoryTrack(long id, AudioTrack track, long listeningMillis) {
        this.track = track;
        this.listeningMillis = listeningMillis;
        this.id = id;
    }

}