package com.example.ubi_interfaces.classes;

import android.content.Context;
import android.content.Intent;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class RecyclerPerformances extends RecyclerView.Adapter<RecyclerPerformances.ViewHolder> {

    private String rvAdapter = "RvAdapter";
    private List<Performance> performances;
    private Context context;
    private int index;

    public RecyclerPerformances(Context context, List<Performance> performances) {
        this.performances  = performances;
        this.context = context;
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
        vh.date.setText(perf.getDate().toString());
        Picasso.get().load(performances.get(i).getPicture()).resize(50, 50).into(vh.picture);
        index = i;
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


    // Ir para uma performance
    public void goToPerformance(Performance perf) {
        Log.d("perfRow", perf.getPicture() + " & " + perf.getId());

        // Tenho que comçar uma atividade aqui
        Intent playPerf = new Intent(context, PlayPerformance.class);

        playPerf.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Sem isto vai dar um erro
        context.startActivity(playPerf);
    }

//    @Override
    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView date; // Por agora é só esta
        protected ImageView picture;
        protected Button btnParticipate;

        // Usei aqui o final para poder usar ao clicar no participante
        // Mas não sei é grande ideia ter um final num parametro
        public ViewHolder(final View itemView) {
            super(itemView);


            date = itemView.findViewById(R.id.hora);
            picture = itemView.findViewById(R.id.performacePicture);
            btnParticipate = itemView.findViewById(R.id.participate);


            Button enterParticipant = itemView.findViewById(R.id.participate);

            enterParticipant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("rvAdapter", "Entrou um participante");
                    Intent goPlay = new Intent(itemView.getContext(), PlayPerformance.class);
                    itemView.getContext().startActivity(goPlay);
                }
            });


        }
    }
}
