package com.example.android2.kampuskannekt.HealthTips;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android2.kampuskannekt.R;
import com.example.android2.kampuskannekt.ServicesActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HealthTipsActivity extends AppCompatActivity {

    private RecyclerView recyclerviewHealthtips;
    String[] Tips=new String[]{"Select Tips","Cold","Fever","FLu"};
    ArrayList<String> healTips=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        healTips.add("Health and Wellness Tips for College");
        healTips.add("Health and Wellness Tips for College");
        healTips.add("Health and Wellness Tips for College");
        healTips.add("Health and Wellness Tips for College");
        healTips.add("Health and Wellness Tips for College");
        healTips.add("Health and Wellness Tips for College");
        healTips.add("Health and Wellness Tips for College");
        healTips.add("Health and Wellness Tips for College");
        healTips.add("Health and Wellness Tips for College");
        healTips.add("Health and Wellness Tips for College");
        healTips.add("Health and Wellness Tips for College");

        recyclerviewHealthtips=findViewById(R.id.recyclerviewHealthtips);
        recyclerviewHealthtips.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerviewHealthtips.setAdapter(new MyadapterHT());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHT);
        setSupportActionBar(toolbar);

        Button btnbacktoServicesfromHT=toolbar.findViewById(R.id.btnbacktoServicesfromHT);
        ImageView saveHT=toolbar.findViewById(R.id.saveHT);

        btnbacktoServicesfromHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HealthTipsActivity.this,ServicesActivity.class));
            }
        });
        saveHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layoutHT);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutHT);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewHT);
        Spinner spinnerHT =navigationView.findViewById(R.id.spinnerHT);


        final List<String> Tipslist =new ArrayList<>(Arrays.asList(Tips));
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Tipslist){
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
        spinnerHT.setAdapter(aa);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutHT);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public class MyadapterHT extends RecyclerView.Adapter<MyadapterHT.ViewHolder7>{

        @NonNull
        @Override
        public ViewHolder7 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View V=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_health_tips,parent,false);
            return new ViewHolder7(V);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder7 holder, int position) {
            holder.tvHT1.setText(healTips.get(position));
            holder.ConsllouttoDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(HealthTipsActivity.this,HealthTipsDetailsActivity.class));
                }
            });

        }

        @Override
        public int getItemCount() {
            return healTips.size();
        }

        public class ViewHolder7 extends RecyclerView.ViewHolder{

            ConstraintLayout ConsllouttoDetails;
            TextView tvHT1;
            public ViewHolder7(View itemView) {
                super(itemView);
                tvHT1=itemView.findViewById(R.id.tvHT1);
                ConsllouttoDetails=itemView.findViewById(R.id.ConsllouttoDetails);
            }
        }
    }

}
