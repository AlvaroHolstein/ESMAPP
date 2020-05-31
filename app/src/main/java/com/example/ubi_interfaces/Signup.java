package com.example.ubi_interfaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    EditText createEmail;
    EditText createUsername;
    EditText createPassword;
    EditText confirmPassword;
    Button buttonSignup;
    TextView goLogIn;
    FirebaseAuth fAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        createEmail = findViewById(R.id.createEmail);
        createPassword = findViewById(R.id.createPassword);
        createUsername = findViewById(R.id.createUsername);
        confirmPassword = findViewById(R.id.confirmPassword);
        buttonSignup = findViewById(R.id.buttonSignup);
        goLogIn = findViewById(R.id.goLogIn);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        goLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);

                startActivity(intent);
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(createEmail.getText().toString().isEmpty() && createPassword.getText().toString().isEmpty() && createUsername.getText().toString().isEmpty() && confirmPassword.getText().toString().isEmpty()){
                    Toast.makeText(Signup.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
                else if (createPassword.getText().toString() == confirmPassword.getText().toString()){
                    Toast.makeText(Signup.this, "Passwords must be the same", Toast.LENGTH_SHORT).show();
                }
                else if (createPassword.length() < 6){
                    Toast.makeText(Signup.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    fAuth.createUserWithEmailAndPassword(createEmail.getText().toString(), createPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Signup.this, "User Created", Toast.LENGTH_SHORT).show();
                                final Task <AuthResult> n = task;

                                // Apanhar o user criado
                                FirebaseUser newUser = fAuth.getCurrentUser();
                                String uid = newUser.getUid();

                                Map<String, Object> newUserData = new HashMap<>();
                                newUserData.put("name", createUsername.getText().toString());
                                newUserData.put("password", createPassword.getText().toString());

                                // Criar utilizador na firestore/base
                                db.collection("users").document(uid)
                                        .set(newUserData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.w("RegisterUSer", String.valueOf(n));
                                                Intent intent = new Intent(getApplicationContext(), BottomNav.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Fazer uma toast depois
                                            Log.w("RegisterUsser", e);
                                        }
                                });

                            }
                            else {
                                Toast.makeText(Signup.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }


}
