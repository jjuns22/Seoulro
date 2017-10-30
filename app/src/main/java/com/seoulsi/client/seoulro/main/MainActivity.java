package com.seoulsi.client.seoulro.main;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
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

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    // private String htmlPageUrl = "http://www.seoul.go.kr/v2012/news/list.html?tr_code=gnb_news";
    private ArrayList<DetailsInfo> detailsDatas;
    private String rankFirstPicture;
    private String rankSecondPicture;
    private String rankThirdPicture;
    private String rankFirstName;
    private String rankSecondName;
    private String rankThirdName;

    @BindView(btn_toolBar_mypage)
    Button BtnToolBarMypage;
    @BindView(R.id.viewPager_main)
    ViewPager viewPagerMain;
    @BindView(R.id.btn_main_facility)
    Button btnMainFacility;
    @BindView(R.id.btn_main_landScape)
    Button btnMainLandScape;
    @BindView(R.id.btn_main_keyPoint)
    Button btnMainKeyPoint;
    @BindView(R.id.circleImageView_first)
    ImageView circleImageViewFirst;
    @BindView(R.id.circleImageView_second)
    ImageView circleImageViewSecond;
    @BindView(R.id.circleImageView_third)
    ImageView circleImageViewThird;
    @BindView(R.id.textview_rank_first)
    TextView textViewRankFirst;
    @BindView(R.id.textview_rank_second)
    TextView textViewRankSecond;
    @BindView(R.id.textview_rank_third)
    TextView textViewRankThird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BtnToolBarMypage.setOnClickListener(onClickListener);

        Intent getData = getIntent();
        rankFirstPicture = getData.getStringExtra("rankFirst");
        rankFirstName = getData.getStringExtra("rankFirstName");
        rankSecondPicture = getData.getStringExtra("rankSecond");
        rankSecondName = getData.getStringExtra("rankSecondName");
        rankThirdPicture = getData.getStringExtra("rankThird");
        rankThirdName = getData.getStringExtra("rankThirdName");

        if(rankFirstPicture!=null) {
            Glide.with(MainActivity.this).load(rankFirstPicture).into(circleImageViewFirst);
            textViewRankFirst.setText(rankFirstName);
        }
        if(rankSecondPicture!=null) {
            Glide.with(MainActivity.this).load(rankSecondPicture).into(circleImageViewSecond);
            textViewRankSecond.setText(rankSecondName);
        }

       if(rankThirdPicture!=null){
            Glide.with(MainActivity.this).load(rankThirdPicture).into(circleImageViewThird);
            textViewRankThird.setText(rankThirdName);
        }
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


}
