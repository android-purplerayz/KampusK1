package Videos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class ServiceVideosMainActivity extends AppCompatActivity {




    private  RecyclerView recyclerViewSchoolList;
    private String Video_URL="http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/getAllvideos";
    private String POSTVideo_URL="http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/getAllvideosSearchData";
    private ProgressBar progVideos;
    private String selectedItemSubject,selectedItemTopic,selectedItemType;
    private NavigationView navigationView;
    private ArrayList<VideosData> ArrayVDATA;
    private ArrayList<VstuType> ArrayStype;
    private ArrayList<Vtopic> ArrayTopic;
    private ArrayList<Vsubject> ArraySUB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_videos_main);

        progVideos=findViewById(R.id.progVideos);
        progVideos.setVisibility(View.GONE);


        GetVideoJSON(Video_URL);


        recyclerViewSchoolList =(RecyclerView)findViewById(R.id.recyclerViewVideosList);
        recyclerViewSchoolList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSchoolList.setAdapter(new MyadapterVideos());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_cat1);
        setSupportActionBar(toolbar);

        ImageView save = toolbar.findViewById(R.id.save1);
        Button btnbacktoServices=toolbar.findViewById(R.id.btnbacktoServices);

        btnbacktoServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

        navigationView = (NavigationView) findViewById(R.id.nav_viewVideos);
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
        public void onBindViewHolder(@NonNull VideosViewHolder holder, final int position) {

            holder.tvViewers.setText(ArrayVDATA.get(position).getCounter());

            holder.tvVideoTitle.setText(ArrayVDATA.get(position).getTitle());
            holder.btnplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i =new Intent(ServiceVideosMainActivity.this,ServicePlayVideosActivity.class);
                    i.putExtra("Video_URL",ArrayVDATA.get(position).getVideos());
                    startActivity(i);
                }
            });
            Glide.with(ServiceVideosMainActivity.this).load(ArrayVDATA.get(position).getVideo_image())
                    .into(holder.VideoThumimage);
        }

        @Override
        public int getItemCount() {
            return ArrayVDATA.size();
        }

        public class VideosViewHolder extends RecyclerView.ViewHolder{

            Button btnplay;
            ImageView VideoThumimage;
            TextView tvViewers,tvVideoTitle;
            public VideosViewHolder(View itemView) {
                super(itemView);
                tvVideoTitle=itemView.findViewById(R.id.tvVideoTitle);
                tvViewers=(TextView)itemView.findViewById(R.id.tvViewers);
                btnplay=(Button) itemView.findViewById(R.id.btnplay);
                VideoThumimage=(ImageView)itemView.findViewById(R.id.VideoThumimage);
            }
        }
    }



    public void GetVideoJSON(String url1){

        progVideos.setVisibility(View.VISIBLE);
        ArrayVDATA=new ArrayList<>();
        ArrayStype=new ArrayList<>();
        ArraySUB=new ArrayList<>();
        ArrayTopic=new ArrayList<>();
        StringRequest request1=new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    Log.i("respo",response);
                    String Status = jsonObject.getString("status");
                    String msg=jsonObject.getString("msg");
                    String ImageURL=jsonObject.getString("image_url");
                    String VideoURL=jsonObject.getString("file_url");
                    if (Status.equalsIgnoreCase("Success")) {
                        JSONArray jsonArrayDATA = jsonObject.getJSONArray("data");
                        Log.i("Size", String.valueOf(jsonArrayDATA.length()));
                        for (int i = 0; i < jsonArrayDATA.length(); i++) {
                            JSONObject jsonObject1=jsonArrayDATA.getJSONObject(i);
                            String VideoID=jsonObject1.optString("video_id");
                            String VideoTitle=jsonObject1.optString("title");
                            String VideoImage=jsonObject1.optString("video_image");
                            String VIDEOS=jsonObject1.optString("videos");
                            String StudentID=jsonObject1.optString("student_id");
                            String TopicID=jsonObject1.optString("topic_id");
                            String SubjectID=jsonObject1.optString("subject_id");
                            String Link=jsonObject1.optString("link");
                            String Counter=jsonObject1.optString("counter");

                            VideosData videosData=new VideosData();
                            videosData.setVideo_id(VideoID);
                            videosData.setTitle(VideoTitle);
                            videosData.setVideo_image(ImageURL+VideoImage);
                            videosData.setVideos(VideoURL+VIDEOS);
                            videosData.setStudent_id(StudentID);
                            videosData.setTopic_id(TopicID);
                            videosData.setSubject_id(SubjectID);
                            videosData.setLink(Link);
                            videosData.setCounter(Counter);
                            ArrayVDATA.add(videosData);
                        }
                        JSONArray jsonArrayStutype = jsonObject.getJSONArray("student_type");
                        for (int i=0;i<jsonArrayStutype.length();i++){
                            JSONObject jsonObject2=jsonArrayStutype.getJSONObject(i);
                            String StuID=jsonObject2.optString("student_id");
                            String StuTYPE=jsonObject2.optString("student_type");

                            VstuType vstuType=new VstuType();
                            vstuType.setStudent_id(StuID);
                            vstuType.setStudent_type(StuTYPE);

                            ArrayStype.add(vstuType);
                        }
                        JSONArray jsonArrayTopic = jsonObject.getJSONArray("topic");
                        for (int i=0;i<jsonArrayTopic.length();i++){
                            JSONObject jsonObject3=jsonArrayTopic.getJSONObject(i);
                            String TopicID=jsonObject3.optString("topic_id");
                            String Topic=jsonObject3.optString("topic");

                            Vtopic vtopic =new Vtopic();
                            vtopic.setTopic_id(TopicID);
                            vtopic.setTopic(Topic);

                            ArrayTopic.add(vtopic);
                        }
                        JSONArray jsonArraySub =jsonObject.getJSONArray("subject");
                        for (int i=0;i<jsonArraySub.length();i++){
                            JSONObject jsonObject4=jsonArraySub.getJSONObject(i);
                            String SubID=jsonObject4.optString("subject_id");
                            String Sub=jsonObject4.optString("subject");

                            Vsubject vsubject= new Vsubject();
                            vsubject.setSubject_id(SubID);
                            vsubject.setSubject(Sub);

                            ArraySUB.add(vsubject);
                        }
                    }
                    recyclerViewSchoolList.setAdapter(new MyadapterVideos());
                    VideoSpinner();
                    progVideos.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    progVideos.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progVideos.setVisibility(View.GONE);

            }
        });
        Volley.newRequestQueue(ServiceVideosMainActivity.this).add(request1);

    }

    public void POSTVideoJSON(String url2){

        progVideos.setVisibility(View.VISIBLE);
        ArrayVDATA=new ArrayList<>();
                StringRequest request2=new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            Log.i("respo23",response);
                            String Status = jsonObject.getString("status");
                            String msg=jsonObject.getString("msg");
                            String ImageURL=jsonObject.getString("image_url");
                            String VideoURL=jsonObject.getString("file_url");
                            if (Status.equalsIgnoreCase("Success")) {
                                JSONArray jsonArrayDATA = jsonObject.getJSONArray("data");
                                Log.i("respo23", String.valueOf(jsonArrayDATA.length()));
                                for (int i = 0; i < jsonArrayDATA.length(); i++) {
                                    JSONObject jsonObject1 = jsonArrayDATA.getJSONObject(i);
                                    String VideoID = jsonObject1.optString("video_id");
                                    String VideoTitle = jsonObject1.optString("title");
                                    String VideoImage = jsonObject1.optString("video_image");
                                    String VIDEOS = jsonObject1.optString("videos");
                                    String StudentID = jsonObject1.optString("student_id");
                                    String TopicID = jsonObject1.optString("topic_id");
                                    String SubjectID = jsonObject1.optString("subject_id");
                                    String Link = jsonObject1.optString("link");
                                    String Counter = jsonObject1.optString("counter");

                                    VideosData videosData = new VideosData();
                                    videosData.setVideo_id(VideoID);
                                    videosData.setTitle(VideoTitle);
                                    videosData.setVideo_image(ImageURL + VideoImage);
                                    videosData.setVideos(VideoURL + VIDEOS);
                                    videosData.setStudent_id(StudentID);
                                    videosData.setTopic_id(TopicID);
                                    videosData.setSubject_id(SubjectID);
                                    videosData.setLink(Link);
                                    videosData.setCounter(Counter);
                                    ArrayVDATA.add(videosData);
                                }
                                recyclerViewSchoolList.setAdapter(new MyadapterVideos());
                                progVideos.setVisibility(View.GONE);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progVideos.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progVideos.setVisibility(View.GONE);

                    }
                }){
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        HashMap params2 = new HashMap();
                        params2.put("student_type",selectedItemType );
                        params2.put("subject", selectedItemSubject);
                        params2.put("topic",selectedItemTopic );
                        return new JSONObject(params2).toString().getBytes();
                    }
                    @Override
                    public String getBodyContentType() {

                        return "application/json";
                    }

                };
                Volley.newRequestQueue(ServiceVideosMainActivity.this).add(request2);

            }


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;

            public void VideoSpinner(){


                navigationView = (NavigationView) findViewById(R.id.nav_viewVideos);
                Button btnsearchXam=navigationView.findViewById(R.id.btnsearchV);

                btnsearchXam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //PostExamJSON(POSTExarRes_URL);

                        POSTVideoJSON(POSTVideo_URL);
                        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
                        if (drawer.isDrawerOpen(GravityCompat.END)) {
                            drawer.closeDrawer(GravityCompat.END);
                        }else{
                            drawer.openDrawer(GravityCompat.END);
                        }
                    }
                });

                Spinner spinnerSType=(Spinner)navigationView.findViewById(R.id.spinnerSType);
                Spinner spinnerStopic=(Spinner)navigationView.findViewById(R.id.spinnerStopic);
                Spinner spinnerSsub=(Spinner)navigationView.findViewById(R.id.spinnerSsub);

                final List<String> STYPE=new ArrayList<>();
                STYPE.add("Select Video Type");
                for (int i=0;i<ArrayStype.size();i++){
                    STYPE.add(ArrayStype.get(i).getStudent_type());
                }
                //final List<String> Subjectlist =new ArrayList<>(Arrays.asList(SubjectNew));
                ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,STYPE){
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
                spinnerSType.setAdapter(aa);
                spinnerSType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {


                        String Sub = (String) parent.getItemAtPosition(position1);
                        if (position1>0){
                            selectedItemType = ArrayStype.get(position1-1).getStudent_id();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });






                final List<String> TOPIC=new ArrayList<>();
                TOPIC.add("Select Topic");
                for (int i=0;i<ArrayTopic.size();i++){
                    TOPIC.add(ArrayTopic.get(i).getTopic());
                }
                //final List<String> Subjectlist =new ArrayList<>(Arrays.asList(SubjectNew));
                ArrayAdapter<String> aa1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,TOPIC){
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
                spinnerStopic.setAdapter(aa1);
                spinnerStopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
                        String class1 = (String) parent.getItemAtPosition(position2);
                        if (position2>0){
                            selectedItemTopic = ArrayTopic.get(position2-1).getTopic_id();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });




                final List<String> SUBJECT=new ArrayList<>();
                SUBJECT.add("Select Subject");
                for (int i=0;i<ArraySUB.size();i++){
                    SUBJECT.add(ArraySUB.get(i).getSubject());
                }
                //final List<String> Subjectlist =new ArrayList<>(Arrays.asList(SubjectNew));
                ArrayAdapter<String> aa2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,SUBJECT){
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
                spinnerSsub.setAdapter(aa2);
                spinnerSsub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position3, long id) {

                        String Age = (String) parent.getItemAtPosition(position3);
                        if (position3>0){
                            selectedItemSubject = ArraySUB.get(position3-1).getSubject_id();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

}
