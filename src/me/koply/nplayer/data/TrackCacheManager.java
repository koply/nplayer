package me.koply.nplayer.data;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.koply.nplayer.api.event.PlayerListenerAdapter;
import me.koply.nplayer.api.event.TrackEndEvent;
import me.koply.nplayer.util.FixedStack;

public class TrackCacheManager extends PlayerListenerAdapter {

    /*  last-in-first-out
     *  push -> adds to the top
     *  pop -> gets from the top also removes from stack */

    // 5 tracks kept here to play quickly. 5 because ram usage
    public static final FixedStack<AudioTrack> pastTracks = new FixedStack<>(5);
    public static final FixedStack<AudioTrack> nextTracks = new FixedStack<>(5);

    @Override
    public void onTrackEnd(TrackEndEvent e) {
        pastTracks.push(e.endTrack);
    }

    // todo: read and write system for pastTracks
    // todo: track history preservence with sqlite local database
}