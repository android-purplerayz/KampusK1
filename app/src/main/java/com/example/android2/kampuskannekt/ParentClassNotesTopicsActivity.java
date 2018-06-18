package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class ParentClassNotesTopicsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPClassNotesTopics;
    Button btnbacktoMainmenuPclassNTopics;
    FloatingActionButton fabPCNT;
    ArrayList<String> Classnumber = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_class_notes_topics);
        recyclerViewPClassNotesTopics=(RecyclerView)findViewById(R.id.recyclerViewPClassNotesTopics);
        btnbacktoMainmenuPclassNTopics=(Button)findViewById(R.id.btnbacktoMainmenuPclassNTopics);
        fabPCNT=(FloatingActionButton)findViewById(R.id.fabPCNT);
        fabPCNT.attachToRecyclerView(recyclerViewPClassNotesTopics);

        fabPCNT.setType(FloatingActionButton.TYPE_MINI);
        fabPCNT.show();
        fabPCNT.hide();
        fabPCNT.show(true); // Show without an animation
        fabPCNT.hide(false); // Hide without an animation
        fabPCNT.setColorRipple(getResources().getColor(R.color.textColor));

        btnbacktoMainmenuPclassNTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentClassNotesTopicsActivity.this,ParentClassNotesActivity.class));
            }
        });

        Classnumber.add("Class 1");
        Classnumber.add("Class 2");
        Classnumber.add("Class 3");
        Classnumber.add("Class 4");
        Classnumber.add("Class 5");
        Classnumber.add("Class 6");
        Classnumber.add("Class 7");
        Classnumber.add("Class 8");
        Classnumber.add("Class 9");
        Classnumber.add("Class 10");
        Classnumber.add("Class 11");
        Classnumber.add("Class 12");




        recyclerViewPClassNotesTopics.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewPClassNotesTopics.setAdapter(new MyClassNTadapter());
    }

    public class MyClassNTadapter extends RecyclerView.Adapter<MyClassNTadapter.CNTViewHolder>{



        @Override
        public CNTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View V=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_class_notestopics,parent,false);

            return new CNTViewHolder(V);
        }

        @Override
        public void onBindViewHolder( CNTViewHolder holder, int position) {

            holder.tvCNTclass.setText(Classnumber.get(position));

        }

        @Override
        public int getItemCount() {
            return Classnumber.size();
        }

        public  class CNTViewHolder extends RecyclerView.ViewHolder{

            TextView tvCNTclass;
            public CNTViewHolder(View itemView) {
                super(itemView);
                tvCNTclass=(TextView)itemView.findViewById(R.id.tvCNTclass);
            }
        }
    }


}
