package com.seoulsi.client.seoulro.signup;

/**
 * Created by SanJuku on 2017-10-07.
 */

public class JoinInfo {
    public String nickname;
    public String email;
    public String password;

    public JoinInfo(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
}
