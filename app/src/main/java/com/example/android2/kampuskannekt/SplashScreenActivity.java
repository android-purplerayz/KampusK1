package com.example.android2.kampuskannekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    LoginStatus();
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    public void LoginStatus(){

        Intent activityIntent;



        SharedPreferences sharedPreferences =getSharedPreferences("DATA", Context.MODE_PRIVATE);
        String Status = sharedPreferences.getString("login_status","");
        Log.i("Hommies",Status);
        if (Status.equals("yes")){

            startActivity(new Intent(SplashScreenActivity.this,ServicesNewActivity.class));
        }else {
            startActivity(new Intent(SplashScreenActivity.this,Tutorial.class));
        }
    }
}
