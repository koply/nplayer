package me.koply.nplayer.commands;

import me.koply.nplayer.api.command.CLICommand;
import me.koply.nplayer.api.command.Command;
import me.koply.nplayer.api.command.CommandClassData;
import me.koply.nplayer.api.command.CommandEvent;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static me.koply.nplayer.api.command.CommandClassData.*;

public class HelpCommand implements CLICommand {

    private String helpMessage;
    public String getHelpMessage() {
        if (helpMessage != null) return helpMessage;
        StringBuilder sb = new StringBuilder();
        Set<Method> duplicateChecker = new HashSet<>();
        for (Map.Entry<String, CommandClassData> entry : CommandHandler.COMMAND_CLASSES.entrySet()) {
            CommandClassData ccd = entry.getValue();
            for (Map.Entry<String, MethodAndAnnotation> innerEntry : ccd.methods.entrySet()) {
                if (duplicateChecker.contains(innerEntry.getValue().method)) continue;
                sb.append(innerEntry.getKey()).append(": ").append(innerEntry.getValue().annotation.desc()).append("\n");
                duplicateChecker.add(innerEntry.getValue().method);
            }
        }
        helpMessage = sb.toString();
        return helpMessage;
    }

    @Command(usages = "help")
    public void som(CommandEvent e) {
        println("im the first martian guy from the universe");
        println(getHelpMessage());
    }

    @Command(usages = "commands")
    public void com(CommandEvent e) {
        println(getHelpMessage());
    }

}