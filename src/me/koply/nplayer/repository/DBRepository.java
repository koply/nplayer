package me.koply.nplayer.repository;

import me.koply.nplayer.model.HistoryTrack;

import java.util.List;
import java.util.Map;

public interface DBRepository {

    boolean connect(Map<String, String> config);
    void initializeListeners();
    void pushHistory(HistoryTrack track);
    List<HistoryTrack> retrieveHistory();
    boolean isFileDB();

}