package me.koply.nplayer.api.event;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public abstract class AudioEvent {

    public final AudioPlayer player;
    public final AudioPlayerManager playerManager;

    protected AudioEvent(AudioPlayer player, AudioPlayerManager playerManager) {
        this.player = player;
        this.playerManager = playerManager;
    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}