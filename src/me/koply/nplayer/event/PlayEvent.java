package me.koply.nplayer.event;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.koply.nplayer.sound.SoundManager;

public class PlayEvent extends AudioEvent {

    // the track now playing
    public final AudioTrack track;
    public final AudioTrackInfo info;
    public final AudioPlayer player;
    public final AudioPlayerManager playerManager;
    public final SoundManager soundManager;

    public PlayEvent(AudioTrack track, AudioTrackInfo info, AudioPlayer player, AudioPlayerManager playerManager, SoundManager soundManager) {
        this.track = track;
        this.info = info;
        this.player = player;
        this.playerManager = playerManager;
        this.soundManager = soundManager;
    }
}