package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginactivity extends AppCompatActivity {

    EditText etusername, etpassword;
    CheckBox cbshowhidepassword;
    Button btmLoginLogin;
    TextView tvLoginNewUser;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        setTitle("Login Activity");

        preferences = PreferenceManager.getDefaultSharedPreferences(loginactivity.this);
        editor= preferences.edit();

        if(preferences.getBoolean("isLogin",false))
        {
            Intent i=new Intent(loginactivity.this, homeactivity.class);
            startActivity(i);
        }

        etusername = findViewById(R.id.etLoginUsername);
        etpassword = findViewById(R.id.etLoginPassword);
        cbshowhidepassword = findViewById(R.id.cbloginshowhidepassword);
        btmLoginLogin = findViewById(R.id.btmLoginLogin);
        tvLoginNewUser = findViewById(R.id.tvLoginNewUser);


        cbshowhidepassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    etpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btmLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etusername.getText().toString().isEmpty()) {
                    etusername.setError("please enter your user name");
                } else if (etpassword.getText().toString().isEmpty()) {
                    etpassword.setError("please enter your password");
                } else if (etusername.getText().toString().length()< 8) {
                    etusername.setError("username must be greater than 8");
                } else if (etpassword.getText().toString().length()< 8) {
                    etpassword.setError("password must be greater than 8");
                } else if (!etusername.getText().toString().matches(".*[A-Z].*")) {
                    etusername.setError("used atleast 1 uppercase letter");
                } else if (!etusername.getText().toString().matches(".*[a-z].*")) {
                    etusername.setError("used atleast 1 lowercase letter");
                } else if (!etusername.getText().toString().matches(".*[0-9].*")) {
                    etusername.setError("used atleast 1 number");
                } else if (!etusername.getText().toString().matches(".*[@,#,$,%,&,!].*")) {
                    etusername.setError("used atleast 1 special symbol");
                } else {
                    Toast.makeText(loginactivity.this, "login successfully done", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(loginactivity.this, homeactivity.class);
                    editor.putString("username",etusername.getText().toString()).commit();
                    editor.putBoolean("isLogin",true).commit();
                    startActivity(i);
                }

            }
        });

        tvLoginNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(loginactivity.this,RegistrationActivity.class);
                startActivity(i);
            }
        });

    }

}



