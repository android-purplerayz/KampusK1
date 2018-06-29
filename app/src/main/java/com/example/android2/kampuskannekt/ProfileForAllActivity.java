package com.example.android2.kampuskannekt;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.theartofdev.edmodo.cropper.CropImage;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.SharedPreferences;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import SchoolandUniversity.ServiceSchoolandUniActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import id.zelory.compressor.Compressor;

public class ProfileForAllActivity extends AppCompatActivity {

    private Button buttonback;
    private CircleImageView imageSave;
    private Button btnCapture, btnupdate;
    private EditText etProName, etProNumber, etProEmail;


    Uri photoURI;
    public static final int RESULT_GALLERY = 0;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_CAMERA = 0;
    private String ProfilePage = "http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/profileUpdate";
    private String ProfileImage = "http://srigopalgoswami.com/saheb_kk/kampus_konnekt_web/index.php/api/api/profileImageUpload";

    private SpotsDialog dialog;
    private String phone,uNAME,uEMAIL,uIMAGE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_for_all);
        buttonback = findViewById(R.id.buttonBack);
        etProName = findViewById(R.id.etProName);
        etProNumber = findViewById(R.id.etProNumber);
        etProNumber.setEnabled(false);
        etProNumber.setKeyListener(null);
        etProEmail = findViewById(R.id.etProEmail);
        btnCapture = findViewById(R.id.btnCapture);
        imageSave = findViewById(R.id.imageSave);
        btnupdate = findViewById(R.id.btnupdate);


        dialog = (SpotsDialog) new SpotsDialog.Builder().setContext(ProfileForAllActivity.this).setTheme(R.style.Custompropic).build();
        dialog.setTitle("Sending OTP");
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.dismiss();


        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

                //startActivity(new Intent(ProfileForAllActivity.this,OTPActivity.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        SharedPreferences Preferences =getSharedPreferences("DATA", Context.MODE_PRIVATE);
        phone=Preferences.getString("user_mobile","");
        uNAME=Preferences.getString("user_name","");
        uIMAGE=Preferences.getString("user_foto","");
        uEMAIL=Preferences.getString("user_email","");
        etProNumber.setText(phone);
        etProEmail.setText(uEMAIL);
        etProName.setText(uNAME);
        Glide.with(ProfileForAllActivity.this).load(uIMAGE)
                .into(imageSave);

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POSTprofile(ProfilePage);
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCamera(v);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.with(getApplicationContext()).pauseRequests();
    }

    private void displayCustomLauncher() {
        Log.e("TAG", "cam8");
        final BottomSheetDialog dialog = new BottomSheetDialog(ProfileForAllActivity.this);
        dialog.setContentView(R.layout.ss_image_chooser);
        dialog.show();

        ImageView img_camera = dialog.findViewById(R.id.img_camera);
        ImageView img_gallery = dialog.findViewById(R.id.img_gallery);

        img_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "cam9");
                dialog.dismiss();
                clickPicture();
            }
        });
        img_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_GALLERY);
            }
        });
    }

    private void clickPicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        "com.schoolerp.kampuskonnekt.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("TAG", "requestCode " + requestCode + " resultCode " + resultCode);

        //camera result
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            CropImage.activity(photoURI).setAspectRatio(1, 1)
                    .setMinCropResultSize(400, 400)
                    .start(this);


        }

        //gallery result
        if (requestCode == RESULT_GALLERY) {
            if (null != data) {
                Uri tempUri = data.getData();
                CropImage.activity(tempUri).setAspectRatio(1, 1)
                        .setMinCropResultSize(400, 400)
                        .start(this);
            }
        }

        //crop result
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (data != null) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (result != null) {
                    if (resultCode == RESULT_OK) {
                        Uri resultUri = result.getUri();
                        if (resultUri != null) {
                            populateImage(resultUri);
                        }
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                }
            }
        }
    }

    private void populateImage(Uri uri) {
        File file = Compressor.getDefault(this).compressToFile(new File(uri.getPath()));
        imageSave.setImageBitmap(Compressor.getDefault(this).compressToBitmap(new File(uri.getPath())));
        uploadFiles(file);
    }

    public void showCamera(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else {
            displayCustomLauncher();
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            alertPermission();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }

    private void alertPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.permission));
        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });
        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void uploadFiles(File file) {
        RequestParams params = new RequestParams();

        try {
            params.put("mobile",phone);
            params.put("userfile", file);
            params.put("extension", ".jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Log.d("TAG" , "url " + SharedPreference.BASE_URL+"update-image");
        // Log.d("TAG" , "param " + params);
        AsyncHttpClient client = new AsyncHttpClient(true,80,443);
        client.post(ProfileImage, params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("TAG", "suc2" + response);
                try {
                    //String status=response.getString("status");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TAG", "fail1 " + responseString);
                Log.d("TAG", "fail1 " + throwable);
            }

        });
    }

    public void POSTprofile(String url1){
        dialog.show();
        dialog.setCancelable(false);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    String Status=jsonObject.optString("status");
                    String msg=jsonObject.optString("msg");
                    String ImageUrl=jsonObject.optString("image_url");
                    Log.e("newres",response);
                    Log.e("newres",Status);
                    if (Status.equalsIgnoreCase("Success")){
                        MDToast.makeText(ProfileForAllActivity.this,msg,MDToast.TYPE_SUCCESS,MDToast.LENGTH_LONG).show();
                        JSONArray jsonArray =jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            String fname=jsonObject1.optString("fname");
                            String Email_id=jsonObject1.optString("email_id");
                            String Mobile=jsonObject1.optString("mobile");
                            String Foto=jsonObject1.optString("profile_img");

                            SharedPreferences sharedPreferences =getSharedPreferences("DATA", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor= sharedPreferences.edit();
                            editor.putString("user_mobile",Mobile);
                            editor.putString("user_foto",ImageUrl+Foto);
                            editor.putString("user_name",fname);
                            editor.putString("user_email",Email_id);
                            editor.commit();
                        }


                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                dialog.dismiss();
                                dialog.setCancelable(true);
                                Intent intent=new Intent(ProfileForAllActivity.this,ServicesNewActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        }, 1000);

                    }else{
                        MDToast.makeText(ProfileForAllActivity.this,msg,MDToast.TYPE_WARNING,MDToast.LENGTH_LONG).show();

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
                params2.put("name",etProName.getText().toString());
                params2.put("email",etProEmail.getText().toString().trim());
                params2.put("mobile",phone);



                return new JSONObject(params2).toString().getBytes();
            }
            @Override
            public String getBodyContentType() {

                return "application/json";
            }
        };
        Volley.newRequestQueue(ProfileForAllActivity.this).add(stringRequest);
    }

}








