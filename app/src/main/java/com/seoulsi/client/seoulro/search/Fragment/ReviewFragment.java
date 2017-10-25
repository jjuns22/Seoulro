package com.seoulsi.client.seoulro.search.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.login.LoginUserInfo;
import com.seoulsi.client.seoulro.network.NetworkService;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.search.ReviewDetailsActivity;
import com.seoulsi.client.seoulro.search.WriteReviewActivity;
import com.seoulsi.client.seoulro.search.recyclerview.ItemDataReview;
import com.seoulsi.client.seoulro.search.recyclerview.ReviewRecyclerAdapter;
import com.seoulsi.client.seoulro.search.review.ReviewInfo;
import com.seoulsi.client.seoulro.search.review.ReviewResult;
import com.seoulsi.client.seoulro.search.review.UpdateReviewInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by SanJuku on 2017-10-15.
 */

public class ReviewFragment extends Fragment {

    @BindView(R.id.fab)
    FloatingActionButton fab;

    final static int REQUEST_WRITE_REVIEW = 1004;
    final String TAG = "ReviewFragment";
    private int id = Integer.MAX_VALUE;
    private int placeid;
    private String placeName;
    private String userNickName;

    private RecyclerView mrecyclerview;
    private ReviewRecyclerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ReviewInfo> itemDataReview = new ArrayList<>(); //데이터 생기면 받아올 배열

    private boolean listIsEnd = false;
    private boolean isListViewAppending = false;
    private boolean isListExpandable = true;
    private NetworkService service;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_review, null);

        ButterKnife.bind(this, view);

        Bundle getData = getArguments();
        placeName = getData.getString("placeName");
        placeid = getData.getInt("placeid");

        mrecyclerview = (RecyclerView) view.findViewById(R.id.recyclerview_review);
        mrecyclerview.setHasFixedSize(true);

        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();

        //레이아웃 매니저 설정
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecyclerview.setLayoutManager(linearLayoutManager);

        //플로팅버튼 누르면
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WriteReviewActivity.class);
                intent.putExtra("placename", placeName);
                intent.putExtra("placeid", placeid);
                startActivityForResult(intent,REQUEST_WRITE_REVIEW);
                //startActivity(intent);

            }
        });

        mrecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

                if (!listIsEnd && lastVisibleItemPosition == itemDataReview.size() - 1
                        && !isListViewAppending && totalItemCount > 0 && isListExpandable) {
                    isListViewAppending = true;
                    callAppendList();
                }
            }
        });

        //각 배열에 모델 개체를 가지는 ArrayList 초기화
        adapter = new ReviewRecyclerAdapter(itemDataReview, clickEvent);
        mrecyclerview.setAdapter(adapter);

        callAppendList();
        return view;
    }


    private void callAppendList() {
        Call<ReviewResult> getReview = service.getReview(placeid, id);
        getReview.enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                Log.d(TAG, "response");
                if (response.isSuccessful()) {
                    if (response.body().msg.equals("3")) {
                        Log.d(TAG, "통신성공");

                        if (response.body().result.size() == 0) {
                            isListExpandable = false;
                        }
                        itemDataReview.addAll(response.body().result);
                        adapter.notifyDataSetChanged();
                        try {
                            id = itemDataReview.get(itemDataReview.size() - 1).article_id;
                        } catch (Exception e) {
                            id = Integer.MAX_VALUE;
                        }
                        isListViewAppending = false;
                    }
                } else {
                    Log.d(TAG, "통신실패");
                    Toast.makeText(getContext(), "커넥팅 에러", Toast.LENGTH_SHORT).show();
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
            //Toast.makeText(getContext(), itemPosition + "번 리스트 클릭", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), ReviewDetailsActivity.class);
            intent.putExtra("writer", itemDataReview.get(itemPosition).nickname);
            intent.putExtra("title", itemDataReview.get(itemPosition).title);
            intent.putExtra("content", itemDataReview.get(itemPosition).content);
            intent.putExtra("placePicture", itemDataReview.get(itemPosition).place_picture);

            startActivity(intent);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            Log.i(TAG,"startActivityForResult 값 전달 실패");
        }

        if (requestCode == REQUEST_WRITE_REVIEW) {
            Log.i(TAG,"ReviewFragment로 돌아옴");
            /*String placeImgUrl = data.getStringExtra("imgUrl");
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");

            itemDataReview = new ArrayList<>();
            itemDataReview.get(0).nickname = LoginUserInfo.getInstance().getUserInfo().nickname;
            itemDataReview.get(0).title = title;
            Log.i(TAG, "title = " + itemDataReview.get(0).title);
            itemDataReview.get(0).content = content;
            itemDataReview.get(0).place_picture = placeImgUrl;
            adapter.updateAdapter(itemDataReview);*/
            callAppendList();
        }
    }
}
