package com.example.android2.kampuskannekt.scholership;

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

public class ScholerShipDetailsActivity extends AppCompatActivity {


    private Toolbar toolbar_ScholerD;
    private TextView tv_descSchoD,tv_SchoGuid,tv_SchoEligi,tv_SchoGbody,tv_email;
    private ImageView img_bannerSchoD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholer_ship_details);
        toolbar_ScholerD=findViewById(R.id.toolbar_ScholerD);
        tv_descSchoD=findViewById(R.id.tv_descSchoD);
        img_bannerSchoD=findViewById(R.id.img_bannerSchoD);
        tv_SchoGuid=findViewById(R.id.tv_SchoGuid);
        tv_SchoEligi=findViewById(R.id.tv_SchoEligi);
        tv_SchoGbody=findViewById(R.id.tv_SchoGbody);
        tv_email=findViewById(R.id.tv_email);
        Button btnbackSchoD=toolbar_ScholerD.findViewById(R.id.btnbackSchoD);
        btnbackSchoD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScholerShipDetailsActivity.this,ScholershipActivity.class));
            }
        });
        TextView tv_scho_title=toolbar_ScholerD.findViewById(R.id.tv_scho_title);

        String _Title=getIntent().getExtras().getString("Scho_TITLE");
        String _DES=getIntent().getExtras().getString("Scho_DES");
        String _ELIGI=getIntent().getExtras().getString("Scho_ELIGI");
        String _GUIDE=getIntent().getExtras().getString("Scho_GUIDE");
        String _GBODY=getIntent().getExtras().getString("Scho_GBODY");
        String _EMAIL=getIntent().getExtras().getString("Scho_EMAIL");
        String _IMAGE=getIntent().getExtras().getString("Scho_IMAGE");
        String _QUALIFYNAME=getIntent().getExtras().getString("Scho_QUANAME");

        tv_scho_title.setText(_Title);
        tv_descSchoD.setText(_DES);
        tv_email.setText(_EMAIL);
        tv_SchoGbody.setText(_GBODY);
        tv_SchoGuid.setText(_GUIDE);
        tv_SchoEligi.setText(_ELIGI);

        Glide.with(ScholerShipDetailsActivity.this).load(_IMAGE)
                .into(img_bannerSchoD);



    }
}
