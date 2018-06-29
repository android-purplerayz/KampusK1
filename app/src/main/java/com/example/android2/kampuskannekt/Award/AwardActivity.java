package com.example.android2.kampuskannekt.Award;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


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

public class AwardActivity extends AppCompatActivity {

    private RecyclerView recyclerviewAward;
    ArrayList<SetGetAward> array_award;
    private ProgressBar progressbarAward;
    private Toolbar toolbar;
    private String AWARD_URL=SharedPreferences.BASE_URL+"getAllAward";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award);
        progressbarAward=findViewById(R.id.progressbarAward);
        progressbarAward.setVisibility(View.GONE);
        recyclerviewAward=findViewById(R.id.recyclerviewAward);
        recyclerviewAward.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerviewAward.setAdapter(new MyadapterAward());

        GetAwardJSON(AWARD_URL);

        toolbar=findViewById(R.id.toolbar_award);
        Button btnback=toolbar.findViewById(R.id.btnbacktoServicesfromAward);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });


    }

    public class MyadapterAward extends RecyclerView.Adapter<MyadapterAward.ViewHolder9>{

        @NonNull
        @Override
        public ViewHolder9 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View V=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_award,parent,false);
            return new ViewHolder9(V);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder9 holder, int position) {

            Glide.with(AwardActivity.this).load(array_award.get(position).getAward_img())
                    .into(holder.imageAward1);
            holder.tvAnameORG.setText(array_award.get(position).getAward_name()+" from "+array_award.get(position).getAward_organized());
            holder.tvAwardTitle.setText(array_award.get(position).getAward_title());
            holder.tvAwardFor.setText(array_award.get(position).getAward_for());
            holder.tvAwardYear.setText(array_award.get(position).getYear());



        }

        @Override
        public int getItemCount() {
            return array_award.size();
        }

        public class ViewHolder9 extends RecyclerView.ViewHolder{

            TextView tvAnameORG,tvAwardTitle,tvAwardFor,tvAwardYear;
            ImageView imageAward1;
            public ViewHolder9(View itemView) {
                super(itemView);
                tvAnameORG=itemView.findViewById(R.id.tvAnameORG);
                tvAwardTitle=itemView.findViewById(R.id.tvAwardTitle);
                tvAwardFor=itemView.findViewById(R.id.tvAwardFor);
                tvAwardYear=itemView.findViewById(R.id.tvAwardYear);
                imageAward1=itemView.findViewById(R.id.imageAward1);

            }
        }
    }

    public void GetAwardJSON(String url){

        progressbarAward.setVisibility(View.VISIBLE);
        array_award=new ArrayList<>();
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    Log.i("respo1",response);
                    String Status = jsonObject.getString("status");
                    String msg=jsonObject.getString("msg");
                    String ImageURL=jsonObject.getString("image_url");
                    if (Status.equalsIgnoreCase("Success")) {
                        JSONArray jsonArrayDATA = jsonObject.getJSONArray("data");
                        Log.i("Size", String.valueOf(jsonArrayDATA.length()));
                        for (int i = 0; i < jsonArrayDATA.length(); i++) {
                            JSONObject jsonObject1=jsonArrayDATA.getJSONObject(i);
                            String awardID=jsonObject1.getString("award_id");
                            String awardTitle=jsonObject1.getString("award_title");
                            String awardName=jsonObject1.getString("award_name");
                            String awardORG=jsonObject1.getString("award_organized");
                            String awardFOR=jsonObject1.getString("award_for");
                            String awardImg=jsonObject1.getString("award_img");
                            String awardYear=jsonObject1.getString("year");

                            SetGetAward setGetAward=new SetGetAward();
                            setGetAward.setAward_id(awardID);
                            setGetAward.setAward_title(awardTitle);
                            setGetAward.setAward_name(awardName);
                            setGetAward.setAward_organized(awardORG);
                            setGetAward.setAward_for(awardFOR);
                            setGetAward.setAward_img(ImageURL+awardImg);
                            setGetAward.setYear(awardYear);

                            array_award.add(setGetAward);

                        }
                        recyclerviewAward.setAdapter(new MyadapterAward());
                        progressbarAward.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressbarAward.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbarAward.setVisibility(View.GONE);

            }
        });
        Volley.newRequestQueue(AwardActivity.this).add(stringRequest);
    }
}
