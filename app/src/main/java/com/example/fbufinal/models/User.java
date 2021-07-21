package com.example.fbufinal.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseObject {
    public static final String KEY_USERNAME="username";
    public static final String KEY_PROFILE_IMAGE="profileImage";
    public ParseUser username;
    public ParseFile profileImage;

    public ParseUser getUser() {
        return ParseUser.getCurrentUser().getParseUser(KEY_USERNAME);
    }

    public void setUser(ParseUser user) {
        put(KEY_USERNAME, username);
    }

    public ParseFile getProfileImage() {
        return ParseUser.getCurrentUser().getParseFile(KEY_PROFILE_IMAGE);
    }

    public void setProfileImage(ParseFile profileImage) {
        ParseUser.getCurrentUser().put(KEY_PROFILE_IMAGE, profileImage);
    }
}
