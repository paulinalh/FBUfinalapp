package com.example.fbufinal.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fbufinal.R;
import com.example.fbufinal.activities.PostsActivity;
import com.example.fbufinal.models.PlaceServicesRating;
import com.example.fbufinal.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class CreatePostFragment extends Fragment {

    public static final String TAG = "MainActivity";
    public static final int PICK_IMAGE = 100;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private EditText etDescription;
    private Button btnCaptureImage;
    private ImageView ivPostImage;
    private Button btnSubmit;
    private File photoFile;
    private Button btnGoToFeed;
    public String photoFileName = "photo.jpg";
    static PlaceServicesRating place;
    static int CODE;
    String KEY;
    FragmentManager fragmentManager;

    public CreatePostFragment() {
        // Required empty public constructor
    }

    public static void setPlace(PlaceServicesRating placeToRate, int code) {
        place = placeToRate;
        CODE = code;
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, parent, false);
        // Defines the xml file for the fragment
        return view;
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

                    /*final File file = new File(Environment.getExternalStorageDirectory(), "read.me");
                    Uri uri = Uri.fromFile(file);
                    File auxFile = new File(uri.getPath());
                    assertEquals(file.getAbsolutePath(), auxFile.getAbsolutePath());*/

                    // wrap File object into a content provider
                    // required for API >= 24
                    // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                    Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
                    pickPhoto.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                    // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                    // So as long as the result is not null, it's safe to use the intent.
                    if (pickPhoto.resolveActivity(getContext().getPackageManager()) != null) {
                        // Start the image capture intent to take photo
                        startActivityForResult(pickPhoto, PICK_IMAGE);
                    }

                    //startActivityForResult(pickPhoto, PICK_IMAGE);


                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        etDescription = view.findViewById(R.id.etDescription);
        btnCaptureImage = view.findViewById(R.id.btnCaptureImage);
        ivPostImage = view.findViewById(R.id.ivPostImage);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        //btnGoToFeed= view.findViewById(R.id.btnGoToFeed);

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(getContext());

            }

        });
/*
        btnGoToFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), FeedActivity.class);
                startActivity(i);
                //finish();
            }
        });*/


        //queryPosts();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etDescription.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(getContext(), "Description cant be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photoFile == null || ivPostImage.getDrawable() == null) {
                    Toast.makeText(getContext(), "There is no image", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, currentUser, photoFile);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new PostsFragment()).commit();

            }
        });

    }

    /*
    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
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
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPostImage.setImageBitmap(takenImage);
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

                /*InputStream imageStream = null;
                try {
                    imageStream = getContext().getContentResolver().openInputStream(selectedImage);
                    Bitmap takenImage = BitmapFactory.decodeStream(imageStream);
                    takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    ivPostImage.setImageBitmap(takenImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/


                ivPostImage.setImageURI(selectedImage);

                //photoFile = new File(getPath(selectedImage));

            }
        }

       // saveNewProfileImage(photoFile);
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
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

    private void savePost(String description, ParseUser currentUser, File photoFile) {
        Post post = new Post();
        post.setTextPost(description);
        post.setImagePost(new ParseFile(photoFile));
        //post.setImage(image);
        post.setUsernamePost(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful");
                etDescription.setText("");
                ivPostImage.setImageResource(0);

            }
        });
        getKey(post);
        //queryPostsfromPlace();
        //addToPlace();
    }

    private void getKey(Post post) {
        if (CODE == 0) {
            KEY = "WheelchairPosts";
            List<Post> list = new ArrayList<>();
            list = place.getWheelchairPosts();
            list.add(post);
            place.setWheelchairPosts(list);
            place.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Toast.makeText(getContext(), "post saved", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (CODE == 1) {
            KEY = "RampPosts";
            List<Post> list = new ArrayList<>();
            list = place.getRampPosts();
            list.add(post);
            place.setRampPosts(list);
            place.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Toast.makeText(getContext(), "post saved", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (CODE == 2) {
            KEY = "ParkingPosts";
            List<Post> list = new ArrayList<>();
            list = place.getParkingPosts();
            list.add(post);
            place.setParkingPosts(list);
            place.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Toast.makeText(getContext(), "post saved", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (CODE == 3) {
            KEY = "ElevatorPosts";
            List<Post> list = new ArrayList<>();
            list = place.getElevatorPosts();
            list.add(post);
            place.setElevatorPosts(list);
            place.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Toast.makeText(getContext(), "post saved", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (CODE == 4) {
            KEY = "DogPosts";
            List<Post> list = new ArrayList<>();
            list = place.getDogPosts();
            list.add(post);
            place.setDogPosts(list);
            place.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Toast.makeText(getContext(), "post saved", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (CODE == 5) {
            KEY = "BraillePosts";
            List<Post> list = new ArrayList<>();
            list = place.getBraillePosts();
            list.add(post);
            place.setBraillePosts(list);
            place.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Toast.makeText(getContext(), "post saved", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (CODE == 6) {
            KEY = "LightsPosts";
            List<Post> list = new ArrayList<>();
            list = place.getLightsPosts();
            list.add(post);
            place.setLightsPosts(list);
            place.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Toast.makeText(getContext(), "post saved", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (CODE == 7) {
            KEY = "SoundPosts";
            List<Post> list = new ArrayList<>();
            list = place.getSoundPosts();
            list.add(post);
            place.setSoundPosts(list);
            place.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Toast.makeText(getContext(), "post saved", Toast.LENGTH_SHORT).show();

                }
            });
        } else if (CODE == 8) {
            KEY = "SignLanguagePosts";
            List<Post> list = new ArrayList<>();
            list = place.getSignPosts();
            list.add(post);
            place.setSignPosts(list);
            place.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Toast.makeText(getContext(), "post saved", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    private void addToPlace() {
        place.getWheelchairPosts();
    }

    private void queryPosts() {
        //ParseQuery<PlaceServicesRating> query = ParseQuery.getQuery(PlaceServicesRating.class);

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USERNAME);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post:" + post.getTextPost() + ", username: " + post.getUsernamePost().getUsername());
                }

            }
        });

    }


}