package me.koply.nplayer.commands;

import me.koply.nplayer.cmdsys.CLICommand;
import me.koply.nplayer.cmdsys.Command;
import me.koply.nplayer.cmdsys.CommandEvent;

public class HelpCommand implements CLICommand {

    @Command(usages = "help")
    public void som(CommandEvent e) {
        println("im the first martian guy from the universe");
    }

}