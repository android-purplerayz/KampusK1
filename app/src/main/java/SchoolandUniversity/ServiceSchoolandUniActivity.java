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
import android.support.design.widget.NavigationView;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceSchoolandUniActivity extends AppCompatActivity {



    private String URLSchoolandUni = "http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/getschooluniversitylist";
    private String URL_post_schoolnUni = "http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/getschooluniversitylistsearch";
    private ImageView ivSchoolLogo, ivUniLogo;
    private ArrayList<String> schoolName = new ArrayList<>();
    private RecyclerView recyclerViewSchoolList;
    private ArrayList<SetgetSchnUni> SchoolandUniversityArrayList;
    private ArrayList<SUage> ARR_AGE;
    private ArrayList<SUdistric> ARR_DISTRIC;
    private ArrayList<SUstate> ARR_STATE;
    private ArrayList<SUboard> ARR_BOARD;
    private String TYPE;
    private ProgressBar ProgressSchonUni;
    private String selectedItemState,selectedItemAGE,selectedItemBoard,selectedItemDistric;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_schooland_uni);
        ProgressSchonUni=findViewById(R.id.ProgressSchonUni);

        GetSchoolandUniList(URLSchoolandUni);


        ProgressSchonUni.setVisibility(View.VISIBLE);
        recyclerViewSchoolList = (RecyclerView) findViewById(R.id.recyclerViewSchoolList);
        recyclerViewSchoolList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_cat);
        setSupportActionBar(toolbar);

        ImageView save = toolbar.findViewById(R.id.save);
        Button btnbacktoServices = toolbar.findViewById(R.id.btnbacktoServices);

        btnbacktoServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Button btnsearchschoNuni = findViewById(R.id.btnsearchschoNuni);



        Switch switchSchoolandUni = (Switch) navigationView.findViewById(R.id.switchSchoolandUni);

        switchSchoolandUni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true) {

                    ivUniLogo.setImageResource(R.drawable.ic_uni_logo_svg);
                    ivSchoolLogo.setImageBitmap(null);
                    TYPE=String.valueOf(1);
                } else {
                    ivSchoolLogo.setImageResource(R.drawable.ic_school_logo);
                    ivUniLogo.setImageBitmap(null);
                    TYPE=String.valueOf(2);
                }
            }
        });



        btnsearchschoNuni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostSchoolnUni(URL_post_schoolnUni);

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }


            }
        });
        ivSchoolLogo = (ImageView) navigationView.findViewById(R.id.ivSchoolLogo);
        ivUniLogo = (ImageView) navigationView.findViewById(R.id.ivUniLogo);



        //navigationView.setNavigationItemSelectedListener(this);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
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

    public class MYadapterSchoollist extends RecyclerView.Adapter<MYadapterSchoollist.ViewHolderSchool> {


        ArrayList<SetgetSchnUni> arrayList;

        public MYadapterSchoollist(Context context, ArrayList<SetgetSchnUni> arrayListA) {
            if (context != null) {
                this.arrayList = arrayListA;


            }
        }

        @NonNull
        @Override
        public ViewHolderSchool onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shool_universitice, parent, false);

            return new ViewHolderSchool(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderSchool holder, final int position) {




            holder.tvSchoolName.setText(arrayList.get(position).getUniversity_nameS());
            holder.textViewshortDes.setText(arrayList.get(position).getShort_desS());

            String link = arrayList.get(position).getSchool_imageS();
            Glide.with(ServiceSchoolandUniActivity.this).load(link)
                    .into(holder.imageViewSchool);
            Log.i("Image", link);

            holder.rowSchoolandUni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ServiceSchoolandUniActivity.this, SchoolDetailsActivity.class);
                    intent.putExtra("Scho_Image",SchoolandUniversityArrayList.get(position).getSchool_imageS());
                    intent.putExtra("Scho_Title",SchoolandUniversityArrayList.get(position).getUniversity_nameS());
                    intent.putExtra("Scho_LDes",SchoolandUniversityArrayList.get(position).getLong_des());
                    intent.putExtra("Scho_SDes",SchoolandUniversityArrayList.get(position).getShort_desS());
                    intent.putExtra("Scho_Add",SchoolandUniversityArrayList.get(position).getAddressS());
                    intent.putExtra("Scho_PrinC",SchoolandUniversityArrayList.get(position).getPrincipalS());
                    intent.putExtra("Scho_Phone",SchoolandUniversityArrayList.get(position).getPhoneS());
                    intent.putExtra("Scho_AgeG",SchoolandUniversityArrayList.get(position).getAge_groupS());
                    intent.putExtra("Scho_Stage",SchoolandUniversityArrayList.get(position).getStageS());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return SchoolandUniversityArrayList.size();
        }

        public class ViewHolderSchool extends RecyclerView.ViewHolder {

            ConstraintLayout rowSchoolandUni;
            TextView tvSchoolName, textViewshortDes;
            ImageView imageViewSchool;
            Button btnShoandUniCall;

            public ViewHolderSchool(View itemView) {
                super(itemView);
                imageViewSchool =  itemView.findViewById(R.id.imageViewSchool);
                btnShoandUniCall = itemView.findViewById(R.id.btnShoandUniCall);
                rowSchoolandUni = itemView.findViewById(R.id.rowSchoolandUni);
                tvSchoolName = itemView.findViewById(R.id.tvSchoolName);
                textViewshortDes = itemView.findViewById(R.id.textViewshortDes);
            }
        }
    }

    public void GetSchoolandUniList(String url) {

        ProgressSchonUni.setVisibility(View.VISIBLE);
        SchoolandUniversityArrayList = new ArrayList<>();
        ARR_DISTRIC=new ArrayList<>();
        ARR_AGE=new ArrayList<>();
        ARR_BOARD=new ArrayList<>();
        ARR_STATE=new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("respo", response);
                    String Status = jsonObject.getString("status");
                    String msg = jsonObject.getString("msg");
                    String ImageURL = jsonObject.getString("image_url");
                    if (Status.equalsIgnoreCase("Success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String SchoolID = jsonObject1.getString("school_id");
                            String UniversityName = jsonObject1.getString("university_name");
                            String ShortDes = jsonObject1.getString("short_des");
                            String LongDes = jsonObject1.getString("long_des");
                            String Address = jsonObject1.getString("address");
                            String Principle = jsonObject1.getString("principal");
                            String Phone = jsonObject1.getString("phone");
                            String Rating = jsonObject1.getString("rating");
                            String Board = jsonObject1.getString("board");
                            String Stage = jsonObject1.getString("stage");
                            String SchoolImage = jsonObject1.getString("school_image");
                            String AgeGroup = jsonObject1.getString("age_group");

                            SetgetSchnUni setgetUnischool = new SetgetSchnUni();
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
                            setgetUnischool.setSchool_imageS(ImageURL + SchoolImage);
                            setgetUnischool.setAge_groupS(AgeGroup);

                            SchoolandUniversityArrayList.add(setgetUnischool);
                        }
                        JSONArray jsonArrayBoard = jsonObject.getJSONArray("board");
                        for (int i = 0; i < jsonArrayBoard.length(); i++) {
                            JSONObject jsonObject2 = jsonArrayBoard.getJSONObject(i);
                            String Board_ID=jsonObject2.optString("board_id");
                            String Board=jsonObject2.optString("board");

                            SUboard sUboard=new SUboard();
                            sUboard.setBoard_id(Board_ID);
                            sUboard.setBoard(Board);
                            ARR_BOARD.add(sUboard);
                        }
                        JSONArray jsonArrayDistric = jsonObject.getJSONArray("district");
                        for (int i = 0; i < jsonArrayDistric.length(); i++) {
                            JSONObject jsonObject3 = jsonArrayDistric.getJSONObject(i);
                            String DIS_ID=jsonObject3.optString("district_id");
                            String DIS=jsonObject3.optString("district");

                            SUdistric sUdistric=new SUdistric();
                            sUdistric.setDistrict_id(DIS_ID);
                            sUdistric.setDistrict(DIS);

                            ARR_DISTRIC.add(sUdistric);
                        }
                        JSONArray jsonArrayState = jsonObject.getJSONArray("state");
                        for (int i = 0; i < jsonArrayState.length(); i++) {
                            JSONObject jsonObject4 = jsonArrayState.getJSONObject(i);
                            String StateID=jsonObject4.optString("state_id");
                            String StateNAME=jsonObject4.optString("state_name");

                            SUstate sUstate =new SUstate();
                            sUstate.setState_id(StateID);
                            sUstate.setState_name(StateNAME);

                            ARR_STATE.add(sUstate);
                        }
                        JSONArray jsonArrayAGe = jsonObject.getJSONArray("age");
                        for (int i = 0; i < jsonArrayAGe.length(); i++) {
                            JSONObject jsonObject5 = jsonArrayAGe.getJSONObject(i);
                            String AgeID=jsonObject5.optString("age_id");
                            String Age=jsonObject5.optString("age");

                            SUage sUage =new SUage();
                            sUage.setAge_id(AgeID);
                            sUage.setAge(Age);

                            ARR_AGE.add(sUage);
                        }

                        if (SchoolandUniversityArrayList.size() > 0) {

                            recyclerViewSchoolList.setAdapter(new MYadapterSchoollist(ServiceSchoolandUniActivity.this, SchoolandUniversityArrayList));
                            SchoolUniSpinner();
                            ProgressSchonUni.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ProgressSchonUni.setVisibility(View.GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ProgressSchonUni.setVisibility(View.GONE);

            }
        });
        Volley.newRequestQueue(ServiceSchoolandUniActivity.this).add(stringRequest);

    }
    public void PostSchoolnUni(String url){
        ProgressSchonUni.setVisibility(View.VISIBLE);
        SchoolandUniversityArrayList=new ArrayList<>();
        StringRequest stringRequest1 =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    String Status = jsonObject.getString("status");
                    String msg=jsonObject.getString("msg");
                    String ImageURL = jsonObject.optString("image_url");
                    if (Status.equalsIgnoreCase("Success")) {
                        Toast.makeText(ServiceSchoolandUniActivity.this,msg,Toast.LENGTH_LONG).show();
                        JSONArray jsonArraydata = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArraydata.length(); i++) {

                            JSONObject jsonObject12 = jsonArraydata.getJSONObject(i);
                            String SchoolID = jsonObject12.getString("school_id");
                            String UniversityName = jsonObject12.getString("university_name");
                            String ShortDes = jsonObject12.getString("short_des");
                            String LongDes = jsonObject12.getString("long_des");
                            String Address = jsonObject12.getString("address");
                            String Principle = jsonObject12.getString("principal");
                            String Phone = jsonObject12.getString("phone");
                            String Rating = jsonObject12.getString("rating");
                            String Board = jsonObject12.getString("board");
                            String Stage = jsonObject12.getString("stage");
                            String SchoolImage = jsonObject12.getString("school_image");
                            String AgeGroup = jsonObject12.getString("age_group");

                            SetgetSchnUni setgetUnischool = new SetgetSchnUni();
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
                            setgetUnischool.setSchool_imageS(ImageURL + SchoolImage);
                            setgetUnischool.setAge_groupS(AgeGroup);

                            SchoolandUniversityArrayList.add(setgetUnischool);
                        }
                        if (SchoolandUniversityArrayList.size() > 0) {

                            recyclerViewSchoolList.setAdapter(new MYadapterSchoollist(ServiceSchoolandUniActivity.this, SchoolandUniversityArrayList));
                            ProgressSchonUni.setVisibility(View.GONE);
                        }
                    }else{
                        Toast.makeText(ServiceSchoolandUniActivity.this,msg,Toast.LENGTH_LONG).show();
                        ProgressSchonUni.setVisibility(View.GONE);
                        SchoolandUniversityArrayList.clear();
                        MYadapterSchoollist mYadapterSchoollist = new MYadapterSchoollist(ServiceSchoolandUniActivity.this, SchoolandUniversityArrayList);
                        recyclerViewSchoolList.setAdapter(mYadapterSchoollist);
                        mYadapterSchoollist.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ProgressSchonUni.setVisibility(View.GONE);
                    Toast.makeText(ServiceSchoolandUniActivity.this,"Error in exception",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ProgressSchonUni.setVisibility(View.GONE);

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap params2 = new HashMap();
                params2.put("age",selectedItemAGE);
                params2.put("state", selectedItemState);
                params2.put("board",selectedItemBoard);
                params2.put("district",selectedItemDistric);
                params2.put("type",TYPE );
                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {

                return "application/json";
            }
        };
        Volley.newRequestQueue(ServiceSchoolandUniActivity.this).add(stringRequest1);

    }

    public void SchoolUniSpinner(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Spinner spinnerState = (Spinner) navigationView.findViewById(R.id.spinnerState);
        Spinner spinnerDistric = (Spinner) navigationView.findViewById(R.id.spinnerDistric);
        Spinner spinnerBoard = (Spinner) navigationView.findViewById(R.id.spinnerBoard);
        Spinner spinnerAgeGroup = (Spinner) navigationView.findViewById(R.id.spinnerAgeGroup);



        //spinner State
        final List<String> statelist = new ArrayList<>();
        statelist.add("Select State");
        for (int i=0;i<ARR_STATE.size();i++){
            statelist.add(ARR_STATE.get(i).getState_name());
        }
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statelist) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
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
        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {

                String Sub = (String) parent.getItemAtPosition(position1);
                if (position1>0){
                     selectedItemState = ARR_STATE.get(position1-1).getState_id();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //spinner Distric
        final List<String> Distric = new ArrayList<>();
        Distric.add("Select Distric");
        for (int i=0;i<ARR_DISTRIC.size();i++){
            Distric.add(ARR_DISTRIC.get(i).getDistrict());
        }
        ArrayAdapter<String> aa1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Distric) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
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
        spinnerDistric.setAdapter(aa1);
        spinnerDistric.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {

                String Sub = (String) parent.getItemAtPosition(position1);
                if (position1>0){
                    selectedItemDistric = ARR_DISTRIC.get(position1-1).getDistrict_id();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner Board
        final List<String> Board = new ArrayList<>();
        Board.add("Select Board");
        for (int i=0;i<ARR_BOARD.size();i++){
            Board.add(ARR_BOARD.get(i).getBoard());
        }
        ArrayAdapter<String> aa2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Board) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
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
        spinnerBoard.setAdapter(aa2);
        spinnerBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {

                String Sub = (String) parent.getItemAtPosition(position1);
                if (position1>0){
                    selectedItemBoard = ARR_BOARD.get(position1-1).getBoard_id();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //spinner Age
        final List<String> Age = new ArrayList<>();
        Age.add("Select Age-Group");
        for (int i=0;i<ARR_AGE.size();i++){
            Age.add(ARR_AGE.get(i).getAge());
        }
        ArrayAdapter<String> aa3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Age) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
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
        spinnerAgeGroup.setAdapter(aa3);
        spinnerAgeGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {

                String Sub = (String) parent.getItemAtPosition(position1);
                if (position1>0){
                    selectedItemAGE = ARR_AGE.get(position1-1).getAge_id();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

