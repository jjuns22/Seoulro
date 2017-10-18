package com.seoulsi.client.seoulro.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seoulsi.client.seoulro.main.MainActivity;
import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.mypage.MyPageActivity;
import com.seoulsi.client.seoulro.search.SearchInfoActivity;
import com.seoulsi.client.seoulro.signup.SignUpActivity;
import com.seoulsi.client.seoulro.network.NetworkService;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.textview_login_sign_up)
    TextView textViewLoginSignUp;
    @BindView(R.id.btn_login_success)
    Button btnLoginSuccess;
    @BindView(R.id.edittext_login_email)
    EditText editTextLoginEmail;
    @BindView(R.id.edittext_login_pwd)
    EditText editTextLoginPwd;

    private NetworkService service;
    private String email;
    private String password;

    //Back 키 두번 클릭 여부 확인
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();

        textViewLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnLoginSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextLoginEmail.getText().toString();
                password = editTextLoginPwd.getText().toString();
                //Intent intent = new Intent(getBaseContext(),MainActivity.class);
                //startActivity(intent);
                checkLogin(email,password);
            }
        });

    }

    public void checkLogin(String email, String password) {
        String regEmail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        email = editTextLoginEmail.getText().toString();
        password = editTextLoginPwd.getText().toString();

        if (email.equals("")) {
            //이메일 미입력
            Toast.makeText(getBaseContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals("")) {
            //이메일 미입력
            Toast.makeText(getBaseContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.matches(regEmail)) {
            //이메일 형식을 맞추지 않았을 때,
            Toast.makeText(getBaseContext(), "이메일 형식을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show();
            editTextLoginEmail.requestFocus();
        }
        LoginInfo info = new LoginInfo(email, password);
        //회원 체크
        Call<LoginResult> checkLogin = service.checkLogin(info);
        checkLogin.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {

                    if (response.body().msg.equals("7")) {
                        //로그인 성공 시
                        LoginResult userObj = new LoginResult(response.body().msg, response.body().nickname, response.body().token);
                        LoginUserInfo.getInstance().setUserInfo(userObj.nickname, userObj.token);
                        SharedPreferences userInfo;
                        userInfo = getSharedPreferences("user", MODE_PRIVATE);

                        SharedPreferences.Editor editor = userInfo.edit();

                        editor.putString("nickname",userObj.nickname);
                        editor.putString("token",userObj.token);
                        Log.e("test","nickname : "+LoginUserInfo.getInstance().getUserInfo().nickname);
                        Log.e("test","token : "+LoginUserInfo.getInstance().getUserInfo().token);
                        editor.commit();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);   //임시로 정보보기로

                        startActivity(intent);
                        finish();
                    }else if (response.body().msg.equals("6")) {
                        Log.i("test","비밀번호 오류");
                        //비밀번호 오류
                        Toast.makeText(getBaseContext(), "비밀번호가 일치하지 않습니다.다시 한번 확인해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (response.body().msg.equals("4")) {
                        //회원이 아닌 경우
                        Toast.makeText(getBaseContext(), "회원가입되지 않은 회원 정보입니다. 회원 가입을 해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (response.body().msg.equals("1")) {
                        //이메일, 비밀번호 모두 입력 안헀을 때
                        Toast.makeText(getBaseContext(), "이메일 또는 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        //커넥션 오류
                        Toast.makeText(getBaseContext(), "서버오류", Toast.LENGTH_SHORT).show();
                    }

                }else
                    Toast.makeText(getBaseContext(), "서버오류", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

            }
        });
    }

    //뒤로가기 버튼 클릭
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로 가기 키를 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
