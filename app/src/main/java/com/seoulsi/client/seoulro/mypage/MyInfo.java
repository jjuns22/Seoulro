package com.seoulsi.client.seoulro.mypage;

/**
 * Created by user on 2017-10-26.
 */

public class MyInfo {
    int user_id;
    String nickname;
    String email;
    String password;
    String registration_time;
    String profile_picture;
    String push_token;
    String lastlogin_time;
    String introduce;
    public void MyInfo(int user_id,
            String nickname,
            String email,
            String password,
            String registration_time,
            String profile_picture,
            String push_token,
            String lastlogin_time,
            String introduce){
       this.user_id = user_id;
       this.nickname =  nickname;
       this.email = email;
       this.password = password;
       this.registration_time =  registration_time;
        this.profile_picture = profile_picture;
        this.push_token = push_token;
        this.lastlogin_time = lastlogin_time;
        this.introduce = introduce;
    }
}
