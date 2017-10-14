package com.seoulsi.client.seoulro.login;

/**
 * Created by SanJuku on 2017-10-09.
 */

public class LoginResult {
    String msg;
    String nickname;
    String token;

    public LoginResult(String msg, String nickname, String token) {
        this.msg = msg;
        this.nickname = nickname;
        this.token = token;
    }
}
