package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class splashactivity extends AppCompatActivity {
    TextView tvMainTitle;
    ImageView ivMainLogo;

    Animation fadeInAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.splashactivity);

        setTitle("splashactivity");

        ivMainLogo = findViewById(R.id.ivMainLogo);
        tvMainTitle = findViewById(R.id.tvMainTitle);


        fadeInAnim = AnimationUtils.loadAnimation(splashactivity.this, R.anim.fadein);
        ivMainLogo.setAnimation(fadeInAnim);


        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent (splashactivity.this,loginactivity.class);

                startActivity(i);


            }
        },3000);

    }
}