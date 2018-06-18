package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android2.kampuskannekt.ParenMsgFragment.AdministrationFragment;
import com.example.android2.kampuskannekt.ParenMsgFragment.PMviewPagerAdapter;
import com.example.android2.kampuskannekt.ParenMsgFragment.RecentFragment;
import com.example.android2.kampuskannekt.ParenMsgFragment.TeacherFragment;

public class ParrentMessageActivity extends AppCompatActivity {


    private Button btnBackMSg;
    private TabLayout tabLayout1;

    //This is our viewPager
    private ViewPager viewPager;

    PMviewPagerAdapter adapter;

    //Fragments

    RecentFragment recentFragment;
    TeacherFragment teacherFragment;
    AdministrationFragment administrationFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parrent_message);
        btnBackMSg=(Button)findViewById(R.id.btnBackMSg);

        btnBackMSg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParrentMessageActivity.this,ParentDashboardActivity.class));
            }
        });

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        //Initializing the tablayout
        tabLayout1 = (TabLayout) findViewById(R.id.tablayout1);

        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout1.getTabAt(position).select();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void setupViewPager(ViewPager viewPager)
    {
        adapter = new PMviewPagerAdapter(getSupportFragmentManager());
        recentFragment=new RecentFragment();
        teacherFragment=new TeacherFragment();
        administrationFragment=new AdministrationFragment();
        adapter.addFragment(recentFragment,"Recent");
        adapter.addFragment(teacherFragment,"Teacher");
        adapter.addFragment(administrationFragment,"Admin");
        viewPager.setAdapter(adapter);
    }
}
