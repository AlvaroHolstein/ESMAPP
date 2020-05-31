package com.example.ubi_interfaces;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ubi_interfaces.classes.Globals;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsAccount extends Fragment {
    View root;
    FirebaseAuth fAuth;
    LoginManager fbUser;
    PopupWindow confirm;
    boolean confirmLogout = true;

    RelativeLayout logout;
     public View onCreateView(@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.fragment_settings_account, container, false);


        Log.d("Setttings Accpuont", "ENTROU NO settings account");


         // --------------------------------------------
         // Firebase USer
         fAuth = FirebaseAuth.getInstance();
         logout = root.findViewById(R.id.logoutBtn);
         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                // Pop Up
                 confirm = new PopupWindow(getContext());
                 LinearLayout placeholder = new LinearLayout(getContext());
                 LinearLayout buttons = new LinearLayout(getContext());

                 TextView message = new TextView(getContext());
                 message.setText("Do you really WANT IT");

                 Button yes = new Button(getContext()), no = new Button(getContext());
                 yes.setText("YES");
                 no.setText("NO");
                 yes.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         // Fazer signOut pelo Facebook, working
                         if(LoginManager.getInstance() != null) {
                             LoginManager.getInstance().logOut();
                         }

                         // Fazer signOut pelo firebase (email, password)
                         if (fAuth != null) {
                             fAuth.signOut();
                         }

                         // Log.d("Logout", fAuth.getCurrentUser().toString());

                         // Enviar para a página de login
                         Globals.goToActivity(getContext(), Login.class);
                         confirm.dismiss();
                     }
                 });
                 no.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         confirmLogout = false;
                         Log.d("Logout SO que nao", fAuth.getCurrentUser().toString());
                         confirm.dismiss();
                     }
                 });

                 ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                 placeholder.setOrientation(LinearLayout.VERTICAL);
                 placeholder.addView(message, params);

                 buttons.setOrientation(LinearLayout.HORIZONTAL);
                 buttons.addView(no, params);
                 buttons.addView(yes, params);
                 placeholder.addView(buttons, params);

                 confirm.setContentView(placeholder);

                 // Show Pop Up
                 confirm.showAtLocation(placeholder, Gravity.BOTTOM, 10, 10);
             }
         });

         return root;
    }
}