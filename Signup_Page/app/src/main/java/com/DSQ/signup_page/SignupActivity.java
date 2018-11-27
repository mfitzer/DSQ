package com.DSQ.signup_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity{
    public static final String EXTRA_MESSAGE  = "com.DSQ.signup_page.MESSAGE";

    private EditText phoneNumber;
    private EditText firstName;
    private EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
    }

    public void signUp(View view)
    {
        Intent intent = new Intent(this, PhoneVerification.class);
        String phone = phoneNumber.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, phone);
        startActivity(intent);
    }

}

