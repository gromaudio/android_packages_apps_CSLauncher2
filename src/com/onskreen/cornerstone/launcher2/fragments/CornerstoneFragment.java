package com.onskreen.cornerstone.launcher2.fragments;

import com.onskreen.cornerstone.launcher2.ApplicationsAdapter;
import com.onskreen.cornerstone.launcher2.CSLauncher2;

public class CornerstoneFragment extends DummyFragment {
    @Override
    protected void setAdapter() {
        CSLauncher2 activity = (CSLauncher2) this.getActivity();

        mListView
                .setAdapter(new ApplicationsAdapter(activity, activity
                        .getFilteredApplications("com.onskreen.cornerstone", true)));
    }

}
