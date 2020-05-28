package com.example.ubi_interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class PlayPerformance extends AppCompatActivity {

    String logTag = "Log Tag";
    LinearLayout checkTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_performance);

        checkTouch = findViewById(R.id.playit);
        Log.d("check", String.valueOf(R.id.playit) /*+ "another: " + String.valueOf(R.id.linearLayout2)*/);
        checkTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent eve) {

                int x = (int) eve.getX();
                int y = (int) eve.getY();

                switch (eve.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        Log.d(logTag, "MOVE values: (" + String.valueOf(x) + " : " + String.valueOf(y) + ")" );
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


    public void goBack(View view) {
        Intent goBack = new Intent(getApplicationContext(), PerformancesActivity.class);
        startActivity(goBack);
    }
}
