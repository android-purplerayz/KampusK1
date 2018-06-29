package epaper;

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

public class EPaper extends AppCompatActivity {
    ImageView save, left, ic_close;
    Button btn_search;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    RecyclerView recyclerview;
    Spinner spinner1, spinner2, spinner3;
    ProgressBar progress_bar;
    ArrayList<ModelEpaperData> arr_data ;
    ArrayList<ModelEpaperType> arr_type;
    ArrayList<ModelEpaperTopic> arr_topic;
    ArrayList<ModelEpaperAgeGroup> arr_age_group;
    String type_id, topic_id, age_id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epaper);

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
        btn_search = findViewById(R.id.btn_search);
        progress_bar = findViewById(R.id.progress_barEpaper);
        progress_bar.setVisibility(View.GONE);
        arr_data = new ArrayList<>();
        arr_type = new ArrayList<>();
        arr_topic = new ArrayList<>();
        arr_age_group = new ArrayList<>();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        navigationView =  findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);ListView lv_category =navigationView.findViewById(R.id.categoryList);
        //add categories to the listview
        //ArrayList<HashMap<String, String>> category_list = new ArrayList<>();
       /* String cats[] = new String[]{"item1","item2" , "item3", "item4"};
        String cats_items[] = new String[]{"10","14" , "6", "7"};*/
       /* for (int i = 0 ; i<15; i++){

            HashMap<String, String> map = new HashMap<>();
            map.put("cat","Most confusing words in the English language");
            map.put("img", "https://www.cornerstone.edu/images/blogs/300-web-desk.jpg");
            category_list.add(map);
        }*/
        //CategoryAdapter adapter_invoice = new CategoryAdapter(this, category_list);
        //lv_category.setAdapter(adapter_invoice);

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
        arr_type.clear();
        arr_topic.clear();
        arr_age_group.clear();
        progress_bar.setVisibility(View.VISIBLE);
        String url = SharedPreferences.BASE_URL +"getAllEPaper";
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
        Volley.newRequestQueue(EPaper.this).add(stringRequest);
    }


    private void parseJson(JSONObject response){
       /* arr_latest.clear();
        arr_students.clear();
        arr_category.clear();
        arr_subject.clear();*/
        progress_bar.setVisibility(View.GONE);
        String success = response.optString("status");
        String msg = response.optString("msg");
        String image_url = response.optString("image_url");
        String file_url = response.optString("file_url");

        if(success.equalsIgnoreCase("Success")){
            JSONArray arr_jsondata = response.optJSONArray("data");
            JSONArray arr_json_student_type = response.optJSONArray("student_type");
            JSONArray arr_json_topic = response.optJSONArray("topic");
            JSONArray arr_json_age_group = response.optJSONArray("age_group");

            for(int i=0; i<arr_jsondata.length(); i++){
                JSONObject object = arr_jsondata.optJSONObject(i);
                if(object!=null) {
                    ModelEpaperData data = new ModelEpaperData();
                    data.setEpaper_id(object.optString("epaper_id"));
                    data.setStudent_id(object.optString("student_id"));
                    data.setAge_id(object.optString("age_id"));
                    data.setSubject_id(object.optString("subject_id"));
                    data.setTitle(object.optString("title"));
                    data.setEpaper_image(image_url+object.optString("epaper_image"));
                    data.setFile(object.optString("file"));
                    data.setLink(object.optString("link"));
                    arr_data.add(data);
                }
            }

            for(int i=0; i<arr_json_student_type.length(); i++){
                JSONObject object = arr_json_student_type.optJSONObject(i);
                if(object!=null) {
                    ModelEpaperType data = new ModelEpaperType();
                    data.setStudent_id(object.optString("student_id"));
                    data.setStudent_type(object.optString("student_type"));
                    arr_type.add(data);
                }
            }

            for(int i=0; i<arr_json_topic.length(); i++){
                JSONObject object = arr_json_topic.optJSONObject(i);
                if(object!=null) {
                    ModelEpaperTopic data = new ModelEpaperTopic();
                    data.setTopic_id(object.optString("topic_id"));
                    data.setTopic(object.optString("topic"));
                    arr_topic.add(data);
                }
            }

            for(int i=0; i<arr_json_age_group.length(); i++){
                JSONObject object = arr_json_age_group.optJSONObject(i);
                if(object!=null) {
                    ModelEpaperAgeGroup data = new ModelEpaperAgeGroup();
                    data.setAge_id(object.optString("age_id"));
                    data.setAge(object.optString("age"));
                    arr_age_group.add(data);
                }
            }


            DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL);
            recyclerview.addItemDecoration(itemDecorator);
            itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.rc_divider_white));
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            CategoryAdapter adapter = new CategoryAdapter(this, arr_data);
            recyclerview.setAdapter(adapter);

            populateSpinner1();
            populateSpinner2();
            populateSpinner3();


        }else {

        }
    }




    private void populateSpinner1(){
        //final List<String> locationList = new ArrayList<>(Arrays.asList(location));
        List<String> locationList = new ArrayList<>();
        locationList.add("Select Student Type");
        for(int i=0;i<arr_type.size();i++){
            locationList.add(arr_type.get(i).getStudent_type());
        }

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(
                this,R.layout.row_spinner_tv,locationList){
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


        spinnerArrayAdapter1.setDropDownViewResource(R.layout.row_spinner_tv);
        spinner1.setAdapter(spinnerArrayAdapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    //Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                    type_id = arr_type.get(position-1).getStudent_id();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void populateSpinner2(){
        //final List<String> schoolList = new ArrayList<>(Arrays.asList(school));
        List<String> schoolList = new ArrayList<>();
        schoolList.add("Select Topic");
        for(int i=0;i<arr_topic.size();i++){
            schoolList.add(arr_topic.get(i).getTopic());
        }

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this,R.layout.row_spinner_tv,schoolList){
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
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    topic_id = arr_topic.get(position-1).getTopic_id();
                    // Notify the selected item text
                    //Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void populateSpinner3(){
        //final List<String> schoolList = new ArrayList<>(Arrays.asList(school1));
        List<String> schoolList = new ArrayList<>();
        schoolList.add("Select Age Group");
        for(int i=0;i<arr_age_group.size();i++){
            schoolList.add(arr_age_group.get(i).getAge());
        }

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this,R.layout.row_spinner_tv,schoolList){
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
        spinner3.setAdapter(spinnerArrayAdapter2);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    age_id = arr_age_group.get(position-1).getAge_id();
                    // Notify the selected item text
                    //Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
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
        String url =SharedPreferences.BASE_URL +"getAllEpaperSearchData";
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
                params2.put("student_type", type_id);
                params2.put("topic", topic_id);
                params2.put("age_group", age_id);
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }
        };
        //queue.add(stringRequest);
        stringRequest.setShouldCache(false);
        Volley.newRequestQueue(EPaper.this).add(stringRequest);
    }

    private void parseJsonFilter(JSONObject response){
        arr_type.clear();
        arr_topic.clear();
        arr_age_group.clear();
        progress_bar.setVisibility(View.GONE);
        String success = response.optString("status");
        String msg = response.optString("msg");
        String image_url = response.optString("image_url");

        if(success.equalsIgnoreCase("Success")){
            JSONArray arr_json_data = response.optJSONArray("data");

            for(int i=0; i<arr_json_data.length(); i++){
                JSONObject object = arr_json_data.optJSONObject(i);
                if(object!=null) {
                    ModelEpaperData data = new ModelEpaperData();
                    data.setEpaper_id(object.optString("epaper_id"));
                    data.setStudent_id(object.optString("student_id"));
                    data.setAge_id(object.optString("age_id"));
                    data.setSubject_id(object.optString("subject_id"));
                    data.setTitle(object.optString("title"));
                    data.setEpaper_image(image_url+object.optString("epaper_image"));
                    data.setFile(object.optString("file"));
                    data.setLink(object.optString("link"));
                    arr_data.add(data);
                }
            }

            DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL);
            recyclerview.addItemDecoration(itemDecorator);
            itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.rc_divider_white));
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            CategoryAdapter adapter = new CategoryAdapter(this, arr_data);
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
