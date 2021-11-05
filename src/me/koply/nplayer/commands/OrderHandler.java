package me.koply.nplayer.commands;

import me.koply.nplayer.Main;
import me.koply.nplayer.api.command.CLICommand;
import me.koply.nplayer.api.command.Command;
import me.koply.nplayer.api.command.CommandClassData;
import me.koply.nplayer.api.command.CommandEvent;
import me.koply.nplayer.sound.SoundManager;
import org.reflections8.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static me.koply.nplayer.api.command.CommandClassData.*;

public class OrderHandler {

    public OrderHandler(String...packages) {
        if (packages.length == 0) throw new IllegalArgumentException("Packages cannot be empty.");
        Set<Class<? extends CLICommand>> classes = new HashSet<>();
        for (String cake : packages) {
            classes.addAll(getClasses(cake));
        }
        try {
            registerCommands(classes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Set<Class<? extends CLICommand>> getClasses(String cake) {
        Reflections reflections = new Reflections(cake);
        return reflections.getSubTypesOf(CLICommand.class);
    }

    public static final Map<String, CommandClassData> COMMAND_CLASSES = new HashMap<>();
    private static void registerCommand(Set<String> commandAliases, CommandClassData clazz) {
        for (String str : commandAliases) COMMAND_CLASSES.put(str, clazz);
    }

    private void registerCommands(Set<Class<? extends CLICommand>> classes) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Class<? extends CLICommand> clazz : classes) {

            // the class must be public
            // bitwise way to do this: (clazz.getModifiers() & Modifier.PUBLIC) != Modifier.PUBLIC
            if (!Modifier.isPublic(clazz.getModifiers())) continue;
            Map<String, MethodAndAnnotation> commandMethodsWithAliases = new HashMap<>();
            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {
                if (!method.getReturnType().equals(Void.TYPE)) continue;
                Command annotation = method.getDeclaredAnnotation(Command.class);
                if (annotation == null) continue;
                Class<?>[] types = method.getParameterTypes();
                if (types.length == 1 && types[0] == CommandEvent.class) {
                    /* void command(CommandEvent e); */
                    MethodAndAnnotation maa = new MethodAndAnnotation(method, annotation);
                    for (String usage : annotation.usages()) {
                        commandMethodsWithAliases.put(usage, maa);
                    }
                }
            }

            CLICommand instance = clazz.getDeclaredConstructor().newInstance();
            CommandClassData commandClassData = new CommandClassData(clazz, instance, commandMethodsWithAliases);
            registerCommand(commandMethodsWithAliases.keySet(), commandClassData);
        }
    }

    public static final Scanner SC = new Scanner(System.in);
    public static boolean isRunning = true;

    /**
     * Blocks the caller thread for handle commands from cli.
     */
    public void startNewHandler() {
        while (isRunning) {
            String entry = SC.nextLine().trim();
            String[] args = entry.split(" ");

            if (!COMMAND_CLASSES.containsKey(args[0])) {
                Main.log.info("There is no command for this entry.");
                continue;
            }

            if (entry.equalsIgnoreCase("exit") || entry.equalsIgnoreCase("quit")) {
                SoundManager.shutdown();
            }

            CommandClassData ccd = COMMAND_CLASSES.get(args[0]);
            CommandEvent event = new CommandEvent(args, entry);
            callCommandMethod(event, ccd);
        }
    }

    private void callCommandMethod(CommandEvent event, CommandClassData ccd) {
        try {
            ccd.methods.get(event.getArgs()[0]).method.invoke(ccd.instance, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}