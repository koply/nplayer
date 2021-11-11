package me.koply.nplayer.keyhook;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import me.koply.nplayer.Main;

public class KeyListener implements NativeKeyListener {

    public void registerHook() {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException ex) {
            Main.log.info("There was a problem registering the native hook.");
        }
    }

    // previous keyCode 57360
    // next keyCode 57369
    // play/pause keyCode 57378
    // TODO: native winapi media info box
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == 57378) {
            Main.soundManager.playPauseButton();
        }
    }

}