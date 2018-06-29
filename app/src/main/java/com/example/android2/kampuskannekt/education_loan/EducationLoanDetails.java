package com.example.android2.kampuskannekt.education_loan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android2.kampuskannekt.R;

public class EducationLoanDetails extends AppCompatActivity {
    ModelEducationData mData;
    TextView tv_desc, tv_amt, tv_interest, tv_process, tv_url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_loan_details);
        mData = (ModelEducationData) getIntent().getSerializableExtra("MyClass");

        setViews();

    }

    private void setViews(){
        tv_desc = findViewById(R.id.tv_desc);
        tv_amt = findViewById(R.id.tv_amt);
        tv_interest = findViewById(R.id.tv_interest);
        tv_process = findViewById(R.id.tv_process);
        tv_url = findViewById(R.id.tv_url);

        setContent();
    }

    private void setContent(){
        tv_desc.setText(mData.getLoan_des());
        tv_amt.setText(mData.getLoan_amt());
        tv_interest.setText(mData.getInterest_amt());
        tv_process.setText(mData.getProcess());
        tv_url.setText(mData.getUrl());
    }
}
