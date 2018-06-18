package ngo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.android2.kampuskannekt.R;

public class NgoDetails extends AppCompatActivity {

    ImageView img_banner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_details);

        setViews();
        //http://digitallearning.eletsonline.com/wp-content/uploads/2016/11/pratham-450x270.jpg
    }

    private void setViews(){
        img_banner = findViewById(R.id.img_banner);



    }
}
