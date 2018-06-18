package com.example.android2.kampuskannekt.scholership;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScholershipActivity extends AppCompatActivity {

    String[] TypeList=new String[]{"Select Type","All","Government","Semi Government","Private"};
    private RecyclerView recyclerViewScholership;
    ArrayList<String> talentHunt=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholership);

        recyclerViewScholership=findViewById(R.id.recyclerViewScholership);
        recyclerViewScholership.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewScholership.setAdapter(new MyCustomAdapter());



        talentHunt.add("Lalit Kala Academi of Schoolership");
        talentHunt.add("Lalit Kala Academi of Schoolership");
        talentHunt.add("Lalit Kala Academi of Schoolership");
        talentHunt.add("Lalit Kala Academi of Schoolership");
        talentHunt.add("Lalit Kala Academi of Schoolership");
        talentHunt.add("Lalit Kala Academi of Schoolership");
        talentHunt.add("Lalit Kala Academi of Schoolership");
        talentHunt.add("Lalit Kala Academi of Schoolership");
        talentHunt.add("Lalit Kala Academi of Schoolership");
        talentHunt.add("Lalit Kala Academi of Schoolership");
        talentHunt.add("Lalit Kala Academi of Schoolership");
        talentHunt.add("Lalit Kala Academi of Schoolership");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarScholership);
        setSupportActionBar(toolbar);

        Button btnback =toolbar.findViewById(R.id.btnbacktoServicesfromScholership);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScholershipActivity.this,ServicesActivity.class));
            }
        });
        ImageView saveScholership=toolbar.findViewById(R.id.saveScholership);

        saveScholership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer_layoutScholer);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutScholer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewScholer);


        Spinner spinnerType=navigationView.findViewById(R.id.spinnerType);
        Spinner spinnerQualify=navigationView.findViewById(R.id.spinnerQualify);
        Spinner spinnerAgeGscholership=navigationView.findViewById(R.id.spinnerAgeGscholership);
        Button btnsearchscholership=navigationView.findViewById(R.id.btnsearchscholership);


        final List<String> Type =new ArrayList<>(Arrays.asList(TypeList));
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Type){
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
        spinnerType.setAdapter(aa);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutScholer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.ViewHolder2>{


        @NonNull
        @Override
        public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View V=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scholership,parent,false);
            return new ViewHolder2(V);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder2 holder, int position) {

            holder.tvExamDes.setText(talentHunt.get(position));
            holder.rowConsScholerShip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ScholershipActivity.this,ScholerShipDetailsActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return talentHunt.size();
        }

        public class ViewHolder2 extends RecyclerView.ViewHolder{

            TextView tvExamDes;
            ConstraintLayout rowConsScholerShip;
            public ViewHolder2(View itemView) {
                super(itemView);
                tvExamDes=itemView.findViewById(R.id.tvExamDes);
                rowConsScholerShip=itemView.findViewById(R.id.rowConsScholerShip);
            }
        }
    }


}
