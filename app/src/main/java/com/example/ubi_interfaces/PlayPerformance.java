package com.example.ubi_interfaces;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ubi_interfaces.classes.Globals;
import com.example.ubi_interfaces.classes.Performance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class PlayPerformance extends AppCompatActivity {

    String logTag = "Log Tag";
    LinearLayout checkTouch;
    private String socketLabelError = "Error in Socket";
    int contador = 0;
    TextView numberOfUsers;

    /* Firebase */
    FirebaseFirestore db;

    // Socekt
    private Socket socket;
    private String uri = "http://192.168.1.6:3001";
    private String username = "user1";
    private String perfId;

    private Performance performance;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_performance);

        db = FirebaseFirestore.getInstance();
        numberOfUsers = findViewById(R.id.numberOfUsers);

        /* Get Performance Information */
        perfId = getIntent().getStringExtra("id");
        db.collection("performances").document(perfId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot doc = task.getResult();

                        performance = doc.toObject(Performance.class);

                        /* Para não ser null \*/
                        assert performance != null;
                        performance.setId(doc.getId());

                        Log.d("PErf Play Perf!", performance.getLocation() + " -- " + performance.getId());
                    }
                });
        // Inicializar Socket
        try {
            socket = IO.socket(uri);
        } catch (Exception ex) {
            Log.w(socketLabelError, ex);
            ex.printStackTrace();
        }

        /* Sitio para tocar */
        checkTouch = findViewById(R.id.playit);

        // Connectar
        socket.connect();

        // Avisar quem entrou, Vai ser substituido pelo nome do utilizador
        socket.emit("join", "Utilizador:" + username);

        /* Aqui é onde estamos à escuta de Eventos
        * Depois deve poder ser manda para fora do onCreate */
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
            }
        }).on("newConnection", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    String text = String.valueOf(args);
                } catch (Exception ex) {
                    Log.w("Error !!!", ex);
                    ex.printStackTrace();
                }
            }
        }).on("totalUsers", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    String nUsers = String.valueOf(args);
                } catch (Exception ex) {
                    Log.w("Error !!!", ex);
                    ex.printStackTrace();
                }
            }
        });

        // Listener para o toque
        checkTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent eve) {

                int x = (int) eve.getX();
                int y = (int) eve.getY();
                switch (eve.getAction()) {
                    // Para quando se arrasta o dedo
                    case MotionEvent.ACTION_MOVE:
                        contador++;

                        if(contador > 5) { // Para determinar o "ritmo" podemos fazer por contador ou por timeouts mas JAVA.....
                            contador = 0;
                            socket.emit("musicnote", x + ":" + y);
                        }
                    break;
                        // Estes os dois ainda não tenho a certeza de como funceminam
                    case MotionEvent.ACTION_DOWN:
                        contador++;
                        break;
                    case MotionEvent.ACTION_UP:
                        contador++;
                    break;
//                    default:
//                        throw new IllegalStateException("Unexpected value: " + eve.getAction());
                }

                // Só returnando true é que é possivel detetar o arraste de dedo
                return true;
            }
        });
        Button goBack = findViewById(R.id.goPerfs);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.goToActivity(getApplicationContext(), BottomNav.class);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
