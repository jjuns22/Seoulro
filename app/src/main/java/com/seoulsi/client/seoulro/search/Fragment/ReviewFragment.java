package com.seoulsi.client.seoulro.search.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.network.NetworkService;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.search.recyclerview.ItemDataReview;
import com.seoulsi.client.seoulro.search.recyclerview.ReviewRecyclerAdapter;
import com.seoulsi.client.seoulro.search.review.ReviewInfo;
import com.seoulsi.client.seoulro.search.review.ReviewResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SanJuku on 2017-10-15.
 */

public class ReviewFragment extends Fragment {
    final String TAG = "ReviewFragment";
    private int id = 10000;
    private String placenum = "1";
    private RecyclerView mrecyclerview;
    private ReviewRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ReviewInfo> itemDataReview; //데이터 생기면 받아올 배열
    //private ArrayList<ItemDataReview> itemdatas;  //임시데이터 저장
    private NetworkService service;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_review, null);
        mrecyclerview = (RecyclerView) view.findViewById(R.id.recyclerview_review);
        mrecyclerview.setHasFixedSize(true);

        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();

        //레이아웃 매니저 설정
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecyclerview.setLayoutManager(linearLayoutManager);


        //각 배열에 모델 개체를 가지는 ArrayList 초기화
        itemDataReview = new ArrayList<ReviewInfo>();
        adapter = new ReviewRecyclerAdapter(itemDataReview, clickEvent);
        mrecyclerview.setAdapter(adapter);

        NetWorking();
        return view;
    }
    public void NetWorking(){
        Call<ReviewResult> getReview = service.getReview(placenum, id);
        getReview.enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                Log.d(TAG, "response");
                if (response.isSuccessful()) {
                    if(response.body().msg.equals("3")) {
                        itemDataReview = new ArrayList<ReviewInfo>();
                        itemDataReview = response.body().result;
                        adapter.setAdapter(itemDataReview);
                        //Log.d(TAG, "길이 : " +itemDataTabHome.size());
                    }
                } else{
                    Toast.makeText(getContext(),"커넥팅 에러",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {
                Log.i("fail", t.getMessage());
            }
        });
    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = mrecyclerview.getChildPosition(v);
            Toast.makeText(getContext(), itemPosition + "번 리스트 클릭", Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        NetWorking();
    }
}
