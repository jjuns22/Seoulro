package com.seoulsi.client.seoulro.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.seoulsi.client.seoulro.MainActivity;
import com.seoulsi.client.seoulro.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLoginSignUp;
    Button btnLoginSuccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLoginSignUp = (Button)findViewById(R.id.btn_login_sign_up);
        btnLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnLoginSuccess = (Button)findViewById(R.id.btn_login_success);
        btnLoginSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
