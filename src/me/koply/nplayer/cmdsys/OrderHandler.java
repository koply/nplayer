package me.koply.nplayer.cmdsys;

import me.koply.nplayer.Main;
import me.koply.nplayer.SoundManager;
import org.reflections8.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class OrderHandler {

    public OrderHandler() {
        try {
            registerCommands();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void registerCommands() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections("me.koply.nplayer.commands");
        Set<Class<? extends CLICommand>> classes = reflections.getSubTypesOf(CLICommand.class);
        for (Class<? extends CLICommand> clazz : classes) {

            // the class must be public
            if ((clazz.getModifiers() & Modifier.PUBLIC) != Modifier.PUBLIC) continue;
            Map<String, Method> commandMethodsWithAliases = new HashMap<>();
            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {
                if (!method.getReturnType().equals(Void.TYPE)) continue;
                Command annotation = method.getDeclaredAnnotation(Command.class);
                if (annotation == null) continue;
                Class<?>[] types = method.getParameterTypes();
                if (types.length == 1 && types[0] == CommandEvent.class) {
                    /* void command(CommandEvent e); */
                    for (String usage : annotation.usages()) {
                        commandMethodsWithAliases.put(usage, method);
                    }
                }
            }

            CLICommand instance = clazz.getDeclaredConstructor().newInstance();
            CommandClassData commandClassData = new CommandClassData(clazz, instance, commandMethodsWithAliases);
            CommandDataManager.registerCommand(commandMethodsWithAliases.keySet(), commandClassData);
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

            if (!CommandDataManager.COMMAND_CLASSES.containsKey(args[0])) {
                Main.log.info("There is no command for this entry.");
                continue;
            }

            if (entry.equalsIgnoreCase("exit") || entry.equalsIgnoreCase("quit")) {
                SoundManager.shutdown();
            }

            CommandClassData ccd = CommandDataManager.COMMAND_CLASSES.get(args[0]);
            CommandEvent event = new CommandEvent(args, entry);
            callCommandMethod(event, ccd);
        }
    }

    private void callCommandMethod(CommandEvent event, CommandClassData ccd) {
        try {
            ccd.getMethods().get(event.getArgs()[0]).invoke(ccd.getInstance(), event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}