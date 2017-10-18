package com.seoulsi.client.seoulro.network;


import com.seoulsi.client.seoulro.login.LoginInfo;
import com.seoulsi.client.seoulro.login.LoginResult;
import com.seoulsi.client.seoulro.search.UploadReviewInfo;
import com.seoulsi.client.seoulro.search.UploadReviewResult;
import com.seoulsi.client.seoulro.signup.DupResult;
import com.seoulsi.client.seoulro.signup.JoinInfo;
import com.seoulsi.client.seoulro.signup.JoinResult;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by SanJuku on 2017-10-06.
 */

public interface NetworkService {
    @POST("/signup/")
    Call<JoinResult> getJoinResult(@Body JoinInfo joinInfo);

    @FormUrlEncoded
    @POST("/signup/dup")
    Call<DupResult> getDupResult(@Field("nickname") String nickname, @Field("email") String email, @Field("flag") int flag);

    @POST("/login/")
    Call<LoginResult> checkLogin(@Body LoginInfo loginInfo);

    @Multipart
    @POST("/profile_image/profile_image")
    Call<UploadReviewResult> uploadReview(@Part MultipartBody.Part uploadFile, @Body UploadReviewInfo uploadReviewInfo);
}
