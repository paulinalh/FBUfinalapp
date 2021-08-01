package com.example.fbufinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fbufinal.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SignupActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;
    Button btnSignup;
    int[] userNeedsArr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent i = getIntent();
        this.userNeedsArr = i.getIntArrayExtra("userNeedsArray");

        for (int j = 0; j < userNeedsArr.length; j++) {
            Log.d("paulina", String.valueOf(userNeedsArr[j]));
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignUp);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                onButtonSignUp(username, password);
            }
        });


    }

    private void onButtonSignUp(String username, String password) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        // Set custom properties
        user.put("phone", "650-253-0000");

        List<Integer> userNeedsList = new ArrayList<Integer>(userNeedsArr.length);

        for (int i = 0; i < userNeedsArr.length; i++) {
            userNeedsList.add(userNeedsArr[i]);
        }

        user.addAll("needs", userNeedsList);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // User can use the app now.
                    goMainActivity();
                } else {
                    // Sign up didn't succeed.
                }
            }
        });
    }


    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
