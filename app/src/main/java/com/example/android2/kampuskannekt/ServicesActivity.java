package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android2.kampuskannekt.ExamResult.ExamResults;
import com.example.android2.kampuskannekt.HealthTips.HealthTipsActivity;
import com.example.android2.kampuskannekt.scholership.ScholershipActivity;

import SchoolandUniversity.ServiceSchoolandUniActivity;
import Videos.ServiceVideosMainActivity;
import ebook.Ebook;
import epaper.EPaper;
import ngo.NGO;

public class ServicesActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView img_enter_school, img_epaper, img_ebook, img_ngo;
    LinearLayout lloutschoolandUni,lloutVideos,lloutExamRes,lloutScholership,lloutHealttips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        setViews();


    }

    public void setViews(){
        lloutschoolandUni=findViewById(R.id.lloutschoolandUni);
        lloutHealttips=findViewById(R.id.lloutHealttips);
        lloutScholership=findViewById(R.id.lloutScholership);
        lloutVideos=findViewById(R.id.lloutVideos);
        img_enter_school = findViewById(R.id.img_enter_school);
        img_epaper = findViewById(R.id.img_epaper);
        lloutExamRes = findViewById(R.id.lloutExamRes);
        img_ebook = findViewById(R.id.img_ebook);
        img_ngo = findViewById(R.id.img_ngo);
        setListeners();
    }

    public void setListeners(){
        lloutschoolandUni.setOnClickListener(this);
        lloutHealttips.setOnClickListener(this);
        lloutVideos.setOnClickListener(this);
        img_enter_school.setOnClickListener(this);
        img_epaper.setOnClickListener(this);
        img_ebook.setOnClickListener(this);
        img_ngo.setOnClickListener(this);
        lloutScholership.setOnClickListener(this);
        lloutExamRes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.img_enter_school:
                Intent i1=new Intent(this, SchoolLoginActivity.class);
                startActivity(i1);
                break;

            case R.id.img_epaper:
                Intent i2=new Intent(this, EPaper.class);
                startActivity(i2);
                break;

            case R.id.img_ebook:
                Intent i3=new Intent(this, Ebook.class);
                startActivity(i3);
                break;

            case R.id.img_ngo:
                Intent i4=new Intent(this, NGO.class);
                startActivity(i4);
                break;

            case R.id.lloutschoolandUni:
                startActivity(new Intent(ServicesActivity.this,ServiceSchoolandUniActivity.class));
                break;

            case R.id.lloutVideos:
                startActivity(new Intent(ServicesActivity.this,ServiceVideosMainActivity.class));
                break;
            case R.id.lloutExamRes:
                startActivity(new Intent(ServicesActivity.this,ExamResults.class));
                break;

            case R.id.lloutScholership:
                startActivity(new Intent(ServicesActivity.this,ScholershipActivity.class));
                break;
            case R.id.lloutHealttips:
                startActivity(new Intent(ServicesActivity.this,HealthTipsActivity.class));
                break;
        }
    }
}
