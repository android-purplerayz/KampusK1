package com.example.android2.kampuskannekt.scholership;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android2.kampuskannekt.R;

public class ScholerShipDetailsActivity extends AppCompatActivity {

    private Button btnbackSchoD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholer_ship_details);
        btnbackSchoD=findViewById(R.id.btnbackSchoD);

        btnbackSchoD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScholerShipDetailsActivity.this,ScholershipActivity.class));
            }
        });
    }
}
