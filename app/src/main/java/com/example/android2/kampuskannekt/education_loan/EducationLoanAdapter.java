package com.example.android2.kampuskannekt.education_loan;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android2.kampuskannekt.R;

import java.util.ArrayList;

public class EducationLoanAdapter extends RecyclerView.Adapter<EducationLoanAdapter.ViewHolder> {

    ArrayList<ModelEducationData> mData;
    private LayoutInflater mInflater;
    private Context c;


    public EducationLoanAdapter(Context context,   ArrayList<ModelEducationData> data) {
        if(context!=null) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
            this.c = context;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_education_loan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pos = position;

        holder.cat_name.setText(mData.get(position).getLoan_title());
        holder.cat_address.setText(mData.get(position).getLoan_des());
        String img_link = "https://pbs.twimg.com/profile_images/772681499334160384/5OF6HMmP_200x200.jpg";

        Glide.with(c).load(img_link).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView cat_name, cat_address;
        ImageView img, img_msg_icon;
        ConstraintLayout clayout;
        int pos;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_cat);
            cat_name  = itemView.findViewById(R.id.cat_name);

            cat_address  = itemView.findViewById(R.id.cat_address);
            clayout = itemView.findViewById(R.id.clayout);
            img_msg_icon = itemView.findViewById(R.id.img_msg_icon);


            clayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(c , EducationLoanDetails.class);
                    i.putExtra("MyClass", mData.get(pos));
                    c.startActivity(i);
                }
            });

            img_msg_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });



        }

    }



}

