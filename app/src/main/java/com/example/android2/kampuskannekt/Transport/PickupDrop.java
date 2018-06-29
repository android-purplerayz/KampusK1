package com.example.android2.kampuskannekt.Transport;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android2.kampuskannekt.ParentDashboardActivity;
import com.example.android2.kampuskannekt.R;

import java.util.ArrayList;

public class PickupDrop extends AppCompatActivity {

    private Toolbar toolbarPnD;
    private RecyclerView recyclerviewPnD;
    ArrayList<String> date=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_drop);
        toolbarPnD=findViewById(R.id.toolbarPnD);
        recyclerviewPnD=findViewById(R.id.recyclerviewPnD);
        Button btnback=toolbarPnD.findViewById(R.id.btnPnDBack);
        TextView notifyNumber=toolbarPnD.findViewById(R.id.tvPnDnotify);
        Button btnnotify=toolbarPnD.findViewById(R.id.btnPnDnoti);
        date.add("1 \n Jan");
        date.add("1 \n Fab");
        date.add("1 \n March");
        date.add("1 \n April");
        date.add("1 \n May");
        date.add("1 \n June");
        date.add("1 \n July");
        date.add("1 \n Aug");
        date.add("1 \n Sep");
        date.add("1 \n Oct");
        date.add("1 \n Nov");
        date.add("1 \n Dec");
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PickupDrop.this,ParentDashboardActivity.class));
            }
        });

        recyclerviewPnD.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerviewPnD.setAdapter(new Myadapter());
    }
    public  class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder1>{

        @NonNull
        @Override
        public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View V=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pickupndrop,parent,false);
            return new ViewHolder1(V);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
            holder.textViewdate.setText(date.get(position));

        }

        @Override
        public int getItemCount() {
            return date.size();
        }

        public class ViewHolder1 extends RecyclerView.ViewHolder{


            TextView textViewdate;
            public ViewHolder1(View itemView) {
                super(itemView);
                textViewdate=itemView.findViewById(R.id.textViewdate);
            }
        }
    }
}
