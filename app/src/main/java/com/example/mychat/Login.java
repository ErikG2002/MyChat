package com.example.mychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private TextView toRegister;
    private Intent intentRegister;
    private ImageView seePasswordLogin;
    private EditText inputPassword;
    private boolean visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toRegister = findViewById(R.id.toRegister);
        intentRegister = new Intent(this, Register.class);
        seePasswordLogin = findViewById(R.id.seePasswordLogin);
        inputPassword = findViewById(R.id.inputPassword);
        visible = false;

        //Method to send to Register
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentRegister);
                finish();
            }
        });
        //Method to change visibility of password
        seePasswordLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputPassword.setInputType( visible ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                inputPassword.setTypeface(Typeface.SANS_SERIF);
                seePasswordLogin.setImageResource(visible ? R.drawable.eye : R.drawable.eyeblock);
                visible = !visible;
            }
        });
    }
}
