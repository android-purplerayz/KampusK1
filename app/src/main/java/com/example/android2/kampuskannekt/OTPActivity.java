package com.example.android2.kampuskannekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

public class OTPActivity extends AppCompatActivity {


    private String ValidateOTP="http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/validateOtp";
    private String ResendOTP="http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/resendOtp";
    private Button btnBack,btnValidate;
    private TextView tvResend;
    private EditText etOPT;
    private String mobile_Numb;
    private long lastClickTime = 0;
    private ArrayList<String> arrMOB;
    private SpotsDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        btnBack=findViewById(R.id.OTPback_button);
        btnValidate=findViewById(R.id.btnValidateID);
        tvResend=findViewById(R.id.tvResend);
        etOPT=findViewById(R.id.etOPT);


        dialog = (SpotsDialog) new SpotsDialog.Builder().setContext(OTPActivity.this).setTheme(R.style.Custom).build();
        dialog.setTitle("Sending OTP");
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.dismiss();
        mobile_Numb=getIntent().getExtras().getString("MobileNumber");


        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONresend(ResendOTP);

            }
        });

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();

               if (etOPT.getText().toString().trim().equals("")){
                   MDToast.makeText(OTPActivity.this,"Enter Your OTP",MDToast.TYPE_WARNING).show();
               }else {

                   POSTValidate(ValidateOTP);
               }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

    }
    public void POSTValidate(String url1){
        dialog.show();
        dialog.setCancelable(false);
        arrMOB=new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    String Status=jsonObject.optString("status");
                    String msg=jsonObject.optString("msg");
                    String ImageUrl=jsonObject.optString("image_url");
                    String USER =jsonObject.optString("user");
                    Log.v("res",response);
                    if(Status.equalsIgnoreCase("Success")){
                        JSONArray jsonArray =jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            String FNAme=jsonObject1.optString("fname");
                            String UserID=jsonObject1.optString("site_users_id");
                            String Email1=jsonObject1.optString("email_id");
                            String Mobile = jsonObject1.optString("mobile");
                            String ProImage=jsonObject1.optString("profile_img");
                            SharedPreferences sharedPreferences =getSharedPreferences("DATA", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor= sharedPreferences.edit();
                            editor.putString("login_status","yes");
                            editor.putString("user_id",UserID);
                            editor.putString("user_mobile",Mobile);
                            editor.putString("user_foto",ImageUrl+ProImage);
                            editor.putString("user_name",FNAme);
                            editor.putString("user_email",Email1);
                            editor.commit();


                            if (USER.equalsIgnoreCase("Old")){

                                MDToast.makeText(OTPActivity.this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {

                                        dialog.dismiss();
                                        dialog.setCancelable(true);
                                        Intent intent = new Intent(OTPActivity.this, ServicesNewActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    }
                                }, 1000);


                            }else {

                                MDToast.makeText(OTPActivity.this, msg, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {

                                        dialog.dismiss();
                                        dialog.setCancelable(true);
                                        Intent intent = new Intent(OTPActivity.this, ProfileForAllActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    }
                                }, 1000);

                            }


                        }


                    }else {
                        dialog.dismiss();
                        dialog.setCancelable(true);
                        MDToast.makeText(OTPActivity.this,msg + Status,MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                    dialog.setCancelable(true);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                dialog.setCancelable(true);

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap params2 = new HashMap();
                params2.put("mobile",mobile_Numb);
                params2.put("otp",etOPT.getText().toString().trim());


                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {

                return "application/json";
            }
        };
        Volley.newRequestQueue(OTPActivity.this).add(stringRequest);
    }
    public void JSONresend(String url2){
        dialog.show();
        dialog.setCancelable(false);
        StringRequest stringRequest1=new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    String Status=jsonObject.optString("status");
                    String data=jsonObject.optString("data");
                    String msg=jsonObject.optString("msg");
                    Log.v("res",response);
                    Log.v("res",data);
                    if(Status.equalsIgnoreCase("Success")){
                        Toast.makeText(OTPActivity.this,msg,Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        dialog.setCancelable(true);
                    }else {
                        Toast.makeText(OTPActivity.this,msg,Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        dialog.setCancelable(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                    dialog.setCancelable(true);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                dialog.setCancelable(true);

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap params2 = new HashMap();
                params2.put("mobile",mobile_Numb);



                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {

                return "application/json";
            }
        };
        Volley.newRequestQueue(OTPActivity.this).add(stringRequest1);
    }
    public class SetGetOTP{
        String mobile;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }




}
