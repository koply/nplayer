package me.koply.nplayer;

import me.koply.nplayer.commands.CommandHandler;
import me.koply.nplayer.data.ConfigManager;
import me.koply.nplayer.data.DatabaseFactory;
import me.koply.nplayer.event.AudioEventDebugger;
import me.koply.nplayer.event.EventManager;
import me.koply.nplayer.keyhook.KeyListener;
import me.koply.nplayer.repository.DBRepository;
import me.koply.nplayer.repository.Repository;
import me.koply.nplayer.sound.SoundManager;
import me.koply.nplayer.util.FileUtil;
import me.koply.nplayer.util.Util;

import javax.sound.sampled.LineUnavailableException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Main {

    public static final Logger log = Logger.getLogger("NPlayer: ");
    static {
        Util.formatLogger(log);
    }
    public static boolean resetConfig = false; // for development
    public static SoundManager soundManager;
    public static DBRepository database;
    private static final KeyListener keyListener = new KeyListener();
    private static final CommandHandler COMMAND_HANDLER = new CommandHandler("me.koply.nplayer.commands");

    public static void main(String[] args) {
        File configFile = new File("./config.yml");
        if (resetConfig || !FileUtil.isFile(configFile)) {
            try {
                configFile.createNewFile();
                FileUtil.writeFile(configFile, ConfigManager.DEFAULT);
            } catch (IOException ex) {
                ex.printStackTrace();
                log.warning("PANIC! Config.yml file could not be accessed");
                return;
            }
        }

        ConfigManager configManager = new ConfigManager(configFile);
        Repository selectedDatabase = Repository.fromName(configManager.get("db"));
        if (selectedDatabase == null) log.info("Database selection: none");
        else {
            database = DatabaseFactory.create(selectedDatabase);
            boolean success = database.connect(configManager);
            if (!success) {
                log.warning("PANIC! Database connection isn't established. Check the credentials/file identifies.");
                return;
            }
        }
        try {
            soundManager = new SoundManager();
        } catch (LineUnavailableException ex) {
            log.info("There is a problem while initializing the sound system.");
        }
        keyListener.registerHook();

        EventManager.registerListener(new AudioEventDebugger());
        EventManager.debugListeners();
        // blocks the main thread
        COMMAND_HANDLER.startNewHandler();
    }
}