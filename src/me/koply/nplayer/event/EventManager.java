package me.koply.nplayer.event;

import me.koply.nplayer.Main;
import me.koply.nplayer.api.event.AudioEvent;
import me.koply.nplayer.api.event.PlayEvent;
import me.koply.nplayer.api.event.PlayerListenerAdapter;
import me.koply.nplayer.model.EventListenerData;
import me.koply.nplayer.util.Util;
import org.reflections8.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// internal event methods
public class EventManager {

    // schema: Class<? extends PlayerListenerAdapter>
    private static final Map<Class<? extends AudioEvent>, Set<EventListenerData>> listeners = new HashMap<>();
    static {
        var reflections = new Reflections(AudioEvent.class.getPackage().getName());
        var classes = reflections.getSubTypesOf(AudioEvent.class);
        for (var clazz : classes) {
            var x = new HashSet<EventListenerData>();
            listeners.put(clazz, x);
        }
    }

    public static void registerListener(Object listener) {
        if (listener == null) throw new IllegalArgumentException("The listener cannot be null.");
        if (!(listener instanceof PlayerListenerAdapter)) throw new IllegalArgumentException("The listener must be a PlayerListenerAdapter");
        Class<? extends PlayerListenerAdapter> clazz = listener.getClass().asSubclass(PlayerListenerAdapter.class);
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            Parameter parameter = method.getParameters()[0];
            if (listeners.containsKey(parameter.getType())) {
                listeners.get(parameter.getType()).add(new EventListenerData(method, listener));
            }
        }
    }

    public static void pushEvent(Object eventObject) {
        if (eventObject == null) throw new IllegalArgumentException("The event cannot be null.");
        if (!(eventObject instanceof AudioEvent)) throw new IllegalArgumentException("eventObject must be an AudioEvent instance.");
        if (listeners.containsKey(eventObject.getClass())) {
            // async
            Util.EXECUTOR_SERVICE.submit(() -> {
                try {
                    for (var data : listeners.get(eventObject.getClass())) {
                        data.method.invoke(data.listener, eventObject);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    public static void debugListeners() {
        Main.log.info(listeners.size()  + " - " + listeners.get(PlayEvent.class).size() + " :D");
    }
}