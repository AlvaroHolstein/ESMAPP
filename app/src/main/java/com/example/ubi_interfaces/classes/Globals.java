package com.example.ubi_interfaces.classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ubi_interfaces.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

// Também é  preciso importar a da __google__

import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Globals {

    private User currentUser; // Vai ser o
    private static final String logTag = "Globals Tag";

    // Vai ser preciso mandar o contexto de certas páginas para aqui
    private Context ctx;

    //Inicializar sem nada
    public void Globals() {
    }

    public void Globals(Context ctx) {

    }

    public static void goToActivity(Context ctx, Class targetClass) {
        Intent goback = new Intent(ctx, targetClass);
        Log.d(logTag, "GO BACK!!!!!");
        goback.setFlags(FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(goback);
    }

    // Como substituir o fragment Manager
    public static void goToFragment(Fragment TargetClass, FragmentManager manager)  {
        FragmentManager fragmentManager = manager;
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, TargetClass);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public User setUser(User user) {
        return user;
    }

    //Não tou a coseguit.........................................................................................................
//    public User getCurrentUser(AccessToken accessToken) {
//        FirebaseAuth fAuth = FirebaseAuth.getInstance();
//        CallbackManager callbackManager;
//        LoginManager facebookLogin = LoginManager.getInstance();
//        GoogleSignInClient mGoogleSignInClient;
//        User currentUser = null;
//        if(fAuth != null) {
//            currentUser = new User(fAuth.getCurrentUser().getUid(), fAuth.getCurrentUser().getEmail(), "");
//        }
//        if(accessToken != null) {
//            GraphRequest request = GraphRequest.newMeRequest(
//                    accessToken,
//                    new GraphRequest.GraphJSONObjectCallback() {
//                        @Override
//                        public void onCompleted(
//                                JSONObject object,
//                                GraphResponse response) {
//                            // Application code
//
//                           try  {
//                                = new User(object.getString("id"), object.getString("email"), object.getString("name"));
//                           } catch(Exception ex) {
//                               Log.w("Globals Tag", ex);
//                           }
//                        }
//                    });
//            Bundle parameters = new Bundle();
//            parameters.putString("fields", "id,name,email, link");
//            request.setParameters(parameters);
//            request.executeAsync();
//        }
//
//
//        return currentUser;
//    }
}
