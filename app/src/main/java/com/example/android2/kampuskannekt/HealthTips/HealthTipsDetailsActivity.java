package com.example.android2.kampuskannekt.HealthTips;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android2.kampuskannekt.R;

public class HealthTipsDetailsActivity extends AppCompatActivity {

    private Button btnbackHTD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips_details);
        btnbackHTD=findViewById(R.id.btnbackHTD);
        btnbackHTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HealthTipsDetailsActivity.this,HealthTipsActivity.class));
            }
        });
    }
}
