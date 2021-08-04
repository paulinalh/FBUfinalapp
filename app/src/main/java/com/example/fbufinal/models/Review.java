package com.example.fbufinal.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Review")
public class Review extends ParseObject {
    private static final String USER_ID_KEY = "userId";
    private static final String TEXT_REVIEW_KEY = "text";
    private static final String USERNAME_KEY = "username";
    private static final String PLACE_ID="placeId";
    public static final String USER_KEY = "user";

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getTextReview() {
        return getString(TEXT_REVIEW_KEY);
    }

    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public void setTextReview(String text) {
        put(TEXT_REVIEW_KEY, text);
    }
    public String getUsername(){
        return getString(USERNAME_KEY);
    }
    public void setUsername(String user){
       put(USERNAME_KEY, user);
    }

    public String getPlaceId(){
        return getString(PLACE_ID);
    }
    public void setPlaceId(String placeId){
        put(PLACE_ID, placeId);
    }

    public ParseUser getUserReview(){
        return getParseUser(USER_KEY);
    }
    public void setUserReview(ParseUser user){
        put(USER_KEY, user);
    }


}
