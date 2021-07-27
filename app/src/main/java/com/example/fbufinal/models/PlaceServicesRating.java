package com.example.fbufinal.models;

import androidx.annotation.Nullable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("PlaceInclusionServices")
public class PlaceServicesRating extends ParseObject {
    public static final String KEY_OBJECTID="username";
    public static final String KEY_PLACEID= "image";
    public static final String KEY_WHEELCHAIR="wheelchairRatings";
    public static final String KEY_RAMP="rampRatings";
    public static final String KEY_PARKING="parkingRatings";
    public static final String KEY_ELEVATOR="elevatorRatings";
    public static final String KEY_DOG="dogRatings";
    public static final String KEY_BRAILLE="brailleRatings";
    public static final String KEY_LIGHTS="lightsRatings";
    public static final String KEY_SOUND="soundRatings";
    public static final String KEY_SIGNLANGUAGE="signlanguageRatings";

    public String getServicesObjectId(){
        return getString(KEY_OBJECTID);
    }

    public String getRatingPlaceId(){
        return getString(KEY_PLACEID);
    }

    public void setServicesObjectId(String id){
        put(KEY_OBJECTID,id);
    }

    public void setRatingPlaceId(String id){
        put(KEY_PLACEID,id);
    }

    public List<Integer> getWheelchairRatings(){
        return getList(KEY_WHEELCHAIR);
    }
    public List<Integer> getRampRatings(){
        return getList(KEY_RAMP);
    }
    public List<Integer> getParkingRatings(){
        return getList(KEY_PARKING);
    }
    public List<Integer> getElevatorRatings(){
        return getList(KEY_ELEVATOR);
    }
    public List<Integer> getDogRatings(){
        return getList(KEY_DOG);
    }
    public List<Integer> getBrailleRatings(){
        return getList(KEY_BRAILLE);
    }
    public List<Integer> getLightsRatings(){
        return getList(KEY_LIGHTS);
    }
    public List<Integer> getSoundRatings(){
        return getList(KEY_SOUND);
    }
    public List<Integer> getSignlanguageRatings(){
        return getList(KEY_SIGNLANGUAGE);
    }
    public void setWheelchairRatings(List<Integer> ratings){
        put(KEY_WHEELCHAIR, ratings);
    }
    public void setRampRatings(List<Integer> ratings){
        put(KEY_RAMP,ratings);
    }
    public void setParkingRatings(List<Integer> ratings){
        put(KEY_PARKING,ratings);
    }
    public void setElevatorRatings(List<Integer> ratings){
        put(KEY_ELEVATOR,ratings);
    }
    public void setDogRatings(List<Integer> ratings){
        put(KEY_DOG,ratings);
    }
    public void setBrailleRatings(List<Integer> ratings){
        put(KEY_BRAILLE,ratings);
    }
    public void setLightsRatings(List<Integer> ratings){
        put(KEY_LIGHTS,ratings);
    }
    public void setSoundRatings(List<Integer> ratings){
        put(KEY_SOUND,ratings);
    }
    public void setSignlanguageRatings(List<Integer> ratings){
        put(KEY_SIGNLANGUAGE,ratings);
    }
}
