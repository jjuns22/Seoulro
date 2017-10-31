package com.seoulsi.client.seoulro.mypage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.login.LoginActivity;
import com.seoulsi.client.seoulro.login.LoginUserInfo;
import com.seoulsi.client.seoulro.main.MainActivity;
import com.seoulsi.client.seoulro.main.proofShot.ProofShotActivity;
import com.seoulsi.client.seoulro.mypage.Fragment.MyReviewFragment;
import com.seoulsi.client.seoulro.mypage.Fragment.MySeoulroFragment;
import com.seoulsi.client.seoulro.network.NetworkService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity extends AppCompatActivity {

    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.imageview_mypage_back)
    ImageView imageViewMyPageBack;
    @BindView(R.id.btn_mypage_proofShot)
    Button btnMypageProofShot;

    private final String TAG = "MyPageAcitivity";

    private final int TAKE_GALLERY = 1;
    ViewPager vp;
//    private MultipartBody.Part profileImgToServer;
//    private File photo;
//    private RequestBody photoBody;
//    private String imgUrl = "";
//    private Uri imgUri;
    Button btnMyreview, btnMyseoul;
    private NetworkService service;
    TextView nickname, introduce;
    ImageView profileImg;
    Button home;
    private String token;

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
        //profileImg = (ImageView) findViewById(R.id.img_profile_mypage);
        home = (Button) findViewById(R.id.btn_home);

        token = LoginUserInfo.getInstance().getUserInfo().token;
        service = ApplicationController.getInstance().getNetworkService();

        home.setOnClickListener(click);

        //뒤로 가기 버튼 클릭
        imageViewMyPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //인즈샷 클릭
        btnMypageProofShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, ProofShotActivity.class);
                startActivity(intent);
            }
        });
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

                } else {
                    Toast.makeText(getApplicationContext(), "서비스 연결 문제", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MyInfoResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서비스 연결 문제", Toast.LENGTH_SHORT).show();
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

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case TAKE_GALLERY:
//                //Log.d(TAG, "requestCode == TAKE_GALLERY");
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // 권한 허가
//                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
//                } else {
//                    // 권한 거부
//                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
//                    //Log.d(TAG, "requestCode == TAKE_GALLERY, 거부");
//                    new TedPermission(MyPageActivity.this)
//                            .setPermissionListener(permissionlistener)
//                            .setDeniedMessage("[설정] > [권한] 에서 권한을 다시 설정할 수 있습니다.")
//                            .setGotoSettingButton(true)
//                            .setPermissions(Manifest.permission.READ_CONTACTS)
//                            .check();
//                }
//                return;
//        }
//    }

//    PermissionListener permissionlistener = new PermissionListener() {
//        @Override
//        public void onPermissionGranted() {
//            Toast.makeText(MyPageActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//            Toast.makeText(MyPageActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
//        }
//    };


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
            }
        }
    };
}
