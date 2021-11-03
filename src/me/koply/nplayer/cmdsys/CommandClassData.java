package me.koply.nplayer.cmdsys;

import java.lang.reflect.Method;
import java.util.Map;

public class CommandClassData {

    public CommandClassData(Class<? extends CLICommand> clazz, CLICommand instance, Map<String, Method> methods) {
        this.clazz = clazz;
        this.instance = instance;
        this.methods = methods;
    }

    private final Class<? extends CLICommand> clazz;
    private final CLICommand instance;
    private final Map<String, Method> methods;

    public Class<? extends CLICommand> getClazz() {
        return clazz;
    }

    public CLICommand getInstance() {
        return instance;
    }

    public Map<String, Method> getMethods() {
        return methods;
    }
}