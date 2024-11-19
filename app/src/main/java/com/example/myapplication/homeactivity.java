package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class homeactivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {



    BottomNavigationView bottomNavigationView;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    boolean doubletap =false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);

        preferences = PreferenceManager.getDefaultSharedPreferences(homeactivity.this);
        editor=preferences.edit();

       boolean firstTime = preferences.getBoolean("isFirstTime",true);
       if(firstTime)
       {
           welcome();
       }

setTitle("Home Page");
       bottomNavigationView = findViewById(R.id.homebottomnav);
       bottomNavigationView.setOnNavigationItemSelectedListener(this);
       bottomNavigationView.setSelectedItemId(R.id.menuhome);


    }
    private void welcome(){
        AlertDialog.Builder ad = new AlertDialog.Builder(homeactivity.this);
        ad.setTitle("Thomas health care");
        ad.setMessage("Welcome to Thomas health care!");
        ad.setPositiveButton("Thank You", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();

        editor.putBoolean("isFirstTime",false).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuhomeQR)
        {
            Intent i = new Intent(homeactivity.this, QRActivity.class);
            startActivity(i);
        }

        else if (item.getItemId()==R.id.menuhomelocation)
        {
            Intent i=new Intent(homeactivity.this,locationActivity.class);
                    startActivity(i);
        }
        else if(item.getItemId() ==R.id.homepageMyProfile)
        {
            Intent i=new Intent(homeactivity.this,MyProfileActivity.class);
            startActivity(i);


        } else if (item.getItemId() ==R.id.homepageSettings) {
            Intent i=new Intent(homeactivity.this,settingsActivity.class);
            startActivity(i);


        } else if (item.getItemId() ==R.id.homepageContactUs) {
            Intent i=new Intent(homeactivity.this,contactusActivity.class);
            startActivity(i);

        } else if (item.getItemId() ==R.id.homepageAboutUs) {
            Intent i=new Intent(homeactivity.this,aboutusActivity.class);
            startActivity(i);


        } else if (item.getItemId() ==R.id.homepageLogout) {

            logout();

        }
        return true;
    }

    private void logout(){
        AlertDialog.Builder ad=new AlertDialog.Builder(homeactivity.this);
        ad.setTitle("Thomas health care");
        ad.setMessage("you really want to logout?");
        ad.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ad.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i=new Intent(homeactivity.this,loginactivity.class);
                editor.putBoolean("isLogin",false).commit();
                startActivity(i);
            }
        }).create().show();
    }

    @Override
    public void onBackPressed() {
    if(doubletap)
        {
            finishAffinity();
        }
        else
        {
            Toast.makeText(homeactivity.this, "Press Again to Exit App", Toast.LENGTH_SHORT).show();
            doubletap=true;
            Handler h=new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubletap=false;
                }
            },2000);
        }
    }

    homeFragment homeFragment = new homeFragment();
    appointmentsFragment appointmentsFragment = new appointmentsFragment();
    musicFragment musicFragment = new musicFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuhome)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeframelayout,homeFragment).commit();

        } else if (item.getItemId() == R.id.menuappointments) {

            getSupportFragmentManager().beginTransaction().replace(R.id.homeframelayout,appointmentsFragment).commit();

        } else if (item.getItemId() == R.id.menumusic) {

            getSupportFragmentManager().beginTransaction().replace(R.id.homeframelayout,musicFragment).commit();

        }
        return true;
    }
}