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
import android.widget.TextView;
import android.widget.Toast;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.login.LoginUserInfo;
import com.seoulsi.client.seoulro.main.MainActivity;
import com.seoulsi.client.seoulro.mypage.MyPageActivity;
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

public class FacilityFragment extends Fragment{

    final String TAG = "FacilityFragment";
    private String token;
    private NetworkService service;
    private ArrayList<DetailsInfo> detailsDatas;
    private int placeid;
    @BindView(R.id.imageView_main_facility)
    ImageView imageViewMainFacility;
    @BindView(R.id.btn_landScape_roseIce)
    Button btnLandScapeReseIce;
    @BindView(R.id.btn_landScape_roseStage)
    Button btnLandScapeRoseStage;
    @BindView(R.id.btn_landScape_seoullo_exibition)
    Button btnLandScapeSeoulloExibition;
    @BindView(R.id.btn_landScape_bangbang_playground)
    Button btnLandScapeBangBangPlayground;
    @BindView(R.id.btn_landScape_suguk)
    Button btnLandScapeSuguk;
    @BindView(R.id.btn_landScape_zayeon_shimteo)
    Button btnLandScapeZayyeonShimteo;
    @BindView(R.id.btn_landScape_mocryeon_mudae)
    Button btnLandScapeMocreonMudae;
    @BindView(R.id.btn_landScape_mocryeon_dabang)
    Button getBtnLandScapeMocreonDabang;
    @BindView(R.id.btn_landScape_seoullo_gage)
    Button btnLandScapeSeoulloGage;
    @BindView(R.id.btn_landScape_hogisim_see)
    Button btnLandScapeHogisimSee;
    @BindView(R.id.btn_landScape_hogisim_hear)
    Button btnLandScapeHogisimHear;
    @BindView(R.id.btn_landScape_hogisim_shinbi)
    Button btnLandScapeHogisimShinbi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_facility, null);
        ButterKnife.bind(this, view);

        token = LoginUserInfo.getInstance().getUserInfo().token;
        imageViewMainFacility.setBackgroundResource(R.drawable.main_map_facilities);
        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();
        btnLandScapeReseIce.setOnClickListener(onClickListener);
        btnLandScapeRoseStage.setOnClickListener(onClickListener);
        btnLandScapeSeoulloExibition.setOnClickListener(onClickListener);
        btnLandScapeBangBangPlayground.setOnClickListener(onClickListener);
        btnLandScapeSuguk.setOnClickListener(onClickListener);
        btnLandScapeZayyeonShimteo.setOnClickListener(onClickListener);
        btnLandScapeMocreonMudae.setOnClickListener(onClickListener);
        getBtnLandScapeMocreonDabang.setOnClickListener(onClickListener);
        btnLandScapeSeoulloGage.setOnClickListener(onClickListener);
        btnLandScapeHogisimSee.setOnClickListener(onClickListener);
        btnLandScapeHogisimHear.setOnClickListener(onClickListener);
        btnLandScapeHogisimShinbi.setOnClickListener(onClickListener);
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_landScape_hogisim_hear:
                    placeid = 7;
                    break;
                case R.id.btn_landScape_roseIce:
                    placeid = 9;
                    break;
                case R.id.btn_landScape_roseStage:
                    placeid = 10;
                    break;
                case R.id.btn_landScape_seoullo_exibition:
                    placeid = 11;
                    break;
                case R.id.btn_landScape_bangbang_playground:
                    placeid = 12;
                    break;
                case R.id.btn_landScape_zayeon_shimteo:
                    placeid = 13;
                    break;
                case R.id.btn_landScape_suguk:
                    placeid = 14;
                    break;
                case R.id.btn_landScape_mocryeon_mudae:
                    placeid = 15;
                    break;
                case R.id.btn_landScape_mocryeon_dabang:
                    placeid = 16;
                    break;
                case R.id.btn_landScape_seoullo_gage:
                    placeid = 17;
                    break;
                case R.id.btn_landScape_hogisim_shinbi:
                    placeid = 19;
                    break;
                case R.id.btn_landScape_hogisim_see:
                    placeid = 20;
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

                        startActivity(intent);
                        //Toast.makeText(getBaseContext(), "성공", Toast.LENGTH_SHORT).show();
                    } else if (response.body().msg.equals("1")) {
                       // Toast.makeText(getBaseContext(), "유효하지 않은 토큰에러", Toast.LENGTH_SHORT).show();
                    }else{
                        //Toast.makeText(getBaseContext(), "해당계정이 없습니다.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d(TAG, "실패");
                    Toast.makeText(getContext(), "커넥팅 에러", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<DetailsResult> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure", Toast.LENGTH_SHORT).show();
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
