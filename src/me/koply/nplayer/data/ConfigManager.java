package me.koply.nplayer.data;


import me.koply.nplayer.util.LightYML;

import java.io.File;

public class ConfigManager extends LightYML {

    // unnecessary field yet.
    // maybe hot changes from commands
    private final File configFile;

    public ConfigManager(File configFile) {
        super(configFile);
        this.configFile = configFile;
    }

    public static final String DEFAULT = "# Default sqlite. just set it to false to disable it\n" +
                                         "# Possible selections: sqlite, disable\n" +
                                         "db: sqlite\n\n" +
                                         "# Default './data.db'\n" +
                                         "dbfile: ./data.db\n";
}