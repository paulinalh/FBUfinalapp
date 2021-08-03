package com.example.fbufinal;

import android.app.Application;

import com.example.fbufinal.models.Favorite;
import com.example.fbufinal.models.Place;
import com.example.fbufinal.models.PlaceServicesRating;
import com.example.fbufinal.models.Post;
import com.example.fbufinal.models.Review;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //register your parse models
        ParseObject.registerSubclass(PlaceServicesRating.class);
        ParseObject.registerSubclass(Favorite.class);
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Review.class);


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("7KkwGno5f3cNEqu9laULsiQPL5tiA73EpP9j6Ttw")
                .clientKey("g7z82LhuswHRksDitEoq6zpGS2hrfzToXNUPVhKQ")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
