package com.example.fbufinal.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
    private static final String KEY_USERNAME = "username";
    private static final String KEY_TEXT = "textPost";
    private static final String KEY_IMAGE = "imagePost";
    private static final String KEY_LIKES = "likes";
    private static final String KEY_PLACE = "likes";

    public void setUsernamePost(ParseUser username) {
        put(KEY_USERNAME, username);
    }

    public void setTextPost(String text) {
        put(KEY_TEXT, text);
    }

    public void setImagePost(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public int getLikesPost() {
        return getInt(KEY_LIKES);
    }

    public void setLikesPost(int likes) {
        put(KEY_LIKES, likes);
    }

    public ParseObject getPlacePost() {
        return getParseObject(KEY_PLACE);
    }

    public void setPlacePost(PlaceServicesRating place) {
        put(KEY_PLACE, place);
    }
}
