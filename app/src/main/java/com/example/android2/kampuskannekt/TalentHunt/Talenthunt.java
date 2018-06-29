package com.example.android2.kampuskannekt.TalentHunt;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.example.android2.kampuskannekt.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Talenthunt extends AppCompatActivity{
    ImageView save, left, ic_close;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    RecyclerView recyclerview;
    Spinner spinner1, spinner2, spinner3;
    ProgressBar progress_bar;
    ArrayList<ModelTalentHuntData> arr_data;
    ArrayList<ModelTalentHuntType> arr_type;
    ArrayList<ModelTalentHuntState> arr_state;
    ArrayList<ModelTalentAge> arr_age;
    String age_id, event_id, state_id;
    Button btn_search;
    TalentHuntAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talenthunt);
        toolbar =  findViewById(R.id.toolbar_add_cat);
        setSupportActionBar(toolbar);

        left = toolbar.findViewById(R.id.left);
        save = toolbar.findViewById(R.id.save);
        recyclerview=findViewById(R.id.recyclerview);
        drawer =  findViewById(R.id.drawer_layout);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        ic_close = findViewById(R.id.ic_close);
        progress_bar = findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.GONE);
        btn_search = findViewById(R.id.btn_search);

        arr_data = new ArrayList<>();
        arr_type = new ArrayList<>();
        arr_state = new ArrayList<>();
        arr_age = new ArrayList<>();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        navigationView =  findViewById(R.id.nav_view);
        ArrayList<HashMap<String, String>> category_list = new ArrayList<>();
        String cats[] = new String[]{"Hyderabad ZTalent Hunt","Hyderabad ZTalent Hunt" , "Hyderabad ZTalent Hunt", "Hyderabad ZTalent Hunt"};
        String cats_adds[] = new String[]{"10 Merlin Matrix","14 Merlin Matrix" , "6 Merlin Matrix", "7 Merlin Matrix"};
        for (int i = 0 ; i<cats.length; i++){

            HashMap<String, String> map = new HashMap<>();
            map.put("cat",cats[i]);
            map.put("address", cats_adds[i]);
            category_list.add(map);
        }

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

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ic_close.setOnClickListener(new View.OnClickListener() {
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

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    getFilter();
                    drawer.closeDrawer(GravityCompat.END);
                }else{
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });

        getTalentHunt();

    }

    private void getTalentHunt(){
        arr_data.clear();
        arr_type.clear();
        arr_state.clear();
        arr_age.clear();
        progress_bar.setVisibility(View.VISIBLE);
        String url = SharedPreferences.BASE_URL +"getAllTalentHunt";
        Log.d("TAG", "url: "+url);
        StringRequest stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    parseJson(jsonObject);
                    Log.i("TAG",response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TAG","No Response");
            }
        });
        Volley.newRequestQueue(Talenthunt.this).add(stringRequest);
    }


    private void parseJson(JSONObject response){


        progress_bar.setVisibility(View.GONE);
        String success = response.optString("status");
        String msg = response.optString("msg");
        String image_url = response.optString("image_url");

        if(success.equalsIgnoreCase("Success")){
            JSONArray arr_json_data = response.optJSONArray("data");
            JSONArray arr_json_type = response.optJSONArray("event_type");
            JSONArray arr_json_state = response.optJSONArray("state");
            JSONArray arr_json_age = response.optJSONArray("age_group");


            for(int i=0; i<arr_json_data.length(); i++){
                JSONObject object = arr_json_data.optJSONObject(i);
                if(object!=null) {
                    ModelTalentHuntData data = new ModelTalentHuntData();
                    data.setTalent_id(object.optString("talent_id"));
                    data.setState_id(object.optString("state_id"));
                    data.setAge_id(object.optString("age_id"));
                    data.setEvent_id(object.optString("event_id"));
                    data.setTalent_title(object.optString("talent_title"));
                    data.setTalent_des(object.optString("talent_des"));
                    data.setTalent_subject(object.optString("talent_subject"));
                    data.setTalent_cbody(object.optString("talent_cbody"));
                    data.setTalent_website(object.optString("talent_website"));
                    data.setTalent_category(object.optString("talent_category"));
                    data.setTalent_img(image_url+object.optString("talent_img"));
                    data.setTalent_phone(object.optString("talent_phone"));
                    data.setTalent_email(object.optString("talent_email"));
                    data.setTalent_datetime(object.optString("talent_datetime"));
                    data.setState_name(object.optString("state_name"));
                    data.setAge(object.optString("age"));

                    arr_data.add(data);
                }
            }

            if(arr_json_type!=null){
                for(int i=0; i<arr_json_type.length(); i++){
                    JSONObject object = arr_json_type.optJSONObject(i);
                    if(object!=null) {
                        ModelTalentHuntType data = new ModelTalentHuntType();
                        data.setEvent_id(object.optString("event_id"));
                        data.setEvent_title(object.optString("event_title"));
                        data.setStatus(object.optString("status"));
                        arr_type.add(data);
                    }
                }
            }

            if(arr_json_state!=null) {
                for (int i = 0; i < arr_json_state.length(); i++) {
                    JSONObject object = arr_json_state.optJSONObject(i);
                    if (object != null) {
                        ModelTalentHuntState data = new ModelTalentHuntState();
                        data.setState_id(object.optString("state_id"));
                        data.setCountry_id(object.optString("country_id"));
                        data.setState_name(object.optString("state_name"));
                        data.setState_status(object.optString("state_status"));
                        data.setIp_address(object.optString("ip_address"));
                        data.setCreated_date(object.optString("created_date"));
                        data.setModified_date(object.optString("modified_date"));
                        arr_state.add(data);
                    }
                }
            }


            if(arr_json_age!=null){
                for(int i=0; i<arr_json_age.length(); i++){
                    JSONObject object = arr_json_age.optJSONObject(i);
                    if(object!=null) {
                        ModelTalentAge data = new ModelTalentAge();
                        data.setAge_id(object.optString("age_id"));
                        data.setAge(object.optString("age"));
                        arr_age.add(data);
                    }
                }
            }


            DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL);
            recyclerview.addItemDecoration(itemDecorator);
            itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.rc_divider_white));
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            adapter = new TalentHuntAdapter(this, arr_data);
            recyclerview.setAdapter(adapter);
            populateSpinner1();
            populateSpinner2();
            populateSpinner3();


        }else {

        }
    }


    private void populateSpinner1(){
        List<String> list = new ArrayList<>();
        list.add("Select Events");
        for(int i=0;i<arr_type.size();i++){
            list.add(arr_type.get(i).getEvent_title());
        }
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                this,R.layout.row_spinner_tv,list){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        spinnerArrayAdapter1.setDropDownViewResource(R.layout.row_spinner_tv);
        spinner1.setAdapter(spinnerArrayAdapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if(position > 0){
                    event_id = arr_type.get(position-1).getEvent_id();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void populateSpinner2(){

        List<String> list = new ArrayList<>();
        list.add("Select State");
        for(int i=0;i<arr_state.size();i++){
            list.add(arr_state.get(i).getState_name());
        }
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this,R.layout.row_spinner_tv,list){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        spinnerArrayAdapter2.setDropDownViewResource(R.layout.row_spinner_tv);
        spinner2.setAdapter(spinnerArrayAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if(position > 0){
                    state_id = arr_state.get(position-1).getState_id();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void populateSpinner3(){
        List<String> list = new ArrayList<>();
        list.add("Select Age Group");
        for(int i=0;i<arr_age.size();i++){
            list.add(arr_age.get(i).getAge());
        }
        final ArrayAdapter<String> spinnerArrayAdapter3 = new ArrayAdapter<String>(
                this,R.layout.row_spinner_tv,list){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        spinnerArrayAdapter3.setDropDownViewResource(R.layout.row_spinner_tv);
        spinner3.setAdapter(spinnerArrayAdapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if(position > 0){
                    age_id = arr_age.get(position-1).getAge_id();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }


    private void getFilter(){
        adapter.notifyDataSetChanged();
        arr_data.clear();
        progress_bar.setVisibility(View.VISIBLE);
        String url =SharedPreferences.BASE_URL +"getAlltalentSearchData";
        Log.d("TAG", "url: "+url);
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("TAG","response "+response);
                    JSONObject jsonObject = new JSONObject(response);
                    parseJsonFilter(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TAG","No Response");
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("state", state_id);
                params2.put("age_group", age_id);
                params2.put("event", event_id);
                Log.d("params" ,"state_id "+state_id);
                Log.d("params" ,"age_id "+age_id);
                Log.d("params" ,"event_id "+event_id);
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                //return "application/x-www-form-urlencoded";
                return "application/json";
            }
        };
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(Talenthunt.this).add(stringRequest);
    }

    private void parseJsonFilter(JSONObject response){


        progress_bar.setVisibility(View.GONE);
        String success = response.optString("status");
        String msg = response.optString("msg");
        String image_url = response.optString("image_url");

        if(success.equalsIgnoreCase("Success")){
            JSONArray arr_json_data = response.optJSONArray("data");

            for(int i=0; i<arr_json_data.length(); i++){
                JSONObject object = arr_json_data.optJSONObject(i);
                if(object!=null) {
                    ModelTalentHuntData data = new ModelTalentHuntData();
                    data.setTalent_id(object.optString("talent_id"));
                    data.setState_id(image_url+object.optString("state_id"));
                    data.setAge_id(object.optString("age_id"));
                    data.setEvent_id(object.optString("event_id"));
                    data.setTalent_title(object.optString("talent_title"));
                    data.setTalent_des(object.optString("talent_des"));
                    data.setTalent_subject(object.optString("talent_subject"));
                    data.setTalent_cbody(object.optString("talent_cbody"));
                    data.setTalent_website(object.optString("talent_website"));
                    data.setTalent_category(object.optString("talent_category"));
                    data.setTalent_img(object.optString("talent_img"));
                    data.setTalent_phone(object.optString("talent_phone"));
                    data.setTalent_email(object.optString("talent_email"));
                    data.setTalent_datetime(object.optString("talent_datetime"));
                    data.setState_name(object.optString("state_name"));
                    data.setAge(object.optString("age"));

                    arr_data.add(data);
                }
            }
            DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL);
            recyclerview.addItemDecoration(itemDecorator);
            itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.rc_divider_white));
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            adapter = new TalentHuntAdapter(this, arr_data);
            recyclerview.setAdapter(adapter);
        }else {

        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }else{
            super.onBackPressed();
        }

    }

}

