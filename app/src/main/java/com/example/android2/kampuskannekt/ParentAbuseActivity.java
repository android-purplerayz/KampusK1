package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ParentAbuseActivity extends AppCompatActivity {


    private RecyclerView recyclerViewParentAbuse;
    ArrayList<String> smg = new ArrayList<>();
    private Button btnPAbuseBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_abuse);
        recyclerViewParentAbuse=(RecyclerView)findViewById(R.id.recyclerViewParentAbuse);
        btnPAbuseBack=(Button)findViewById(R.id.btnPAbuseBack);

        btnPAbuseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentAbuseActivity.this,ParentDashboardActivity.class));
            }
        });

        recyclerViewParentAbuse.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewParentAbuse.setAdapter(new MyAbuseAdapter());

        smg.add("Loren ipsum dolor sit amet,consectetur.....");
        smg.add("Loren ipsum dolor sit amet,consectetur.....");
        smg.add("Loren ipsum dolor sit amet,consectetur.....");
        smg.add("Loren ipsum dolor sit amet,consectetur.....");
        smg.add("Loren ipsum dolor sit amet,consectetur.....");
        smg.add("Loren ipsum dolor sit amet,consectetur.....");
        smg.add("Loren ipsum dolor sit amet,consectetur.....");
        smg.add("Loren ipsum dolor sit amet,consectetur.....");
        smg.add("Loren ipsum dolor sit amet,consectetur.....");
        smg.add("Loren ipsum dolor sit amet,consectetur.....");

    }

    public class MyAbuseAdapter extends RecyclerView.Adapter<MyAbuseAdapter.AbuseViewHolder>{


        @NonNull
        @Override
        public AbuseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_abuse,parent,false);
            return new AbuseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AbuseViewHolder holder, int position) {
            holder.textViewPabuse4.setText(smg.get(position));

        }

        @Override
        public int getItemCount() {
            return smg.size();
        }

        public class AbuseViewHolder extends RecyclerView.ViewHolder{

            TextView textViewPabuse4;
            public AbuseViewHolder(View itemView) {
                super(itemView);
                textViewPabuse4=(TextView)itemView.findViewById(R.id.textViewPabuse4);
            }
        }
    }
}
