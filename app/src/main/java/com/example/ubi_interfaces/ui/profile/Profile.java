package com.example.ubi_interfaces.ui.profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ubi_interfaces.R;
import com.example.ubi_interfaces.classes.Globals;
import com.example.ubi_interfaces.classes.Performance;
import com.example.ubi_interfaces.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Profile extends Fragment {

    ImageView profilePic;
    TextView name, username;

    FirebaseFirestore db;
    User currentUser;

//    FrameLayout frmLayout;
    TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // Registar insstancia da firestore
        db = FirebaseFirestore.getInstance();

        /* Ir buscar o currentUser*/
        currentUser = Globals.getCurrentUser();
        Log.d("USer No Profile", currentUser.getName() + " --- " + currentUser.getId());

        try {
            db.collection("users").document(currentUser.getId())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.d("Depois de ir à BD", currentUser.getName() + " --- " + currentUser.getId());
                            Log.d("SUCCESS", String.valueOf(documentSnapshot));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("GetUSer", e);
                        }
                    }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()) {
                        Log.d("SUCCESS ONCOMplete", String.valueOf(task.getResult().getData()));
                        DocumentSnapshot doc = task.getResult();
                        User us = doc.toObject(User.class);

                        currentUser.setName(us.getName());
                        name.setText(currentUser.getName());
                    }
                }
            });

        } catch (Exception ex) {
            // Podiamos mandar o user de volta para a página de Login
            Log.w("Error in profile", ex);
            ex.printStackTrace();
        }

        // Registar os pfu...
        profilePic = root.findViewById(R.id.profilePic);
        name = root.findViewById(R.id.name);
        username = root.findViewById(R.id.username);

        // Receber dados e mostrar

        return root;
    }
}
