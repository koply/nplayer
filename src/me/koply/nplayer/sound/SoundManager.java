package me.koply.nplayer.sound;

import com.sedmelluq.discord.lavaplayer.filter.equalizer.EqualizerFactory;
import com.sedmelluq.discord.lavaplayer.player.*;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.koply.nplayer.Main;

import javax.sound.sampled.*;

import static com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats.*;

public class SoundManager {

    // singleton
    private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private static final AudioPlayer player = playerManager.createPlayer();
    private static final EqualizerFactory equalizerFactory = new EqualizerFactory();
    private static final OutputHandler outputHandler = new OutputHandler(playerManager, player);
    private static final TrackManager scheduler = new TrackManager(player, outputHandler);
    private static final AudioResultHandler handler = new AudioResultHandler(scheduler);

    public SoundManager() throws LineUnavailableException {

        // we don't need a bunch of these sources
        // AudioSourceManagers.registerRemoteSources(playerManager);
        // just YouTube for now
        playerManager.registerSourceManager(new YoutubeAudioSourceManager(true));

        playerManager.getConfiguration().setOutputFormat(COMMON_PCM_S16_BE);
        player.addListener(scheduler);
        setVolume(75);
    }

    public static void shutdown() {
        playerManager.shutdown();
        outputHandler.shutdownThread();
    }

    // IDK WTF IS THIS
    private static final float[] BASS_BOOST = { 0.2f, 0.15f, 0.1f, 0.05f, 0.0f,
            -0.05f, -0.1f, -0.1f, -0.1f, -0.1f,
            -0.1f, -0.1f, -0.1f, -0.1f, -0.1f };

    // -------------- PUBLIC API -----------------
    public void playTrack(String query, boolean isUrl) {
        Main.log.info("Order query: " + query);
        handler.setOrderPlaylist(isUrl);
        playerManager.loadItem(query, handler);
    }

    public AudioTrack getPlayingTrack() {
        return player.getPlayingTrack();
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

    public int getVolume() { return player.getVolume(); }

    public void activeEqualizer() {
        player.setFilterFactory(equalizerFactory);
    }

    public void disableEqualizer() {
        player.setFilterFactory(null);
    }

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

}