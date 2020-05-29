package com.example.ubi_interfaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubi_interfaces.ui.performances.PerformancesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    EditText createEmail;
    EditText createUsername;
    EditText createPassword;
    EditText confirmPassword;
    Button buttonSignup;
    TextView goLogIn;
    FirebaseAuth fAuth;

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

        if (fAuth.getCurrentUser() != null){
            Intent intent1 = new Intent(getApplicationContext(), PerformancesActivity.class);
            startActivity(intent1);

            finish();
        }

        goLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, com.example.ubi_interfaces.MainActivity.class);

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

                                Intent intent = new Intent(getApplicationContext(), PerformancesActivity.class);

                                startActivity(intent);
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
