package me.koply.nplayer.cmdsys;

import me.koply.nplayer.Main;

import java.util.Scanner;

public interface CLICommand {
    Scanner SC = OrderHandler.SC;
    default void println(String o) {
        Main.log.info(o);
    }
}