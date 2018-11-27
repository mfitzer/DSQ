package com.DSQ.signup_page;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class PhoneVerification extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private String phoneNumber;
    private String verificationCode;
    EditText verificationCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);


        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra(SignupActivity.EXTRA_MESSAGE);
        //TextView phoneDisplay = findViewById(R.id.phoneDisplay);
        //phoneDisplay.setText(phoneNumber);

        sendVerificationCode(phoneNumber);

        verificationCodeEditText = findViewById(R.id.verificationCode);
    }

    private void sendVerificationCode(String phone)
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                //Toast.makeText(getApplicationContext(), "Don't have permission yet.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else
        {
            //Toast.makeText(getApplicationContext(), "Have permissions, sms sending", Toast.LENGTH_LONG).show();
            sendSMS(phone, generateVerificationCode());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS(phoneNumber, generateVerificationCode());
                } else {
                    Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    private void sendSMS(String number, String message)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
    }

    private String generateVerificationCode()
    {
        verificationCode = "123456";
        return verificationCode;
    }

    public void checkVerificationCode(View view)
    {
        String enteredCode = verificationCodeEditText.getText().toString();
        //Toast.makeText(getApplicationContext(), "Entered code " + enteredCode, Toast.LENGTH_LONG).show();

        if (enteredCode.equals(verificationCode))
        {
            Toast.makeText(getApplicationContext(), "Phone number verified.", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Invalid code.", Toast.LENGTH_LONG).show();
        }
    }
}
