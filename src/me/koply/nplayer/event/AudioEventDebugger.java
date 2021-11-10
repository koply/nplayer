package me.koply.nplayer.event;

import me.koply.nplayer.Main;
import me.koply.nplayer.api.event.NextTrackEvent;
import me.koply.nplayer.api.event.PlayEvent;
import me.koply.nplayer.api.event.PlayerListenerAdapter;
import me.koply.nplayer.api.event.TrackEndEvent;

public class AudioEventDebugger extends PlayerListenerAdapter {

    @Override
    public void onPlay(PlayEvent e) {
        Main.log.info("AudioEventDebugger#onPlayEvent");
    }

    @Override
    public void onTrackEnd(TrackEndEvent e) {
        Main.log.info("AudioEventDebugger#onTrackEnd");
    }

    @Override
    public void onNextTrack(NextTrackEvent e) {
        Main.log.info("AudioEventDebugger#onNextTrack");
    }
}