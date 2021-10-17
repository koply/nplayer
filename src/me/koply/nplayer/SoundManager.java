package me.koply.nplayer;

import com.sedmelluq.discord.lavaplayer.filter.equalizer.EqualizerFactory;
import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import javax.sound.sampled.*;

import static com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats.*;

public class SoundManager {

    private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();

    // public cuz getVolume
    public static final AudioPlayer player = playerManager.createPlayer();

    private static final EqualizerFactory equalizerFactory = new EqualizerFactory();

    private static final OutputHandler outputHandler = new OutputHandler(playerManager, player);
    private static final TrackManager scheduler = new TrackManager(player, outputHandler);
    private static final AudioResultHandler handler = new AudioResultHandler(scheduler);

    public SoundManager() throws LineUnavailableException {
        AudioSourceManagers.registerRemoteSources(playerManager);
        playerManager.getConfiguration().setOutputFormat(COMMON_PCM_S16_BE);
        player.addListener(scheduler);
        setVolume(75);
    }

    public void activeEqualizer() {
        player.setFilterFactory(equalizerFactory);
    }

    public void disableEqualizer() {
        player.setFilterFactory(null);
    }

    // IDK WTF IS THIS
    private static final float[] BASS_BOOST = { 0.2f, 0.15f, 0.1f, 0.05f, 0.0f, -0.05f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f,
            -0.1f, -0.1f, -0.1f, -0.1f };

    public void increaseBassBoost(float diff) {
        for (int i = 0; i < BASS_BOOST.length; i++) {
            equalizerFactory.setGain(i, BASS_BOOST[i] + diff);
        }
    }

    public void decreaseBassBoost(float diff) {
        for (int i = 0; i < BASS_BOOST.length; i++) {
            equalizerFactory.setGain(i, -BASS_BOOST[i] + diff);
        }
    }

    public void playTrack(String query, boolean isUrl) {
        Main.log.info("Order query: " + query);
        handler.setOrderPlaylist(isUrl);
        playerManager.loadItem(query, handler);
    }

    private boolean paused = false;
    public boolean isPaused() { return paused; }

    public void playPauseButton() {
        if (paused) resume();
        else pause();
    }

    public void pause() {
        player.setPaused(true);
        outputHandler.pauseOutputLine();
        paused = true;
        Main.log.info("Paused.");
    }

    public void resume() {
        player.setPaused(false);
        outputHandler.resumeOutputLine();
        paused = false;
        Main.log.info("Resumed.");
    }

    public void stop() {
        player.stopTrack();
        Main.log.info("Track stoped.");
    }

    public void setVolume(int x) {
        player.setVolume(x);
    }

}