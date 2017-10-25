package com.seoulsi.client.seoulro.mypage.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.login.LoginUserInfo;
import com.seoulsi.client.seoulro.mypage.ItemDataMyReview;
import com.seoulsi.client.seoulro.mypage.MyPageActivity;
import com.seoulsi.client.seoulro.mypage.MyReviewRecyclerAdapter;
import com.seoulsi.client.seoulro.mypage.MySeoulloDatas;
import com.seoulsi.client.seoulro.mypage.MyseoulloAdapter;
import com.seoulsi.client.seoulro.mypage.MyseoulloResult;
import com.seoulsi.client.seoulro.network.NetworkService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SanJuku on 2017-10-15.
 */

public class MySeoulroFragment extends Fragment {
    //    GridView gridView;
    RecyclerView recyclerView;
    ArrayList<MySeoulloDatas> myseoulloDatas;
    //    StaggeredGridLayoutManager mLayoutManager;
    GridLayoutManager mLayoutManager;
    MyseoulloAdapter adapter;
    private NetworkService service;
    String token;
    int id;

    private ArrayList<String> imgURLs = new ArrayList<String>();
    ImageView img_seoullo;

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
        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();
        token = LoginUserInfo.getInstance().getUserInfo().token;

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_myseoul, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview_myseoul_mypage);
        //각 item의 크기가 일정할 경우 고정
        recyclerView.setHasFixedSize(true);
        myseoulloDatas = new ArrayList<MySeoulloDatas>();
        adapter = new MyseoulloAdapter(myseoulloDatas, clickEvent);
        mLayoutManager = new GridLayoutManager(getActivity(), 3); //설정에서 지정하는 상징의 크기에 따라 칼럼수변화
        recyclerView.setLayoutManager(mLayoutManager);


        recyclerView.setAdapter(adapter);

        id = Integer.MAX_VALUE;
        Call<MyseoulloResult> MySeoulloListData = service.getMyseouloDataResult(token, id);
        MySeoulloListData.enqueue(new Callback<MyseoulloResult>() {
            @Override
            public void onResponse(Call<MyseoulloResult> call, Response<MyseoulloResult> response) {
                if (response.isSuccessful()) {
                    MyseoulloResult myseoulloList = response.body();
                    myseoulloDatas.addAll(myseoulloList.result);
                    Log.i("data", myseoulloList.result.get(0).getimage_url()+" 랑 " +myseoulloList.result.get(0).getPlace_name());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MyseoulloResult> call, Throwable t) {
                Toast.makeText(getActivity(), "서비스 연결을 확인하세요.", Toast.LENGTH_SHORT).show();
            }
        });



        return layout;

    }
    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            final int itemPosition = recyclerView.getChildPosition(v);
            Toast.makeText(getContext(),itemPosition+"번 리스트 클릭!!",Toast.LENGTH_SHORT).show();

        }
    };

}

