package com.example.mychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Slider extends AppCompatActivity {

    private Button goToRegisterSlider;
    private TextView goToLoginSlider;
    private Intent register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        goToRegisterSlider = findViewById(R.id.goToRegisterSlider);
        goToLoginSlider = findViewById(R.id.goToLoginSlider);
        register = new Intent(this, Register.class);
        login = new Intent(this, Login.class);

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
    }
}
