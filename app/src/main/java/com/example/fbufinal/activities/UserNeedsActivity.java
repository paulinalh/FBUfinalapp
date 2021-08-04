package com.example.fbufinal.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fbufinal.R;

public class UserNeedsActivity extends AppCompatActivity {
    //Activity that lets the user choose their needs after sign up
    private final int WEELCHAIR_CODE = 0;
    private final int RAMP_CODE = 1;
    private final int PARKING_CODE = 2;
    private final int ELEVATOR_CODE = 3;
    private final int DOG_CODE = 4;
    private final int BRAILLE_CODE = 5;
    private final int LIGHT_CODE = 6;
    private final int SOUND_CODE = 7;
    private final int SIGNLANGUAGE_CODE = 8;
    private ImageView ivWheelchair, ivRamp, ivParking, ivElevator, ivDog, ivBraille, ivLight, ivSound, ivSignlanguage, ivNext;
    private final int[] userNeedsArr = {0, 0, 0, 0, 0, 0, 0, 0, 0};



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_needs);

        ivWheelchair = findViewById(R.id.ivWheelchair);
        ivRamp = findViewById(R.id.ivRamp);
        ivParking = findViewById(R.id.ivParking);
        ivElevator = findViewById(R.id.ivElevator);
        ivDog = findViewById(R.id.ivDog);
        ivBraille = findViewById(R.id.ivBraille);
        ivLight = findViewById(R.id.ivLight);
        ivSound = findViewById(R.id.ivSound);
        ivSignlanguage = findViewById(R.id.ivSignlanguage);

        ivNext = findViewById(R.id.ivNext);

        ivWheelchair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNeedsArr[WEELCHAIR_CODE] == 0) {
                    ivWheelchair.setImageDrawable(getDrawable(R.drawable.wheelchair_icon_2));
                    userNeedsArr[WEELCHAIR_CODE] = 1;
                    Toast toast = Toast.makeText(UserNeedsActivity.this, "Wheelchair accesibility in general (doors, dressing rooms,...)", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else if (userNeedsArr[WEELCHAIR_CODE] == 1) {
                    ivWheelchair.setImageDrawable(getDrawable(R.drawable.wheelchair_icon_1));
                    userNeedsArr[WEELCHAIR_CODE] = 0;
                }

            }
        });

        ivRamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNeedsArr[RAMP_CODE] == 0) {
                    ivRamp.setImageDrawable(getDrawable(R.drawable.ramp_icon_2));
                    userNeedsArr[RAMP_CODE] = 1;
                    Toast toast = Toast.makeText(UserNeedsActivity.this, "Need for quality ramps", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else if (userNeedsArr[RAMP_CODE] == 1) {
                    ivRamp.setImageDrawable(getDrawable(R.drawable.ramp_icon_1));
                    userNeedsArr[RAMP_CODE] = 0;
                }

            }
        });

        ivParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNeedsArr[PARKING_CODE] == 0) {
                    ivParking.setImageDrawable(getDrawable(R.drawable.parking_icon_2));
                    userNeedsArr[PARKING_CODE] = 1;
                    Toast toast = Toast.makeText(UserNeedsActivity.this, "Accesible Parking spot", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else if (userNeedsArr[PARKING_CODE] == 1) {
                    ivParking.setImageDrawable(getDrawable(R.drawable.parking_icon_1));
                    userNeedsArr[PARKING_CODE] = 0;
                }

            }
        });

        ivElevator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNeedsArr[ELEVATOR_CODE] == 0) {
                    ivElevator.setImageDrawable(getDrawable(R.drawable.elevator_icon_2));
                    userNeedsArr[ELEVATOR_CODE] = 1;
                    Toast toast = Toast.makeText(UserNeedsActivity.this, "Quality eleavtor(s) for wheelchair of equipment", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else if (userNeedsArr[ELEVATOR_CODE] == 1) {
                    ivElevator.setImageDrawable(getDrawable(R.drawable.elevator_icon_1));
                    userNeedsArr[ELEVATOR_CODE] = 0;
                }

            }
        });

        ivDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNeedsArr[DOG_CODE] == 0) {
                    ivDog.setImageDrawable(getDrawable(R.drawable.dog_icon_2));
                    userNeedsArr[DOG_CODE] = 1;
                    Toast toast = Toast.makeText(UserNeedsActivity.this, "Service dog-friendly", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else if (userNeedsArr[DOG_CODE] == 1) {
                    ivDog.setImageDrawable(getDrawable(R.drawable.dog_icon_1));
                    userNeedsArr[DOG_CODE] = 0;
                }

            }
        });

        ivBraille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNeedsArr[BRAILLE_CODE] == 0) {
                    ivBraille.setImageDrawable(getDrawable(R.drawable.braille_icon_2));
                    userNeedsArr[BRAILLE_CODE] = 1;
                    Toast toast = Toast.makeText(UserNeedsActivity.this, "Braille assistance (menus, signs, paths)", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else if (userNeedsArr[BRAILLE_CODE] == 1) {
                    ivBraille.setImageDrawable(getDrawable(R.drawable.braille_icon_1));
                    userNeedsArr[BRAILLE_CODE] = 0;
                }

            }
        });

        ivLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNeedsArr[LIGHT_CODE] == 0) {
                    ivLight.setImageDrawable(getDrawable(R.drawable.light_icon_2));
                    userNeedsArr[LIGHT_CODE] = 1;
                    Toast toast = Toast.makeText(UserNeedsActivity.this, "Control of lights for people with sensitivity", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else if (userNeedsArr[LIGHT_CODE] == 1) {
                    ivLight.setImageDrawable(getDrawable(R.drawable.light_icon_1));
                    userNeedsArr[LIGHT_CODE] = 0;
                }

            }
        });

        ivSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNeedsArr[SOUND_CODE] == 0) {
                    ivSound.setImageDrawable(getDrawable(R.drawable.sound_icon_2));
                    userNeedsArr[SOUND_CODE] = 1;
                    Toast toast = Toast.makeText(UserNeedsActivity.this, "Control of sounds for people with sensitivity", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else if (userNeedsArr[SOUND_CODE] == 1) {
                    ivSound.setImageDrawable(getDrawable(R.drawable.sound_icon_1));
                    userNeedsArr[SOUND_CODE] = 0;
                }

            }
        });

        ivSignlanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userNeedsArr[SIGNLANGUAGE_CODE] == 0) {
                    ivSignlanguage.setImageDrawable(getDrawable(R.drawable.signlanguage_icon_2));
                    userNeedsArr[SIGNLANGUAGE_CODE] = 1;
                    Toast toast = Toast.makeText(UserNeedsActivity.this, "Sign language assistance or service", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                } else if (userNeedsArr[SIGNLANGUAGE_CODE] == 1) {
                    ivSignlanguage.setImageDrawable(getDrawable(R.drawable.signlanguage_icon_1));
                    userNeedsArr[SIGNLANGUAGE_CODE] = 0;
                }

            }
        });


        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignupActivity();
            }
        });

    }

    private void goSignupActivity() {
        Intent i = new Intent(this, SignupActivity.class);
        i.putExtra("userNeedsArray", userNeedsArr);
        startActivity(i);

        finish();

    }


}
