package com.example.ubi_interfaces;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ubi_interfaces.ui.performances.PerformancesActivity;

import io.socket.client.IO;
import io.socket.client.Socket;

public class PlayPerformance extends AppCompatActivity {

    String logTag = "Log Tag";
    LinearLayout checkTouch;
    private String socketLabelError = "Error in Socket";

    // Socekt
    private Socket socket;
    private String uri = "http://192.168.1.3:3000";
    private String username = "user1";

    @SuppressLint("ClickableViewAccessibility") // Porque??? nao sei bem
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_performance);

        // Socket
        try {
            socket = IO.socket(uri);
        } catch (Exception ex) {
            Log.w(socketLabelError, ex);
            ex.printStackTrace();
        }

        checkTouch = findViewById(R.id.playit);
        Log.d("check", String.valueOf(R.id.playit) /*+ "another: " + String.valueOf(R.id.linearLayout2)*/);


        socket.connect();
        socket.emit("join", "Utilizador:" + username);
        checkTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent eve) {

                int x = (int) eve.getX();
                int y = (int) eve.getY();

                switch (eve.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        Log.d(logTag, "MOVE values: (" + String.valueOf(x) + " : " + String.valueOf(y) + ")" );
                        socket.emit("musicnote", x + ":" + y);
                    break;
                    case MotionEvent.ACTION_DOWN:
                        Log.d(logTag, "DOWN values: (" + String.valueOf(x) + " : " + String.valueOf(y) + ")" );

                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(logTag, "UP values: (" + String.valueOf(x) + " : " + String.valueOf(y) + ")" );
                    break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(logTag, "disconnecting");
        socket.disconnect();
    }
    public void goBack(View view) {
        socket.disconnect();
        Intent goBack = new Intent(getApplicationContext(), PerformancesActivity.class);
        startActivity(goBack);
        finish();
    }
}
