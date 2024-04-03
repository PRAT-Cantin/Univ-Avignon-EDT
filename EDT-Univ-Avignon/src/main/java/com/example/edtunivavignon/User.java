package com.example.edtunivavignon;

public class User {
    private String userName;
    private String edtURL;
    private boolean darkmode;
    private boolean isAdmin;

    public User(String userName, String edtURL, boolean darkmode, boolean isAdmin) {
        this.userName = userName;
        this.edtURL = edtURL;
        this.darkmode = darkmode;
        this.isAdmin = isAdmin;
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

    public boolean isAdmin() {
        return isAdmin;
    }
}
