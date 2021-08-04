package com.example.fbufinal.models;

import androidx.annotation.Nullable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("PlaceServices")
public class PlaceServicesRating extends ParseObject {
    private static final String KEY_OBJECTID="objectId";
    private static final String KEY_PLACEID= "placeId";
    private static final String KEY_WHEELCHAIR="wheelchairRatings";
    private static final String KEY_RAMP="rampRatings";
    private static final String KEY_PARKING="parkingRatings";
    private static final String KEY_ELEVATOR="elevatorRatings";
    private static final String KEY_DOG="dogRatings";
    private static final String KEY_BRAILLE="brailleRatings";
    private static final String KEY_LIGHTS="lightsRatings";
    private static final String KEY_SOUND="soundRatings";
    private static final String KEY_SIGNLANGUAGE="signlanguageRatings";
    private static final String KEY_IMAGE="imageofPlace";
    private static final String KEY_NAME="nameofPlace";
    private static final String KEY_ALL_SERVICES="allServices";
    private static final String KEY_WHEELCHAIR_POSTS="WheelchairPosts";
    private static final String KEY_RAMP_POSTS="RampPosts";
    private static final String KEY_PARKING_POSTS="ParkingPosts";
    private static final String KEY_ELEVATOR_POSTS="ElevatorPosts";
    private static final String KEY_DOG_POSTS="DogPosts";
    private static final String KEY_BRAILLE_POSTS="BraillePosts";
    private static final String KEY_LIGHTS_POSTS="LightsPosts";
    private static final String KEY_SOUND_POSTS="SoundPosts";
    private static final String KEY_SIGNLANGUAGE_POSTS="SignLanguagePosts";

    public String getRatingPlaceId(){
        return getString(KEY_PLACEID);
    }

    public void setServicesObjectId(String id){
        put(KEY_OBJECTID,id);
    }

    public void setRatingPlaceId(String id){
        put(KEY_PLACEID,id);
    }

    public String getImageofPlace22(){
        return getString(KEY_IMAGE);
    }

    public String getNameofPlace22(){
        return getString(KEY_NAME);
    }

    public void setImageofPlace22(String image){
        put(KEY_IMAGE,image);
    }

    public void setNameofPlace22(String name){
        put(KEY_NAME,name);
    }

    public List<Integer> getAllServices(){
        return getList(KEY_ALL_SERVICES);
    }

    public void setAllServices(List<Integer> list){
        put(KEY_ALL_SERVICES,list);
    }

    public List<Post> getWheelchairPosts(){
        return getList(KEY_WHEELCHAIR_POSTS);
    }
    public List<Post> getRampPosts(){
        return getList(KEY_RAMP_POSTS);
    }
    public List<Post> getParkingPosts(){
        return getList(KEY_PARKING_POSTS);
    }
    public List<Post> getElevatorPosts(){
        return getList(KEY_ELEVATOR_POSTS);
    }
    public List<Post> getDogPosts(){
        return getList(KEY_DOG_POSTS);
    }
    public List<Post> getBraillePosts(){
        return getList(KEY_BRAILLE_POSTS);
    }
    public List<Post> getLightsPosts(){
        return getList(KEY_LIGHTS_POSTS);
    }
    public List<Post> getSoundPosts(){
        return getList(KEY_SOUND_POSTS);
    }
    public List<Post> getSignPosts(){
        return getList(KEY_SIGNLANGUAGE_POSTS);
    }
    public void setWheelchairPosts(List<Post> ratings){
        put(KEY_WHEELCHAIR_POSTS, ratings);
    }
    public void setRampPosts(List<Post> ratings){
        put(KEY_RAMP_POSTS,ratings);
    }
    public void setParkingPosts(List<Post> ratings){
        put(KEY_PARKING_POSTS,ratings);
    }
    public void setElevatorPosts(List<Post> ratings){
        put(KEY_ELEVATOR_POSTS,ratings);
    }
    public void setDogPosts(List<Post> ratings){
        put(KEY_DOG_POSTS,ratings);
    }
    public void setBraillePosts(List<Post> ratings){
        put(KEY_BRAILLE_POSTS,ratings);
    }
    public void setLightsPosts(List<Post> ratings){
        put(KEY_LIGHTS_POSTS,ratings);
    }
    public void setSoundPosts(List<Post> ratings){
        put(KEY_SOUND_POSTS,ratings);
    }
    public void setSignPosts(List<Post> ratings){
        put(KEY_SIGNLANGUAGE_POSTS,ratings);
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
