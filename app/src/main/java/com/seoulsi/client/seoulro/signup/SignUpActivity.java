package com.seoulsi.client.seoulro.signup;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.application.ApplicationController;
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

    String nickName="";
    String email="";
    String password = "";
    String rePassword = "";

    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        //서비스 객체 초기화
        service = ApplicationController.getInstance().getNetworkService();

        btnSignupSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()) {
                    final JoinInfo joinInfo = new JoinInfo(nickName, email, password);
                    Call<JoinResult> getJoinResult = service.getJoinResult(joinInfo);
                    getJoinResult.enqueue(new Callback<JoinResult>() {
                        @Override
                        public void onResponse(Call<JoinResult> call, Response<JoinResult> response) {
                            if (response.isSuccessful()) {
                                if (response.body().message.equals("2")) {
                                    //회원가입 성공 시
                                    Toast.makeText(getBaseContext(),"회원가입성공", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else
                                    Toast.makeText(getBaseContext(),"서버연결오류", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JoinResult> call, Throwable t) {

                        }
                    });
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

        if(nickName.equals("")){
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
            editTextSignupEmail.requestFocus();
            return false;
        }
        if (!password.equals(rePassword)) {
            //두 비밀번호가 같지 않을 때,
            Toast.makeText(getBaseContext(), "비밀번호가 일치하지 않습니다. 다시 한번 확인해주세요.", Toast.LENGTH_SHORT).show();
            editTextSignupConfirmPwd.requestFocus();
            return false;
        }
        return true;
    }
}
