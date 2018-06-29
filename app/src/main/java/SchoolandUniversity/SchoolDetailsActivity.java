package SchoolandUniversity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android2.kampuskannekt.R;
import com.example.android2.kampuskannekt.scholership.ScholerShipDetailsActivity;

public class SchoolDetailsActivity extends AppCompatActivity {

    private ImageView imageSchoolnUni;
    private TextView tv_desc,tv_address,tvPrinciN,tv_call,tv_stage,tvAG,tv_titleScho;
    private Button btnbacktoSchonUni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_details);
        btnbacktoSchonUni=findViewById(R.id.btnbacktoSchonUni);
        imageSchoolnUni=findViewById(R.id.imageSchoolnUni);
        tv_desc=findViewById(R.id.tv_desc);
        tv_address=findViewById(R.id.tv_address);
        tvPrinciN=findViewById(R.id.tvPrinciN);
        tv_call=findViewById(R.id.tv_call);
        tv_stage=findViewById(R.id.tv_stage);
        tvAG=findViewById(R.id.tvAG);
        tv_titleScho=findViewById(R.id.tv_titleScho);


        String Scho_IMAGE=getIntent().getExtras().getString("Scho_Image");
        String Scho_Title=getIntent().getExtras().getString("Scho_Title");
        String Scho_LDES=getIntent().getExtras().getString("Scho_LDes");
        String Scho_SDES=getIntent().getExtras().getString("Scho_SDes");
        String Scho_ADD=getIntent().getExtras().getString("Scho_Add");
        String Scho_PrinC=getIntent().getExtras().getString("Scho_PrinC");
        String Scho_AgeG=getIntent().getExtras().getString("Scho_AgeG");
        String Scho_Stage=getIntent().getExtras().getString("Scho_Stage");
        String Scho_PHONE=getIntent().getExtras().getString("Scho_Phone");

        Glide.with(SchoolDetailsActivity.this).load(Scho_IMAGE)
                .into(imageSchoolnUni);
        tv_desc.setText(Scho_SDES+" \n "+ Scho_LDES );
        tv_address.setText(Scho_ADD);
        tvPrinciN.setText(Scho_PrinC);
        tvAG.setText(Scho_AgeG);
        tv_stage.setText(Scho_Stage);
        tv_call.setText(Scho_PHONE);
        tv_titleScho.setText(Scho_Title);

        btnbacktoSchonUni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchoolDetailsActivity.this,ServiceSchoolandUniActivity.class));
            }
        });
    }
}
