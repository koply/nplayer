package me.koply.nplayer;

import com.sedmelluq.discord.lavaplayer.format.AudioDataFormat;
import com.sedmelluq.discord.lavaplayer.format.AudioPlayerInputStream;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import static com.sedmelluq.discord.lavaplayer.format.StandardAudioDataFormats.*;

public class OutputHandler extends Thread {

    private final AudioPlayerManager playerManager;
    private final AudioPlayer player;

    public OutputHandler(AudioPlayerManager playerManager, AudioPlayer player) {
        this.playerManager = playerManager;
        this.player = player;
    }

    @Override
    public void run() {
        try {
            AudioDataFormat format = this.playerManager.getConfiguration().getOutputFormat();
            AudioInputStream stream = AudioPlayerInputStream.createStream(this.player, format, 10000L, false);
            SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class, stream.getFormat());
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(stream.getFormat());
            line.start();

            byte[] buffer = new byte[COMMON_PCM_S16_BE.maximumChunkSize()];
            int chunkSize;

            while ((chunkSize = stream.read(buffer)) >= 0) {
                line.write(buffer, 0, chunkSize);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}