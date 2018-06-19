package com.example.android2.kampuskannekt.ExamResult;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android2.kampuskannekt.R;
import com.example.android2.kampuskannekt.ServicesActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamResults extends AppCompatActivity {


    private RecyclerView recyclerViewExamResults;

    String[] Subject=new String[]{"Select Subject","JAVA","KOTLIN","SCALA","PYTHON"};

    ArrayList<String> ExamBoard = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_results_adaptrer);

        ExamBoard.add("NTSE");
        ExamBoard.add("IAIS");
        ExamBoard.add("ASSET");
        ExamBoard.add("NSEJS");
        ExamBoard.add("NEET");
        ExamBoard.add("JEE");



        recyclerViewExamResults=findViewById(R.id.recyclerViewExamResults);
        recyclerViewExamResults.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewExamResults.setAdapter(new MyAdpterExamRes());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_ExamRes);
        setSupportActionBar(toolbar);

        Button btnbacktoServicesfromXamRes=(Button)toolbar.findViewById(R.id.btnbacktoServicesfromXamRes);
        ImageView save2=(ImageView)toolbar.findViewById(R.id.saveXam);

        btnbacktoServicesfromXamRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExamResults.this,ServicesActivity.class));
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutExamRes);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewXamRes);

        Spinner spinnerSubj=(Spinner)navigationView.findViewById(R.id.spinnerSubj);
        Spinner spinnerClass=(Spinner)navigationView.findViewById(R.id.spinnerClass);
        Spinner spinnerAgeG=(Spinner)navigationView.findViewById(R.id.spinnerAgeG);


        final List<String> Subjectlist =new ArrayList<>(Arrays.asList(Subject));
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Subjectlist){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    tv.setTextColor(Color.GRAY);

                } else {

                    tv.setTextColor(Color.BLACK);

                }
                return view;
            }
        };
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubj.setAdapter(aa);









        Button btnsearchXam=(Button)navigationView.findViewById(R.id.btnsearchXam);

        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layoutExamRes);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutExamRes);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public class MyAdpterExamRes extends RecyclerView.Adapter<MyAdpterExamRes.ViewHolder1>{

        @NonNull
        @Override
        public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View V=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xam_results,parent,false);
            return new ViewHolder1(V);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {

            holder.tvExamName.setText(ExamBoard.get(position));
            holder.ConsGotoXamDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ExamResults.this,ExamResultsDetails.class));
                }
            });

            if(position %2 == 1)
            {
               // holder.itemView.setBackgroundColor(Color.parseColor("#4FBAA7"));
                holder.ConsllouExamBoard.setBackgroundColor(Color.parseColor("#4FBAA7"));
            }
            else
            {
                //holder.itemView.setBackgroundColor(Color.parseColor("#135864"));
                holder.ConsllouExamBoard.setBackgroundColor(Color.parseColor("#135864"));
            }

        }

        @Override
        public int getItemCount() {
            return ExamBoard.size();
        }

        public class ViewHolder1 extends RecyclerView.ViewHolder{

            ConstraintLayout ConsllouExamBoard,ConsGotoXamDetails;
            TextView tvExamName;
            public ViewHolder1(View itemView) {
                super(itemView);
                tvExamName=itemView.findViewById(R.id.tvExamName);
                ConsllouExamBoard=itemView.findViewById(R.id.ConsllouExamBoard);
                ConsGotoXamDetails=itemView.findViewById(R.id.ConsGotoXamDetails);
            }
        }
    }




}
