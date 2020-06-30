package com.example.ubi_interfaces.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.ubi_interfaces.PlayPerformance;
import com.example.ubi_interfaces.R;
import com.example.ubi_interfaces.Signup;
import com.example.ubi_interfaces.classes.Globals;

import org.w3c.dom.Text;

public class PassCheckEnterPerformance extends DialogFragment {

    public PassCheckEnterPerformance() {
        PassCheckEnterPerformance f = new PassCheckEnterPerformance();
        // Supply num input as an argument.
        // Bundle args = new Bundle();
        // args.putInt("num", num);
        // f.\setArguments(args);
        //
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Pass check", "/ Per pass" + Globals.perf.getPassword());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.password_dialog, container, false);

        final TextView pass = v.findViewById(R.id.passCheck);
        final String passVal = pass.getText().toString();

        // Watch for button clicks.
//        Button confirm = v.findViewById(R.id.confirm);
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(passVal.equals("")) {
//                    pass.setError("Required");
//                }
//                if(passVal.equals(Globals.perf.getPassword())) {
//                    Globals.goToActivity(v.getContext(), PlayPerformance.class);
//                    dismiss();
//                }
//                else {
//                    Toast.makeText(v.getContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        Button cancel = v.findViewById(R.id.cancel);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View vi) {
//                // When button is clicked, call up to owning activity.
//                dismiss();
//            }
//        });


        return v;
    }
}
