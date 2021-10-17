package me.koply.nplayer;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.koply.nplayer.util.TrackBoxBuilder;
import me.koply.nplayer.util.Util;

import java.util.Scanner;

import static me.koply.nplayer.Main.*;

public class OrderHandler {

    public static final Scanner SC = new Scanner(System.in);
    private static void println(String o) {
        Main.log.info(o);
    }

    public void startHandler(String[] args) {
        while (true) {
            String entry = SC.nextLine().toLowerCase();
            if (entry.equals("exit") || entry.equals("quit")) {
                break;
            }
            String[] splitted = entry.split(" ");

            if (entry.startsWith("play")) play(entry);
            else if (entry.startsWith("pause")) SOUND_MANAGER.pause();
            else if (entry.startsWith("resume")) SOUND_MANAGER.resume();
            else if (entry.startsWith("stop")) SOUND_MANAGER.stop();
            else if (entry.startsWith("gc")) System.gc();
            else if (entry.startsWith("now")) {
                AudioTrack nowPlayin = SoundManager.player.getPlayingTrack();
                boolean isPause = SOUND_MANAGER.isPaused();
                int volume = SoundManager.player.getVolume();
                if (nowPlayin == null) {
                    println("There is nothing to play...");
                } else {
                    System.out.println(TrackBoxBuilder.buildTrackBox(75, nowPlayin, isPause, volume));
                }
            }
            else if (entry.startsWith("eq")) {
                SOUND_MANAGER.activeEqualizer();
                // 0 1     2 3
                // eq bass + 1
                if (splitted.length <= 3) {
                    println("Example usage: eq bass + 0.3");
                } else if (splitted[1].equals("bass")) {
                    Float diff = Util.parseFloat(splitted[3]);
                    if (diff == null) {
                        println("Invalid diff.");
                        continue;
                    }
                    if (splitted[2].equals("+")) {
                        SOUND_MANAGER.increaseBassBoost(diff);
                    } else if (splitted[2].equals("-")) {
                        SOUND_MANAGER.decreaseBassBoost(diff);
                    }
                }
            } else if (entry.startsWith("volume")) {
                if (splitted.length < 1) continue;
                Integer volume = Util.parseInt(splitted[1]);
                if (volume == null) {
                    Main.log.info("Invalid volume. Current volume: " + SoundManager.player.getVolume());
                } else {
                    volume = volume > 100 ? 100 : volume < 0 ? 0 : volume;
                    SOUND_MANAGER.setVolume(volume);
                    Main.log.info("New volume: " + volume);
                }
            }
            else if (entry.startsWith("help")) {
                println("hi keke");
            }

        }
    }

    // play command
    public void play(String entry) {
        String order = entry.substring(4).trim();
        if (order.equals("")) {
            println("Geçersiz...");
        } else {
            boolean isUrl = true;
            if (!Util.isUrl(order)) {
                order = "ytsearch: " + order;
                isUrl = false;
            }
            Main.SOUND_MANAGER.playTrack(order, isUrl);
        }
    }

}