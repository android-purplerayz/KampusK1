package epaper;

/**
 * Created by ANDROID on 9/19/2017.
 */

import android.app.DownloadManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.android2.kampuskannekt.R;

import java.util.ArrayList;
import java.util.HashMap;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {



    ArrayList<ModelEpaperData>  mData;
    private LayoutInflater mInflater;
    private Context c;


    public CategoryAdapter(Context context,  ArrayList<ModelEpaperData> data) {
        if(context!=null) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
            this.c = context;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_epaper_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pos = position;

        holder.cat_name.setText(mData.get(position).getTitle());

        String img_link = mData.get(position).getEpaper_image();

        Glide.with(c).load(img_link).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView cat_name;
        ImageView img, img_cat_icon;
        int pos;

        ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_cat);
            cat_name  = itemView.findViewById(R.id.cat_name);
            img_cat_icon  = itemView.findViewById(R.id.img_cat_icon);



        }

    }



}
/*public class CategoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> category_list;
    TextView cat_name,cat_item;
    ImageView img;



    public CategoryAdapter(Context context , ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.category_list = list;

    }


    @Override
    public int getCount() {

        return category_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return category_list.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v= convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(R.layout.row_epaper_list, null);
        img = listViewItem.findViewById(R.id.img_cat);
        cat_name  = listViewItem.findViewById(R.id.cat_name);
        cat_item  = listViewItem.findViewById(R.id.cat_item);
        cat_name.setText(category_list.get(position).get("cat"));


        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return listViewItem;
    }


}*/
