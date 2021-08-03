package com.example.fbufinal.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Favorite")

public class Favorite extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_FAV_PLACE_ID = "favPlaceId";
    public static final String KEY_FAV_NAME = "favName";
    public static final String KEY_FAV_IMAGEURL = "favImageURL";
    public String favPlaceId;

    public String getFavPlaceId() {
        return getString(KEY_FAV_PLACE_ID);
    }

    public void setFavPlaceId(String favPlaceId) {
        put(KEY_FAV_PLACE_ID, favPlaceId);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getFavName() {
        return getString(KEY_FAV_NAME);
    }

    public void setFavName(String favName) {
        put(KEY_FAV_NAME, favName);
    }

    public String getFavImageURL() {
        return getString(KEY_FAV_IMAGEURL);
    }

    public void setFavImageURL(String favImageURL) {
        put(KEY_FAV_IMAGEURL, favImageURL);
    }
}



