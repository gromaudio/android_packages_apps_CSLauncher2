package com.onskreen.cornerstone.launcher2.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.onskreen.cornerstone.launcher2.ApplicationInfo;

/**
 * Starts the selected activity/application in the grid view.
 */
public class ApplicationLauncher implements AdapterView.OnItemClickListener {
    private static final boolean RUN_TASKS_IN_SEPARATE_THREAD = false;

    @Override
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        final Intent intent = ((ApplicationInfo) parent.getItemAtPosition(position)).getIntent();
        final Context context = v.getContext();
        if (RUN_TASKS_IN_SEPARATE_THREAD) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    context.startActivity(intent);
                }
            }).start();
        } else {
            context.startActivity(intent);
        }
    }
}

