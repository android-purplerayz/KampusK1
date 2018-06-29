package com.example.android2.kampuskannekt.HealthTips;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android2.kampuskannekt.R;

public class HealthTipsDetailsActivity extends AppCompatActivity {

    private Button btnbackHTD;
    private ImageView imageHTD;
    private TextView tvShortDes,tvlongDes,tvTitle,tvDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips_details);
        btnbackHTD=findViewById(R.id.btnbackHTD);
        imageHTD=findViewById(R.id.imageHTD);
        tvShortDes=findViewById(R.id.tvShortDes);
        tvlongDes=findViewById(R.id.tvlongDes);
        tvTitle=findViewById(R.id.tvTitle);
        tvDate=findViewById(R.id.tvDate);
        btnbackHTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HealthTipsDetailsActivity.this,HealthTipsActivity.class));
            }
        });

        String Image=getIntent().getExtras().getString("HT_Image");
        String ShortDes=getIntent().getExtras().getString("HT_ShortDes");
        String LongDes=getIntent().getExtras().getString("HT_Des");
        String Title=getIntent().getExtras().getString("HT_Title");
        String Date=getIntent().getExtras().getString("HT_Date");

        tvlongDes.setText(LongDes);
        tvShortDes.setText(ShortDes);
        tvTitle.setText(Title);
        tvDate.setText("Date: "+Date);
        Glide.with(HealthTipsDetailsActivity.this).load(Image)
                .into(imageHTD);
    }
}
