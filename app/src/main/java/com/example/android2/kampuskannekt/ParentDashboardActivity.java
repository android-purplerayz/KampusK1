package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ParentDashboardActivity extends AppCompatActivity {


    private Button btndasdMSG,btndasdEmail,btndashgotoMainmenu;
    private LinearLayout LloutRequestID,llout_notic,lloutabuse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
        btndasdMSG=(Button)findViewById(R.id.btndasdMSG);
        LloutRequestID=(LinearLayout) findViewById(R.id.LloutRequestID);
        llout_notic=(LinearLayout) findViewById(R.id.llout_notic);
        lloutabuse=(LinearLayout) findViewById(R.id.lloutabuse);

        lloutabuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentDashboardActivity.this,ParentAbuseActivity.class));
            }
        });

        btndashgotoMainmenu=(Button)findViewById(R.id.btndashgotoMainmenu);


        llout_notic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentDashboardActivity.this,ParentNoticeActivity.class));
            }
        });


        btndashgotoMainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentDashboardActivity.this,ParentManiMenuActivity.class));
            }
        });

        LloutRequestID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentDashboardActivity.this,ParentRequestActivity.class));
            }
        });
        btndasdEmail=(Button)findViewById(R.id.btndasdEmail);

        btndasdMSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentDashboardActivity.this,ParrentMessageActivity.class));
            }
        });
        btndasdEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentDashboardActivity.this,ParentEmailActivity.class));
            }
        });
    }
}
