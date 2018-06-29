package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Tutorial extends AppCompatActivity {

    private ImageView tutorial_image;
    private Button btnSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        //tutorial image moving
        tutorial_image=(ImageView)findViewById(R.id.imageView_tutorial);
        tutorial_image.setImageResource(R.drawable.tutorial_run);
        AnimationDrawable Ad =(AnimationDrawable)tutorial_image.getDrawable();
        Ad.start();

        btnSkip=(Button)findViewById(R.id.buttonSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tutorial.this,LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

    }
}
