package me.koply.nplayer;

import me.koply.nplayer.cmdsys.OrderHandler;
import me.koply.nplayer.keyhook.KeyListener;

import javax.sound.sampled.LineUnavailableException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Main {

    public static final Logger log = Logger.getLogger("NPlayer: ");
    static {
        log.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            private final DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");

            @Override
            public String format(LogRecord record) {
                /* Unnecessary for now: final String[] splitted = record.getSourceClassName().split("\\.");
                 final String name = splitted[splitted.length-1]; */
                final String date = formatter.format(new Date(record.getMillis()));
                return "[" + date + "] -> " + record.getMessage() + "\n";
            }
        });
        log.addHandler(consoleHandler);
    }

    private static final OrderHandler ORDER_HANDLER = new OrderHandler();
    public static SoundManager SOUND_MANAGER;
    private static final KeyListener keyListener = new KeyListener();

    public static void main(String[] args) {
        keyListener.registerHook();
        try {
            SOUND_MANAGER = new SoundManager();
        } catch (LineUnavailableException ex) {
            log.info("There is a problem while initializing the sound system.");
        }

        ORDER_HANDLER.startNewHandler();
    }
}
