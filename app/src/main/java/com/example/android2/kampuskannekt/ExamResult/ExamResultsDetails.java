package com.example.android2.kampuskannekt.ExamResult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.android2.kampuskannekt.R;

import SchoolandUniversity.ServiceSchoolandUniActivity;

public class ExamResultsDetails extends AppCompatActivity {

    private Button btnbackED;
    private ImageView examResImage;
    private TextView tv_govern,tv_Guidline,tv_Title,tv_descED,tv_XRsubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_results_details);
        examResImage=findViewById(R.id.examResImage);
        tv_govern=findViewById(R.id.tv_govern);
        tv_Guidline=findViewById(R.id.tv_Guidline);
        tv_descED=findViewById(R.id.tv_descED);
        tv_XRsubject=findViewById(R.id.tv_XRsubject);


        String Image=getIntent().getExtras().getString("EXAM_IMAGE");
        String Title=getIntent().getExtras().getString("EXAM_TITLE");
        String Govern=getIntent().getExtras().getString("EXAM_GOVERN");
        String Guidline=getIntent().getExtras().getString("EXAM_GUIDLINE");
        String Description=getIntent().getExtras().getString("EXAM_DES");
        //String Subject=getIntent().getExtras().getString("EXAM_SUBJECT");

        Toolbar toolbar =findViewById(R.id.toolbar_examres);
        tv_Title=toolbar.findViewById(R.id.tv_Title);
        btnbackED=toolbar.findViewById(R.id.btnbackED);
        btnbackED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExamResultsDetails.this,ExamResults.class));
            }
        });

        tv_Title.setText(Title);
        tv_govern.setText(Govern);
        tv_descED.setText(Description);
       // tv_XRsubject.setText(Subject);
        tv_Guidline.setText(Guidline);
        Glide.with(ExamResultsDetails.this).load(Image)
                .into(examResImage);
    }
}
