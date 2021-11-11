package me.koply.nplayer.repository;

import me.koply.nplayer.model.HistoryTrack;

import java.util.List;
import java.util.Map;

public class SqliteRepository implements DBRepository {

    // TODO write the methods

    @Override
    public boolean connect(Map<String, String> config) {
        return false;
    }

    @Override
    public void initializeListeners() {

    }

    @Override
    public void pushHistory(HistoryTrack track) {

    }

    @Override
    public List<HistoryTrack> retrieveHistory() {
        return null;
    }

    @Override
    public boolean isFileDB() {
        return false;
    }
}