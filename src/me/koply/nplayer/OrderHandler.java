package me.koply.nplayer;

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

            if (entry.startsWith("play")) play(entry);
            else if (entry.startsWith("pause")) SOUND_MANAGER.pause();
            else if (entry.startsWith("resume")) SOUND_MANAGER.resume();
            else if (entry.startsWith("stop")) SOUND_MANAGER.stop();
            else if (entry.startsWith("gc")) System.gc();
            else if (entry.startsWith("volume")) {
                String[] splitted = entry.split(" ");
                if (splitted.length < 1) continue;
                Integer volume = Util.parseInt(splitted[1]);
                if (volume == null) {
                    Main.log.info("Invalid volume.");
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