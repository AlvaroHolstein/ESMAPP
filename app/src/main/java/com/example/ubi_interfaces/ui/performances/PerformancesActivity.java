package com.example.ubi_interfaces.ui.performances;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.example.ubi_interfaces.R;
import com.example.ubi_interfaces.classes.Globals;
import com.example.ubi_interfaces.classes.Performance;
import com.example.ubi_interfaces.classes.RecyclerPerformances;
import com.example.ubi_interfaces.create_performance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PerformancesActivity extends Fragment {

    FirebaseFirestore db;
    RecyclerView rvPerf;
    private List<Performance> performances = new ArrayList<Performance>();

    final private String perfTag = "perfTag";

    /* Grab RecyclerView */
    private RecyclerPerformances perfAdapter;

    View root;
    private PerformancesViewModel performancesViewModel;

    //
    public View onCreateView(@NonNull LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.fragment_performances, container, false);

        db = FirebaseFirestore.getInstance();
        // Write a message to the database
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");

        // Catch Recycler View
        rvPerf = root.findViewById(R.id.rvPerf);
        rvPerf.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(root.getContext());
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
                           perfAdapter = new RecyclerPerformances(root.getContext(), performances);

                           Log.d(perfTag, performances.toString());
                           //perfAdapter = new RecyclerPerformances(performances);
                           rvPerf.setAdapter(perfAdapter);
                       } else {
                           Log.w(perfTag, "Erro ao receber documents"
                           + task.getException());
                       }
                    }
                });

        final Button createPerf = root.findViewById(R.id.goToPerformancePage);
        createPerf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPerformance();
            }
        });

        final ImageView goBack = (ImageView) root.findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });

        return root;
    }



    public void createPerformance() {
        Log.d("perf act", "create Performance button");

        FragmentManager fragM = getFragmentManager();
        Globals.goToFragment(new create_performance(), fragM);
    }

    //Mudar o nome da funcção
    // Nem sempre vai ser para ir para o login....
    public void goLogin() { // é como se fosse um go back
        //Criar uma class global que tenha funções que vão ser usadas muitas vezes em diferentes sitios, como ir para a homePage, neste caso vai para o login porque testes...
//        Intent goLogin = new Intent(root.getContext(), MainActivity.class);
//        startActivity(goLogin);
        Log.d("Quem????", "Oh pá ta calado \n Joao Campos (99)");
    }
}
