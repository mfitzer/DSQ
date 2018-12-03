package com.example.samue.desq;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.List;

public class appointmentActivity  extends AppCompatActivity implements View.OnClickListener {

    EditText editTextname, descriptionView, titleView;
    Button send, cancel;
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    TextView dateView;
    ListView listViewUsers;

    //List storing all users
    List<userInfo> users;

    //database reference object
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        //setting the reference of users that are alread existing
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        //getting the XML views
        descriptionView = (EditText) findViewById(R.id.description);
        titleView = (EditText) findViewById(R.id.titleView);  //title
        dateView = (TextView) findViewById(R.id.dateView);
        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.dateView);
        txtTime = (EditText) findViewById(R.id.timeView);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.button_cancel);
        send = (Button) findViewById(R.id.button_send);
        send.setOnClickListener(this);

        //oncoming data changes
        Intent incoming = getIntent();
        String date = incoming.getStringExtra("date");  //pass the key (date)
        dateView.setText(date);

    } //end of onCreate


    @Override
    public void onClick(View view) {

        if (view == send){
            databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
            String title = titleView.getText().toString().trim();  //title
            String name = descriptionView.getText().toString().trim();  //username
            String date = dateView.getText().toString().trim();  //date

            if (!TextUtils.isEmpty(name) && (!TextUtils.isEmpty(date))) {

                String id = databaseUsers.push().getKey();    //used to generate unique id
                userInfo user = new userInfo(name, date, id, title);  //adding new user to the database

                databaseUsers.child(id).setValue(user);

                Toast.makeText(this, "User added to the database", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please Make sure all fields are filled correctly", Toast.LENGTH_LONG).show();
            }

        }

        if (view == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (view == cancel){
            Intent cancel  = new Intent(this, MainActivity.class);
            this.startActivity(cancel);
            Toast.makeText(this, " CANCELLED ", Toast.LENGTH_LONG).show();

        }
    }
}