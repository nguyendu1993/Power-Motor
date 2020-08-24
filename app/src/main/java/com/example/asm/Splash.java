package com.example.asm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    TextView tv,tv2;
    Animation topAnim,bottomAnim;
    ImageView imv;

    private static int SPLASH_SCREEN = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
       topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
       bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
       imv = (ImageView)findViewById(R.id.imvSplash);
       tv = (TextView)findViewById(R.id.tvSplash);
       tv2 = (TextView)findViewById(R.id.tvSplash2);
       imv.setAnimation(topAnim);
       tv.setAnimation(bottomAnim);
       tv2.setAnimation(bottomAnim);
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent = new Intent(Splash.this,Login.class);
               Pair[] pairs = new Pair[2];
               pairs[0] = new Pair<View,String>(imv,"logo_image");
               pairs[1] = new Pair<View,String>(tv,"logo_text");

               ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Splash.this,pairs);
               startActivity(intent,options.toBundle());

           }
       },SPLASH_SCREEN);


    }
}