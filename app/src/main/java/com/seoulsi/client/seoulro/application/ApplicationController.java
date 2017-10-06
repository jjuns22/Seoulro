package com.seoulsi.client.seoulro.application;

/**
 * Created by SanJuku on 2017-10-06.
 */

import android.app.Application;
import com.seoulsi.client.seoulro.network.NetworkService;
import com.seoulsi.client.seoulro.network.AddCookiesInterceptor;
import com.seoulsi.client.seoulro.network.PersistentCookieStore;
import com.seoulsi.client.seoulro.network.ReceivedCookiesInterceptor;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationController extends Application {
    private static ApplicationController instance;    // 먼저 어플리케이션 인스턴스 객체를 하나 선언

    private static String baseUrl = "http://52.78.65.47:3000";  // 베이스 url 초기화


    private NetworkService networkService;                        // 네트워크 서비스 객체 선언

    public static ApplicationController getInstance() {
        return instance;
    }    // 인스턴스 객체 반환  왜? static 안드에서 static 으로 선언된 변수는 매번 객체를 새로 생성하지 않아도 다른 액티비티에서
    //자유롭게 사용가능합니다.

    public NetworkService getNetworkService() {
        return networkService;
    }    // 네트워크서비스 객체 반환


    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationController.instance = this; //인스턴스 객체 초기화
        buildService();
    }

    public void buildService() {
        PersistentCookieStore cookieStore = new PersistentCookieStore(this);
        CookieManager cookieManager = new CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL);
        AddCookiesInterceptor in1 = new AddCookiesInterceptor(this);
        ReceivedCookiesInterceptor in2 = new ReceivedCookiesInterceptor(this);
//return OkHttpClient

        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .addNetworkInterceptor(in1)
                        .addInterceptor(in2)
                        .build())
                .build();

        networkService = retrofit.create(NetworkService.class);

    }
}

