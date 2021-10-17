package me.koply.nplayer.keyhook;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import me.koply.nplayer.Main;

public class KeyListener implements NativeKeyListener {

    // previous keyCode 57360
    // next keyCode 57369
    // play/pause keyCode 57378
    // TODO: WINAPI MEDIA INFO HOOK
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == 57378) {
            Main.SOUND_MANAGER.playPauseButton();
        }
    }

}