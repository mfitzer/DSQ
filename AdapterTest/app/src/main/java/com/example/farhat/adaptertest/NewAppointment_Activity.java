package com.example.farhat.adaptertest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class NewAppointment_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);

        EditText apptTitle = (EditText) findViewById(R.id.apptTitle);
        EditText apptTime = (EditText) findViewById(R.id.apptTime);
        EditText apptDesq = (EditText) findViewById(R.id.apptDesq);
        EditText apptLocation = (EditText) findViewById(R.id.apptLocation);

        // To send info between activities I think (?)
        Intent intent = getIntent();

    }

    // hide keyboard
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        View view = getCurrentFocus();
        if(view != null && (ev.getAction() == MotionEvent.ACTION_UP ||
                ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText &&
                !view.getClass().getName().startsWith("android.webkit.")){
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];

            if(x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow((this.getWindow().getDecorView().
                                getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void addNewAppt(){

    }
}
