package com.example.farhat.adaptertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView list;
    Button addApptBtn;

    public class Appointment{
        public String appt_Title;
        public String appt_Time;
        public String appt_desq;
        public String appt_Location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.apptListView);
        addApptBtn = findViewById(R.id.newAppt);

        // launch new appointment activity
        addApptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewAppointment_Activity.class);
                startActivity(intent);
            }
        });
    }

    public void addNewAppt(){
        // launch new activity to fill in appointment with text
    }
}
