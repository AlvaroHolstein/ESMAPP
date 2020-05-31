package com.example.ubi_interfaces.classes;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;


public class User {

    String name;
    String id; // Não tenho a certeza se os id's são todos ints...
    String email;
    int performanceId; // Por default vai ser;
    String picture; //Não sei see vai ser sempre String por causa de guardar as imagens na base de dados
    boolean currentUser; // Saber se o user está a ver o perfil dele ou nao
    String authType; // Mais fácil para saber que call fazer
    String tagName = "USER Class";

    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    CallbackManager callbackManager;
    LoginManager facebookLogin = LoginManager.getInstance();


    GoogleSignInClient mGoogleSignInClient;

    public User() {

    }

    public User(String id, String email,String name) {
        this.email = email;
        this.name = name;
        this.id = id;
    }

    // Função que devolve o utilizador logado



    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getPerformanceId() {
        return this.performanceId;
    }
    public void setPerformanceId(int perfId) {
        this.performanceId = perfId;
    }

    public String getPicture() {
        return this.picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isCurrentUser () {
        boolean current = false;

        return true;
    }
}
