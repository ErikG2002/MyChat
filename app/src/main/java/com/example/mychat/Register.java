package com.example.mychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private TextView goLogin;
    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("server/saving-data/fireblog");
    String email,password,user;

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
       // user = FirebaseDatabase.getInstance().getReference();

        //Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.emailInput);
        TextPassword = (EditText) findViewById(R.id.passInput);

        btnRegistrar = (Button) findViewById(R.id.sendButton);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        btnRegistrar.setOnClickListener(this);
    }

    private void registrarUsuario() {

        //Obtenemos el email y la contraseña desde las cajas de texto
        email = TextEmail.getText().toString().trim();
        password = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
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
                        } else {

                            Toast.makeText(Register.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        //Invocamos al método:
        registrarUsuario();
    }

}