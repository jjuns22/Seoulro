package com.seoulsi.client.seoulro.network;


import com.seoulsi.client.seoulro.signup.DupResult;
import com.seoulsi.client.seoulro.signup.JoinInfo;
import com.seoulsi.client.seoulro.signup.JoinResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by SanJuku on 2017-10-06.
 */

public interface NetworkService {
    @POST("/signup/")
    Call<JoinResult> getJoinResult(@Body JoinInfo joinInfo);

    @FormUrlEncoded
    @POST("/signup/dup")
    Call<DupResult> getDupResult(@Field("nickname") String nickname, @Field("email") String email, @Field("flag") int flag);

}
