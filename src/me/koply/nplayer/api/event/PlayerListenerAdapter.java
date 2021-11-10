package me.koply.nplayer.api.event;

public abstract class PlayerListenerAdapter {

    // TODO: make the other methods and event classes
    public void onPlay(PlayEvent e) { }
    public void onTrackEnd(TrackEndEvent e) { }
    public void onNextTrack(NextTrackEvent e) { }

}