package com.example.fbufinal.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_TEXT="textPost";
    public static final String KEY_IMAGE="imagePost";
    public static final String KEY_PLACE="place";
    public static final String KEY_LIKES="likes";


    public ParseUser getUsernamePost() {
        return getParseUser(KEY_USERNAME);
    }

    public String getTextPost() {
        return getString(KEY_TEXT);
    }

    public void setUsernamePost(ParseUser username) {
        put(KEY_USERNAME, username);
    }

    public void setTextPost(String text) {
        put(KEY_TEXT, text);
    }

    public ParseFile getImagePost(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImagePost(ParseFile image){
        put(KEY_IMAGE,image);
    }

    public int getLikesPost(){
        return getInt(KEY_LIKES);
    }

    public void setLikesPost(int likes){
        put(KEY_LIKES,likes);
    }
}
