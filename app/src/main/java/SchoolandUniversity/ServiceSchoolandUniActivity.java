package SchoolandUniversity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android2.kampuskannekt.R;
import com.example.android2.kampuskannekt.ServicesActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceSchoolandUniActivity extends AppCompatActivity {


    private String URLSchoolandUni="http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/getschooluniversitylist";

    private ImageView ivSchoolLogo,ivUniLogo;
    ArrayList<String>  schoolName= new ArrayList<>();
    private RecyclerView recyclerViewSchoolList;
    ArrayList<SetGetUnischool>  SchoolandUniversityArrayList=null;
    String[] State=new String[]{"Select State","Kolkata","Chennai","Bangalore","Borofkol"};
    String[] distric=new String[]{"Select Distric","Kolkata","Chennai","Bangalore","Borofkol"};
    String[] board=new String[]{"Select Board","Kolkata","Chennai","Bangalore","Borofkol"};
    String[] age=new String[]{"Select age","All","1-10","11-17","18-22"};
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_schooland_uni);

        GetSchoolandUniList(URLSchoolandUni);



        recyclerViewSchoolList =(RecyclerView)findViewById(R.id.recyclerViewSchoolList);

        recyclerViewSchoolList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_cat);
        setSupportActionBar(toolbar);

        ImageView save = toolbar.findViewById(R.id.save);
        Button btnbacktoServices=toolbar.findViewById(R.id.btnbacktoServices);

        btnbacktoServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceSchoolandUniActivity.this,ServicesActivity.class));
            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        ivSchoolLogo=(ImageView)navigationView.findViewById(R.id.ivSchoolLogo);
        ivUniLogo=(ImageView)navigationView.findViewById(R.id.ivUniLogo);
        Spinner spinnerState=(Spinner)navigationView.findViewById(R.id.spinnerState);
        Spinner spinnerDistric=(Spinner)navigationView.findViewById(R.id.spinnerDistric);
        Spinner spinnerBoard=(Spinner)navigationView.findViewById(R.id.spinnerBoard);
        Spinner spinnerAgeGroup=(Spinner)navigationView.findViewById(R.id.spinnerAgeGroup);

        final List<String> statelist =new ArrayList<>(Arrays.asList(State));
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,statelist){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    tv.setTextColor(Color.GRAY);

                } else {

                    tv.setTextColor(Color.BLACK);

                }
                return view;
            }
        };
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(aa);



        Switch switchSchoolandUni=(Switch)navigationView.findViewById(R.id.switchSchoolandUni);

        switchSchoolandUni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked==true){

                    ivUniLogo.setImageResource(R.drawable.ic_uni_logo_svg);
                    ivSchoolLogo.setImageBitmap(null);
                }else{
                    ivSchoolLogo.setImageResource(R.drawable.ic_school_logo);
                    ivUniLogo.setImageBitmap(null);
                }
            }
        });

        //navigationView.setNavigationItemSelectedListener(this);




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public class MYadapterSchoollist extends RecyclerView.Adapter<MYadapterSchoollist.ViewHolderSchool>{


        ArrayList<SetGetUnischool> arrayList;
        public MYadapterSchoollist(Context context,ArrayList<SetGetUnischool> arrayListA){
            if(context!=null){
                this.arrayList=arrayListA;



            }
        }
        @NonNull
        @Override
        public ViewHolderSchool onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shool_universitice,parent,false);

            return new ViewHolderSchool(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderSchool holder, int position) {

            holder.tvSchoolName.setText(arrayList.get(position).getUniversity_nameS());
            holder.textViewshortDes.setText(arrayList.get(position).getShort_desS());

            String link=arrayList.get(position).getSchool_imageS();
            Glide.with(ServiceSchoolandUniActivity.this).load(link)
                    .into(holder.imageViewSchool);
            Log.i("Image",link);

            holder.rowSchoolandUni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ServiceSchoolandUniActivity.this,SchoolDetailsActivity.class));
                }
            });

        }

        @Override
        public int getItemCount() {
            return SchoolandUniversityArrayList.size();
        }

        public class ViewHolderSchool extends RecyclerView.ViewHolder{

            ConstraintLayout rowSchoolandUni;
            TextView tvSchoolName,textViewshortDes;
            ImageView imageViewSchool;
            public ViewHolderSchool(View itemView) {
                super(itemView);
                imageViewSchool=(ImageView)itemView.findViewById(R.id.imageViewSchool);
                rowSchoolandUni=(ConstraintLayout)itemView.findViewById(R.id.rowSchoolandUni);
                tvSchoolName=(TextView)itemView.findViewById(R.id.tvSchoolName);
                textViewshortDes=(TextView)itemView.findViewById(R.id.textViewshortDes);
            }
        }
    }

    public void GetSchoolandUniList(String url){

        SchoolandUniversityArrayList=new ArrayList<>();
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("respo",response);
                    String Status = jsonObject.getString("status");
                    String msg=jsonObject.getString("msg");
                    String ImageURL=jsonObject.getString("image_url");
                    if(Status.equalsIgnoreCase("Success")){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++){

                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            String SchoolID=jsonObject1.getString("school_id");
                            String UniversityName=jsonObject1.getString("university_name");
                            String ShortDes=jsonObject1.getString("short_des");
                            String LongDes=jsonObject1.getString("long_des");
                            String Address=jsonObject1.getString("address");
                            String Principle=jsonObject1.getString("principal");
                            String Phone=jsonObject1.getString("phone");
                            String Rating=jsonObject1.getString("rating");
                            String Board=jsonObject1.getString("board");
                            String Stage=jsonObject1.getString("stage");
                            String SchoolImage=jsonObject1.getString("school_image");
                            String AgeGroup=jsonObject1.getString("age_group");

                            SetGetUnischool setgetUnischool=new SetGetUnischool();
                            setgetUnischool.setSchool_idS(SchoolID);
                            setgetUnischool.setUniversity_nameS(UniversityName);
                            setgetUnischool.setShort_desS(ShortDes);
                            setgetUnischool.setLong_des(LongDes);
                            setgetUnischool.setAddressS(Address);
                            setgetUnischool.setPrincipalS(Principle);
                            setgetUnischool.setPhoneS(Phone);
                            setgetUnischool.setRatingS(Rating);
                            setgetUnischool.setBoardS(Board);
                            setgetUnischool.setStageS(Stage);
                            setgetUnischool.setSchool_imageS(ImageURL+SchoolImage);
                            setgetUnischool.setAge_groupS(AgeGroup);

                            SchoolandUniversityArrayList.add(setgetUnischool);
                        }

                        if(SchoolandUniversityArrayList.size()>0){

                            recyclerViewSchoolList.setAdapter(new MYadapterSchoollist(ServiceSchoolandUniActivity.this,SchoolandUniversityArrayList));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(ServiceSchoolandUniActivity.this).add(stringRequest);

    }

    public class SetGetUnischool{

        String  school_idS,university_nameS,short_desS,long_des,addressS,principalS,phoneS,ratingS,boardS,stageS,school_imageS,age_groupS;



        public String getLong_des() {
            return long_des;
        }

        public void setLong_des(String long_des) {
            this.long_des = long_des;
        }

        public String getSchool_idS() {
            return school_idS;
        }

        public void setSchool_idS(String school_idS) {
            this.school_idS = school_idS;
        }

        public String getUniversity_nameS() {
            return university_nameS;
        }

        public void setUniversity_nameS(String university_nameS) {
            this.university_nameS = university_nameS;
        }

        public String getShort_desS() {
            return short_desS;
        }

        public void setShort_desS(String short_desS) {
            this.short_desS = short_desS;
        }

        public String getAddressS() {
            return addressS;
        }

        public void setAddressS(String addressS) {
            this.addressS = addressS;
        }

        public String getPrincipalS() {
            return principalS;
        }

        public void setPrincipalS(String principalS) {
            this.principalS = principalS;
        }

        public String getPhoneS() {
            return phoneS;
        }

        public void setPhoneS(String phoneS) {
            this.phoneS = phoneS;
        }

        public String getRatingS() {
            return ratingS;
        }

        public void setRatingS(String ratingS) {
            this.ratingS = ratingS;
        }

        public String getBoardS() {
            return boardS;
        }

        public void setBoardS(String boardS) {
            this.boardS = boardS;
        }

        public String getStageS() {
            return stageS;
        }

        public void setStageS(String stageS) {
            this.stageS = stageS;
        }

        public String getSchool_imageS() {
            return school_imageS;
        }

        public void setSchool_imageS(String school_imageS) {
            this.school_imageS = school_imageS;
        }

        public String getAge_groupS() {
            return age_groupS;
        }

        public void setAge_groupS(String age_groupS) {
            this.age_groupS = age_groupS;
        }
    }


}
