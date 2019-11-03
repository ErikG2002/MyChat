package com.example.mychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class Slider extends AppCompatActivity {

    private Button goToRegisterSlider;
    private ImageView backgroundImageContainer;
    private TextView goToLoginSlider, textInitOne , textInitTwo;
    private Intent register, login;
    private FrameLayout backImages;
    private Handler handler;
    private int newImage;
    private Animation anim_out, anim_in;
    private Animation text_anim_out, text_anim_in;

    private int[] images;
    private String[] texts1, texts2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        goToRegisterSlider = findViewById(R.id.goToRegisterSlider);
        goToLoginSlider = findViewById(R.id.goToLoginSlider);
        register = new Intent(this, Register.class);
        login = new Intent(this, Login.class);
        images = new int[] { R.drawable.test, R.drawable.test2, R.drawable.test3, R.drawable.test4 };
        texts1 = new String[] { "Comunicacion", "Nuevo", "Adios", "Test" };
        texts2 = new String[] { "Segura y sencilla", "Aplicacion", "Amigos", "Testing i32ehjlfeqwbj" };
        backgroundImageContainer = findViewById(R.id.backgroundImageContainer);
        newImage = 0;
        textInitOne = findViewById(R.id.textInitOne);
        textInitTwo = findViewById(R.id.textInitTwo);

        goToRegisterSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(register);
                finish();
            }
        });

        goToLoginSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(login);
                finish();
            }
        });
        changeImage(1);
    }

    private void changeImage (final int number ) {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                anim_out = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
                anim_in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
                anim_out.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation)
                    {
                        backgroundImageContainer.setImageResource(images[number]);
                        anim_in.setAnimationListener(new Animation.AnimationListener() {
                            @Override public void onAnimationStart(Animation animation) {}
                            @Override public void onAnimationRepeat(Animation animation) {}
                            @Override public void onAnimationEnd(Animation animation) {}
                        });
                        backgroundImageContainer.startAnimation(anim_in);
                    }
                });
                backgroundImageContainer.startAnimation(anim_out);
                newImage = number + 1;
                text_anim_out = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_out_right);
                text_anim_in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left);
                text_anim_out.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation)
                    {
                        textInitOne.setText(texts1[number]);
                        textInitTwo.setText(texts2[number]);
                        text_anim_in.setAnimationListener(new Animation.AnimationListener() {
                            @Override public void onAnimationStart(Animation animation) {}
                            @Override public void onAnimationRepeat(Animation animation) {}
                            @Override public void onAnimationEnd(Animation animation) {}
                        });
                        textInitOne.startAnimation(text_anim_in);
                        textInitTwo.startAnimation(text_anim_in);
                    }
                });
                textInitOne.startAnimation(text_anim_out);
                textInitTwo.startAnimation(text_anim_out);
                changeImage(newImage <= 3 ? newImage : 0);
            }
        }, 5000);
    }
}
