package me.koply.nplayer.cmdsys;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandDataManager {

    public static final Map<String, CommandClassData> COMMAND_CLASSES = new HashMap<>();

    public static void registerCommand(Set<String> commandAliases, CommandClassData clazz) {
        for (String str : commandAliases) COMMAND_CLASSES.put(str, clazz);
    }

}