package com.example.android2.kampuskannekt.TalentHunt;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TalentHuntAdapter extends RecyclerView.Adapter<TalentHuntAdapter.ViewHolder> {

    ArrayList<ModelTalentHuntData> mData;
    private LayoutInflater mInflater;
    private Context c;


    public TalentHuntAdapter(Context context,  ArrayList<ModelTalentHuntData> data) {
        if(context!=null) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
            this.c = context;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_talenthunt, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pos = position;

        holder.cat_name.setText(mData.get(position).getTalent_title());
        holder.cat_address.setText(mData.get(position).getState_name());
        String img_link = "https://pbs.twimg.com/profile_images/772681499334160384/5OF6HMmP_200x200.jpg";

        Glide.with(c).load(img_link).into(holder.img);

        holder.cat_date.setText(parseDateToddMMyyyy(mData.get(position).getTalent_datetime()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView cat_name, cat_address, cat_date;
        ImageView img, img_cat_icon, img_msg_icon, img_call_icon;
        ConstraintLayout clayout;
        int pos;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_cat);
            cat_name  = itemView.findViewById(R.id.cat_name);
            img_cat_icon  = itemView.findViewById(R.id.img_cat_icon);
            cat_address  = itemView.findViewById(R.id.cat_address);
            clayout = itemView.findViewById(R.id.clayout);
            img_msg_icon = itemView.findViewById(R.id.img_msg_icon);
            img_call_icon = itemView.findViewById(R.id.img_call_icon);
            cat_date = itemView.findViewById(R.id.cat_date);

            clayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(c , TalentHuntDetails.class);
                   /* i.putExtra("img", mData.get(pos).getTalent_img());
                    i.putExtra("title", mData.get(pos).getTalent_title());
                    i.putExtra("desc", mData.get(pos).getTalent_cbody());
                    i.putExtra("subject", mData.get(pos).getTalent_subject());
                    i.putExtra("location", mData.get(pos).getState_name());*/
                    i.putExtra("MyClass", mData.get(pos));
                    c.startActivity(i);
                }
            });

            img_msg_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            img_call_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });


        }

    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}

