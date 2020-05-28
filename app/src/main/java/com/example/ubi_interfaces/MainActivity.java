package com.example.ubi_interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import android.content.Context;

import java.util.Arrays;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;


public class MainActivity extends AppCompatActivity {

    TextView createAccount;
    EditText addPassword;
    EditText addEmail;
    Button login;
    ImageButton buttonFacebook;
    ImageButton buttonGoogle;
    TextView changePassword;
    FirebaseAuth fAuth;
    LoginButton login_button;
    CallbackManager callbackManager;

    GoogleSignInClient mGoogleSignInClient;
    SignInButton signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppEventsLogger.activateApp(getApplication());
        FacebookSdk.sdkInitialize(getApplicationContext());

        addPassword = findViewById(R.id.addPassword);
        addEmail = findViewById(R.id.addEmail);
//        buttonFacebook = findViewById(R.id.buttonFacebook);
//        buttonGoogle = findViewById(R.id.buttonGoogle);
        changePassword = findViewById(R.id.changePassword);
        login = findViewById(R.id.login);
        createAccount = findViewById(R.id.createAccount);
        signin = findViewById(R.id.sign_in_button);

        fAuth = FirebaseAuth.getInstance();


        callbackManager = CallbackManager.Factory.create();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        login_button = findViewById(R.id.login_button);
        login_button.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Intent intent1 = new Intent(MainActivity.this, com.example.ubi_interfaces.PerformancesActivity.class);

                startActivity(intent1);
            }

            @Override
            public void onCancel() {
                /*Intent intent1 = new Intent(MainActivity.this, com.example.ubi_interfaces.MainActivity.class);

                startActivity(intent1);*/
            }

            @Override
            public void onError(FacebookException exception) {
                //  Toast.makeText(MainActivity.this, "Error " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, com.example.ubi_interfaces.Signup.class);

                startActivity(intent);
            }
        });

//        buttonFacebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        buttonGoogle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addEmail.getText().toString().isEmpty() && addPassword.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("ai", "lalalalalalalalalalalalalalalllllllllllllaaaaaaaaaaaaaaaa");
                    Intent goPerf = new Intent(getApplicationContext(), PerformancesActivity.class);
                    startActivity(goPerf);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
    }

    int RC_SIGN_IN = 0;

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent2 = new Intent(MainActivity.this, PerformancesActivity.class);
            startActivity(intent2);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }



}