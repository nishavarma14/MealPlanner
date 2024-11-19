package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrationActivity extends AppCompatActivity {
    EditText etName, etEmailId, etMobileNo, etGender, etAge, etAddress, etUserName, etPassword;
    Button btmSubmit;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        setTitle("Registration Activity");

        preferences = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
        editor = preferences.edit();


        etName = findViewById(R.id.etName);
        etEmailId = findViewById(R.id.etEmailId);
        etMobileNo = findViewById(R.id.etMobileNo);
        etGender = findViewById(R.id.etGender);
        etAge = findViewById(R.id.etAge);
        etAddress = findViewById(R.id.etAddress);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btmSubmit = findViewById(R.id.btmSubmit);


        btmSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty()) {
                    etName.setError("please enter your  name");
                } else if (etEmailId.getText().toString().isEmpty()) {
                    etEmailId.setError("please enter your EmailId");
                } else if (!etEmailId.getText().toString().contains("@") ||
                        !etEmailId.getText().toString().contains(".com")) {
                    etEmailId.setError("please enter your valid email id");
                } else if (etMobileNo.getText().toString().isEmpty()) {
                    etMobileNo.setError("please enter your mobile no");
                } else if (etMobileNo.getText().toString().length() != 10) {
                    etMobileNo.setError("invalid mobile no");
                } else if (etGender.getText().toString().isEmpty()) {
                    etGender.setError("please enter your gender");
                } else if (etAge.getText().toString().isEmpty()) {
                    etAge.setError("please enter your age");
                } else if (etAddress.getText().toString().isEmpty()) {
                    etAddress.setError("please enter your address");
                } else if (etUserName.getText().toString().isEmpty()) {
                    etUserName.setError("please enter your user name");
                } else if (etUserName.getText().toString().length() < 8) {
                    etUserName.setError("username must be greater than 8");
                } else if (!etUserName.getText().toString().matches(".*[A-Z].*")) {
                    etUserName.setError("used atleast 1 uppercase letter");
                } else if (!etUserName.getText().toString().matches(".*[a-z].*")) {
                    etUserName.setError("used atleast 1 lowercase letter");
                } else if (!etUserName.getText().toString().matches(".*[0-9].*")) {
                    etUserName.setError("used atleast 1 number");
                } else if (!etUserName.getText().toString().matches(".*[@,#,$,%,&,!].*")) {
                    etUserName.setError("used atleast 1 special symbol");
                } else if (etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("enter your password");
                } else if (etPassword.getText().toString().length() < 8) {
                    etPassword.setError("password must be greater than 8 digit");
                } else {
                    Toast.makeText(RegistrationActivity.this, "Registration successfully done", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(RegistrationActivity.this, loginactivity.class);
                    editor.putString("Name",etName.getText().toString()).commit();
                    editor.putString("EmailId",etEmailId.getText().toString()).commit();
                    editor.putString("MobileNo",etMobileNo.getText().toString()).commit();
                    editor.putString("Gender",etGender.getText().toString()).commit();
                    editor.putString("Age",etAge.getText().toString()).commit();
                    editor.putString("Address",etAddress.getText().toString()).commit();
                    editor.putString("UserName",etUserName.getText().toString()).commit();
                    editor.putString("Password",etPassword.getText().toString()).commit();






                    startActivity(i);
                }
            }
        });
    }

}


