package com.onskreen.cornerstone.launcher2.listeners;

import android.view.KeyEvent;
import android.view.View;

/**
 * Specific key listener for apps list
 */
public class ListKeyListener implements View.OnKeyListener {
    public ListKeyListener() {
        // Empty standard
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.isLongPress()) { // KeyEvent.FLAG_LONG_PRESS
            switch (keyCode) {
                case KeyEvent.KEYCODE_ENTER:
                case KeyEvent.KEYCODE_DPAD_CENTER:
//                    Intent intent = new Intent("android.intent.action.CORNERSTONE_TOGGLE");
//                    v.getContext().sendBroadcast(intent);
                    return true;
            }
        } else if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    v.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                            KeyEvent.KEYCODE_DPAD_UP));
                    v.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,
                            KeyEvent.KEYCODE_DPAD_UP));
                    return true;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    v.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                            KeyEvent.KEYCODE_DPAD_DOWN));
                    v.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,
                            KeyEvent.KEYCODE_DPAD_DOWN));
                    return true;
                case KeyEvent.KEYCODE_ENTER:
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    event.startTracking();
                    return false;
            }
        }
        return false;
    }
}