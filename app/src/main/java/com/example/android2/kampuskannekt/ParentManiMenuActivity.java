package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ParentManiMenuActivity extends AppCompatActivity {

    private Button btngotoDashboard,btngotoservice;
    private LinearLayout lloutclassnotes,lloutparentfees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_mani_menu);
        lloutclassnotes=findViewById(R.id.lloutclassnotes);
        lloutparentfees=findViewById(R.id.lloutparentfees);
        lloutparentfees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentManiMenuActivity.this,ParentFeesActivity.class));
            }
        });

        lloutclassnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentManiMenuActivity.this,ParentClassNotesActivity.class));
            }
        });
        btngotoDashboard=(Button)findViewById(R.id.btngotoDashboard);
        btngotoservice=(Button)findViewById(R.id.btngotoservice);

        btngotoservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentManiMenuActivity.this,ServicesActivity.class));
            }
        });

        btngotoDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentManiMenuActivity.this,ParentDashboardActivity.class));
            }
        });
    }
}
