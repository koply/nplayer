package me.koply.nplayer.data;

import me.koply.nplayer.repository.DBRepository;
import me.koply.nplayer.repository.Repository;
import me.koply.nplayer.repository.SqliteRepository;

import java.lang.reflect.InvocationTargetException;

public class DatabaseFactory {

    public static DBRepository create(Repository choice) {
        try {
            return choice.repositoryClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
            return new SqliteRepository();
        }
    }
}