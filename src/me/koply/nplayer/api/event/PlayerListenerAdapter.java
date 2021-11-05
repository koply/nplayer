package me.koply.nplayer.api.event;

public abstract class PlayerListenerAdapter implements EventListener {

    // TODO: make the other methods and event classes
    void onPlayEvent(PlayEvent e) { }

    @Override
    public void onEvent(AudioEvent event) {

    }
}