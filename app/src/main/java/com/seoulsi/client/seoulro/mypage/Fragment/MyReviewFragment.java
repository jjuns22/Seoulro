package com.seoulsi.client.seoulro.mypage.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.login.LoginUserInfo;
import com.seoulsi.client.seoulro.mypage.ItemDataMyReview;
import com.seoulsi.client.seoulro.mypage.MyReviewRecyclerAdapter;
import com.seoulsi.client.seoulro.mypage.MyReviewResult;
import com.seoulsi.client.seoulro.mypage.MyseoulloResult;
import com.seoulsi.client.seoulro.network.NetworkService;
import com.seoulsi.client.seoulro.search.recyclerview.ItemDataReview;
import com.seoulsi.client.seoulro.search.recyclerview.ReviewRecyclerAdapter;
import com.seoulsi.client.seoulro.search.review.ReviewResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SanJuku on 2017-10-15.
 */

public class MyReviewFragment extends Fragment {
    private final String TAG = "MyReviewFragment";
    private RecyclerView myReviewRecyclerview;
    private MyReviewRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    //private ArrayList<WorryInfo> itemDataTabHome; //나중에 데이터 생기면 받아올 배열
    private ArrayList<ItemDataMyReview> itemdatas = new ArrayList<>();
    private NetworkService service;
    private String token;
    private int id = Integer.MAX_VALUE;
    private boolean listIsEnd = false;
    private boolean isListViewAppending = false;
    private boolean isListExpandable = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_myreview, null);

        myReviewRecyclerview = (RecyclerView) view.findViewById(R.id.recyclerview_review_mypage);
        myReviewRecyclerview.setHasFixedSize(true);

        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();

        token = LoginUserInfo.getInstance().getUserInfo().token;

        //레이아웃 매니저 설정
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myReviewRecyclerview.setLayoutManager(linearLayoutManager);

        myReviewRecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

                if (!listIsEnd && lastVisibleItemPosition == itemdatas.size() - 1
                        && !isListViewAppending && totalItemCount > 0 && isListExpandable) {
                    isListViewAppending = true;
                    callAppendList();
                }
            }
        });
        adapter = new MyReviewRecyclerAdapter(itemdatas, clickEvent);
        myReviewRecyclerview.setAdapter(adapter);

        callAppendList();

        return view;
    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = myReviewRecyclerview.getChildPosition(v);
            //Toast.makeText(getContext(), itemPosition + "번 리스트 클릭!!", Toast.LENGTH_SHORT).show();

        }
    };

    private void callAppendList() {

        Call<MyReviewResult> getMyReviewDataResult = service.getMyReviewDataResult(token, id);
        getMyReviewDataResult.enqueue(new Callback<MyReviewResult>() {
            @Override
            public void onResponse(Call<MyReviewResult> call, Response<MyReviewResult> response) {
                Log.d(TAG, "response");
                if (response.isSuccessful()) {
                    if (response.body().msg.equals("6")) {
                         Log.d(TAG, "통신성공");

                        if (response.body().result.size() == 0) {
                            isListExpandable = false;
                        }
                        itemdatas.addAll(response.body().result);
                        adapter.notifyDataSetChanged();
                        try {
                            id = itemdatas.get(itemdatas.size() - 1).article_id;
                        } catch (Exception e) {
                            id = Integer.MAX_VALUE;
                        }
                        isListViewAppending = false;
                    } else {
                        Toast.makeText(getContext(), "서비스 연결 문제", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "통신실패");
                    Toast.makeText(getContext(), "서비스 연결 문제", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyReviewResult> call, Throwable t) {
                Log.i("fail", t.getMessage());
            }
        });
    }
}


