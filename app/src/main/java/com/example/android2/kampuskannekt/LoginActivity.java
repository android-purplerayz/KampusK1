package com.example.android2.kampuskannekt;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private String Login_URL="http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/sendOtp";
    private Button back_button;
    private Button btn_get_otp;
    private EditText etLoginMobile;
    private long lastClickTime = 0;
    private SpotsDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_get_otp=findViewById(R.id.btn_getOTP);
        etLoginMobile=findViewById(R.id.etLoginMobile);

        dialog = (SpotsDialog) new SpotsDialog.Builder().setContext(LoginActivity.this).setTheme(R.style.Custom).build();
        dialog.setTitle("Sending OTP");
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.dismiss();





        btn_get_otp.setOnClickListener(new View.OnClickListener() {
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


                if (etLoginMobile.getText().toString().trim().equals("")){
                    //etLoginMobile.setError("Enter Valid Mobile Number");
                    MDToast.makeText(LoginActivity.this,"Enter Your Number",MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                }else {


                    String validPhoneNumber="[7,8,9]{1}[0-9]{9}";
                    Matcher matcher= Pattern.compile(validPhoneNumber).matcher(etLoginMobile.getText().toString().trim());
                    if(matcher.matches()){

                        POSTlogin(Login_URL);
                    }else{

                        MDToast.makeText(LoginActivity.this,"Enter Valid Mobile Number",MDToast.LENGTH_LONG,MDToast.TYPE_WARNING).show();

                    }
                }





            }
        });


        back_button=findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }
    public void POSTlogin(String url){
        dialog.show();
        dialog.setCancelable(false);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject =new JSONObject(response);
                    String Status=jsonObject.optString("status");
                    String data=jsonObject.optString("data");
                    String msg=jsonObject.optString("msg");
                    if(Status.equalsIgnoreCase("Success")){
                        MDToast.makeText(LoginActivity.this,msg,MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();

                        Timer timer=new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                Intent intent=new Intent(LoginActivity.this,OTPActivity.class);
                                dialog.dismiss();
                                dialog.setCancelable(true);
                                intent.putExtra("MobileNumber",etLoginMobile.getText().toString().trim());
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                            }
                        },1000);



                    }else {
                        MDToast.makeText(LoginActivity.this,msg,MDToast.LENGTH_LONG,MDToast.TYPE_WARNING).show();
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
                params2.put("mobile",etLoginMobile.getText().toString().trim());
                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {

                return "application/json";
            }
        };
        Volley.newRequestQueue(LoginActivity.this).add(request);
    }
}
