package com.example.ubi_interfaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import com.example.ubi_interfaces.classes.Globals;
import com.example.ubi_interfaces.classes.Performance;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class create_performance extends AppCompatActivity {

    Button saveNewPerf;
    Globals globals;


    // Inputs
    EditText location, maxCapacity, timeStart, accessCode;
    Switch accessCodeSwitch;

    // Firestore
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_performance);
        db = FirebaseFirestore.getInstance();

        Log.d("Create Perf", "ENTROU|!!!!!!!!!!!!!!!!!");


        location = findViewById(R.id.setLocation);
        maxCapacity = findViewById(R.id.maxCapacity);
        timeStart = findViewById(R.id.timeStart);
        accessCode = findViewById(R.id.accessCode);

//---------------------------------
        // Será que vou foder isto.... Probably .... final ?????????
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Log.d("Calendar", String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear) + "/" + String.valueOf(year));
                updateLabel(String.valueOf(dayOfMonth), String.valueOf(monthOfYear), String.valueOf(year));
            }

        };

        timeStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(create_performance.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//---------------------------------
        // Listener para clicar no botão.
        saveNewPerf = (Button) findViewById(R.id.createPerformance);
        saveNewPerf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Fazer os getText();....................

                // Gravar a performance e sair
                // Aqui devia mandar alguma informação
                String locationValue = location.getText().toString(),
                accessCodeValue = accessCode.getText().toString(), maxCapacityValue = maxCapacity.getText().toString();
                Date date = new Date();
                try {
                    SimpleDateFormat d = new SimpleDateFormat("DD/MM/YYY");
                    date = d.parse(timeStart.getText().toString());
                } catch (Exception ex) {
                    Log.e("Date Error", ex.toString());
                }

                Performance newPerf = new Performance(new Timestamp(date),
                        accessCodeSwitch.isChecked(),
                        3,
                        locationValue,
                        "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcdn.vox-cdn.com%2Fthumbor%2FFgiZSpHjp1vcKkV0PrdRppJszhA%3D%2F0x0%3A960x960%2F1200x800%2Ffilters%3Afocal(404x404%3A556x556)%2Fcdn.vox-cdn.com%2Fuploads%2Fchorus_image%2Fimage%2F58799523%2F14915318_10155148305236754_7471955098066766739_n.0.png&f=1&nofb=1",
                        accessCodeValue);

                Log.d("savePerf",
                "Location: " + newPerf.getLocation() +
                "\n accessCodeSwitch: " + newPerf.getReqPass() +
                "\n accessCode: " +  newPerf.getPassword() +
                "\n timeStart: " + newPerf.getDate()); // Os valores estão corretos.

//                Timestamp ts = ;
                Map<String, Object> performance = new HashMap<>();
                performance.put("active", 0);
                performance.put("adminId", 999);
                performance.put("date", new Timestamp(newPerf.getDate()));
                performance.put("duration", 999);
                performance.put("id", 999);
                performance.put("location", newPerf.getLocation());
                performance.put("picture", newPerf.getPicture());
                performance.put("password", newPerf.getPassword());

                db.collection("performances").document()
                        .set(performance)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("firestore", "Gravou com sucesso");
                                // E isto só devia acontecer se tudo for gravado com sucesso
                                Intent savePerf =  new Intent(getApplicationContext(), PerformancesActivity.class);
                                startActivity(savePerf);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("firestore", e.toString());
                            }
                        });

            }
        });

        accessCodeSwitch = findViewById(R.id.accessCodeSwitch); //.isChecked();
        accessCodeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Switch Button", String.valueOf(accessCodeSwitch.isChecked()) + " --- Location Text: " + location.getText());
                accessCode.setEnabled(accessCodeSwitch.isChecked());
            }
        });
    }

    private void updateLabel(String day, String month, String year) {

        // Mudar o valor do EditText (TimeStart)
        timeStart = findViewById(R.id.timeStart);
        timeStart.setText(day + "/" + month + "/" + year);
    }

    public void goBack(View view) {
        globals.goBack(getApplicationContext(), PerformancesActivity.class);
    }
}
