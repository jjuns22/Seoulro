package com.seoulsi.client.seoulro.main.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.login.LoginUserInfo;
import com.seoulsi.client.seoulro.network.NetworkService;
import com.seoulsi.client.seoulro.search.SearchInfoActivity;
import com.seoulsi.client.seoulro.search.details.DetailsInfo;
import com.seoulsi.client.seoulro.search.details.DetailsResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SanJuku on 2017-10-15.
 */

public class KeyPointFragment extends Fragment{
    final String TAG = "KeyPointFragment";
    private String token;
    private NetworkService service;
    private ArrayList<DetailsInfo> detailsDatas;
    private int placeid;

    @BindView(R.id.imageView_main_keyPoint)
    ImageView imageViewMainKeyPoint;
    @BindView(R.id.btn_keyPlace_seoullo_woonyeong_center)
    Button btnKeyPlaceSeoulloWoonyeongCenter;
    @BindView(R.id.btn_keyPlace_seoul_hwaban)
    Button btnKeyPlaceSeoulHwaban;
    @BindView(R.id.btn_keyPlace_jeongwan_class)
    Button btnKeyPlaceJeongwanClass;
    @BindView(R.id.btn_keyPlace_seoullo_tourist_cafe)
    Button btnKeyPlaceSeoulloTouristCafe;
    @BindView(R.id.btn_keyPlace_seoullo_woonyeongdan)
    Button btnKeyPlaceSeoulloWoonyeongdan;
    @BindView(R.id.btn_keyPlace_seoul_information_center)
    Button btnKeyPlaceSeoulInformationCenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_keypoint, null);

        Log.d("TAG","주요지점 들어옴");
        ButterKnife.bind(this, view);
        token = LoginUserInfo.getInstance().getUserInfo().token;
        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();
        imageViewMainKeyPoint.setBackgroundResource(R.drawable.main_map_keyspace);

        btnKeyPlaceSeoulloWoonyeongCenter.setOnClickListener(onClickListener);
        btnKeyPlaceSeoulHwaban.setOnClickListener(onClickListener);
        btnKeyPlaceJeongwanClass.setOnClickListener(onClickListener);
        btnKeyPlaceSeoulloTouristCafe.setOnClickListener(onClickListener);
        btnKeyPlaceSeoulloWoonyeongdan.setOnClickListener(onClickListener);
        btnKeyPlaceSeoulInformationCenter.setOnClickListener(onClickListener);
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_keyPlace_seoullo_tourist_cafe:
                    placeid = 1;
                    break;
                case R.id.btn_keyPlace_seoul_information_center:
                    placeid = 2;
                    break;
                case R.id.btn_keyPlace_seoul_hwaban:
                    placeid = 4;
                    break;
                case R.id.btn_keyPlace_seoullo_woonyeongdan:
                    placeid = 6;
                    break;
                case R.id.btn_keyPlace_jeongwan_class:
                    placeid = 8;
                    break;
                case R.id.btn_keyPlace_seoullo_woonyeong_center:
                    placeid = 21;
                    break;
            }
            NetWorking();
        }
    };

    void NetWorking() {
        token = LoginUserInfo.getInstance().getUserInfo().token;
        detailsDatas = new ArrayList<>();
        Call<DetailsResult> getDetailsResult = service.getDetailsResult(token, placeid);
        getDetailsResult.enqueue(new Callback<DetailsResult>() {
            @Override
            public void onResponse(Call<DetailsResult> call, Response<DetailsResult> response) {
                Log.d(TAG, "통신 전");
                if (response.isSuccessful()) {
                    Log.d(TAG, "통신 후");
                    if (response.body().msg.equals("6")) {
                        Log.d(TAG, "성공");
                        detailsDatas = response.body().result;

                        Intent intent = new Intent(getContext(), SearchInfoActivity.class);
                        intent.putExtra("placeid",placeid);
                        intent.putExtra("place_picture",detailsDatas.get(0).place_picture);
                        intent.putExtra("place_name",detailsDatas.get(0).place_name);
                        intent.putExtra("place_address",detailsDatas.get(0).place_address);
                        intent.putExtra("place_info",detailsDatas.get(0).place_info);
                        intent.putExtra("place_tel",detailsDatas.get(0).place_tel);
                        intent.putExtra("place_opentime",detailsDatas.get(0).place_opentime);
                        intent.putExtra("place_introduce",detailsDatas.get(0).place_introduce);
                        intent.putExtra("islike",detailsDatas.get(0).islike);
                        intent.putExtra("likeCount",detailsDatas.get(0).like_count);
                        startActivity(intent);
                    } else{
                        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d(TAG, "실패");
                    Toast.makeText(getContext(), "서비스 연결 문제", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<DetailsResult> call, Throwable t) {
                Toast.makeText(getContext(), "서비스 연결 문제", Toast.LENGTH_SHORT).show();
            }
        });
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
