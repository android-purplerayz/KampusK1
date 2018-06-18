package teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android2.kampuskannekt.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class TeacherDashboard extends AppCompatActivity implements View.OnClickListener{

    BarChart barchart1, barchart2;
    Button btndashgotoMainmenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        setViews();

    }

    private void setViews(){
        barchart1 = findViewById(R.id.barchart);
        barchart2 = findViewById(R.id.barchart1);
        btndashgotoMainmenu = findViewById(R.id.btndashgotoMainmenu);

        setListeners();
    }

    private void setListeners(){
        btndashgotoMainmenu.setOnClickListener(this);

        setContent();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btndashgotoMainmenu:
                Intent i = new Intent(this, TeacherMainMenu.class);
                startActivity(i);
                break;
        }
    }

    private void setContent(){
        barchart1.addBar(new BarModel(2.3f, 0xFF123456));
        barchart1.addBar(new BarModel(2.f,  0xFF343456));
        barchart1.addBar(new BarModel(3.3f, 0xFF563456));
        barchart1.addBar(new BarModel(1.1f, 0xFF873F56));
        barchart1.addBar(new BarModel(2.7f, 0xFF56B7F1));
        barchart1.addBar(new BarModel(2.f,  0xFF343456));
        barchart1.addBar(new BarModel(0.4f, 0xFF1FF4AC));
        barchart1.addBar(new BarModel(4.f,  0xFF1BA4E6));
        barchart1.startAnimation();


        barchart2.addBar(new BarModel(2.3f, 0xFF123456));
        barchart2.addBar(new BarModel(2.f,  0xFF343456));
        barchart2.addBar(new BarModel(3.3f, 0xFF563456));
        barchart2.addBar(new BarModel(1.1f, 0xFF873F56));
        barchart2.addBar(new BarModel(2.7f, 0xFF56B7F1));
        barchart2.addBar(new BarModel(2.f,  0xFF343456));
        barchart2.addBar(new BarModel(0.4f, 0xFF1FF4AC));
        barchart2.addBar(new BarModel(4.f,  0xFF1BA4E6));
        barchart2.startAnimation();
    }
}
