package me.koply.nplayer.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EventManager {

    private static final Map<Class<?>, Set<Object>> listeners = new HashMap<>();

    // move this event to NPlayer.java
    // because this class must contains internal calls
    public static void registerListener(Object listener) {
        // TODO: Find out the overrided method.
    }


    void pushEvent() {}

}