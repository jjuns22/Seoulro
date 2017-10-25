package com.seoulsi.client.seoulro.mypage;

import android.content.Intent;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.IntentCompat;
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
import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.login.LoginUserInfo;
import com.seoulsi.client.seoulro.main.MainActivity;
import com.seoulsi.client.seoulro.mypage.Fragment.MyReviewFragment;
import com.seoulsi.client.seoulro.mypage.Fragment.MySeoulroFragment;
import com.seoulsi.client.seoulro.network.NetworkService;
import com.seoulsi.client.seoulro.search.SearchActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity extends AppCompatActivity {
    ViewPager vp;
    Button btnMyreview, btnMyseoul;
    private NetworkService service;
    TextView nickname, introduce;
    ImageView profile_img;
    Button search, home;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        vp = (ViewPager)findViewById(R.id.viewpager_mypage);
        btnMyreview = (Button)findViewById(R.id.btn_myreview_mypage);
        btnMyseoul = (Button)findViewById(R.id.btn_myseoul_mypage);
        nickname = (TextView)findViewById(R.id.text_name_mypage);
        introduce = (TextView)findViewById(R.id.text_introduce_mypage);
        profile_img = (ImageView)findViewById(R.id.img_profile_mypage);
        search = (Button)findViewById(R.id.btn_toolBar_search);
        home = (Button)findViewById(R.id.btn_home);

        service = ApplicationController.getInstance().getNetworkService();

        home.setOnClickListener(click);
        search.setOnClickListener(click);

        token = LoginUserInfo.getInstance().getUserInfo().token;
        Call<MyInfoResult> MyInfo = service.getMyInformation(token);
        MyInfo.enqueue(new Callback<MyInfoResult>() {
            @Override
            public void onResponse(Call<MyInfoResult> call, Response<MyInfoResult> response) {
                if (response.isSuccessful()) {
                    MyInfoResult myInfoList = response.body();
                    nickname.setText(myInfoList.result.get(0).nickname);
                    introduce.setText(myInfoList.result.get(0).introduce);
                    if(!myInfoList.result.get(0).profile_picture.equals("")){
                        Glide.with(getApplicationContext()).load(myInfoList.result.get(0).profile_picture).into(profile_img);
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "어디가 문제지", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyInfoResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서비스 연결을 확인하세요.", Toast.LENGTH_SHORT).show();
                Log.i("err", t.getMessage());
            }
        });





        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0 :
                        btnMyseoul.setBackgroundResource(R.drawable.mypage_menu_green);
                        btnMyreview.setBackgroundResource(R.drawable.mypage_menu_white);

                        break;
                    case 1 :
                        btnMyreview.setBackgroundResource(R.drawable.mypage_menu_green);
                        btnMyseoul.setBackgroundResource(R.drawable.mypage_menu_white);

                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnMyseoul.setOnClickListener(movePageListener);
        btnMyseoul.setTag(0);
        btnMyreview.setOnClickListener(movePageListener);
        btnMyreview.setTag(1);
    }

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            switch (tag){
                case 0 :
                    btnMyseoul.setBackgroundResource(R.drawable.mypage_menu_green);
                    btnMyreview.setBackgroundResource(R.drawable.mypage_menu_white);
                    break;
                case 1:
                    btnMyreview.setBackgroundResource(R.drawable.mypage_menu_green);
                    btnMyseoul.setBackgroundResource(R.drawable.mypage_menu_white);
                    break;
            }
            vp.setCurrentItem(tag);

        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new MySeoulroFragment();
                case 1:
                    return new MyReviewFragment();

                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 2;
        }
    }
    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_home:
                    Intent homeIntent = new Intent(MyPageActivity.this, MainActivity.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);
                    finish();

                    break;
                case R.id.btn_toolBar_search :
                    Intent searchintent = new Intent(MyPageActivity.this, SearchActivity.class);
                    startActivity(searchintent);

                    break;
            }
        }
    };
}
