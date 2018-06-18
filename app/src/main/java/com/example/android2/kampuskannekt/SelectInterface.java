package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import teacher.TeacherDashboard;

public class SelectInterface extends AppCompatActivity {

    Button btn_principle , btn_teacher , btn_parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_interface);

        btn_principle=findViewById(R.id.btn_principle);
        btn_teacher=findViewById(R.id.btn_teacher);
        btn_parent=findViewById(R.id.btn_parent);

        btn_principle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SelectInterface.this, TeacherDashboard.class);
                startActivity(i);

            }
        });

        btn_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectInterface.this, ParentDashboardActivity.class);
                startActivity(i);
            }
        });
    }
}
