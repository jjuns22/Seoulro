package com.seoulsi.client.seoulro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.seoulsi.client.seoulro.R;
import com.seoulsi.client.seoulro.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class); //LoginActivity 로 다시 바꿔야함
                startActivity(intent);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0, 3000);
    }
}
