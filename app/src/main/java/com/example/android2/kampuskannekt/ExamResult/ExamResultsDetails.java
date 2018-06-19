package com.example.android2.kampuskannekt.ExamResult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android2.kampuskannekt.R;

public class ExamResultsDetails extends AppCompatActivity {

    private Button btnbackED;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_results_details);

        btnbackED=findViewById(R.id.btnbackED);
        btnbackED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExamResultsDetails.this,ExamResults.class));
            }
        });
    }
}
