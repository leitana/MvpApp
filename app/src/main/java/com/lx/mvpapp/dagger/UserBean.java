package com.lx.mvpapp.dagger;

/**
 * Created by 11300 on 2018/2/27.
 */

public class UserBean {
    private String Tag;

    private String name;
    private String password;
    private String userId;

    public UserBean(String tag) {
        Tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
