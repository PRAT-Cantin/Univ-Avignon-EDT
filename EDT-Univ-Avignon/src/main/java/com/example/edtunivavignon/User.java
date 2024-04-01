package com.example.edtunivavignon;

public class User {
    private String userName;
    private String edtURL;
    private boolean darkmode;

    public User(String userName, String edtURL, boolean darkmode) {
        this.userName = userName;
        this.edtURL = edtURL;
        this.darkmode = darkmode;
    }

    public String getUserName() {
        return userName;
    }

    public String getEdtURL() {
        return edtURL;
    }

    public boolean isDarkmode() {
        return darkmode;
    }
}
