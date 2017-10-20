package com.seoulsi.client.seoulro.signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.MotionEvent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.application.ApplicationController;
import com.seoulsi.client.seoulro.login.LoginActivity;
import com.seoulsi.client.seoulro.network.NetworkService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.edittext_signup_nickname)
    EditText editTextSignupNickname;
    @Nullable
    @BindView(R.id.edittext_signup_email)
    EditText editTextSignupEmail;
    @Nullable
    @BindView(R.id.edittext_signup_pwd)
    EditText editTextSignupPwd;
    @Nullable
    @BindView(R.id.edittext_signup_confirm_pwd)
    EditText editTextSignupConfirmPwd;
    @Nullable
    @BindView(R.id.btn_signup_success)
    Button btnSignupSuccess;

    String nickName = "";
    String email = "";
    String password = "";
    String rePassword = "";

    public int nickdupcount, emaildupcount;
    public int nickfocus;
    public int emailfocus;
    public int pwdFocus;

    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();

        nickdupcount = 0;
        emaildupcount = 0;
        nickfocus = 0;
        emailfocus = 0;


        //포커스 이동
        editTextSignupNickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    nickfocus = 1;
                    nickdupcount = 0;
                } else {

                    nickName = editTextSignupNickname.getText().toString();
                    email = editTextSignupEmail.getText().toString();
                    Log.i("msg", "닉네임 포커스 잃어" + nickName + "/" + nickfocus);
                    DupValid();

                }
            }
        });
        editTextSignupEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    emailfocus = 1;
                    emaildupcount = 0;
                } else {

                    nickName = editTextSignupNickname.getText().toString();
                    email = editTextSignupEmail.getText().toString();
                    Log.i("msg", "이메일 포커스 잃어" + email + "/" + emailfocus);
                    DupValid();

                }
            }
        });

       /* editTextSignupConfirmPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    isValidPwd();
                }
            }
        });*/

        btnSignupSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid() && nickdupcount == 1 && emaildupcount == 1) {
                    final JoinInfo joinInfo = new JoinInfo(nickName, email, password);
                    Call<JoinResult> getJoinResult = service.getJoinResult(joinInfo);
                    getJoinResult.enqueue(new Callback<JoinResult>() {
                        @Override
                        public void onResponse(Call<JoinResult> call, Response<JoinResult> response) {
                            Log.e("test", "통신 성공 전");
                            if (response.isSuccessful()) {
                                Log.e("test", "통신 성공 후");
                                if (response.body().msg.equals("2")) {
                                    //회원가입 성공 시
                                    Toast.makeText(getBaseContext(), "회원가입성공", Toast.LENGTH_SHORT).show();
                                    SharedPreferences userInfo;
                                    userInfo = getSharedPreferences("user", MODE_PRIVATE);

                                    SharedPreferences.Editor editor = userInfo.edit();

                                    //sharedPreferences에 유저정보 객체로 저장
                                    Gson gson = new Gson();
                                    String json = gson.toJson(joinInfo);
                                    editor.putString("email", joinInfo.email);
                                    editor.putString("password", joinInfo.password);
                                    editor.putString("nickname", joinInfo.nickname);
                                    editor.commit();
                                    finish();
                                } else
                                    Toast.makeText(getBaseContext(), "서버연결오류", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JoinResult> call, Throwable t) {

                        }
                    });
                } else if (emaildupcount != 1 || nickdupcount != 1) {
                    Toast.makeText(getBaseContext(), "닉네임/ 이메일 중복을 다시 확인합니다.", Toast.LENGTH_SHORT).show();
                    nickName = editTextSignupNickname.getText().toString();
                    email = editTextSignupEmail.getText().toString();
                    DupValid();
                }
            }
        });

    }

    private boolean isValid() {
        nickName = editTextSignupNickname.getText().toString();
        email = editTextSignupEmail.getText().toString();
        password = editTextSignupPwd.getText().toString();
        rePassword = editTextSignupConfirmPwd.getText().toString();

        String regEmail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";

        if (nickName.equals("")) {
            //닉네임 미입력
            Toast.makeText(getBaseContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.equals("")) {
            //이메일 미입력
            Toast.makeText(getBaseContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.equals("") || rePassword.equals("")) {
            //비밀번호 미입력
            Toast.makeText(getBaseContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!email.matches(regEmail)) {
            //이메일 형식을 맞추지 않았을 때,
            Toast.makeText(getBaseContext(), "이메일 형식을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(rePassword)) {
            //두 비밀번호가 같지 않을 때,
            Toast.makeText(getBaseContext(), "비밀번호가 일치하지 않습니다. 다시 한번 확인해주세요.", Toast.LENGTH_SHORT).show();
            //텍스트 : 비밀번호 재입력
            return false;
        }
        return true;
    }

    private boolean isValidEmail() {
        String regEmail = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        if (!email.matches(regEmail)) {
            //이메일 형식을 맞추지 않았을 때,
            Toast.makeText(getBaseContext(), "이메일 형식을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void DupValid() {
        Log.i("msg", "dup 에 들어옴" + nickName + "/" + nickfocus + "/" + nickdupcount);
        if (nickfocus == 1 && !nickName.equals("") && nickdupcount != 1) {
            // 닉네임이 입력되었고 아직 중복검사를 완료하지 않은 상태
            Log.i("msg", "if문 에 들어옴");
            Call<DupResult> getDupResult = service.getDupResult(nickName, "", 1);
            getDupResult.enqueue(new Callback<DupResult>() {
                @Override
                public void onResponse(Call<DupResult> call, Response<DupResult> response) {
                    if (response.isSuccessful()) {
                        DupResult dupResult = response.body();
                        Log.i("msg", "msg/" + dupResult.msg);
                        if (dupResult.msg.equals("3")) {
                            nickdupcount = 1;
                            Toast.makeText(SignUpActivity.this, "닉네임 중복확인 성공", Toast.LENGTH_SHORT).show();
                        } else if (dupResult.msg.equals("4")) {
                            nickdupcount = 0;
                            Toast.makeText(SignUpActivity.this, "이미 사용중인 닉네임 입니다.", Toast.LENGTH_SHORT).show();
                            editTextSignupNickname.requestFocus();
                        } else {
                            nickdupcount = 0;
                            Toast.makeText(SignUpActivity.this, "서버연결오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DupResult> call, Throwable t) {
                    Toast.makeText(getBaseContext(), "서버연결오류", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (emailfocus == 1 && !email.equals("") && emaildupcount != 1) {
            //이메일이 입력되었고 아직 중복검사를 완료하지 않은상태
            if (isValidEmail()) {
                Call<DupResult> getDupResult = service.getDupResult("", email, 2);
                getDupResult.enqueue(new Callback<DupResult>() {
                    @Override
                    public void onResponse(Call<DupResult> call, Response<DupResult> response) {
                        if (response.isSuccessful()) {
                            if (response.body().msg.equals("3")) {
                                emaildupcount = 1;
                                Toast.makeText(SignUpActivity.this, "이메일 중복확인 성공", Toast.LENGTH_SHORT).show();
                            } else if (response.body().msg.equals("4")) {
                                emaildupcount = 0;
                                Toast.makeText(SignUpActivity.this, "이미 사용중인 이메일 입니다.", Toast.LENGTH_SHORT).show();
                                editTextSignupEmail.requestFocus();
                            } else {
                                emaildupcount = 0;
                                Toast.makeText(SignUpActivity.this, "서버연결오류", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DupResult> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "서버연결오류", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (nickdupcount == 1 && emaildupcount == 1) {
            //중복확인이 모두 완료된 상태
            Log.i("msg", "중복확인 모두 완료");

        } else if (nickdupcount != 1 && emaildupcount != 1) {
            Log.i("msg", "둘다 1이 아니다");
        } else {
            Log.i("msg", "문제발생");
        }


    }
}