package me.koply.nplayer;

import java.util.Scanner;

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