package com.seoulsi.client.seoulro.network;


import com.seoulsi.client.seoulro.login.LoginInfo;
import com.seoulsi.client.seoulro.login.LoginResult;
import com.seoulsi.client.seoulro.mypage.MyInfoResult;
import com.seoulsi.client.seoulro.mypage.MyReviewResult;
import com.seoulsi.client.seoulro.mypage.MyseoulloResult;
import com.seoulsi.client.seoulro.search.UploadReviewResult;
import com.seoulsi.client.seoulro.search.details.DetailsResult;
import com.seoulsi.client.seoulro.search.like.IsLikeInfo;
import com.seoulsi.client.seoulro.search.like.IsLikeResult;
import com.seoulsi.client.seoulro.search.review.ReviewResult;
import com.seoulsi.client.seoulro.signup.DupResult;
import com.seoulsi.client.seoulro.signup.JoinInfo;
import com.seoulsi.client.seoulro.signup.JoinResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @POST("/place/upload")
    Call<UploadReviewResult> uploadReview(@Part MultipartBody.Part placeimage,
                                          @Header("Authorization") String token,
                                          @Part("title") RequestBody title,
                                          @Part("content") RequestBody content,
                                          @Part("placeid") RequestBody placeid);

    @GET("/mypage/myseoullo/{id}")
    Call<MyseoulloResult> getMyseouloDataResult(@Header("Authorization") String token, @Path("id") int id);

    @GET("/mypage/myarticle/{id}")
    Call<MyReviewResult> getMyReviewDataResult(@Header("Authorization") String token, @Path("id") int id);

    @GET("/mypage/myinfo")
    Call<MyInfoResult> getMyInformation(@Header("Authorization") String token);


    //@Part("placeid") RequestBody placeid);
    @GET("/place/review")
    Call<ReviewResult> getReview(@Query("placeid") int placeid, @Query("id") int id);

    @POST("/place/updatelike")
    Call<IsLikeResult> getIsLike(@Header("Authorization") String token, @Body IsLikeInfo isLikeInfo);

    @GET("/place/placeInfo/{placeid}")
    Call<DetailsResult> getDetailsResult(@Header("Authorization") String token, @Path("placeid") int placeid);
}
