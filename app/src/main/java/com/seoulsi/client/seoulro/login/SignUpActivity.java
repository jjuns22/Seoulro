package com.seoulsi.client.seoulro.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.seoulsi.client.seoulro.R;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextSignupNickname;
    EditText editTextSignupEmail;
    EditText editTextSignupPwd;
    EditText editTextSignupConfirmPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


    }
}
