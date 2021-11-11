package me.koply.nplayer.util;

import java.io.*;
import java.util.HashMap;

public class LightYML extends HashMap<String, String> {
    public LightYML(File file) {
        try {
            InputStream fis = new FileInputStream(file);
            InputStreamReader isreader = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isreader);

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 0 || line.isBlank() || line.startsWith("#")) continue;
                System.out.println(line);
                String[] sided = line.split(":");
                this.put(sided[0].trim(), sided[1].trim());
            }

            isreader.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Example plugin.yml
        author: Koply
        main: me.koply.kcommandoplugin.Main
        version: 1.0
        name: FirstPlugin
        description: this plugin is so wonderful
     */
}