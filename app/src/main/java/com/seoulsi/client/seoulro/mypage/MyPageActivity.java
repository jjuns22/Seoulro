package com.seoulsi.client.seoulro.mypage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
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
import com.seoulsi.client.seoulro.login.LoginActivity;
import com.seoulsi.client.seoulro.login.LoginUserInfo;
import com.seoulsi.client.seoulro.main.MainActivity;
import com.seoulsi.client.seoulro.mypage.Fragment.MyReviewFragment;
import com.seoulsi.client.seoulro.mypage.Fragment.MySeoulroFragment;
import com.seoulsi.client.seoulro.network.NetworkService;
import com.seoulsi.client.seoulro.search.SearchActivity;
import com.seoulsi.client.seoulro.search.WriteReviewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity extends AppCompatActivity {

    @BindView(R.id.btn_logout)
    Button btnLogout;

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

        ButterKnife.bind(this);
        vp = (ViewPager) findViewById(R.id.viewpager_mypage);
        btnMyreview = (Button) findViewById(R.id.btn_myreview_mypage);
        btnMyseoul = (Button) findViewById(R.id.btn_myseoul_mypage);
        nickname = (TextView) findViewById(R.id.text_name_mypage);
        introduce = (TextView) findViewById(R.id.text_introduce_mypage);
        profile_img = (ImageView) findViewById(R.id.img_profile_mypage);
        search = (Button) findViewById(R.id.btn_toolBar_search);
        home = (Button) findViewById(R.id.btn_home);

        service = ApplicationController.getInstance().getNetworkService();

        home.setOnClickListener(click);
        search.setOnClickListener(click);

        //로그아웃 버튼
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyPageActivity.this);

                // 제목셋팅
                alertDialogBuilder.setTitle("로그아웃");

                // AlertDialog 셋팅
                alertDialogBuilder
                        .setMessage("정말 로그아웃 하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("예",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
                                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                                            //안드로이드 버전이 진저브레드가 아니면,
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        } else {
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        }
                                        SharedPreferences sp = MyPageActivity.this.getSharedPreferences("user", MODE_PRIVATE);
                                        sp.edit().remove("token").commit();

                                        startActivity(intent);
                                        // 프로그램을 종료한다
                                        finish();
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // 다이얼로그를 취소한다
                                        dialog.cancel();
                                    }
                                });

                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();
                // 다이얼로그 보여주기
                alertDialog.show();

//                AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
//                // builder.setTitle("AlertDialog Title");
//                builder.setMessage("정말 로그아웃 하시겠습니까??");
//                builder.setPositiveButton("예",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
//                                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
//                                    //안드로이드 버전이 진저브레드가 아니면,
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                } else {
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                }
//                                boolean flag = true;
//                                intent.putExtra("logoutFlag",flag);
//                                //activity stack 비우고 새로 시작하기
//                                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
//                                    //안드로이드 버전이 진저브레드가 아니면,
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                } else {
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                }
//                                startActivity(intent);
//                            }
//                        });
//                builder.setNegativeButton("아니오",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                //Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
//                                dialog.dismiss();
//                            }
//                        });
//                builder.show();

            }
        });


        token = LoginUserInfo.getInstance().getUserInfo().token;
        Call<MyInfoResult> MyInfo = service.getMyInformation(token);
        MyInfo.enqueue(new Callback<MyInfoResult>() {
            @Override
            public void onResponse(Call<MyInfoResult> call, Response<MyInfoResult> response) {
                if (response.isSuccessful()) {
                    MyInfoResult myInfoList = response.body();
                    nickname.setText(myInfoList.result.get(0).nickname);
                    introduce.setText(myInfoList.result.get(0).introduce);
                    if (!myInfoList.result.get(0).profile_picture.equals("")) {
                        Glide.with(getApplicationContext()).load(myInfoList.result.get(0).profile_picture).into(profile_img);
                    }


                } else {
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
                switch (position) {
                    case 0:
                        btnMyseoul.setBackgroundResource(R.drawable.mypage_menu_green);
                        btnMyreview.setBackgroundResource(R.drawable.mypage_menu_white);

                        break;
                    case 1:
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

    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();
            switch (tag) {
                case 0:
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

    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MySeoulroFragment();
                case 1:
                    return new MyReviewFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_home:
                    Intent homeIntent = new Intent(MyPageActivity.this, MainActivity.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);
                    finish();

                    break;
                case R.id.btn_toolBar_search:
                    Intent searchintent = new Intent(MyPageActivity.this, SearchActivity.class);
                    startActivity(searchintent);

                    break;
            }
        }
    };
}
