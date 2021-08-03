package com.example.fbufinal.models;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("User")
public class User extends ParseObject {
    public static final String KEY_USERNAME="username";
    public static final String KEY_IMAGE= "image";
    public static final String KEY_PICTURE="picture";
    public static final String KEY_LIKES= "likedPosts";
    public ParseUser username;
    public ParseFile image;
    public ParseObject profileImage;

    public ParseUser getUserCurrent() {
        return ParseUser.getCurrentUser();
    }

    public String getUserName() {
        return ParseUser.getCurrentUser().getString(KEY_USERNAME);
    }

    public void setUserName(String user) {
        put(KEY_USERNAME, user);
    }

    public ParseFile getProfileImage() {
        return getParseFile(KEY_PICTURE);
    }

    public void setProfileImage(ParseFile profileImage) {
        put(KEY_PICTURE, profileImage);
    }

    public List<Post> getLikedPosts() {
        return getList(KEY_LIKES);
    }

    public void setLikedPosts(List<Post>  likes) {
        put(KEY_LIKES, likes);
    }

}
