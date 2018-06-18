package Videos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android2.kampuskannekt.R;
import com.example.android2.kampuskannekt.ServicesActivity;

import java.util.ArrayList;

public class ServiceVideosMainActivity extends AppCompatActivity
        {

    ArrayList<String> ViewersNum= new ArrayList<>();
    String[] VideoImage=new String[]{"https://4.bp.blogspot.com/-Pv3B__yg6FA/VxCpV511FmI/AAAAAAAAHvI/1Vq7ODG-fvMOCia4BqKiXe_aIscDJMkWgCLcB/s1600/plustwo_scert_text_book.png","http://ormistonprimary.school.nz/wp-content/uploads/2016/04/entertainment-book1.png",
    "http://images.newindianexpress.com/uploads/user/imagelibrary/2018/4/16/w600X300/abc-books-chalk-265076.jpg","https://positivepsychologyprogram.com/wp-content/uploads/2017/02/mindfulness-books-beginners.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_videos_main);

        ViewersNum.add("1");
        ViewersNum.add("2");
        ViewersNum.add("3");
        ViewersNum.add("4");
        ViewersNum.add("5");
        ViewersNum.add("6");
        ViewersNum.add("7");
        ViewersNum.add("8");

        RecyclerView recyclerViewSchoolList =(RecyclerView)findViewById(R.id.recyclerViewVideosList);
        recyclerViewSchoolList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSchoolList.setAdapter(new MyadapterVideos());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_cat1);
        setSupportActionBar(toolbar);

        ImageView save = toolbar.findViewById(R.id.save1);
        Button btnbacktoServices=toolbar.findViewById(R.id.btnbacktoServices);

        btnbacktoServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceVideosMainActivity.this,ServicesActivity.class));
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout1);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public class MyadapterVideos extends RecyclerView.Adapter<MyadapterVideos.VideosViewHolder>{


        @NonNull
        @Override
        public VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videos_list,parent,false);
            return new VideosViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VideosViewHolder holder, int position) {

            holder.tvViewers.setText(ViewersNum.get(position));

            holder.btnplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ServiceVideosMainActivity.this,ServicePlayVideosActivity.class));
                }
            });
            Glide.with(ServiceVideosMainActivity.this).load(VideoImage[position])
                    .into(holder.VideoThumimage);
        }

        @Override
        public int getItemCount() {
            return VideoImage.length;
        }

        public class VideosViewHolder extends RecyclerView.ViewHolder{

            Button btnplay;
            ImageView VideoThumimage;
            TextView tvViewers;
            public VideosViewHolder(View itemView) {
                super(itemView);
                tvViewers=(TextView)itemView.findViewById(R.id.tvViewers);
                btnplay=(Button) itemView.findViewById(R.id.btnplay);
                VideoThumimage=(ImageView)itemView.findViewById(R.id.VideoThumimage);
            }
        }
    }





//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;

}
