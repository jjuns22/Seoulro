package com.seoulsi.client.seoulro.mypage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
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
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.login.LoginActivity;
import com.seoulsi.client.seoulro.login.LoginUserInfo;
import com.seoulsi.client.seoulro.main.MainActivity;
import com.seoulsi.client.seoulro.mypage.Fragment.MyReviewFragment;
import com.seoulsi.client.seoulro.mypage.Fragment.MySeoulroFragment;
import com.seoulsi.client.seoulro.mypage.profile.UpdateProfileResult;
import com.seoulsi.client.seoulro.network.NetworkService;
import com.seoulsi.client.seoulro.search.SearchActivity;
import com.seoulsi.client.seoulro.search.UploadReviewResult;
import com.seoulsi.client.seoulro.search.WriteReviewActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity extends AppCompatActivity {

    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.btn_mypage_back)
    Button btnMyPageBack;

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
        btnMyPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        //프로필 이미지 선택
//        profileImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyPageActivity.this);
//
//                // 제목셋팅
//                alertDialogBuilder.setTitle("프로필");
//
//                // AlertDialog 셋팅
//                alertDialogBuilder
//                        .setMessage("프로필 사진 선택")
//                        .setCancelable(false)
//                        .setPositiveButton("앨범에서 사진 선택",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        int permissionCheck = ContextCompat.checkSelfPermission(MyPageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
//
//                                        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
//                                            //권한없음
//                                            ActivityCompat.requestPermissions(MyPageActivity.this,
//                                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                                                    TAKE_GALLERY);
//                                        } else {
//                                            // 권한 있음
//                                            Intent intent = new Intent();
//                                            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                                            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                                            intent.setAction(Intent.ACTION_GET_CONTENT);
//                                            startActivityForResult(intent, TAKE_GALLERY);
//                                        }
//                                    }
//
//                                })
//                        .setNegativeButton("취소",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//
//                // 다이얼로그 생성
//                AlertDialog alertDialog = alertDialogBuilder.create();
//                // 다이얼로그 보여주기
//                alertDialog.show();
//            }
//        });
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
//                    if (!myInfoList.result.get(0).profile_picture.equals("")) {
//                        Glide.with(getApplicationContext()).load(myInfoList.result.get(0).profile_picture).into(profileImg);
//                    }


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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == TAKE_GALLERY) {
//                try {
//                    //이미지 데이터를 비트맵으로 받아온다.
//                    //Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//
//                    //imageViewProfile에 이미지 세팅
//                    //imageViewWriteReviewImg.setImageBitmap(image_bitmap);
//                    Glide.with(getBaseContext())
//                            .load(data.getData())
//                            .into(profileImg);
//                    profileImg.setScaleType(ImageView.ScaleType.FIT_XY);
//                    this.imgUri = data.getData();
//                    imgUrl = this.imgUri.getPath();
//
//                    Log.i(TAG,"imgUrl : "+ imgUrl);
//                    if (imgUrl.equals("") || imgUrl == null) {
//                        photo = null;
//                    } else {
//
//                        /**
//                         * 비트맵 관련한 자료는 아래의 링크에서 참고
//                         * http://mainia.tistory.com/468
//                         */
//
//                        BitmapFactory.Options options = new BitmapFactory.Options();
//                        options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다
//
//                        InputStream in = null;
//                        try {
//                            in = getContentResolver().openInputStream(imgUri);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//
//                        Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
//                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
//                        // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ), 압축된 바이트 배열을 담을 스트림
//                        photoBody = RequestBody.create(MediaType.parse("image/png"), baos.toByteArray());
//                        photo = new File(imgUrl);
//
//                        if (photo != null) {
//                            profileImgToServer = MultipartBody.Part.createFormData("profileimg", photo.getName(), photoBody);
//                        }
//
//                        Call<UpdateProfileResult> getProfileResult = service.getProfileResult(token, profileImgToServer,null);
//                        Log.d(TAG,"프로필사진 통신전");
//                        getProfileResult.enqueue(new Callback<UpdateProfileResult>() {
//                            @Override
//                            public void onResponse(Call<UpdateProfileResult> call, Response<UpdateProfileResult> response) {
//                                if (response.isSuccessful()) {
//                                    Log.d(TAG,"프로필사진 통신 성공");
//                                    if (response.body().msg.equals("5")) {
//                                        Toast.makeText(getBaseContext(), "성공", Toast.LENGTH_SHORT).show();
//                                    }
//                                } else {
//                                    Log.d(TAG,"프로필사진 통신 실패");
//                                    Toast.makeText(getBaseContext(), "실패", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<UpdateProfileResult> call, Throwable t) {
//                                Log.d(TAG,"프로필사진 통신 실패");
//
//                                Toast.makeText(getBaseContext(), "onFailure", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
