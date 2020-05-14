package com.example.ubi_interfaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.example.ubi_interfaces.classes.Performance;
import com.example.ubi_interfaces.classes.RecyclerPerformances;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PerformancesActivity extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView rvPerf;
    private List<Performance> performances = new ArrayList<Performance>();

    final private String perfTag = "perfTag";

    /* Grab RecyclerView */
    private RecyclerPerformances perfAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performances);
        db = FirebaseFirestore.getInstance();
        // Write a message to the database
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");

        // Catch Recycler View
        rvPerf = findViewById(R.id.rvPerf);
        rvPerf.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvPerf.setLayoutManager(llm);
        // Read from database
        db.collection("performances")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if(task.isSuccessful()) {
                           // Working til here...
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               Log.d(perfTag, document.getId() + "=>"
                               + document.getData());
                               Performance perf = document.toObject(Performance.class);
//                               Log.d(perfTag, String.valueOf(new Date(perf.getDate())));
//                               Log.d(perfTag, perf.getData());
                               performances.add(perf);

                           }
                           perfAdapter = new RecyclerPerformances(getApplicationContext(), performances);

                           Log.d(perfTag, performances.toString());
                           //perfAdapter = new RecyclerPerformances(performances);
                           rvPerf.setAdapter(perfAdapter);
                       } else {
                           Log.w(perfTag, "Erro ao receber documents"
                           + task.getException());
                       }
                    }
                });

    }



    public void insertParticipant(View view) {
    }

    //Mudar o nome da funcção
    public void goLogin(View view) {
        //Criar uma class global que tenha funções que vão ser usadas muitas vezes em diferentes sitios, como ir para a homePage, neste caso vai para o login porque testes...
        Intent goLogin = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goLogin);
    }


//    @Override
//    public void onCancelled(DatabaseError error) {
//        // Failed to read value
//        Log.w(perfTag, "Failed to read value.", error.toException());
//    }
}
