package com.seoulsi.client.seoulro.search.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.search.SearchInfoActivity;
import com.seoulsi.client.seoulro.search.details.DetailsInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SanJuku on 2017-10-15.
 */

public class DetailsFragment extends Fragment {

    private final String TAG = "DetailsFragment";

    TextView textViewDetailsPlaceLocation;
    TextView textViewDetailsPlaceTel;
    TextView textViewDetailsPlaceOpenTime;
    TextView textViewDetailsPlaceIntroduce;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final String TAG = "DetailFragment";
        final View view = inflater.inflate(R.layout.fragment_details, null);

        textViewDetailsPlaceLocation = (TextView)view.findViewById(R.id.textview_details_place_location);
        textViewDetailsPlaceTel = (TextView)view.findViewById(R.id.textview_details_place_tel);
        textViewDetailsPlaceOpenTime = (TextView)view.findViewById(R.id.textview_details_place_opentime);
        textViewDetailsPlaceIntroduce = (TextView)view.findViewById(R.id.textview_details_place_introduce);

        if(getArguments() != null) {
            Log.i(TAG,"데이터 받기 성공");
            Bundle extra = getArguments();
            String placeLocation = extra.getString("place_location");
            String placeTel = extra.getString("place_tel");
            String placeOpenTime = extra.getString("place_opentime");
            String placeIntroduce = extra.getString("place_introduce");

            textViewDetailsPlaceLocation.setText(placeLocation);
            textViewDetailsPlaceTel.setText(placeTel);
            textViewDetailsPlaceOpenTime.setText(placeOpenTime);
            textViewDetailsPlaceIntroduce.setText(placeIntroduce);
        } else {
            Log.i(TAG,"데이터 널 값");
        }

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
