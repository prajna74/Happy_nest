package com.example.happy_nest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.happy_nest.R.anim.bottom_animation;
import static com.example.happy_nest.R.anim.top_animation;

public class MainActivity extends AppCompatActivity {

    Animation topanim,bottomanim;
    ImageView image;
    TextView logo;
    private static int SPLASH_SCREEN=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        topanim= AnimationUtils.loadAnimation(this,top_animation);
        bottomanim= AnimationUtils.loadAnimation(this,bottom_animation);
        image=(ImageView)findViewById(R.id.imageView);
        logo= findViewById(R.id.textView2);
        image.setAnimation(topanim);
        logo.setAnimation(bottomanim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,Register.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}