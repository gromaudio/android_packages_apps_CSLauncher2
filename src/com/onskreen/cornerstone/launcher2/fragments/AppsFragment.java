package com.onskreen.cornerstone.launcher2.fragments;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onskreen.cornerstone.launcher2.ApplicationInfo;
import com.onskreen.cornerstone.launcher2.ApplicationsAdapter;
import com.onskreen.cornerstone.launcher2.ApplicationsStackLayout;
import com.onskreen.cornerstone.launcher2.CSLauncher2;
import com.onskreen.cornerstone.launcher2.R;

import java.util.ArrayList;
import java.util.List;

public class AppsFragment extends DummyFragment {
    private static final int MAX_RECENT_TASKS = 10; // Maximum number of recent
                                                    // tasks to query

    private ApplicationsStackLayout mApplicationsStack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_apps, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        bindRecents();
    }

    /**
     * Creates a new applications adapter for the grid view and registers it.
     */
    @Override
    protected void bindApplications() {
        super.bindApplications();

        if (this.getResources().getBoolean(R.bool.config_recentsStack)
                && mApplicationsStack == null) {
            mApplicationsStack = (ApplicationsStackLayout) this.getActivity()
                    .findViewById(R.id.faves_and_recents);
            mApplicationsStack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setAdapter() {
        CSLauncher2 activity = (CSLauncher2) this.getActivity();

        mListView.setAdapter(new ApplicationsAdapter(activity, activity
                .getFilteredApplications("com.onskreen.cornerstone", false)));
    }

    /**
     * Refreshes the recently launched applications stacked over the favorites.
     * The number of recents depends on how many favorites are present.
     */
    private void bindRecents() {
        if (this.getResources().getBoolean(R.bool.config_recentsStack)) {
            final PackageManager manager = this.getActivity().getPackageManager();
            final ActivityManager tasksManager = (ActivityManager) this.getActivity()
                    .getSystemService(Application.ACTIVITY_SERVICE);
            final List<ActivityManager.RecentTaskInfo> recentTasks = tasksManager.getRecentTasks(
                    MAX_RECENT_TASKS, 0);

            final int count = recentTasks.size();
            final ArrayList<ApplicationInfo> recents = new ArrayList<ApplicationInfo>();

            for (int i = count - 1; i >= 0; i--) {
                final Intent intent = recentTasks.get(i).baseIntent;

                if (Intent.ACTION_MAIN.equals(intent.getAction()) &&
                        !intent.hasCategory(Intent.CATEGORY_HOME)) {

                    ApplicationInfo info = getApplicationInfo(manager, intent);
                    if (info != null) {
                        info.setIntent(intent);
                        recents.add(info);
                    }
                }
            }

            mApplicationsStack.setRecents(recents);
        }
    }

    private static ApplicationInfo getApplicationInfo(PackageManager manager, Intent intent) {
        final ResolveInfo resolveInfo = manager.resolveActivity(intent, 0);

        if (resolveInfo == null) {
            return null;
        }

        final ApplicationInfo info = new ApplicationInfo();
        final ActivityInfo activityInfo = resolveInfo.activityInfo;
        info.setIcon(activityInfo.loadIcon(manager));
        if (info.getTitle() == null || info.getTitle().length() == 0) {
            info.setTitle(activityInfo.loadLabel(manager));
        }
        if (info.getTitle() == null) {
            info.setTitle("");
        }
        return info;
    }

}
