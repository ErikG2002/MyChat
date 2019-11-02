package com.example.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private TextView goToLogin;
    private EditText TextEmail, TextPassword;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("server/saving-data/fireblog");
    private String email,password,user;
    private CheckBox acceptConditions;
    private Intent toLogin;
    private ImageView seePasswordRegister;
    private boolean visible;

    //Create object firebase auth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Init Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
       // user = FirebaseDatabase.getInstance().getReference();

        //Initialize views
        TextEmail = findViewById(R.id.emailInput);
        TextPassword = findViewById(R.id.passInput);

        acceptConditions = findViewById(R.id.acceptConditions);

        btnRegistrar = findViewById(R.id.sendButton);

        progressDialog = new ProgressDialog(this);

        toLogin = new Intent(this, Login.class);
        goToLogin = findViewById(R.id.goToLogin);

        visible = false;
        seePasswordRegister = findViewById(R.id.seePasswordRegister);

        seePasswordRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextPassword.setInputType( visible ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                TextPassword.setTypeface(Typeface.SANS_SERIF);
                seePasswordRegister.setImageResource(visible ? R.drawable.eye : R.drawable.eyeblock);
                visible = !visible;
            }
        });

        //attaching listener to button
        btnRegistrar.setOnClickListener(this);

        //question for account
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toLogin);
                finish();
            }
        });
    }

    private void registrarUsuario() {

        //Get values of inputs email and password
        email = TextEmail.getText().toString().trim();
        password = TextPassword.getText().toString().trim();

        //Verefy that the ipnuts aren't empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!acceptConditions.isChecked()) {
            Toast.makeText(this, "Acepta los términos y condiciones", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            DatabaseReference usersRef = ref.child("Users");;
                            Map<String, Object> users = new HashMap<>();
                            users.put("NameUser",user);
                            users.put("Correo", email);
                            usersRef.updateChildren(users);

                            Toast.makeText(Register.this, "Se ha registrado el usuario con el email: " + TextEmail.getText(), Toast.LENGTH_LONG).show();
                            startActivity(toLogin);
                            finish();
                        } else {

                            Toast.makeText(Register.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        //Get method
        registrarUsuario();
    }
}