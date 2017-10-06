package com.seoulsi.client.seoulro.network;

/**
 * Created by SanJuku on 2017-10-06.
 */
import android.content.Context;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {
    private MySharedPreferences sharedPreferences;

    public ReceivedCookiesInterceptor(Context context) {
        sharedPreferences = MySharedPreferences.getInstanceOf(context);
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            sharedPreferences.putHashSet(MySharedPreferences.KEY_COOKIE, cookies);
        }


        return originalResponse;
    }
}

