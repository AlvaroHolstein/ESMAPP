package com.example.ubi_interfaces.classes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ubi_interfaces.R;
import com.google.firebase.firestore.proto.TargetOuterClass;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class Globals {

    private static final String logTag = "Globals Tag";

    // Vai ser preciso mandar o contexto de certas páginas para aqui
    private Context ctx;

    //Inicializar sem nada
    public void Globals() {

    }

    public void Globals(Context ctx) {

    }

    public static void goBack(Context ctx, Class targetClass) {
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

}
