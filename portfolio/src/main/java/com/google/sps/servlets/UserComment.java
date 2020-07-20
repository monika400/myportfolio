
package com.google.sps.servlets;
public class UserComment {
    private final boolean LoggedIn;
    private final String userURL;

    public UserComment(boolean LoggedIn, String userURL) {
        this.LoggedIn = LoggedIn;
        this.userURL = userURL;
    }
}