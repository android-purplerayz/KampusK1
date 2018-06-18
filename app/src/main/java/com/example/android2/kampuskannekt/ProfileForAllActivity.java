package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileForAllActivity extends AppCompatActivity {

    Button buttonback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_for_all);
        buttonback=(Button)findViewById(R.id.buttonBack);

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileForAllActivity.this,SchoolLoginActivity.class));
            }
        });
    }
}
