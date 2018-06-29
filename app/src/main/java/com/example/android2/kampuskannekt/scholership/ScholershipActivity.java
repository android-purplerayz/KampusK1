package com.example.android2.kampuskannekt.scholership;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.android2.kampuskannekt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScholershipActivity extends AppCompatActivity {


    private RecyclerView recyclerViewScholership;
    private ArrayList<SchoDATA> Arr_Scho_data;
    private ArrayList<SchoQualify> Arr_Scho_Qualify;
    private ArrayList<SchoAGE> Arr_Scho_AGE;
    private ArrayList<SchoCAREER> Arr_Scho_Ctype;
    private ProgressBar ProgressBarScholership;
    private NavigationView navigationView;
    private String selectedItemCtype,selectedItemQualify,selectedItemAge;
    private String SchoGET_URL="http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/getAllscholarshipData";
    private String SchoPOST_URL="http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/getScholarshipsearchData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholership);

        ProgressBarScholership=findViewById(R.id.ProgressBarScholership);
        ProgressBarScholership.setVisibility(View.GONE);

        recyclerViewScholership=findViewById(R.id.recyclerViewScholership);
        recyclerViewScholership.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        GetJSONScholer(SchoGET_URL);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarScholership);
        setSupportActionBar(toolbar);

        Button btnback =toolbar.findViewById(R.id.btnbacktoServicesfromScholership);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ImageView saveScholership=toolbar.findViewById(R.id.saveScholership);

        saveScholership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer_layoutScholer);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutScholer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutScholer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.ViewHolder2>{
        @NonNull
        @Override
        public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View V=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scholership,parent,false);
            return new ViewHolder2(V);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder2 holder, final int position) {


            holder.tvScholerTitle.setText(Arr_Scho_data.get(position).getScholarship_title());
            holder.tvCareerType.setText(Arr_Scho_data.get(position).getQualification_name());
            Glide.with(ScholershipActivity.this).load(Arr_Scho_data.get(position).getScholarshipimg())
                    .into(holder.ImageScholer);
            holder.rowConsScholerShip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ScholershipActivity.this,ScholerShipDetailsActivity.class);
                    intent.putExtra("Scho_TITLE",Arr_Scho_data.get(position).getScholarship_title());
                    intent.putExtra("Scho_DES",Arr_Scho_data.get(position).getScholarship_des());
                    intent.putExtra("Scho_ELIGI",Arr_Scho_data.get(position).getEligibility());
                    intent.putExtra("Scho_GUIDE",Arr_Scho_data.get(position).getGuidline());
                    intent.putExtra("Scho_GBODY",Arr_Scho_data.get(position).getGbody());
                    intent.putExtra("Scho_EMAIL",Arr_Scho_data.get(position).getScholarship_email());
                    //intent.putExtra("Scho_WEBSITE",Arr_Scho_data.get(position).getScholarship_website());
                    intent.putExtra("Scho_IMAGE",Arr_Scho_data.get(position).getScholarshipimg());
                    intent.putExtra("Scho_QUANAME",Arr_Scho_data.get(position).getQualification_name());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return Arr_Scho_data.size();
        }

        public class ViewHolder2 extends RecyclerView.ViewHolder{

            TextView tvScholerTitle,tvCareerType;
            ImageView ImageScholer;
            ConstraintLayout rowConsScholerShip;
            public ViewHolder2(View itemView) {
                super(itemView);
                tvScholerTitle=itemView.findViewById(R.id.tvScholerTitle);
                tvCareerType=itemView.findViewById(R.id.tvCareerType);
                ImageScholer=itemView.findViewById(R.id.ImageScholer);
                rowConsScholerShip=itemView.findViewById(R.id.rowConsScholerShip);
            }
        }
    }


    public void GetJSONScholer(String url1) {
        ProgressBarScholership.setVisibility(View.VISIBLE);
        Arr_Scho_data=new ArrayList<>();
        Arr_Scho_Qualify=new ArrayList<>();
        Arr_Scho_AGE=new ArrayList<>();
        Arr_Scho_Ctype=new ArrayList<>();
        StringRequest request1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    Log.v("respo1",response);
                    String Status = jsonObject.getString("status");
                    String msg=jsonObject.getString("msg");
                    String ImageURL=jsonObject.getString("image_url");
                    if (Status.equalsIgnoreCase("Success")) {
                        JSONArray jsonArrayDATA = jsonObject.getJSONArray("data");
                        Log.v("respo1", String.valueOf(jsonArrayDATA.length()));
                        for (int i = 0; i < jsonArrayDATA.length(); i++) {
                            JSONObject jsonObject1 = jsonArrayDATA.getJSONObject(i);
                            String ScholershipID=jsonObject1.optString("scholarship_id");
                            String ScholershipTITLE=jsonObject1.optString("scholarship_title");
                            String ScholershipDES=jsonObject1.optString("scholarship_des");
                            String EligiBility=jsonObject1.optString("eligibility");
                            String Guidline=jsonObject1.optString("guidline");
                            String Gbody=jsonObject1.optString("gbody");
                            String ScholershipEmail=jsonObject1.optString("scholarship_email");
                            String ScholershipWebsite=jsonObject1.optString("scholarship_website");
                            String ScholershipImage=jsonObject1.optString("scholarshipimg");
                            String AgeID=jsonObject1.optString("age_id");
                            String QualifyID=jsonObject1.optString("qualification_id");
                            String CareerTypeID=jsonObject1.optString("career_type_id");
                            String Age=jsonObject1.optString("age");
                            String CareerType=jsonObject1.optString("career_type");
                            String QualifyName=jsonObject1.optString("qualification_name");

                            SchoDATA schoDATA =new SchoDATA();
                            schoDATA.setScholarship_id(ScholershipID);
                            schoDATA.setScholarship_title(ScholershipTITLE);
                            schoDATA.setScholarship_des(ScholershipDES);
                            schoDATA.setEligibility(EligiBility);
                            schoDATA.setGuidline(Guidline);
                            schoDATA.setGbody(Gbody);
                            schoDATA.setScholarship_email(ScholershipEmail);
                            schoDATA.setScholarship_website(ScholershipWebsite);
                            schoDATA.setScholarshipimg(ImageURL+ScholershipImage);
                            schoDATA.setAge_id(AgeID);
                            schoDATA.setQualification_id(QualifyID);
                            schoDATA.setCareer_type_id(CareerTypeID);
                            schoDATA.setAge(Age);
                            schoDATA.setCareer_type(CareerType);
                            schoDATA.setQualification_name(QualifyName);

                            Arr_Scho_data.add(schoDATA);
                        }
                        JSONArray jsonArrayQualify = jsonObject.getJSONArray("qualification");
                        for (int j=0;j<jsonArrayQualify.length();j++){
                            JSONObject object1=jsonArrayQualify.getJSONObject(j);
                            String QualifyID=object1.optString("qualification_id");
                            String QualifyName=object1.optString("qualification_name");
                            String QualifyStatus=object1.optString("status");

                            SchoQualify schoQualify = new SchoQualify();
                            schoQualify.setQualification_id(QualifyID);
                            schoQualify.setQualification_name(QualifyName);
                            schoQualify.setStatus(QualifyStatus);

                            Arr_Scho_Qualify.add(schoQualify);
                        }
                        JSONArray jsonArrayAge = jsonObject.getJSONArray("age");
                        for (int j=0;j<jsonArrayAge.length();j++) {
                            JSONObject object2 = jsonArrayAge.getJSONObject(j);
                            String AgeID=object2.optString("age_id");
                            String Age=object2.optString("age");

                            SchoAGE schoAGE =new SchoAGE();
                            schoAGE.setAge_id(AgeID);
                            schoAGE.setAge(Age);

                            Arr_Scho_AGE.add(schoAGE);
                        }
                        JSONArray jsonArrayCareerType = jsonObject.getJSONArray("career_type");
                        for (int j=0;j<jsonArrayCareerType.length();j++) {
                            JSONObject object3 = jsonArrayCareerType.getJSONObject(j);
                            String CareerTid=object3.optString("career_type_id");
                            String CareerT=object3.optString("career_type");

                            SchoCAREER schoCAREER =new SchoCAREER();
                            schoCAREER.setCareer_type_id(CareerTid);
                            schoCAREER.setCareer_type(CareerT);

                            Arr_Scho_Ctype.add(schoCAREER);
                        }

                    }
                    recyclerViewScholership.setAdapter(new MyCustomAdapter());
                    ProgressBarScholership.setVisibility(View.GONE);
                    ScholerSpinner();

                } catch (JSONException e) {
                    e.printStackTrace();
                    ProgressBarScholership.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ProgressBarScholership.setVisibility(View.GONE);

            }
        });
        Volley.newRequestQueue(ScholershipActivity.this).add(request1);
    }

    public void PostJSONScho(String url2){
        ProgressBarScholership.setVisibility(View.VISIBLE);
        Arr_Scho_data=new ArrayList<>();
        StringRequest request2=new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    Log.v("respo1",response);
                    String Status = jsonObject.getString("status");
                    String msg=jsonObject.getString("msg");
                    String ImageURL=jsonObject.getString("image_url");
                    if (Status.equalsIgnoreCase("Success")) {
                        JSONArray jsonArrayDATA = jsonObject.getJSONArray("data");
                        Log.v("respo1", String.valueOf(jsonArrayDATA.length()));
                        for (int i = 0; i < jsonArrayDATA.length(); i++) {
                            JSONObject jsonObject1 = jsonArrayDATA.getJSONObject(i);
                            String ScholershipID = jsonObject1.optString("scholarship_id");
                            String ScholershipTITLE = jsonObject1.optString("scholarship_title");
                            String ScholershipDES = jsonObject1.optString("scholarship_des");
                            String EligiBility = jsonObject1.optString("eligibility");
                            String Guidline = jsonObject1.optString("guidline");
                            String Gbody = jsonObject1.optString("gbody");
                            String ScholershipEmail = jsonObject1.optString("scholarship_email");
                            String ScholershipWebsite = jsonObject1.optString("scholarship_website");
                            String ScholershipImage = jsonObject1.optString("scholarshipimg");
                            String AgeID = jsonObject1.optString("age_id");
                            String QualifyID = jsonObject1.optString("qualification_id");
                            String CareerTypeID = jsonObject1.optString("career_type_id");
                            String Age = jsonObject1.optString("age");
                            String CareerType = jsonObject1.optString("career_type");
                            String QualifyName = jsonObject1.optString("qualification_name");

                            SchoDATA schoDATA = new SchoDATA();
                            schoDATA.setScholarship_id(ScholershipID);
                            schoDATA.setScholarship_title(ScholershipTITLE);
                            schoDATA.setScholarship_des(ScholershipDES);
                            schoDATA.setEligibility(EligiBility);
                            schoDATA.setGuidline(Guidline);
                            schoDATA.setGbody(Gbody);
                            schoDATA.setScholarship_email(ScholershipEmail);
                            schoDATA.setScholarship_website(ScholershipWebsite);
                            schoDATA.setScholarshipimg(ImageURL + ScholershipImage);
                            schoDATA.setAge_id(AgeID);
                            schoDATA.setQualification_id(QualifyID);
                            schoDATA.setCareer_type_id(CareerTypeID);
                            schoDATA.setAge(Age);
                            schoDATA.setCareer_type(CareerType);
                            schoDATA.setQualification_name(QualifyName);

                            Arr_Scho_data.add(schoDATA);
                        }
                    }
                    recyclerViewScholership.setAdapter(new MyCustomAdapter());
                    ProgressBarScholership.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ProgressBarScholership.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ProgressBarScholership.setVisibility(View.GONE);

            }
        }){

            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap params2 = new HashMap();
                params2.put("career_type", selectedItemCtype);
                params2.put("qualification",selectedItemQualify );
                params2.put("age", selectedItemAge);
                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {

                return "application/json";
            }
        };
        Volley.newRequestQueue(ScholershipActivity.this).add(request2);

    }



    public void ScholerSpinner(){



        navigationView = (NavigationView) findViewById(R.id.nav_viewScholer);
        Spinner spinnerType=navigationView.findViewById(R.id.spinnerType);
        Spinner spinnerQualify=navigationView.findViewById(R.id.spinnerQualify);
        Spinner spinnerAgeGscholership=navigationView.findViewById(R.id.spinnerAgeGscholership);
        Button btnsearchscholership=navigationView.findViewById(R.id.btnsearchscholership);

        btnsearchscholership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostJSONScho(SchoPOST_URL);
                DrawerLayout drawer = findViewById(R.id.drawer_layoutScholer);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }

            }
        });



        final List<String> CareerType =new ArrayList<>();
        CareerType.add("Select Career Type");
        for (int i=0;i<Arr_Scho_Ctype.size();i++){
            CareerType.add(Arr_Scho_Ctype.get(i).getCareer_type());
        }
        ArrayAdapter<String> aa1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,CareerType){
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
        aa1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(aa1);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Sub = (String) parent.getItemAtPosition(position);
                if (position>0){
                    selectedItemCtype = Arr_Scho_Ctype.get(position-1).getCareer_type_id();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final List<String> Qualification =new ArrayList<>();
        Qualification.add("Select Qualification");
        for (int i=0;i<Arr_Scho_Qualify.size();i++){
            Qualification.add(Arr_Scho_Qualify.get(i).getQualification_name());
        }
        ArrayAdapter<String> aa2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Qualification){
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
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQualify.setAdapter(aa2);
        spinnerQualify.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Sub = (String) parent.getItemAtPosition(position);
                if (position>0){
                    selectedItemQualify = Arr_Scho_Qualify.get(position-1).getQualification_id();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final List<String> Age =new ArrayList<>();
        Age.add("Select Age-Group");
        for (int i=0;i<Arr_Scho_AGE.size();i++){
            Age.add(Arr_Scho_AGE.get(i).getAge());
        }
        ArrayAdapter<String> aa3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Age){
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
        aa3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgeGscholership.setAdapter(aa3);
        spinnerAgeGscholership.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Sub = (String) parent.getItemAtPosition(position);
                if (position>0){
                    selectedItemAge = Arr_Scho_AGE.get(position-1).getAge_id();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






    }
}
