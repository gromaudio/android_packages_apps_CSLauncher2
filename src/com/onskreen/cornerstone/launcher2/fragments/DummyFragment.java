package com.onskreen.cornerstone.launcher2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;

import com.onskreen.cornerstone.launcher2.R;
import com.onskreen.cornerstone.launcher2.listeners.ApplicationLauncher;

abstract class DummyFragment extends Fragment {
    protected AbsListView mListView; // Can be GridView or ListView

    public DummyFragment() {
        // Empty standard
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dummy, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bindApplications();
        bindButtons();
    }

    /**
     * Creates a new applications adapter for the grid view and registers it.
     */
    protected void bindApplications() {
        mListView = null; // Clean up link to view after fragment change
        if (this.getResources().getBoolean(R.bool.config_gridView)) {
            mListView = (GridView) this.getView().findViewById(R.id.all_apps_grid);
        } else {
            mListView = (ListView) this.getView().findViewById(R.id.all_apps_list);
        }

        setAdapter();

        mListView.setSelection(0);
        mListView.setVisibility(View.VISIBLE);
    }

    protected abstract void setAdapter();

    /**
     * Binds actions to the various buttons.
     */
    protected void bindButtons() {
        mListView.setOnItemClickListener(new ApplicationLauncher());
        if (!this.getResources().getBoolean(R.bool.config_gridView)) {
            // mListView.setOnKeyListener(new ListKeyListener());
        }
    }

}
