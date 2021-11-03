package me.koply.nplayer.model;

import com.eclipsesource.json.JsonObject;

public class TrackData {
    public final String title;
    public final String author;
    public final long length;
    public final String uri;
    public final long lastPlayed;

    public TrackData(String title, String author, long length, String uri, long lastPlayed) {
        this.title = title;
        this.author = author;
        this.length = length;
        this.uri = uri;
        this.lastPlayed = lastPlayed;
    }

    public JsonObject toJson() {
        return new JsonObject()
                .add("title", title)
                .add("author", author)
                .add("length", length)
                .add("uri", uri)
                .add("lastPlayed", lastPlayed);
    }

    public static TrackData fromJson(JsonObject obj) {
        return new TrackData(
                obj.getString("title", ""),
                obj.getString("author", ""),
                obj.getLong("length", 0),
                obj.getString("uri", ""),
                obj.getLong("lastPlayed", 0)
        );
    }
}