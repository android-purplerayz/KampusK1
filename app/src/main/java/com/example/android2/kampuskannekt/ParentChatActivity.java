package com.example.android2.kampuskannekt;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ParentChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_chat);
        recyclerView=(RecyclerView) findViewById(R.id.my_recycler_view2);

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
        recyclerView.setAdapter(new MyrecycleAdapter1());
    }
    public class MyrecycleAdapter1 extends RecyclerView.Adapter<MyrecycleAdapter1.ViewHolder1>{

        @NonNull
        @Override
        public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_chat_reciver,parent,false);
            return new ViewHolder1(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
            holder.tv12.setText(arrayList.get(position));
            holder.tv22.setText(arrayList.get(position));

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder1 extends RecyclerView.ViewHolder{


            private TextView tv12,tv22;
            public ViewHolder1(View itemView) {
                super(itemView);
                tv12=(TextView)itemView.findViewById(R.id.tvChat1);
                tv22=(TextView)itemView.findViewById(R.id.tvChat2);
            }
        }
    }
}
