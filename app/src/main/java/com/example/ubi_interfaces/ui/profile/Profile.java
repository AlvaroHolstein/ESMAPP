package com.example.ubi_interfaces.ui.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubi_interfaces.R;
import com.example.ubi_interfaces.classes.Performance;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {

    ImageView profilePic;
    TextView name, username;

    FirebaseFirestore db;
    FirebaseAuth currentUser;
//    private ArrayList<Achievments> = new List<Achievments>;
//    private ArrayList<Performance> performaceHistory = new ArrayList<Performance>;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        Log.d("fragments", "Profile");

        // Registar insstancia da firestore
        db = FirebaseFirestore.getInstance();

        db.collection("users").document(currentUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("GetUSer", String.valueOf(documentSnapshot));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("GetUSer", e);
                    }
                });


        // Registar os pfu...
        profilePic = root.findViewById(R.id.profilePic);
        name = root.findViewById(R.id.name);
        username = root.findViewById(R.id.username);

        // Receber dados e mostrar

        return root;
    }
}
