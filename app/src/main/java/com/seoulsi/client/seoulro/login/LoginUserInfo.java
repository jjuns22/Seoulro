package com.seoulsi.client.seoulro.login;

/**
 * Created by SanJuku on 2017-10-14.
 */

public class LoginUserInfo {
    private static LoginUserInfo instance = null;

    private LoginUserInfo() {

    }

    public static synchronized LoginUserInfo getInstance() {
        if (instance == null) {
            instance = new LoginUserInfo();
        }
        return instance;
    }

    private UserInfo userObject;
    public UserInfo getUserInfo() {
        return userObject;
    }

    public void setUserInfo(UserInfo userObject) {
        this.userObject = userObject;
    }
    public void setUserInfo(String nickname, String token) {
        this.userObject.nickname = nickname;
        this.userObject.token = token;
    }
}