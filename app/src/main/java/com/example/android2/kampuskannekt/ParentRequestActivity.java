package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ParentRequestActivity extends AppCompatActivity {


    private Button btnReqBackID;
    private RecyclerView recyclerView;
    ArrayList<String> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_request);
        btnReqBackID=(Button)findViewById(R.id.btnReqBackID);
        btnReqBackID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ParentRequestActivity.this,ParentDashboardActivity.class));
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.my_recycler_viewID);

        arrayList.add("Loren ipsum dolor sit amet,consectetur.....");
        arrayList.add("Loren ipsum dolor sit amet,consectetur....");
        arrayList.add("Loren ipsum dolor sit amet,consectetur....");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur....");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur...");
        arrayList.add("Loren ipsum dolor sit amet,consectetur....");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new myRecycleAdapter());




    }

    public class myRecycleAdapter extends RecyclerView.Adapter<myRecycleAdapter.ViewHolder>{

        public class ViewHolder extends RecyclerView.ViewHolder{
            //textview
            private TextView textView;
            private ConstraintLayout constraintLayout;
            public ViewHolder(View itemView) {
                //casting tv
                super(itemView);
                constraintLayout=(ConstraintLayout)itemView.findViewById(R.id.constraintLayoutP_requestID);
                textView=(TextView)itemView.findViewById(R.id.textView14);
            }
        }

        @NonNull
        @Override
        public myRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_request,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull myRecycleAdapter.ViewHolder holder, int position) {
            holder.textView.setText(arrayList.get(position));


            //holder.tv.setText

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

    }


}
