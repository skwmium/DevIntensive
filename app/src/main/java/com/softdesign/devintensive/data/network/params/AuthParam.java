package com.softdesign.devintensive.data.network.params;

/**
 * Created by skwmium on 27.06.16.
 */
public class AuthParam {
    final String email;
    final String password;

    public AuthParam(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
