package ngo;

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
import android.widget.Toast;

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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import epaper.CategoryAdapter;

public class NGO extends AppCompatActivity {
    ImageView save, left, ic_close;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    RecyclerView recyclerview;
    Spinner spinner1, spinner2;
    ArrayList<ModelNgoData> arr_data;
    ArrayList<ModelNgoState> arr_state;
    ArrayList<ModelNgoCategory> arr_category;
    ProgressBar progress_bar;
    String cat_id, state_id;
    Button btn_search;
    NgoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo);
        toolbar =  findViewById(R.id.toolbar_add_cat);
        setSupportActionBar(toolbar);

        left = toolbar.findViewById(R.id.left);
        save = toolbar.findViewById(R.id.save);
        recyclerview=findViewById(R.id.recyclerview);
        drawer =  findViewById(R.id.drawer_layout);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        ic_close = findViewById(R.id.ic_close);
        arr_data = new ArrayList<>();
        arr_state = new ArrayList<>();
        arr_category = new ArrayList<>();
        progress_bar = findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.GONE);
        btn_search = findViewById(R.id.btn_search);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        navigationView =  findViewById(R.id.nav_view);

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


        getEpaper();

    }

    private void getEpaper(){
        arr_data.clear();
        arr_state.clear();
        arr_category.clear();

        progress_bar.setVisibility(View.VISIBLE);
        String url = SharedPreferences.BASE_URL +"getAllNgoList";
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
        Volley.newRequestQueue(NGO.this).add(stringRequest);

    }


    private void parseJson(JSONObject response){
        progress_bar.setVisibility(View.GONE);
        String success = response.optString("status");
        String msg = response.optString("msg");
        String image_url = response.optString("image_url");
        String file_url = response.optString("file_url");

        if(success.equalsIgnoreCase("Success")){
            JSONArray arr_jsondata = response.optJSONArray("data");
            JSONArray arr_json_state = response.optJSONArray("state");
            JSONArray arr_json_category = response.optJSONArray("ngo_category");

            for(int i=0; i<arr_jsondata.length(); i++){
                JSONObject object = arr_jsondata.optJSONObject(i);
                if(object!=null) {
                    ModelNgoData data = new ModelNgoData();
                    data.setNgo_id(object.optString("ngo_id"));
                    data.setState_id(object.optString("state_id"));
                    data.setNgo_title(object.optString("ngo_title"));
                    data.setNgo_des(object.optString("ngo_des"));
                    data.setNgo_cat_id(object.optString("ngo_cat_id"));
                    data.setNgo_address(object.optString("ngo_address"));
                    data.setNgo_address2(object.optString("ngo_address2"));
                    data.setNgo_img(image_url+object.optString("ngo_img"));
                    data.setNgo_phone(object.optString("ngo_phone"));
                    data.setNgo_email_address(object.optString("ngo_email_address"));
                    data.setNgo_rating(object.optString("ngo_rating"));
                    data.setNgo_cat_name(object.optString("ngo_cat_name"));
                    data.setState_name(object.optString("state_name"));
                    arr_data.add(data);
                }
            }

            for(int i=0; i<arr_json_state.length(); i++){
                JSONObject object = arr_json_state.optJSONObject(i);
                if(object!=null) {
                    ModelNgoState data = new ModelNgoState();
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

            for(int i=0; i<arr_json_category.length(); i++){
                JSONObject object = arr_json_category.optJSONObject(i);
                if(object!=null) {
                    ModelNgoCategory data = new ModelNgoCategory();
                    data.setNgo_cat_id(object.optString("ngo_cat_id"));
                    data.setNgo_cat_name(object.optString("ngo_cat_name"));
                    data.setNgo_cat_des(object.optString("ngo_cat_des"));
                    data.setNgo_cat_img(object.optString("ngo_cat_img"));
                    arr_category.add(data);
                }
            }

            DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL);
            recyclerview.addItemDecoration(itemDecorator);
            itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.rc_divider_white));
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            adapter = new NgoAdapter(this, arr_data);
            recyclerview.setAdapter(adapter);

            populateSpinner1();
            populateSpinner2();


        }else {

        }
    }


    private void populateSpinner1(){
        List<String> locationList = new ArrayList<>();
        locationList.add("Select Category");
        for(int i=0;i<arr_category.size();i++){
            locationList.add(arr_category.get(i).getNgo_cat_name());
        }
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                this,R.layout.row_spinner_tv,locationList){
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
                    cat_id = arr_category.get(position-1).getNgo_cat_id();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void populateSpinner2(){
        List<String> locationList = new ArrayList<>();
        locationList.add("Select State");
        for(int i=0;i<arr_state.size();i++){
            locationList.add(arr_state.get(i).getState_name());
        }
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this,R.layout.row_spinner_tv,locationList){
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

    private void getFilter(){
        arr_data.clear();
        progress_bar.setVisibility(View.VISIBLE);
        String url =SharedPreferences.BASE_URL +"getNgosearchData";
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
                params2.put("ngo_category", cat_id);
                params2.put("state", state_id);
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                //return "application/x-www-form-urlencoded";
                return "application/json";
            }
        };
        //queue.add(stringRequest);
        //stringRequest.setShouldCache(false);
        Volley.newRequestQueue(NGO.this).add(stringRequest);
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
                    ModelNgoData data = new ModelNgoData();
                    data.setNgo_id(object.optString("ngo_id"));
                    data.setState_id(object.optString("state_id"));
                    data.setNgo_title(object.optString("ngo_title"));
                    data.setNgo_des(object.optString("ngo_des"));
                    data.setNgo_cat_id(object.optString("ngo_cat_id"));
                    data.setNgo_address(object.optString("ngo_address"));
                    data.setNgo_address2(object.optString("ngo_address2"));
                    data.setNgo_img(image_url+object.optString("ngo_img"));
                    data.setNgo_phone(object.optString("ngo_phone"));
                    data.setNgo_email_address(object.optString("ngo_email_address"));
                    data.setNgo_rating(object.optString("ngo_rating"));
                    data.setNgo_cat_name(object.optString("ngo_cat_name"));
                    data.setState_name(object.optString("state_name"));
                    arr_data.add(data);
                }
            }

            DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL);
            recyclerview.addItemDecoration(itemDecorator);
            itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.rc_divider_white));
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            adapter = new NgoAdapter(this, arr_data);
            recyclerview.setAdapter(adapter);

        }else {
            arr_data.clear();
            adapter.notifyDataSetChanged();
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