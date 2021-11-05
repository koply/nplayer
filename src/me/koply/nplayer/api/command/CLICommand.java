package me.koply.nplayer.api.command;

import me.koply.nplayer.Main;
import me.koply.nplayer.commands.OrderHandler;

import java.util.Scanner;

public interface CLICommand {
    Scanner SC = OrderHandler.SC;
    default void println(String o) {
        Main.log.info(o);
    }
}