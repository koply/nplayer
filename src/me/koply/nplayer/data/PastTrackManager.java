package me.koply.nplayer.data;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.koply.nplayer.model.TrackData;

import java.util.ArrayList;
import java.util.List;

public class PastTrackManager {

    // to avoid confusion here is an api design

    // 5 tracks kept here to play quickly. 5 because ram usage
    private static final List<AudioTrack> hotTracks = new ArrayList<>();
    private static final List<TrackData> pastTracks = new ArrayList<>();
    public static final int fixedHotTracksSize = 5;

    public void addTrack(AudioTrack audioTrack) {
        hotTracks.add(audioTrack);
    }

    public AudioTrack getLastTrack(boolean removeIt) {
        AudioTrack lastTrack = hotTracks.get(hotTracks.size()-1);
        if (removeIt) hotTracks.remove(lastTrack);
        return lastTrack;
    }

    // todo: fix sized push-pull system for hotTracks
    // todo: read and write system for pastTracks
    // json file

}