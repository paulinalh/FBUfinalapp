package com.example.fbufinal.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Review")
public class Review extends ParseObject {
    public static final String USER_ID_KEY = "userId";
    public static final String TEXT_REVIEW_KEY = "text";
    private static final String USER_KEY = "username";

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
    public ParseUser getUser(){
        return getParseUser(getUserId());
    }
    public void setUser(ParseUser user){
       put(USER_KEY, user);
    }



}
