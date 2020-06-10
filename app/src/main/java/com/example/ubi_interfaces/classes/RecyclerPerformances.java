package com.example.ubi_interfaces.classes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubi_interfaces.PlayPerformance;
import com.example.ubi_interfaces.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class RecyclerPerformances extends RecyclerView.Adapter<RecyclerPerformances.ViewHolder> {

    private String rvAdapter = "RvAdapter";
    private List<Performance> performances;
    private List<Performance> performancesCopy = new ArrayList<Performance>();
    private Context context;
    private int index;

    private FirebaseStorage fs;
    private StorageReference storageRef;

    public RecyclerPerformances(Context context, List<Performance> performances) {
        this.performances  = performances;
        this.context = context;
        this.performancesCopy.addAll(this.performances);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup vg, int i) {
        View itemView = LayoutInflater.from(vg.getContext()).inflate(R.layout.performances_row, vg, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, int i) {
        Performance perf = performances.get(i);

        fs = FirebaseStorage.getInstance();
        storageRef = fs.getReference();
//        StorageReference islandRef = storageRef.child("vdc.jpg");
        index = i;
        final ViewHolder auxVh = vh;

        // Isto depois vai ser substituido pelo perf.getPicture()
        storageRef.child("performances/" + perf.getPicture()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Picasso.get().load(uri.toString()).resize(50, 50).into(auxVh.picture);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                exception.printStackTrace();
            }
        });

        // Mostrar uma data mais adequada
        String total = String.valueOf(perf.getParticipantsId() == null ? 0 : perf.getParticipantsId().size()) + "/" + String.valueOf(perf.getTotalParticipants());
        vh.date.setText(perf.getDate().toString());
        vh.location.setText("Location " + perf.getLocation());
        vh.reqPass.setText(perf.getReqPass() ? "Yes" : "No");
        vh.totalParticipants.setText(total);
        vh.btnParticipate.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPerformance(performances.get(index));

                // Falta fazer Udpate À lista de performances
                // Enviar para o ecrã de tocar perfomance
            }
        }));
    }
    @Override
    public int getItemCount() {
        return performances.size();
    }

    public void filterPerformances(String text) {
        List<Performance> newList = new ArrayList<Performance>();
        Log.d("Search", "Ata caralho!!!" + text);
        if(!text.isEmpty()) {
            performances.clear();


            for(Performance perf : performancesCopy) {
               if(perf.getLocation().toUpperCase().contains(text.toUpperCase())) {
                    performances.add(perf);
               }
            }
        }
        else {
            performances.addAll(performancesCopy);
        }
        notifyDataSetChanged();
    }
    // Ir para uma performance
    public void goToPerformance(Performance perf) {

        // Tenho que comçar uma atividade aqui
        Intent playPerf = new Intent(context, PlayPerformance.class);

        playPerf.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Sem isto vai dar um erro
        context.startActivity(playPerf);
    }

//    @Override
    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView date, reqPass, totalParticipants; // Por agora é só esta
        protected ImageView picture;
        protected Button btnParticipate;
        protected TextView location;
        // Usei aqui o final para poder usar ao clicar no participante
        // Mas não sei é grande ideia ter um final num parametro
        public ViewHolder(final View itemView) {
            super(itemView);



            date = itemView.findViewById(R.id.hora);
            picture = itemView.findViewById(R.id.performacePicture);
            btnParticipate = itemView.findViewById(R.id.participate);
            location = itemView.findViewById(R.id.localization);
            reqPass = itemView.findViewById(R.id.reqPassText);
            totalParticipants = itemView.findViewById(R.id.totalParticipants);

            Button enterParticipant = itemView.findViewById(R.id.participate);

            enterParticipant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goPlay = new Intent(itemView.getContext(), PlayPerformance.class);
                    itemView.getContext().startActivity(goPlay);
                }
            });


        }

    }
}
