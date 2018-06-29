package ebook;

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
import android.support.v7.widget.GridLayoutManager;
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
import com.android.volley.RequestQueue;
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


public class Ebook extends AppCompatActivity {
    ImageView save, left, ic_close;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    RecyclerView recyclerview, recyclerview2;
    Spinner spinner1, spinner2;
    Button btn_search;
    ArrayList<ModelData> arr_latest , arr_students;
    ArrayList<ModelCategory> arr_category;
    ArrayList<ModelSubject> arr_subject;
    String selectedItemCat, selectedItemState, cat_id , sub_id;
    ProgressBar progress_bar;
    public static final String POST_REQUEST_TAG = "JSON_OBJECT_POST_REQUEST_TAG";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);



        toolbar =  findViewById(R.id.toolbar_add_cat);
        setSupportActionBar(toolbar);

        left = toolbar.findViewById(R.id.left);
        save = toolbar.findViewById(R.id.save);
        recyclerview=findViewById(R.id.recyclerview);
        recyclerview2=findViewById(R.id.recyclerview2);
        drawer =  findViewById(R.id.drawer_layout);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        ic_close = findViewById(R.id.ic_close);
        btn_search = findViewById(R.id.btn_search);
        progress_bar = findViewById(R.id.progress_barEbook);
        progress_bar.setVisibility(View.GONE);


        arr_latest = new ArrayList<>();
        arr_students = new ArrayList<>();
        arr_category = new ArrayList<>();
        arr_subject = new ArrayList<>();

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

        getEbook();
    }




    private void getEbook(){
        progress_bar.setVisibility(View.VISIBLE);
        String url =SharedPreferences.BASE_URL +"getAllEbook";
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
        Volley.newRequestQueue(Ebook.this).add(stringRequest);
    }


    private void parseJson(JSONObject response){
        arr_latest.clear();
        arr_students.clear();
        arr_category.clear();
        arr_subject.clear();
        progress_bar.setVisibility(View.GONE);
        String success = response.optString("status");
        String msg = response.optString("msg");
        String image_url = response.optString("image_url");

        if(success.equalsIgnoreCase("Success")){
            JSONArray arr_json_data = response.optJSONArray("data");
            JSONArray arr_json_cat = response.optJSONArray("category");
            JSONArray arr_json_sub = response.optJSONArray("subject");
            // ModelEbook ebook = new ModelEbook();
            //ebook.setStatus(success);
            //ebook.setMsg(msg);
            //ebook.setImage_url(image_url);

            for(int i=0; i<5; i++){
                JSONObject object = arr_json_data.optJSONObject(i);
                if(object!=null) {
                    ModelData data = new ModelData();
                    data.setEbook_id(object.optString("ebook_id"));
                    data.setEbook_image(image_url+object.optString("ebook_image"));
                    data.setCategory_id(object.optString("category_id"));
                    data.setSubject_id(object.optString("subject_id"));
                    data.setTitle(object.optString("title"));
                    data.setSubtitle(object.optString("subtitle"));
                    data.setFile(object.optString("file"));
                    data.setLink(object.optString("link"));
                    data.setCategory(object.optString("category"));
                    data.setSubject(object.optString("subject"));
                    arr_latest.add(data);
                }
            }
            //ebook.setArr_ebooks(arr_latest);


            for(int i=5; i<arr_json_data.length(); i++){
                JSONObject object = arr_json_data.optJSONObject(i);
                if(object!=null) {
                    ModelData data = new ModelData();
                    data.setEbook_id(object.optString("ebook_id"));
                    data.setEbook_image(image_url+object.optString("ebook_image"));
                    data.setCategory_id(object.optString("category_id"));
                    data.setSubject_id(object.optString("subject_id"));
                    data.setTitle(object.optString("title"));
                    data.setSubtitle(object.optString("subtitle"));
                    data.setFile(object.optString("file"));
                    data.setLink(object.optString("link"));
                    data.setCategory(object.optString("category"));
                    data.setSubject(object.optString("subject"));
                    arr_students.add(data);
                }
            }
            //ebook.setArr_ebooks(arr_students);

            if(arr_json_cat!=null){
                for(int i=0; i<arr_json_cat.length(); i++){
                    JSONObject object = arr_json_cat.optJSONObject(i);
                    if(object!=null) {
                        ModelCategory data = new ModelCategory();
                        data.setCategory(object.optString("category_id"));
                        data.setCategory(object.optString("category"));
                        arr_category.add(data);
                    }
                }
            }
            //ebook.setArr_category(arr_category);

            if(arr_json_sub!=null) {
                for (int i = 0; i < arr_json_sub.length(); i++) {
                    JSONObject object = arr_json_sub.optJSONObject(i);
                    if (object != null) {
                        ModelSubject data = new ModelSubject();
                        data.setSubject_id(object.optString("subject_id"));
                        data.setSubject(object.optString("subject"));
                        arr_subject.add(data);
                    }
                }
            }
            //ebook.setArr_subject(arr_subject);


            DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL);
            recyclerview.addItemDecoration(itemDecorator);
            itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.rc_divider_white));
            recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            LatestCollectionAdapter adapter = new LatestCollectionAdapter(this, arr_latest);
            recyclerview.setAdapter(adapter);


            GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            recyclerview2.setLayoutManager(mLayoutManager);
            recyclerview2.addItemDecoration(new SpacesItemDecoration(2, 1, false));
            StudentCollectionAdapter adapter2 = new StudentCollectionAdapter(this, arr_students);
            recyclerview2.setAdapter(adapter2);


            populateSpinner1();
            populateSpinner2();


        }else {

        }
    }




    private void populateSpinner1(){
        List<String> locationList = new ArrayList<>();
        locationList.add("Select Category");
        for(int i=0;i<arr_category.size();i++){
            locationList.add(arr_category.get(i).getCategory());
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
                selectedItemCat = (String) parent.getItemAtPosition(position);

                if(position > 0){
                    cat_id = arr_category.get(position-1).getCategory_id();
                    //Toast.makeText(getApplicationContext(), "Selected : " + selectedItemCat, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void populateSpinner2(){
        List<String> schoolList = new ArrayList<>();
        schoolList.add("Select Subject");
        for(int i=0;i<arr_subject.size();i++){
            schoolList.add(arr_subject.get(i).getSubject());
        }
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this,R.layout.row_spinner_tv,schoolList){
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
            public void onItemSelected(AdapterView<?> parent, View view, int positio, long l) {
                selectedItemState = (String) parent.getItemAtPosition(positio);

                if(positio > 0){
                    sub_id = arr_subject.get(positio-1).getSubject_id();
                    // Toast.makeText(getApplicationContext(), "Selected : " + selectedItemState, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    private void getFilter(){
        progress_bar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(Ebook.this);
        String url =SharedPreferences.BASE_URL +"getAllEbookSearchData";
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
                params2.put("category", cat_id);
                params2.put("subject", sub_id);
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

        };
        queue.add(stringRequest);
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(Ebook.this).add(stringRequest);
    }



    private void parseJsonFilter(JSONObject response){
        arr_latest.clear();
        arr_students.clear();
        progress_bar.setVisibility(View.GONE);
        String success = response.optString("status");
        String msg = response.optString("msg");
        String image_url = response.optString("image_url");

        if(success.equalsIgnoreCase("Success")){
            JSONArray arr_json_data = response.optJSONArray("data");

            for(int i=0; i<5; i++){
                JSONObject object = arr_json_data.optJSONObject(i);
                if(object!=null) {
                    ModelData data = new ModelData();
                    data.setEbook_id(object.optString("ebook_id"));
                    data.setEbook_image(image_url+object.optString("ebook_image"));
                    data.setCategory_id(object.optString("category_id"));
                    data.setSubject_id(object.optString("subject_id"));
                    data.setTitle(object.optString("title"));
                    data.setSubtitle(object.optString("subtitle"));
                    data.setFile(object.optString("file"));
                    data.setLink(object.optString("link"));
                    data.setCategory(object.optString("category"));
                    data.setSubject(object.optString("subject"));
                    arr_latest.add(data);
                }
            }


            for(int i=5; i<arr_json_data.length(); i++){
                JSONObject object = arr_json_data.optJSONObject(i);
                if(object!=null) {
                    ModelData data = new ModelData();
                    data.setEbook_id(object.optString("ebook_id"));
                    data.setEbook_image(image_url+object.optString("ebook_image"));
                    data.setCategory_id(object.optString("category_id"));
                    data.setSubject_id(object.optString("subject_id"));
                    data.setTitle(object.optString("title"));
                    data.setSubtitle(object.optString("subtitle"));
                    data.setFile(object.optString("file"));
                    data.setLink(object.optString("link"));
                    data.setCategory(object.optString("category"));
                    data.setSubject(object.optString("subject"));
                    arr_students.add(data);
                }
            }


            DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL);
            recyclerview.addItemDecoration(itemDecorator);
            itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.rc_divider_white));
            recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            LatestCollectionAdapter adapter = new LatestCollectionAdapter(this, arr_latest);
            recyclerview.setAdapter(adapter);


            GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            recyclerview2.setLayoutManager(mLayoutManager);
            recyclerview2.addItemDecoration(new SpacesItemDecoration(2, 1, false));
            StudentCollectionAdapter adapter2 = new StudentCollectionAdapter(this, arr_students);
            recyclerview2.setAdapter(adapter2);


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
