package com.seoulsi.client.seoulro.search.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.network.NetworkService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.search.recyclerview.ItemDataReview;
import com.seoulsi.client.seoulro.search.recyclerview.ReviewRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by SanJuku on 2017-10-15.
 */

public class ReviewFragment extends Fragment {
    private RecyclerView mrecyclerview;
    private ReviewRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    //private ArrayList<WorryInfo> itemDataTabHome; //나중에 데이터 생기면 받아올 배열
    private ArrayList<ItemDataReview> itemdatas;
    private NetworkService service;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_review, null);
        mrecyclerview = (RecyclerView) view.findViewById(R.id.recyclerview_review);
        mrecyclerview.setHasFixedSize(true);

        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();

        //레이아웃 매니저 설정
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecyclerview.setLayoutManager(linearLayoutManager);

        itemdatas = new ArrayList<ItemDataReview>();
        itemdatas.add(new ItemDataReview(R.drawable.login_bg, "1번", "내용","이상준","2017.10.29"));
        itemdatas.add(new ItemDataReview(R.drawable.login_bg, "2번", "내용","김지희","2017.10.29"));
        itemdatas.add(new ItemDataReview(R.drawable.login_bg, "3번", "내용","김다혜","2017.10.29"));
        itemdatas.add(new ItemDataReview(R.drawable.login_bg, "4번", "내용","박성준","2017.10.29"));
        itemdatas.add(new ItemDataReview(R.drawable.login_bg, "5번", "내용","정승후","2017.10.29"));
        itemdatas.add(new ItemDataReview(R.drawable.login_bg, "6번", "내용","배지원","2017.10.29"));

        adapter = new ReviewRecyclerAdapter(itemdatas,clickEvent);
        mrecyclerview.setAdapter(adapter);
        return view;
    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = mrecyclerview.getChildPosition(v);
            Toast.makeText(getContext(),itemPosition+"번 리스트 클릭!!",Toast.LENGTH_SHORT).show();

        }
    };
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
