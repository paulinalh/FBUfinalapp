package com.example.fbufinal.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.BufferedOutputStream;
import com.example.fbufinal.R;
import com.example.fbufinal.activities.LoginActivity;
import com.example.fbufinal.activities.MainActivity;
import com.example.fbufinal.adapters.PlacesAdapter;
import com.example.fbufinal.adapters.ProfileNeedsAdapter;
import com.example.fbufinal.models.Review;
import com.example.fbufinal.models.User;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    ImageView ivProfileImage;
    TextView tvUsername;
    ParseUser user= ParseUser.getCurrentUser();
    ParseFile image;
    String photoFileName= "photo.jpg";
    List<Integer> userNeedsList;
    protected ProfileNeedsAdapter profileNeedsAdapter;
    String imageId;
    File newPicture;
    Button btnLogout;
    User profileUser;
    ParseObject profileImage;
    List<User> images;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use for monitoring Parse network traffic
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // any network interceptors must be added with the Configuration Builder given this syntax
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // Set applicationId and server based on the values in the Back4App settings.
        Parse.initialize(new Parse.Configuration.Builder(getContext())
                .applicationId("MRjGoRuk6WryotgaNXvpBv2B0ntvUO4kRS4ZxwbS") // ⚠️ TYPE IN A VALID APPLICATION ID HERE
                .clientKey("c3Zl3ou44eUPgiM3PrRU7WKAUSSdyKQSbRxnfFps") // ⚠️ TYPE IN A VALID CLIENT KEY HERE
                .clientBuilder(builder)
                .server("https://parseapi.back4app.com/").build());  // ⚠️ TYPE IN A VALID SERVER URL HERE

        userNeedsList = new ArrayList<>();
        userNeedsList=user.getList("needs");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_profile, container, false);

        ivProfileImage=view.findViewById(R.id.ivProfileImage);
        tvUsername=view.findViewById(R.id.tvUsername);
        btnLogout=view.findViewById(R.id.btnLogout);
        tvUsername.setText("@"+ParseUser.getCurrentUser().getUsername());

        image = ParseUser.getCurrentUser().getParseFile("picture");
        String username = user.getString("username");

        if (image != null) {
            Glide.with(getContext()).load(image.getUrl()).circleCrop().into(ivProfileImage);
        }

        Log.i("ProfileFragment", username);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvProfileNeeds = view.findViewById(R.id.rvProfileNeeds);

        //userNeedsList = new ArrayList<>();
        profileNeedsAdapter = new ProfileNeedsAdapter(getContext(), userNeedsList);
        rvProfileNeeds.setAdapter(profileNeedsAdapter);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvProfileNeeds.setLayoutManager(horizontalLayoutManager);


        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar mySnackbar = Snackbar.make(view, "Would you like to change your profile image?", Snackbar.LENGTH_SHORT);
                mySnackbar.setAction("Yes", new ChangeImageListener());
                mySnackbar.show();

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutButton();

            }
        });


    }
    public void onLogoutButton() {

        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null

        // navigate backwards to Login screen
        Intent i = new Intent(getContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // this makes sure the Back button won't work
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // same as above
        startActivity(i);
        getActivity().finish();

    }
    public class ChangeImageListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            launchCamera();

        }}

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        newPicture = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider.FBUfinalapp", newPicture);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(newPicture.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivProfileImage.setImageBitmap(takenImage);



                ParseUser currentUser = ParseUser.getCurrentUser();
                saveNewProfileImage(currentUser, newPicture);


            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);

    }

    private void saveNewProfileImage(ParseUser currentUser, File file)  {

        ParseFile newImage= new ParseFile(file);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");

        String objectId= ParseUser.getCurrentUser().getObjectId();
        // Retrieve the object by id
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject newImageUser, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to your Parse Server. playerName hasn't changed.
                    newImageUser.put("picture", newImage);
                    newImageUser.saveInBackground();
                }
            }
        });


    }


}