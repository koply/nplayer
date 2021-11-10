package me.koply.nplayer.api.event;

import me.koply.nplayer.sound.SoundManager;

public abstract class AudioEvent {

    public final SoundManager soundManager;
    protected AudioEvent(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}