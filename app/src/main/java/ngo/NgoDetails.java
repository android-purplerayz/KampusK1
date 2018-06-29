package ngo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android2.kampuskannekt.R;

public class NgoDetails extends AppCompatActivity {

    ImageView img_banner;
    ModelNgoData mData;
    TextView tv_left, tv_desc, tv_address, tv_call, tv_msg;
    Toolbar toolbar_add_cat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_details);
        mData = (ModelNgoData) getIntent().getSerializableExtra("MyClass");
        Log.e("TAG" ,"oo "+ mData.getNgo_address());
        setViews();
    }

    private void setViews(){
        toolbar_add_cat = findViewById(R.id.toolbar_add_cat);
        img_banner = findViewById(R.id.img_banner);
        tv_left = toolbar_add_cat.findViewById(R.id.tv_left);
        tv_desc = findViewById(R.id.tv_desc);
        tv_address = findViewById(R.id.tv_address);
        tv_call = findViewById(R.id.tv_call);
        tv_msg = findViewById(R.id.tv_msg);

        setContents();
    }

    private void setContents(){
        tv_left.setText(mData.getNgo_title()+"");
        String imglink=mData.getNgo_img();
        Glide.with(this).load(imglink).into(img_banner);
        tv_desc.setText(mData.getNgo_des());
        tv_address.setText(mData.getNgo_address()+" "+mData.getNgo_address2());
        tv_call.setText(mData.getNgo_phone());
        tv_msg.setText(mData.getNgo_email_address());
    }
}
