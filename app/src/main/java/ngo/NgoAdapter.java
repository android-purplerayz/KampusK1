package ngo;

import android.content.Context;
import android.content.Intent;
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

public class NgoAdapter  extends RecyclerView.Adapter<NgoAdapter.ViewHolder> {

    ArrayList<ModelNgoData> mData;
    private LayoutInflater mInflater;
    private Context c;


    public NgoAdapter(Context context,  ArrayList<ModelNgoData> data) {
        if(context!=null) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
            this.c = context;
        }
    }

    @NonNull
    @Override
    public NgoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_ngo_list, parent, false);
        return new NgoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NgoAdapter.ViewHolder holder, int position) {
        holder.pos = position;

        holder.cat_name.setText(mData.get(position).getNgo_title());
        holder.cat_address.setText(mData.get(position).getNgo_address());
        holder.tv_rating.setText(mData.get(position).getNgo_rating());

        String imglink=mData.get(position).getNgo_img();
        Glide.with(c).load(imglink).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView cat_name, cat_address, tv_rating;
        ImageView img, img_cat_icon;
        int pos;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_cat);
            cat_name  = itemView.findViewById(R.id.cat_name);
            img_cat_icon  = itemView.findViewById(R.id.img_cat_icon);
            cat_address = itemView.findViewById(R.id.cat_address);
            tv_rating = itemView.findViewById(R.id.tv_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(c , NgoDetails.class);
                    i.putExtra("MyClass", mData.get(pos));
                    c.startActivity(i);
                }
            });



        }

    }



}
