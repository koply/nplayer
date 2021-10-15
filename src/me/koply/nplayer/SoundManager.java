package me.koply.nplayer;

import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import javax.sound.sampled.*;

import static com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats.*;

public class SoundManager {

    private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private static final AudioPlayer player = playerManager.createPlayer();
    private static final OutputHandler outputHandler = new OutputHandler(playerManager, player);
    private static final TrackScheduler scheduler = new TrackScheduler(player, outputHandler);
    private static final AudioResultHandler handler = new AudioResultHandler(scheduler);


    public SoundManager() throws LineUnavailableException {
        AudioSourceManagers.registerRemoteSources(playerManager);
        playerManager.getConfiguration().setOutputFormat(COMMON_PCM_S16_BE);
    }

    public void playTrack(String query, boolean isUrl) {
        Main.log.info("Order query: " + query);
        handler.setOrderPlaylist(isUrl);
        playerManager.loadItem(query, handler);
    }




}