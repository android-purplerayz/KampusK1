package com.example.android2.kampuskannekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.android2.kampuskannekt.Award.AwardActivity;
import com.example.android2.kampuskannekt.ExamResult.ExamResults;
import com.example.android2.kampuskannekt.HealthTips.HealthTipsActivity;
import com.example.android2.kampuskannekt.TalentHunt.Talenthunt;
import com.example.android2.kampuskannekt.education_loan.EducationalLoan;
import com.example.android2.kampuskannekt.scholership.ScholershipActivity;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import SchoolandUniversity.ServiceSchoolandUniActivity;
import Videos.ServiceVideosMainActivity;
import ebook.Ebook;
import epaper.EPaper;
import ngo.NGO;


public class ServicesNewActivity extends AppCompatActivity {

    private  Intent intent11;
    private ProgressBar progService;
    private RecyclerView recyclerviewSerVicesnew;
    private ArrayList<SetGetService> serviceArr ;
    private RelativeLayout rloutEnterSchool,rloutBLOG,rloutERP;
    private String ServiceURL="http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/getAllservicesData";
    private TextView tvEmailID,tvUserNAme;
    private ImageView userImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sevicenew);
        progService=findViewById(R.id.progService);
        tvEmailID=findViewById(R.id.tvEmail);
        tvUserNAme=findViewById(R.id.tvuname);
        userImage=findViewById(R.id.cimageUSER);
        rloutEnterSchool=findViewById(R.id.rloutEnterSchool);
        rloutBLOG=findViewById(R.id.rloutBLOG);
        rloutERP=findViewById(R.id.rloutERP);

        rloutERP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServicesNewActivity.this,ERPActivity.class));
            }
        });

        rloutBLOG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServicesNewActivity.this,BlogActivity.class));
            }
        });

        SharedPreferences sharedPreferences =getSharedPreferences("DATA", Context.MODE_PRIVATE);
        String USER_N=sharedPreferences.getString("user_name","");
        String USER_FOTO=sharedPreferences.getString("user_foto","");
        String USER_EMAIL=sharedPreferences.getString("user_email","");



        tvEmailID.setText(USER_EMAIL);
        tvUserNAme.setText(USER_N);
        Glide.with(ServicesNewActivity.this).load(USER_FOTO)
                .into(userImage);


        Toolbar toolbar = findViewById(R.id.tbServices);
        Button btnBack =toolbar.findViewById(R.id.btnProfile);
        Button btnlogout =toolbar.findViewById(R.id.btnlogout);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(ServicesNewActivity.this)
                        .setTitle("Logout!")
                        .setDescription("Are you sure?")
                        .withDialogAnimation(true)
                        .setCancelable(false)
                        .setHeaderColor(R.color.colorPrimary)
                        .setIcon(R.drawable.ic_warning_white_24dp)
                        .setPositiveText("Yes")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                SharedPreferences sharedPreferences =getSharedPreferences("DATA", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor= sharedPreferences.edit();
                                editor.putString("login_status","no");
                                editor.commit();
                                intent11.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                finish();
                                dialog.setCancelable(true);
                            }
                        })
                        .setNegativeText("No")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.setCancelable(true);
                            }
                        })
                        .build();
                dialog.show();




            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ServicesNewActivity.this,ProfileForAllActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });




        rloutEnterSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServicesNewActivity.this,SchoolLoginActivity.class));
            }
        });
        recyclerviewSerVicesnew=findViewById(R.id.recyclerviewSerVicesnew);
        recyclerviewSerVicesnew.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        progService.setVisibility(View.GONE);
        GetService(ServiceURL);

    }

    public class MyServiceAdap extends RecyclerView.Adapter<MyServiceAdap.ViewHolderServ>{

        @NonNull
        @Override
        public ViewHolderServ onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicenew,parent,false);
            return new ViewHolderServ(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderServ holder, int position) {
            holder.pos =position;
            holder.tvserviceName.setText(serviceArr.get(position).getService_title());
            holder.tvCounter.setText(serviceArr.get(position).getService_count());
            Glide.with(ServicesNewActivity.this).load(serviceArr.get(position).getLogoS())
                    .into(holder.logoService);

            if(position %2 == 1)
            {
                // holder.itemView.setBackgroundColor(Color.parseColor("#4FBAA7"));
                holder.relacolourChange.setBackgroundColor(Color.parseColor("#135864"));
            }
            else
            {
                //holder.itemView.setBackgroundColor(Color.parseColor("#135864"));
                holder.relacolourChange.setBackgroundColor(Color.parseColor("#25C8CE"));
            }

        }

        @Override
        public int getItemCount() {
            return serviceArr.size();
        }

        public class ViewHolderServ extends RecyclerView.ViewHolder{
            TextView tvserviceName,tvCounter;
            ImageView logoService;
            RelativeLayout relacolourChange;
            int pos;
            public ViewHolderServ(View itemView) {
                super(itemView);
                tvserviceName=itemView.findViewById(R.id.tvserviceName);
                tvCounter=itemView.findViewById(R.id.tvCounter);
                relacolourChange=itemView.findViewById(R.id.relacolourChange);
                logoService=itemView.findViewById(R.id.logoService);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String itemClick =serviceArr.get(pos).getU_id();
                        if (itemClick.equalsIgnoreCase("s_id")){
                            startActivity(new Intent(ServicesNewActivity.this,ServiceSchoolandUniActivity.class));

                        }else if (itemClick.equalsIgnoreCase("ep_id")){
                            Intent i2=new Intent(ServicesNewActivity.this, EPaper.class);
                            startActivity(i2);

                        }else if (itemClick.equalsIgnoreCase("v_id")){
                            startActivity(new Intent(ServicesNewActivity.this, ServiceVideosMainActivity.class));

                        }else if (itemClick.equalsIgnoreCase("eb_id")){
                            Intent i3=new Intent(ServicesNewActivity.this, Ebook.class);
                            startActivity(i3);

                        }else if (itemClick.equalsIgnoreCase("n_id")){
                            Intent i4=new Intent(ServicesNewActivity.this, NGO.class);
                            startActivity(i4);

                        }else if (itemClick.equalsIgnoreCase("er_id")){
                            startActivity(new Intent(ServicesNewActivity.this,ExamResults.class));

                        }else if (itemClick.equalsIgnoreCase("th_id")){
                            startActivity(new Intent(ServicesNewActivity.this,Talenthunt.class));

                        }else if (itemClick.equalsIgnoreCase("sp_id")){
                            startActivity(new Intent(ServicesNewActivity.this,ScholershipActivity.class));

                        }else if (itemClick.equalsIgnoreCase("ht_id")){
                            startActivity(new Intent(ServicesNewActivity.this,HealthTipsActivity.class));

                        }else if (itemClick.equalsIgnoreCase("el_id")){
                            startActivity(new Intent(ServicesNewActivity.this,EducationalLoan.class));

                        }else if (itemClick.equalsIgnoreCase("a_id")){
                            startActivity(new Intent(ServicesNewActivity.this,AwardActivity.class));

                        }


                    }
                });
            }
        }
    }
    public void GetService(String url){

        progService.setVisibility(View.VISIBLE);
        serviceArr=new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    String Status = jsonObject.optString("status");
                    String msg = jsonObject.optString("msg");
                    String ImageURL = jsonObject.optString("image_url");
                    if (Status.equalsIgnoreCase("Success")){
                        JSONArray jsonArrayDATA = jsonObject.optJSONArray("data");
                        for (int i=0;i<jsonArrayDATA.length();i++){
                            JSONObject jsonObject1=jsonArrayDATA.optJSONObject(i);
                            String Serviceid = jsonObject1.optString("u_id");
                            String ServiceName = jsonObject1.optString("service_title");
                            String Logo = jsonObject1.optString("logo");
                            String Counter = jsonObject1.optString("service_count");

                            SetGetService setGetService =new SetGetService();
                            setGetService.setU_id(Serviceid);
                            setGetService.setService_title(ServiceName);
                            setGetService.setService_count(Counter);
                            setGetService.setLogoS(ImageURL+Logo);
                            serviceArr.add(setGetService);
                        }
                        progService.setVisibility(View.GONE);
                        recyclerviewSerVicesnew.setAdapter(new MyServiceAdap());
                        //Toast.makeText(ServicesNewActivity.this,msg,Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(ServicesNewActivity.this,msg,Toast.LENGTH_LONG).show();
                        progService.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                   // Toast.makeText(ServicesNewActivity.this,"Error exception",Toast.LENGTH_LONG).show();
                    progService.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ServicesNewActivity.this,"Volley Error",Toast.LENGTH_LONG).show();
                progService.setVisibility(View.GONE);
            }
        });
        Volley.newRequestQueue(ServicesNewActivity.this).add(stringRequest);
    }


}
