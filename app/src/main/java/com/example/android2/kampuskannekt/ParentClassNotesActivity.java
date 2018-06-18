package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class ParentClassNotesActivity extends AppCompatActivity {

    private Button btnbacktoMainmenuCN;
    private RecyclerView recyclerViewClassNotes;
    ArrayList<Integer> subject= new ArrayList<>();
    FloatingActionButton ClassnotesFloatingbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_class_notes);
        btnbacktoMainmenuCN=(Button)findViewById(R.id.btnbacktoMainmenuCN);
        recyclerViewClassNotes=(RecyclerView)findViewById(R.id.recyclerViewClassNotes);
        ClassnotesFloatingbtn=(FloatingActionButton)findViewById(R.id.ClassnotesFloatingbtn);
        ClassnotesFloatingbtn.attachToRecyclerView(recyclerViewClassNotes);



        ClassnotesFloatingbtn.setType(FloatingActionButton.TYPE_MINI);
        ClassnotesFloatingbtn.show();
        ClassnotesFloatingbtn.hide();
        ClassnotesFloatingbtn.show(true); // Show without an animation
        ClassnotesFloatingbtn.hide(false); // Hide without an animation
        ClassnotesFloatingbtn.setColorRipple(getResources().getColor(R.color.textColor));


        subject.add(R.drawable.teacher1_pic);
        subject.add(R.drawable.teacher2_pic);
        subject.add(R.drawable.teacher3_pic);
        subject.add(R.drawable.teacher4_pic);
        btnbacktoMainmenuCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentClassNotesActivity.this,ParentManiMenuActivity.class));
            }
        });
        recyclerViewClassNotes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewClassNotes.setAdapter(new MyCNadapter());

    }

    public class MyCNadapter extends RecyclerView.Adapter<MyCNadapter.CNViewHolder>{

        @NonNull
        @Override
        public CNViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_classnotes,parent,false);
            return new CNViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CNViewHolder holder, int position) {

            holder.CNimageTeacher.setImageResource(subject.get(position));
            if(position %2 == 1)
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#457C81"));
                  holder.CNconstrainLlout.setBackgroundColor(Color.parseColor("#457C81"));
            }
            else
            {
                holder.itemView.setBackgroundColor(Color.parseColor("#51BCA9"));
                holder.CNconstrainLlout.setBackgroundColor(Color.parseColor("#51BCA9"));
            }

            holder.ConstrainlloutClassNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ParentClassNotesActivity.this,ParentClassNotesTopicsActivity.class));
                }
            });

        }

        @Override
        public int getItemCount() {
            return subject.size();
        }

        public class CNViewHolder extends RecyclerView.ViewHolder{

            ConstraintLayout CNconstrainLlout,ConstrainlloutClassNotes;
            ImageView CNimageTeacher;
            public CNViewHolder(View itemView) {
                super(itemView);
                CNconstrainLlout=(ConstraintLayout)itemView.findViewById(R.id.CNconstrainLlout);
                ConstrainlloutClassNotes=(ConstraintLayout)itemView.findViewById(R.id.ConstrainlloutClassNotes);
                CNimageTeacher=(ImageView)itemView.findViewById(R.id.CNimageTeacher);
            }
        }
    }
}
