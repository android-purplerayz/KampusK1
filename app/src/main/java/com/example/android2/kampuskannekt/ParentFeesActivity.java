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

public class ParentFeesActivity extends AppCompatActivity {


    private RecyclerView recyclerViewFessID;
    private Button btnPFessBack;
    ArrayList<String> feesDetails = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_fees);

        feesDetails.add("Admission");
        feesDetails.add("Re-Admission");
        feesDetails.add("Computer Fees");
        feesDetails.add("Lab Fees");
        feesDetails.add("Transport Fees");
        feesDetails.add("Eamination Fees");
        feesDetails.add("Caution Money");

        btnPFessBack=(Button)findViewById(R.id.btnPFessBack);

        btnPFessBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentFeesActivity.this,ParentManiMenuActivity.class));
            }
        });

        recyclerViewFessID=(RecyclerView)findViewById(R.id.recyclerViewFessID);

        recyclerViewFessID.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewFessID.setAdapter(new MyFeesAdapter());
    }
    public class MyFeesAdapter extends RecyclerView.Adapter<MyFeesAdapter.FeesViewHolder>{


        @NonNull
        @Override
        public FeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_fees,parent,false);
            return new FeesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FeesViewHolder holder, int position) {

            holder.tvItemFess1.setText(feesDetails.get(position));

        }

        @Override
        public int getItemCount() {
            return feesDetails.size();
        }

        public class FeesViewHolder extends RecyclerView.ViewHolder{

            TextView tvItemFess1;
            public FeesViewHolder(View itemView) {
                super(itemView);
                tvItemFess1=(TextView)itemView.findViewById(R.id.tvItemFess1);
            }
        }
    }
}
