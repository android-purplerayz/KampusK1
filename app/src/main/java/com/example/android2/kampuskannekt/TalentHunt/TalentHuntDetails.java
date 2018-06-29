package com.example.android2.kampuskannekt.TalentHunt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android2.kampuskannekt.R;

public class TalentHuntDetails extends AppCompatActivity {

    ImageView img_banner;
    ProgressBar progress_bar;
    TextView tv_left, tv_events, tv_desc, tv_address;
    String img, title, desc, subj, location;
    ModelTalentHuntData mData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talenthunt_details);


        mData = (ModelTalentHuntData) getIntent().getSerializableExtra("MyClass");

        img = mData.getTalent_img();
        title = mData.getTalent_title();
        desc = mData.getTalent_des();
        subj =  mData.getTalent_subject();
        location =  mData.getState_name();



        setViews();
    }

    private void setViews(){
        img_banner = findViewById(R.id.img_banner);
        progress_bar = findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.GONE);
        tv_left = findViewById(R.id.tv_left);
        tv_events= findViewById(R.id.tv_events);
        tv_desc= findViewById(R.id.tv_desc);
        tv_address= findViewById(R.id.tv_address);
        setContents();
    }

    private void setContents(){
        //String img_link = "http://quovx4d83tr2hp1r22mgwa1m.wpengine.netdna-cdn.com/wp-content/uploads/2017/06/young-game-designers-awards-600x300.jpg";


        Glide.with(this).load(img).into(img_banner);

        tv_left.setText(title);
        tv_events.setText(subj);
        tv_desc.setText(desc);
        tv_address.setText(location);
    }






}
