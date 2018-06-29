package com.example.android2.kampuskannekt.education_loan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class EducationalLoan extends AppCompatActivity {
    RecyclerView recyclerview;
    ArrayList<ModelEducationData> arr_data;
    ArrayList<ModelEducationBank> arr_bank;
    ArrayList<ModelEducationType> arr_loan_type;
    ProgressBar progress_bar;
    ImageView img_filter;
    Spinner spinner1,spinner2;
    String loan_id , bank_id, loan_amt;
    EditText editext;
    EducationLoanAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.educational_loan);

        recyclerview=findViewById(R.id.recyclerview);
        arr_data = new ArrayList<>();
        arr_bank = new ArrayList<>();
        arr_loan_type = new ArrayList<>();
        progress_bar = findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.GONE);
        img_filter = findViewById(R.id.img_filter);


        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureialog();
                populateSpinner1();
                populateSpinner2();
            }
        });

        getEducationLoan();


    }

    private void showPictureialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.filter_education, null);
        spinner1 = alertLayout.findViewById(R.id.spinner1);
        spinner2 = alertLayout.findViewById(R.id.spinner2);
        editext = alertLayout.findViewById(R.id.spinner3);
        final Button btn_search = alertLayout.findViewById(R.id.btn_search);
        AlertDialog.Builder alert = new AlertDialog.Builder(EducationalLoan.this);
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        dialog.show();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loan_amt = editext.getText().toString().trim();
                dialog.dismiss();
                getFilter();

            }
        });


    }

    private void isConnected() {
        try{

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void getEducationLoan(){
        arr_data.clear();
        arr_bank.clear();
        arr_loan_type.clear();

        progress_bar.setVisibility(View.VISIBLE);
        String url = SharedPreferences.BASE_URL +"getEducationLoan";
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
        Volley.newRequestQueue(EducationalLoan.this).add(stringRequest);

    }


    private void parseJson(JSONObject response){
        progress_bar.setVisibility(View.GONE);
        String success = response.optString("status");
        String msg = response.optString("msg");
        String image_url = response.optString("image_url");

        if(success.equalsIgnoreCase("Success")){
            JSONArray arr_jsondata = response.optJSONArray("data");
            JSONArray arr_json_bank = response.optJSONArray("bank");
            JSONArray arr_json_loan_type = response.optJSONArray("loan_type");

            for(int i=0; i<arr_jsondata.length(); i++){
                JSONObject object = arr_jsondata.optJSONObject(i);
                if(object!=null) {
                    ModelEducationData data = new ModelEducationData();
                    data.setEdu_loan_id(object.optString("edu_loan_id"));
                    data.setBank_id(object.optString("bank_id"));
                    data.setLoan_type_id(object.optString("loan_type_id"));
                    data.setLoan_title(object.optString("loan_title"));
                    data.setLoan_des(object.optString("loan_des"));
                    data.setLoan_amt(object.optString("loan_amt"));
                    data.setInterest_amt(object.optString("interest_amt"));
                    data.setProcess(object.optString("process"));
                    data.setUrl(object.optString("url"));
                    data.setBank_img(image_url+object.optString("bank_img"));
                    data.setBank_name(object.optString("bank_name"));
                    data.setLoan_name(object.optString("loan_name"));
                    arr_data.add(data);
                }
            }

            for(int i=0; i<arr_json_bank.length(); i++){
                JSONObject object = arr_json_bank.optJSONObject(i);
                if(object!=null) {
                    ModelEducationBank data = new ModelEducationBank();
                    data.setBank_id(object.optString("bank_id"));
                    data.setBank_name(object.optString("bank_name"));
                    data.setStatus(object.optString("status"));
                    arr_bank.add(data);
                }
            }

            for(int i=0; i<arr_json_loan_type.length(); i++){
                JSONObject object = arr_json_loan_type.optJSONObject(i);
                if(object!=null) {
                    ModelEducationType data = new ModelEducationType();
                    data.setLoan_type_id(object.optString("loan_type_id"));
                    data.setLoan_name(object.optString("loan_name"));
                    data.setStatus(object.optString("status"));

                    arr_loan_type.add(data);
                }
            }

            DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL);
            recyclerview.addItemDecoration(itemDecorator);
            itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.rc_divider_white));
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            adapter = new EducationLoanAdapter(this, arr_data);
            recyclerview.setAdapter(adapter);


            //populateSpinner2();

        }else {

        }
    }

    private void populateSpinner1(){
        List<String> locationList = new ArrayList<>();
        locationList.add("Select Bank");
        for(int i=0;i<arr_bank.size();i++){
            locationList.add(arr_bank.get(i).getBank_name());
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
                    bank_id = arr_bank.get(position-1).getBank_id();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void populateSpinner2(){
        List<String> locationList = new ArrayList<>();
        locationList.add("Select Loan Type");
        for(int i=0;i<arr_loan_type.size();i++){
            locationList.add(arr_loan_type.get(i).getLoan_name());
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
                    loan_id = arr_loan_type.get(position-1).getLoan_type_id();
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
        String url =SharedPreferences.BASE_URL +"getEducationLoanSearch";
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
                params2.put("bank", bank_id);
                params2.put("loan_type", loan_id);
                params2.put("loan_amount", loan_amt);
                return new JSONObject(params2).toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        Volley.newRequestQueue(EducationalLoan.this).add(stringRequest);
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
                    ModelEducationData data = new ModelEducationData();
                    data.setEdu_loan_id(object.optString("edu_loan_id"));
                    data.setBank_id(object.optString("bank_id"));
                    data.setLoan_type_id(object.optString("loan_type_id"));
                    data.setLoan_title(object.optString("loan_title"));
                    data.setLoan_des(object.optString("loan_des"));
                    data.setLoan_amt(object.optString("loan_amt"));
                    data.setInterest_amt(object.optString("interest_amt"));
                    data.setProcess(object.optString("process"));
                    data.setUrl(object.optString("url"));
                    data.setBank_img(image_url+object.optString("bank_img"));
                    data.setBank_name(object.optString("bank_name"));
                    data.setLoan_name(object.optString("loan_name"));
                    arr_data.add(data);
                }
            }

            DividerItemDecoration itemDecorator = new DividerItemDecoration(recyclerview.getContext(), DividerItemDecoration.VERTICAL);
            recyclerview.addItemDecoration(itemDecorator);
            itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.rc_divider_white));
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            adapter = new EducationLoanAdapter(this, arr_data);
            recyclerview.setAdapter(adapter);

        }else {
            arr_data.clear();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();


    }
}
