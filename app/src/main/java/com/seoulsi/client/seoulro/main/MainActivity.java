package com.seoulsi.client.seoulro.main;

import android.content.Intent;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.seoulsi.client.seoulro.R;

import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.login.LoginUserInfo;
import com.seoulsi.client.seoulro.main.Fragment.FacilityFragment;
import com.seoulsi.client.seoulro.main.Fragment.KeyPointFragment;
import com.seoulsi.client.seoulro.main.Fragment.LandScapeFragment;
import com.seoulsi.client.seoulro.network.NetworkService;
import com.seoulsi.client.seoulro.search.SearchInfoActivity;
import com.seoulsi.client.seoulro.mypage.MyPageActivity;
import com.seoulsi.client.seoulro.search.details.DetailsInfo;
import com.seoulsi.client.seoulro.search.details.DetailsResult;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.seoulsi.client.seoulro.R.id.btn_toolBar_mypage;

public class MainActivity extends AppCompatActivity{

    private final String TAG = "MainActivity";
   // private String htmlPageUrl = "http://www.seoul.go.kr/v2012/news/list.html?tr_code=gnb_news";

   // public ImageView news1, news2;
    //public ArrayList<String> news_link = new ArrayList<>();
    private ArrayList<DetailsInfo> detailsDatas;
    private String token;
    private int placeid = 1;
    private NetworkService service;

    @BindView(btn_toolBar_mypage)
    Button BtnToolBarMypage;
    @BindView(R.id.btn_toolBar_search)
    Button BtnToolBarSearch;
    @BindView(R.id.viewPager_main)
    ViewPager viewPagerMain;
    @BindView(R.id.btn_main_facility)
    Button btnMainFacility;
    @BindView(R.id.btn_main_landScape)
    Button btnMainLandScape;
    @BindView(R.id.btn_main_keyPoint)
    Button btnMainKeyPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();
        BtnToolBarMypage.setOnClickListener(onClickListener);
        BtnToolBarSearch.setOnClickListener(onClickListener);

        viewPagerMain.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        viewPagerMain.setCurrentItem(0);

        viewPagerMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        btnMainFacility.setBackgroundResource(R.drawable.mypage_menu_green);
                        btnMainLandScape.setBackgroundResource(R.drawable.mypage_menu_white);
                        btnMainKeyPoint.setBackgroundResource(R.drawable.mypage_menu_white);
                        break;

                    case 1:
                        btnMainFacility.setBackgroundResource(R.drawable.mypage_menu_white);
                        btnMainLandScape.setBackgroundResource(R.drawable.mypage_menu_green);
                        btnMainKeyPoint.setBackgroundResource(R.drawable.mypage_menu_white);
                        break;

                    case 2:
                        btnMainFacility.setBackgroundResource(R.drawable.mypage_menu_white);
                        btnMainLandScape.setBackgroundResource(R.drawable.mypage_menu_white);
                        btnMainKeyPoint.setBackgroundResource(R.drawable.mypage_menu_green);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnMainFacility.setOnClickListener(movePageListener);
        btnMainFacility.setTag(0);
        btnMainLandScape.setOnClickListener(movePageListener);
        btnMainLandScape.setTag(1);
        btnMainKeyPoint.setOnClickListener(movePageListener);
        btnMainKeyPoint.setTag(2);
    }

    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();
            switch (tag) {
                case 0:
                    btnMainFacility.setBackgroundResource(R.drawable.mypage_menu_green);
                    btnMainLandScape.setBackgroundResource(R.drawable.mypage_menu_white);
                    btnMainKeyPoint.setBackgroundResource(R.drawable.mypage_menu_white);
                    break;
                case 1:
                    btnMainFacility.setBackgroundResource(R.drawable.mypage_menu_white);
                    btnMainLandScape.setBackgroundResource(R.drawable.mypage_menu_green);
                    btnMainKeyPoint.setBackgroundResource(R.drawable.mypage_menu_white);
                    break;
                case 2:
                    btnMainFacility.setBackgroundResource(R.drawable.mypage_menu_white);
                    btnMainLandScape.setBackgroundResource(R.drawable.mypage_menu_white);
                    btnMainKeyPoint.setBackgroundResource(R.drawable.mypage_menu_green);
                    break;
            }
            viewPagerMain.setCurrentItem(tag);
        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_toolBar_mypage:
                    Intent mypage = new Intent(MainActivity.this, MyPageActivity.class);
                    startActivity(mypage);
                    break;
                case R.id.btn_toolBar_search:
                    NetWorking();
                    break;
            }
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter {

        public pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FacilityFragment();
                case 1:
                    return new LandScapeFragment();
                case 2:
                    return new KeyPointFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
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

                        Intent intent = new Intent(MainActivity.this, SearchInfoActivity.class);
                        intent.putExtra("placeid",placeid);
                        intent.putExtra("place_picture",detailsDatas.get(0).place_picture);
                        intent.putExtra("place_name",detailsDatas.get(0).place_name);
                        intent.putExtra("place_address",detailsDatas.get(0).place_address);
                        intent.putExtra("place_info",detailsDatas.get(0).place_info);
                        intent.putExtra("place_tel",detailsDatas.get(0).place_tel);
                        intent.putExtra("place_opentime",detailsDatas.get(0).place_opentime);
                        intent.putExtra("place_introduce",detailsDatas.get(0).place_introduce);

                        startActivity(intent);
                        Toast.makeText(getBaseContext(), "성공", Toast.LENGTH_SHORT).show();
                    } else if (response.body().msg.equals("1")) {
                        Toast.makeText(getBaseContext(), "유효하지 않은 토큰에러", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getBaseContext(), "해당계정이 없습니다.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d(TAG, "실패");
                    Toast.makeText(getBaseContext(), "커넥팅 에러", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<DetailsResult> call, Throwable t) {
                Toast.makeText(getBaseContext(), "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
