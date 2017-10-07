package com.seoulsi.client.seoulro.network;


import com.seoulsi.client.seoulro.signup.JoinInfo;
import com.seoulsi.client.seoulro.signup.JoinResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by SanJuku on 2017-10-06.
 */

public interface NetworkService {
    @POST("/signup/")
    Call<JoinResult> getJoinResult(@Body JoinInfo joinInfo);
}
