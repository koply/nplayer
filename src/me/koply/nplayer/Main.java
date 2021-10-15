package me.koply.nplayer;

import javax.sound.sampled.LineUnavailableException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Main {

    private static final OrderHandler ORDER_HANDLER = new OrderHandler();
    public static SoundManager SOUND_MANAGER;

    static {
        try {
            SOUND_MANAGER = new SoundManager();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static final Logger log = Logger.getLogger("NPlayer: ");

    static {
        log.setUseParentHandlers(false);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new Formatter() {
            private final DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");

            @Override
            public String format(LogRecord record) {
                final String[] splitted = record.getSourceClassName().split("\\.");
                final String name = splitted[splitted.length-1];
                return String.format("[%s] %s -> %s\n", formatter.format(new Date(record.getMillis())), name, record.getMessage());
            }
        });
        log.addHandler(consoleHandler);
    }

    public static void main(String[] args) {
        ORDER_HANDLER.startHandler(args);
    }
}
