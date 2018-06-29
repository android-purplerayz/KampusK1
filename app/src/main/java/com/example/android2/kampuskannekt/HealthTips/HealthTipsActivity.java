package com.example.android2.kampuskannekt.HealthTips;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.android2.kampuskannekt.R;
import com.example.android2.kampuskannekt.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HealthTipsActivity extends AppCompatActivity {

    private RecyclerView recyclerviewHealthtips;
    private String GetHealthURL=SharedPreferences.BASE_URL+"getAllHealthTips";
    private  String PostHealthURL=SharedPreferences.BASE_URL+"getHealthsearchData";
    ArrayList<HealthDATA> ArrHdata;
    ArrayList<HealthTOPICS> ArrHtopics;
    private NavigationView navigationView;
    private String selectedItemTopic;
    private ProgressBar ProgressBarHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);

        ProgressBarHT=findViewById(R.id.ProgressBarHT);
        ProgressBarHT.setVisibility(View.GONE);

      /*  navigationView = (NavigationView) findViewById(R.id.nav_viewHT);
        Button btnsearchHT=navigationView.findViewById(R.id.btnsearchHT);
        btnsearchHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostHealthJSON(PostHealthURL);
                DrawerLayout drawer = findViewById(R.id.drawer_layoutHT);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });
*/


        recyclerviewHealthtips=findViewById(R.id.recyclerviewHealthtips);
        recyclerviewHealthtips.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHT);
        setSupportActionBar(toolbar);

        Button btnbacktoServicesfromHT=toolbar.findViewById(R.id.btnbacktoServicesfromHT);
        ImageView saveHT=toolbar.findViewById(R.id.saveHT);

        btnbacktoServicesfromHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        saveHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layoutHT);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutHT);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        GetHealthJSON(GetHealthURL);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutHT);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public class MyadapterHT extends RecyclerView.Adapter<MyadapterHT.ViewHolder7>{

        @NonNull
        @Override
        public ViewHolder7 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View V=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_health_tips,parent,false);
            return new ViewHolder7(V);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder7 holder, final int position) {

            holder.ConsllouttoDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(HealthTipsActivity.this,HealthTipsDetailsActivity.class);
                    intent.putExtra("HT_Title",ArrHdata.get(position).getHealth_title());
                    intent.putExtra("HT_Image",ArrHdata.get(position).getHealth_img());
                    intent.putExtra("HT_ShortDes",ArrHdata.get(position).getHealth_short_des());
                    intent.putExtra("HT_Des",ArrHdata.get(position).getHealth_des());
                    intent.putExtra("HT_Date",ArrHdata.get(position).getHealth_date());
                    startActivity(intent);
                }
            });
            holder.tvTitle.setText(ArrHdata.get(position).getHealth_title());
            holder.tvShortDes.setText(ArrHdata.get(position).getHealth_short_des());
            Glide.with(HealthTipsActivity.this).load(ArrHdata.get(position).getHealth_img())
                    .into(holder.ImageHT);

        }

        @Override
        public int getItemCount() {
            return ArrHdata.size();
        }

        public class ViewHolder7 extends RecyclerView.ViewHolder{

            ConstraintLayout ConsllouttoDetails;
            TextView tvTitle,tvShortDes;
            ImageView ImageHT;
            public ViewHolder7(View itemView) {
                super(itemView);
                tvTitle=itemView.findViewById(R.id.tvTitle);
                ImageHT=itemView.findViewById(R.id.ImageHT);
                tvShortDes=itemView.findViewById(R.id.tvShortDes);
                ConsllouttoDetails=itemView.findViewById(R.id.ConsllouttoDetails);
            }
        }
    }
    public void GetHealthJSON(String url1){
        ProgressBarHT.setVisibility(View.VISIBLE);
        ArrHdata=new ArrayList<>();
        ArrHtopics=new ArrayList<>();
//        ProgressBarHT.setVisibility(View.VISIBLE);
        StringRequest request1=new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    Log.i("respo3",response);
                    String Status = jsonObject.getString("status");
                    String msg=jsonObject.getString("msg");
                    String ImageURL=jsonObject.getString("image_url");
                    if (Status.equalsIgnoreCase("Success")) {
                        JSONArray jsonArrayDATA = jsonObject.getJSONArray("data");
                        Log.i("respo3", String.valueOf(jsonArrayDATA.length()));
                        for (int i = 0; i < jsonArrayDATA.length(); i++) {
                            JSONObject jsonObject1 = jsonArrayDATA.getJSONObject(i);
                            String HealthID=jsonObject1.optString("health_id");
                            String TopicID=jsonObject1.optString("topic_id");
                            String HealthTitle=jsonObject1.optString("health_title");
                            String HealthIMG=jsonObject1.optString("health_img");
                            String HealthDes=jsonObject1.optString("health_des");
                            String HeathSdes=jsonObject1.optString("health_short_des");
                            String HealthDate=jsonObject1.optString("health_date");

                            HealthDATA healthDATA= new HealthDATA();
                            healthDATA.setHealth_id(HealthID);
                            healthDATA.setTopic_id(TopicID);
                            healthDATA.setHealth_title(HealthTitle);
                            healthDATA.setHealth_img(ImageURL+HealthIMG);
                            healthDATA.setHealth_des(HealthDes);
                            healthDATA.setHealth_short_des(HeathSdes);
                            healthDATA.setHealth_date(HealthDate);

                            ArrHdata.add(healthDATA);
                        }
                        JSONArray jsonArrayTopis= jsonObject.getJSONArray("topic");
                        for (int i=0;i<jsonArrayTopis.length();i++){
                            JSONObject jsonObject2=jsonArrayTopis.getJSONObject(i);
                            String TopicID=jsonObject2.optString("topic_id");
                            String Topic=jsonObject2.optString("topic");

                            HealthTOPICS healthTOPICS=new HealthTOPICS();
                            healthTOPICS.setTopic_id(TopicID);
                            healthTOPICS.setTopic(Topic);

                            ArrHtopics.add(healthTOPICS);
                        }

                        recyclerviewHealthtips.setAdapter(new MyadapterHT());
                        ProgressBarHT.setVisibility(View.GONE);
                        HealthSpinner();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                   ProgressBarHT.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               ProgressBarHT.setVisibility(View.GONE);

            }
        });
        Volley.newRequestQueue(HealthTipsActivity.this).add(request1);
    }
    public void PostHealthJSON(String url2){
        ProgressBarHT.setVisibility(View.VISIBLE);
        ArrHdata=new ArrayList<>();
        StringRequest request2=new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    Log.v("respo1",response);
                    String Status = jsonObject.getString("status");
                    String msg=jsonObject.getString("msg");
                    String ImageURL=jsonObject.getString("image_url");
                    if (Status.equalsIgnoreCase("Success")) {
                        JSONArray jsonArrayDATA = jsonObject.getJSONArray("data");
                        Log.v("respo1", String.valueOf(jsonArrayDATA.length()));
                        for (int i = 0; i < jsonArrayDATA.length(); i++) {
                            JSONObject jsonObject1 = jsonArrayDATA.getJSONObject(i);
                            String HealthID = jsonObject1.optString("health_id");
                            String TopicID = jsonObject1.optString("topic_id");
                            String HealthTitle = jsonObject1.optString("health_title");
                            String HealthIMG = jsonObject1.optString("health_img");
                            String HealthDes = jsonObject1.optString("health_des");
                            String HeathSdes = jsonObject1.optString("health_short_des");
                            String HealthDate = jsonObject1.optString("health_date");

                            HealthDATA healthDATA = new HealthDATA();
                            healthDATA.setHealth_id(HealthID);
                            healthDATA.setTopic_id(TopicID);
                            healthDATA.setHealth_title(HealthTitle);
                            healthDATA.setHealth_img(ImageURL + HealthIMG);
                            healthDATA.setHealth_des(HealthDes);
                            healthDATA.setHealth_short_des(HeathSdes);
                            healthDATA.setHealth_date(HealthDate);

                            ArrHdata.add(healthDATA);
                        }
                    }
                    recyclerviewHealthtips.setAdapter(new MyadapterHT());
                    ProgressBarHT.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    ProgressBarHT.setVisibility(View.GONE);
                    Toast.makeText(HealthTipsActivity.this,"Error exception",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ProgressBarHT.setVisibility(View.GONE);
                Toast.makeText(HealthTipsActivity.this,"Error response",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap params2 = new HashMap();
                params2.put("topic", selectedItemTopic);
                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {

                return "application/json";
            }
        };
        Volley.newRequestQueue(HealthTipsActivity.this).add(request2);
    }

    public void HealthSpinner(){

        navigationView = (NavigationView) findViewById(R.id.nav_viewHT);
        Spinner spinnerHT =navigationView.findViewById(R.id.spinnerHT);
        Button btnsearchHT=navigationView.findViewById(R.id.btnsearchHT);
        btnsearchHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostHealthJSON(PostHealthURL);
                DrawerLayout drawer = findViewById(R.id.drawer_layoutHT);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        final List<String> Topiclist =new ArrayList<>();
        Topiclist.add("Select Topic");
        for (int i=0;i<ArrHtopics.size();i++){
            Topiclist.add(ArrHtopics.get(i).getTopic());
        }
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Topiclist){
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
        spinnerHT.setAdapter(aa);
        spinnerHT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                String Sub = (String) parent.getItemAtPosition(position1);
                if (position1>0){
                    selectedItemTopic = ArrHtopics.get(position1-1).getTopic_id();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
