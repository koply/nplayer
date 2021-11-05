package me.koply.nplayer.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.koply.nplayer.Main;
import me.koply.nplayer.api.command.CLICommand;
import me.koply.nplayer.api.command.Command;
import me.koply.nplayer.api.command.CommandEvent;
import me.koply.nplayer.util.TrackBoxBuilder;
import me.koply.nplayer.util.Util;

import static me.koply.nplayer.Main.SOUND_MANAGER;

public class TrackControlCommands implements CLICommand {

    @Command(usages = {"play", "p"})
    public void play(CommandEvent e) {
        String order = e.getPureCommand().substring(e.getArgs()[0].length()).trim();
        if (order.equals("")) {
            println("Unknown...");
        } else {
            boolean isUrl = true;
            if (!Util.isUrl(order)) {
                order = "ytsearch: " + order;
                isUrl = false;
            }
            SOUND_MANAGER.playTrack(order, isUrl);
        }
    }

    @Command(usages = "pause")
    public void pause(CommandEvent e) {
        SOUND_MANAGER.pause();
    }

    @Command(usages = "resume")
    public void resume(CommandEvent e) {
        SOUND_MANAGER.resume();
    }

    @Command(usages = "stop")
    public void stop(CommandEvent e) {
        SOUND_MANAGER.stop();
    }

    @Command(usages = "volume")
    public void volume(CommandEvent e) {
        if (e.getArgs().length < 1) {
           println("Current volume: " + SOUND_MANAGER.getVolume());
        }
        Integer volume = Util.parseInt(e.getArgs()[1]);
        if (volume == null) {
            Main.log.info("Invalid volume. Current volume: " + SOUND_MANAGER.getVolume());
        } else {
            volume = volume > 100 ? 100 : volume < 0 ? 0 : volume;
            SOUND_MANAGER.setVolume(volume);
            Main.log.info("New volume: " + volume);
        }
    }

    @Command(usages = "now")
    public void nowPlaying(CommandEvent e) {
        AudioTrack nowPlayin = SOUND_MANAGER.getPlayingTrack();
        boolean isPause = SOUND_MANAGER.isPaused();
        int volume = SOUND_MANAGER.getVolume();
        if (nowPlayin == null) {
            println("Silence...");
        } else {
            System.out.println(TrackBoxBuilder.buildTrackBox(75, nowPlayin, isPause, volume));
        }
    }

    @Command(usages = "eq")
    public void eq(CommandEvent e) {
        SOUND_MANAGER.activeEqualizer();
        // 0 1     2 3
        // eq bass + 1
        if (e.getArgs().length <= 3) {
            println("Example usage: eq bass + 0.3");
        } else if (e.getArgs()[1].equals("bass")) {
            Float diff = Util.parseFloat(e.getArgs()[3]);
            if (diff == null) {
                println("Invalid diff.");
                return;
            }
            if (e.getArgs()[2].equals("+")) {
                SOUND_MANAGER.increaseBassBoost(diff);
            } else if (e.getArgs()[2].equals("-")) {
                SOUND_MANAGER.decreaseBassBoost(diff);
            }
        }
    }


}