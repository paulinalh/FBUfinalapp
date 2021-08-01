package com.example.fbufinal.fragments;

import android.content.Context;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
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
import com.example.fbufinal.models.Post;
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
    public static final int PICK_IMAGE = 100;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private static final String TAG = "ProfileFragment";
    ImageView ivProfileImage;
    TextView tvUsername;
    ParseUser user = ParseUser.getCurrentUser();
    ParseFile image;
    private File photoFile;
    public String photoFileName = "photo.jpg";
    List<Integer> userNeedsList;
    protected ProfileNeedsAdapter profileNeedsAdapter;
    String imageId;
    File newPicture;
    Button btnLogout;
    User profileUser;
    ParseObject profileImage;
    List<User> images;

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
        userNeedsList = user.getList("needs");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        tvUsername = view.findViewById(R.id.tvUsername);
        btnLogout = view.findViewById(R.id.btnLogout);
        tvUsername.setText("@" + ParseUser.getCurrentUser().getUsername());

        image = user.getParseFile("picture");
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

        List<Integer> imagesNeedsList = new ArrayList<>();
        for (int i = 0; i < userNeedsList.size(); i++) {
            if (userNeedsList.get(i) == 1) {
                imagesNeedsList.add(i);
            }
        }
        profileNeedsAdapter = new ProfileNeedsAdapter(getContext(), imagesNeedsList);
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
            selectImage(getContext());

        }
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your  picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    /*Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);*/
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //launchCamera();
                    photoFile = getPhotoFileUri(photoFileName);

                    // wrap File object into a content provider
                    // required for API >= 24
                    // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                    Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                    // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                    // So as long as the result is not null, it's safe to use the intent.
                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        // Start the image capture intent to take photo
                        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    photoFile = getPhotoFileUri(photoFileName);

                    // wrap File object into a content provider
                    // required for API >= 24
                    // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                    Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
                    pickPhoto.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                    // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                    // So as long as the result is not null, it's safe to use the intent.
                    if (pickPhoto.resolveActivity(getContext().getPackageManager()) != null) {
                        // Start the image capture intent to take photo
                        startActivityForResult(pickPhoto, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                    }

                    //startActivityForResult(pickPhoto, PICK_IMAGE);


                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivProfileImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PICK_IMAGE) {

            if (resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

            /*if (selectedImage != null) {
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    ivPostImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    cursor.close();
                }
            }*/

                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                ivProfileImage.setImageURI(selectedImage);

            }
        }

        saveNewProfileImage(photoFile);
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


    private void saveNewProfileImage(File file) {

        ParseFile newImage = new ParseFile(file);
        //ParseQuery<ParseObject> query = ParseQuery.getQuery("User");

        user.put("picture", newImage);

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(getContext(),"Change successful",Toast.LENGTH_SHORT).show();
            }
        });


    }


}