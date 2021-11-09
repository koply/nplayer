package me.koply.nplayer.api.command;

import me.koply.nplayer.Main;
import me.koply.nplayer.commands.CommandHandler;

import java.util.Scanner;

public interface CLICommand {
    Scanner SC = CommandHandler.SC;
    default void println(String o) {
        Main.log.info(o);
    }
}