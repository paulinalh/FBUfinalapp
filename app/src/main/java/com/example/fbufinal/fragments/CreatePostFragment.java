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
import android.preference.PreferenceManager;
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
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

//Creates a new posts, launches camera, lets the user select image from gallery and saves the image to parse database.
public class CreatePostFragment extends Fragment {

    public static final String TAG = "MainActivity";
    public static final int PICK_IMAGE = 100;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private final String photoFileName = "photo.jpg";
    private static PlaceServicesRating place;
    private static int CODE;
    private EditText etDescription;
    private ImageView ivPostImage;
    private File photoFile;

    public CreatePostFragment() {
        // Required empty public constructor
    }

    public static void setPlace(PlaceServicesRating placeToRate, int code) {
        place = placeToRate;
        CODE = code;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post, parent, false);
        return view;
    }

    //Shows a dialog box and asks the user whether they want to take a picture (launch camera)
    // or select an image from gallery, then calls startActivity for result with the provided answer
    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your  picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    photoFile = getPhotoFileUri(photoFileName);


                    Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);


                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    photoFile = getPhotoFileUri(photoFileName);


                    Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
                    pickPhoto.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);


                    if (pickPhoto.resolveActivity(getContext().getPackageManager()) != null) {
                        // Start the image capture intent to take photo
                        startActivityForResult(pickPhoto, PICK_IMAGE);
                    }


                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        etDescription = view.findViewById(R.id.etDescription);
        Button btnCaptureImage = view.findViewById(R.id.btnCaptureImage);
        ivPostImage = view.findViewById(R.id.ivPostImage);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(getContext());

            }

        });

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
                //Uri selectedImage = data.getData();

                Uri selectedImage =  data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (selectedImage != null) {
                    Cursor cursor = getContext().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        Bitmap takenImage = BitmapFactory.decodeFile(picturePath);
                        ivPostImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                        //ivPostImage.setImageBitmap(takenImage);
                        cursor.close();
                    }
                }


                //ivPostImage.setImageURI(selectedImage);

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

    private void savePost(String description, ParseUser currentUser, File photoFile) {
        Post post = new Post();
        post.setTextPost(description);
        post.setImagePost(new ParseFile(photoFile));
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

    }

    private void getKey(Post post) {
        if (CODE == 0) {
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

}