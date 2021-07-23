package com.example.fbufinal.models;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseObject {
    public static final String KEY_USERNAME="username";
    public static final String KEY_IMAGE= "image";
    public static final String KEY_PICTURE="picture";
    public ParseUser username;
    public ParseFile image;
    public ParseObject profileImage;

    public ParseUser getUsername() {
        return ParseUser.getCurrentUser().getParseUser(KEY_USERNAME);
    }

    public void setUsername(ParseUser user) {
        put(KEY_USERNAME, user);
    }

    public ParseFile getProfileImage() {
        return getParseFile(KEY_PICTURE);
    }

    public void setProfileImage(ParseFile profileImage) {
        put(KEY_PICTURE, profileImage);
    }




}
