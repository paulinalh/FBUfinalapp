package com.example.fbufinal;

import android.app.Application;

import com.example.fbufinal.models.Place;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //register your parse models
        ParseObject.registerSubclass(Place.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("MRjGoRuk6WryotgaNXvpBv2B0ntvUO4kRS4ZxwbS")
                .clientKey("c3Zl3ou44eUPgiM3PrRU7WKAUSSdyKQSbRxnfFps")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
