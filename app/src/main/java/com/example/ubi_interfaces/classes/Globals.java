package com.example.ubi_interfaces.classes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
}
