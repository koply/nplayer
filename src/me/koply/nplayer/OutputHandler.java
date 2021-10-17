package me.koply.nplayer;

import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.format.AudioPlayerInputStream;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import java.io.IOException;

import static com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats.*;

public class OutputHandler implements Runnable {

    private final AudioPlayerManager playerManager;
    private final AudioPlayer player;

    public OutputHandler(AudioPlayerManager playerManager, AudioPlayer player) {
        this.playerManager = playerManager;
        this.player = player;
    }

    private boolean isPause = false;
    public void pauseOutputLine() {
        synchronized (this) {
            isPause = true;
        }
    }

    private AudioInputStream stream;
    private SourceDataLine line;

    private Thread workerThread = new Thread(this);

    public void resumeOutputLine() {
        isPause = false;
        workerThread = null;
        workerThread = new Thread(this);
        workerThread.start();
    }

    public void prepareAndRun() {
        try {
            AudioDataFormat format = this.playerManager.getConfiguration().getOutputFormat();
            AudioInputStream innerStream = AudioPlayerInputStream.createStream(this.player, format, 10000L, false);
            SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class, innerStream.getFormat());
            SourceDataLine innerLine = (SourceDataLine) AudioSystem.getLine(info);
            innerLine.open(innerStream.getFormat());
            innerLine.start();

            stream = innerStream;
            line = innerLine;
            if (isPause) {
                workerThread = new Thread(this);
                isPause = false;
            }
            if (!workerThread.isAlive()) {
                workerThread.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    byte[] buffer = new byte[COMMON_PCM_S16_BE.maximumChunkSize()];
    @Override
    public void run() {
        int chunkSize;

        try {
            while ((chunkSize = stream.read(buffer)) >= 0) {
                line.write(buffer, 0, chunkSize);
                if (isPause) break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}