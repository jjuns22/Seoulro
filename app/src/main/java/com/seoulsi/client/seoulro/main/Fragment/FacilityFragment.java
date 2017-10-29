package com.seoulsi.client.seoulro.main.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seoulsi.client.seoulro.R;

import butterknife.ButterKnife;

/**
 * Created by SanJuku on 2017-10-15.
 */

public class FacilityFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final String TAG = "FacilityFragment";
        final View view = inflater.inflate(R.layout.fragment_facility, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
