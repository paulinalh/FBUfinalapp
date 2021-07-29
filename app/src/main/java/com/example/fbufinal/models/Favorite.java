package com.example.fbufinal.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Favorite")

public class Favorite extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_FAV_PLACE_ID = "favPlaceId";
    public String favPlaceId;
    //public ParseUser userFav;

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
}
