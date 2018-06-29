package ebook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android2.kampuskannekt.R;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentCollectionAdapter extends RecyclerView.Adapter<StudentCollectionAdapter.ViewHolder> {

    ArrayList<ModelData> mData;
    private LayoutInflater mInflater;
    private Context c;


    public StudentCollectionAdapter(Context context,  ArrayList<ModelData> data) {
        if(context!=null) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
            this.c = context;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_student_collection_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pos = position;

        holder.cat_name.setText(mData.get(position).getTitle());
        String img_link = mData.get(position).getEbook_image();

        Glide.with(c).load(img_link).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView cat_name;
        ImageView img;
        int pos;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_cat);
            cat_name  = itemView.findViewById(R.id.cat_name);




        }

    }

}

