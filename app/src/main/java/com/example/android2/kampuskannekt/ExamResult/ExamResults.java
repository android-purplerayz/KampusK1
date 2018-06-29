package com.example.android2.kampuskannekt.ExamResult;

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
import com.example.android2.kampuskannekt.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExamResults extends AppCompatActivity {


    private RecyclerView recyclerViewExamResults;

    String GETExarRes_URL="http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/getExamResult";
    String POSTExarRes_URL="http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/getAllExamresultSearchData";

    private ProgressBar progressBar;
    private String selectedItemSubject,selectedItemClass,selectedItemAgeGroup;
    ArrayList<SetGetExamData> ExamDATA;
    ArrayList<ExamAge> ExamAGE;
    ArrayList<ExamSubject> ExamSUBJECT;
    ArrayList<ExamClass> ExamCLASS;


    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_results_adaptrer);

        progressBar=findViewById(R.id.ProgressBarExam);

        progressBar.setVisibility(View.GONE);

        recyclerViewExamResults=findViewById(R.id.recyclerViewExamResults);
        recyclerViewExamResults.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        GetExamJSON(GETExarRes_URL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_ExamRes);
        setSupportActionBar(toolbar);

        Button btnbacktoServicesfromXamRes=(Button)toolbar.findViewById(R.id.btnbacktoServicesfromXamRes);
        ImageView save2=(ImageView)toolbar.findViewById(R.id.saveXam);

        btnbacktoServicesfromXamRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutExamRes);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);



      //  Button btnsearchXam=(Button)navigationView.findViewById(R.id.btnsearchXam);

        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layoutExamRes);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutExamRes);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public class MyAdpterExamRes extends RecyclerView.Adapter<MyAdpterExamRes.ViewHolder1>{

        @NonNull
        @Override
        public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View V=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xam_results,parent,false);
            return new ViewHolder1(V);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder1 holder, final int position) {

            holder.tvExamTitle.setText(ExamDATA.get(position).getTitle());
            holder.tvExamDes.setText(ExamDATA.get(position).getDescription());
            holder.ConsGotoXamDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i =new Intent(ExamResults.this,ExamResultsDetails.class);
                    i.putExtra("EXAM_IMAGE",ExamDATA.get(position).getExam_image());
                    i.putExtra("EXAM_DES",ExamDATA.get(position).getDescription());
                    i.putExtra("EXAM_LINK",ExamDATA.get(position).getLink());
                    i.putExtra("EXAM_TITLE",ExamDATA.get(position).getTitle());
                    i.putExtra("EXAM_GOVERN",ExamDATA.get(position).getGoverning_body());
                    i.putExtra("EXAM_GUIDLINE",ExamDATA.get(position).getGuideline());
                   // i.putExtra("EXAM_SUBJECT",ExamSUBJECT.get(position).getSubject());
                    startActivity(i);
                }
            });

            if(position %2 == 1)
            {
               // holder.itemView.setBackgroundColor(Color.parseColor("#4FBAA7"));
                holder.ConsllouExamBoard.setBackgroundColor(Color.parseColor("#4FBAA7"));
            }
            else
            {
                //holder.itemView.setBackgroundColor(Color.parseColor("#135864"));
                holder.ConsllouExamBoard.setBackgroundColor(Color.parseColor("#135864"));
            }

        }

        @Override
        public int getItemCount() {
            return ExamDATA.size();
        }

        public class ViewHolder1 extends RecyclerView.ViewHolder{

            ConstraintLayout ConsllouExamBoard,ConsGotoXamDetails;
            TextView tvExamTitle,tvExamDes;
            public ViewHolder1(View itemView) {
                super(itemView);
                tvExamTitle=itemView.findViewById(R.id.tvExamTitle);
                tvExamDes=itemView.findViewById(R.id.tvExamDes);
                ConsllouExamBoard=itemView.findViewById(R.id.ConsllouExamBoard);
                ConsGotoXamDetails=itemView.findViewById(R.id.ConsGotoXamDetails);
            }
        }
    }

    public void GetExamJSON(String url1){

        progressBar.setVisibility(View.VISIBLE);
        ExamDATA = new ArrayList<>();
        ExamSUBJECT=new ArrayList<>();
        ExamCLASS=new ArrayList<>();
        ExamAGE=new ArrayList<>();
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("respo",response);
                    String Status = jsonObject.getString("status");
                    String msg=jsonObject.getString("msg");
                    String ImageURL=jsonObject.getString("image_url");
                    if (Status.equalsIgnoreCase("Success")){
                        JSONArray jsonArrayDATA = jsonObject.getJSONArray("data");
                        Log.i("Size", String.valueOf(jsonArrayDATA.length()));
                        for (int i=0;i<jsonArrayDATA.length();i++){
                            JSONObject jsonObject1=jsonArrayDATA.getJSONObject(i);
                            String ExamID=jsonObject1.optString("exam_id");
                            String TITLE=jsonObject1.optString("title");
                            String DESCRIP=jsonObject1.optString("description");
                            String EXAM_IMAGE=jsonObject1.optString("exam_image");
                            String LINK=jsonObject1.optString("link");
                            String GUIDEL=jsonObject1.optString("guideline");
                            String SUB_ID=jsonObject1.optString("subject_id");
                            String CLASS_ID=jsonObject1.optString("class_id");
                            String AGE_ID=jsonObject1.optString("age_id");
                            String GOVERN_B=jsonObject1.optString("governing_body");

                            SetGetExamData examData =new SetGetExamData();
                            examData.setExam_id(ExamID);
                            examData.setTitle(TITLE);
                            examData.setDescription(DESCRIP);
                            examData.setExam_image(ImageURL+EXAM_IMAGE);
                            examData.setLink(LINK);
                            examData.setGuideline(GUIDEL);
                            examData.setSubject_id(SUB_ID);
                            examData.setClass_id(CLASS_ID);
                            examData.setAge_id(AGE_ID);
                            examData.setGoverning_body(GOVERN_B);
                            ExamDATA.add(examData);

                            Log.i("Size", String.valueOf(ExamDATA.size()));
                        }
                        JSONArray jsonArraySub = jsonObject.getJSONArray("subject");
                        for (int i=0;i<jsonArraySub.length();i++){
                            JSONObject jsonObject2=jsonArraySub.getJSONObject(i);
                            String Sub_ID=jsonObject2.optString("subject_id");
                            String Sub=jsonObject2.optString("subject");

                            ExamSubject examSubject=new ExamSubject();
                            examSubject.setSubject_id(Sub_ID);
                            examSubject.setSubject(Sub);

                            ExamSUBJECT.add(examSubject);
                        }
                        JSONArray jsonArrayClass = jsonObject.getJSONArray("class");
                        for (int i=0;i<jsonArrayClass.length();i++){
                            JSONObject jsonObject2=jsonArrayClass.getJSONObject(i);
                            String Class_ID=jsonObject2.optString("class_id");
                            String Class=jsonObject2.optString("class");

                            ExamClass examClass=new ExamClass();
                            examClass.setClass_id(Class_ID);
                            examClass.setClasses(Class);
                            ExamCLASS.add(examClass);
                        }
                        JSONArray jsonArrayAGE_G= jsonObject.getJSONArray("age_group");
                        for(int i=0;i<jsonArrayAGE_G.length();i++){
                            JSONObject jsonObject3=jsonArrayAGE_G.getJSONObject(i);
                            String AGE_ID=jsonObject3.optString("age_id");
                            String AGE=jsonObject3.optString("age");

                            ExamAge examAge = new ExamAge();
                            examAge.setAge_id(AGE_ID);
                            examAge.setAge(AGE);

                            ExamAGE.add(examAge);
                        }


                        recyclerViewExamResults.setAdapter(new MyAdpterExamRes());
                        progressBar.setVisibility(View.GONE);
                        SpinnerMethod();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

            }
        });
        Volley.newRequestQueue(ExamResults.this).add(stringRequest1);

    }
   public void PostExamJSON(String url2){

        progressBar.setVisibility(View.VISIBLE);
        ExamDATA = new ArrayList<>();

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("respo1",response);
                    String Status = jsonObject.getString("status");
                    String msg=jsonObject.getString("msg");
                    String ImageURL=jsonObject.getString("image_url");
                    if (Status.equalsIgnoreCase("Success")) {
                        JSONArray jsonArrayDATA = jsonObject.getJSONArray("data");
                        Log.i("Size", String.valueOf(jsonArrayDATA.length()));
                        for (int i = 0; i < jsonArrayDATA.length(); i++) {
                            JSONObject jsonObject1 = jsonArrayDATA.optJSONObject(i);
                            String ExamID = jsonObject1.getString("exam_id");
                            String TITLE = jsonObject1.getString("title");
                            String DESCRIP = jsonObject1.getString("description");
                            String EXAM_IMAGE = jsonObject1.getString("exam_image");
                            String LINK = jsonObject1.getString("link");
                            String GUIDEL = jsonObject1.getString("guideline");
                            String SUB_ID = jsonObject1.getString("subject_id");
                            String CLASS_ID = jsonObject1.getString("class_id");
                            String AGE_ID = jsonObject1.getString("age_id");
                            String GOVERN_B = jsonObject1.getString("governing_body");

                            SetGetExamData examData = new SetGetExamData();
                            examData.setExam_id(ExamID);
                            examData.setTitle(TITLE);
                            examData.setDescription(DESCRIP);
                            examData.setExam_image(ImageURL + EXAM_IMAGE);
                            examData.setLink(LINK);
                            examData.setGuideline(GUIDEL);
                            examData.setSubject_id(SUB_ID);
                            examData.setClass_id(CLASS_ID);
                            examData.setAge_id(AGE_ID);
                            examData.setGoverning_body(GOVERN_B);
                            ExamDATA.add(examData);
                        }

                        recyclerViewExamResults.setAdapter(new MyAdpterExamRes());
                        progressBar.setVisibility(View.GONE);
                    } else {
                        ExamDATA.clear();
                        MyAdpterExamRes myAdpterExamRes =new MyAdpterExamRes();
                        myAdpterExamRes.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap params2 = new HashMap();
                params2.put("subject", selectedItemSubject);
                params2.put("class", selectedItemClass);
                params2.put("age_group", selectedItemAgeGroup);
                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {

                return "application/x-www-form-urlencoded";
            }

        };
        Volley.newRequestQueue(ExamResults.this).add(stringRequest2);

    }



    public void SpinnerMethod(){

        navigationView = (NavigationView) findViewById(R.id.nav_viewXamRes);
        Button btnsearchXam=navigationView.findViewById(R.id.btnsearchXam);

        btnsearchXam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostExamJSON(POSTExarRes_URL);

                DrawerLayout drawer = findViewById(R.id.drawer_layoutExamRes);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        Spinner spinnerSubj=(Spinner)navigationView.findViewById(R.id.spinnerSubj);
        Spinner spinnerClass=(Spinner)navigationView.findViewById(R.id.spinnerClass);
        Spinner spinnerAgeG=(Spinner)navigationView.findViewById(R.id.spinnerAgeG);

        final List<String> Subjectlist=new ArrayList<>();
        Subjectlist.add("Select Subject");
        for (int i=0;i<ExamSUBJECT.size();i++){
            Subjectlist.add(ExamSUBJECT.get(i).getSubject());
        }
        //final List<String> Subjectlist =new ArrayList<>(Arrays.asList(SubjectNew));
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Subjectlist){
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
        spinnerSubj.setAdapter(aa);
        spinnerSubj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {


                String Sub = (String) parent.getItemAtPosition(position1);
                if (position1>0){
                    selectedItemSubject = ExamSUBJECT.get(position1-1).getSubject_id();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        final List<String> Classlist=new ArrayList<>();
        Classlist.add("Select Class");
        for (int i=0;i<ExamCLASS.size();i++){
            Classlist.add(ExamCLASS.get(i).getClasses());
        }
        //final List<String> Subjectlist =new ArrayList<>(Arrays.asList(SubjectNew));
        ArrayAdapter<String> aa1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Classlist){
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
        spinnerClass.setAdapter(aa1);
        spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                String class1 = (String) parent.getItemAtPosition(position2);
                if (position2>0){
                    selectedItemClass = ExamCLASS.get(position2-1).getClass_id();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        final List<String> AgeGroup=new ArrayList<>();
        AgeGroup.add("Select Age");
        for (int i=0;i<ExamAGE.size();i++){
            AgeGroup.add(ExamAGE.get(i).getAge());
        }
        //final List<String> Subjectlist =new ArrayList<>(Arrays.asList(SubjectNew));
        ArrayAdapter<String> aa2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,AgeGroup){
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
        spinnerAgeG.setAdapter(aa2);
        spinnerAgeG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position3, long id) {

                String Age = (String) parent.getItemAtPosition(position3);
                if (position3>0){
                    selectedItemAgeGroup = ExamAGE.get(position3-1).getAge_id();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }




}
