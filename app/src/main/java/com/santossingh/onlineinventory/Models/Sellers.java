package com.santossingh.onlineinventory.Models;

/**
 * Created by santoshsingh on 04/02/17.
 */

public class Sellers {
    private String username;
    private String password;
    private String mobile;
    private String key;

    public Sellers() {
    }

    public Sellers(String username, String password, String mobile) {
        this.username = username;
        this.password = password;
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
