package com.softdesign.devintensive.model.network.params;

/**
 * Created by skwmium on 27.06.16.
 */
public class ParamAuth {
    private final String email;
    private final String password;

    public ParamAuth(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
