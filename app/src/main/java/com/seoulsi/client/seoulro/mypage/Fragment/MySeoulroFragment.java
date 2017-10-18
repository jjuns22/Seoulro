package com.seoulsi.client.seoulro.mypage.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.seoulsi.client.seoulro.R;

/**
 * Created by SanJuku on 2017-10-15.
 */

public class MySeoulroFragment extends Fragment {

    public MySeoulroFragment()
    {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_myseoul, container, false);
        return layout;
    }
}

