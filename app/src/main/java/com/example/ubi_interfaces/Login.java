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

import com.example.ubi_interfaces.ui.performances.PerformancesActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
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

import org.json.JSONObject;

import java.util.Arrays;


public class Login extends AppCompatActivity {

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
    LoginManager faceboolLogin;

    GoogleSignInClient mGoogleSignInClient;
    SignInButton signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();

        // isto tem que ficar aqui porque é no login que começa a APP
        if (fAuth.getCurrentUser() != null){
//            Intent intent1 = new Intent(getApplicationContext(), BottomNav.class);
//            startActivity(intent1);
//            finish();

            //Fazer signOut
//            FirebaseAuth.getInstance().signOut();
            Log.d("OH MANO", fAuth.getCurrentUser().getEmail() + " Tá AUTENTICADO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            // Tenho que desautenticar o user por agora (testes)
        }
        setContentView(R.layout.activity_login);
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
        login_button.setReadPermissions(Arrays.asList("email"));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.w("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String email = object.getString("email");
                                    String name = object.getString("name");
                                    Log.d("Login Facebook", loginResult.getAccessToken().getUserId() +
                                    " Email: "  + email +
                                    " Public profile: " + name);
                                    LoginManager logged = faceboolLogin.getInstance();
                                    Log.d("Login Facebook", String.valueOf(logged));

                                    // New User


                                    Intent intent1 = new Intent(Login.this, BottomNav.class);
                                    startActivity(intent1);
                                } catch (Exception ex) {
                                    Log.w("Error Getting User Data", ex);
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.d("Login Facebook", "CANCELOU!!!!!!!!!!!!!");
                /*Intent intent1 = new Intent(MainActivity.this, com.example.ubi_interfaces.MainActivity.class);
                startActivity(intent1);*/
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("Login Facebook", "ERrow ");
                Log.w("LoginFacebook", exception);
                //  Toast.makeText(MainActivity.this, "Error " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, com.example.ubi_interfaces.Signup.class);

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
                    Toast.makeText(Login.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d("ai", "lalalalalalalalalalalalalalalllllllllllllaaaaaaaaaaaaaaaa");
                    Intent goPerf = new Intent(getApplicationContext(), BottomNav.class);
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
            Intent intent2 = new Intent(Login.this, PerformancesActivity.class);
            startActivity(intent2);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }
}