package com.example.ubi_interfaces.ui.profile.tabLayout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubi_interfaces.R;
import com.example.ubi_interfaces.classes.Achievement;
import com.example.ubi_interfaces.classes.Globals;
import com.example.ubi_interfaces.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAchivements extends RecyclerView.Adapter<RecyclerViewAchivements.ViewHolder> {

    List<Achievement> achievments;
    User user = new User("ai", "u", "2", "fa");
    Context context;

    FirebaseFirestore db;

    public RecyclerViewAchivements(Context context, List<Achievement> achievements, final User us) {
        this.context = context;
        this.achievments = achievements;
        this.user = us;
        db = FirebaseFirestore.getInstance();

        Log.d("Achiv!!!!!!!!!User", String.valueOf(this.user));
//        db.collection("users").document(us.getId())
//            .get()
//            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    Log.d("SUCCESS ONCOMplete", String.valueOf(task.getResult().getData()));
//                    DocumentSnapshot doc = task.getResult();
//                    User uss = doc.toObject(User.class);
//                    us.setAchievements(uss.getAchievements());
//                    Log.d("USS!!! ", String.valueOf(us));
//                }
//            });

    }

    @NonNull
    @Override
    public RecyclerViewAchivements.ViewHolder onCreateViewHolder(@NonNull ViewGroup vg, int i) {
        View itemView = LayoutInflater.from(vg.getContext()).inflate(R.layout.achievments_column, vg, false);
        return new RecyclerViewAchivements.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAchivements.ViewHolder holder, int position) {
        Achievement achievement = achievments.get(position);
        Map<String, Integer> userA = new HashMap<>();
        boolean found = false;
        Log.d("Achiv...User", String.valueOf(achievement) + " - ");
        if(this.user.getAchievements() != null) {
            for (Map.Entry<String, Integer> entry : this.user.getAchievements().entrySet()) {
                Log.d("Achiv...User", entry.getKey() + " - " + entry.getValue() + " - " + String.valueOf(achievement));

                if(entry.getKey().equals(String.valueOf(achievement.getAchivId()))) {
                    Log.d("Achiv...User!!!", entry.getKey() + " - " + entry.getValue() + " - " + String.valueOf(achievement));
                }
            }
        }
        Log.d("Dubuggings", String.valueOf(achievement) + " -- " + String.valueOf(achievement.getAchivId()));
        holder.achivId.setText(String.valueOf(achievement.getAchivId()));
    }

    @Override
    public int getItemCount() {
        return this.achievments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView achivId;
        public ViewHolder(View itemView) {
            super(itemView);

            achivId = itemView.findViewById(R.id.achivId);
        }
    }
}
