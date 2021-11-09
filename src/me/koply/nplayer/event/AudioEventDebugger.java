package me.koply.nplayer.event;

import me.koply.nplayer.Main;
import me.koply.nplayer.api.event.AudioEvent;
import me.koply.nplayer.api.event.PlayEvent;
import me.koply.nplayer.api.event.PlayerListenerAdapter;

public class AudioEventDebugger extends PlayerListenerAdapter {

    public AudioEventDebugger() {

    }

    @Override
    public void onPlayEvent(PlayEvent e) {
        Main.log.info("AudioEventDebugger#onPlayEvent");
    }

    @Override
    public void onEvent(AudioEvent event) {
        Main.log.info("AudioEventDebugger#onEvent");
    }
}