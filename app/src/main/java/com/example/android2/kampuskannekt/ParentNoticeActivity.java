package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android2.kampuskannekt.ParentNoticeFragment.PNAdminFragment;
import com.example.android2.kampuskannekt.ParentNoticeFragment.PNAllFragment;
import com.example.android2.kampuskannekt.ParentNoticeFragment.PNStudentFragment;
import com.example.android2.kampuskannekt.ParentNoticeFragment.PNTeacherFragment;
import com.example.android2.kampuskannekt.ParentNoticeFragment.PNviewPagerAdapter;

public class ParentNoticeActivity extends AppCompatActivity {


    private TabLayout tabLayoutNotice;

    private Button btnPNoticeBack;

    //This is our viewPager
    private ViewPager viewPager;

    PNviewPagerAdapter adapter;

    PNAllFragment pnAllFragment;
    PNStudentFragment pnStudentFragment;
    PNTeacherFragment pnTeacherFragment;
    PNAdminFragment pnAdminFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_notice);

        btnPNoticeBack=(Button)findViewById(R.id.btnPNoticeBack);

        btnPNoticeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentNoticeActivity.this,ParentDashboardActivity.class));
            }
        });


        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpagerPNotice);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);

        //Initializing the tablayout
        tabLayoutNotice = (TabLayout) findViewById(R.id.tablayoutPNotice);

        tabLayoutNotice.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                tabLayoutNotice.getTabAt(position).select();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void setupViewPager(ViewPager viewPager)
    {
        adapter = new PNviewPagerAdapter(getSupportFragmentManager());
        pnAllFragment=new PNAllFragment();
        pnTeacherFragment=new PNTeacherFragment();
        pnAdminFragment=new PNAdminFragment();
        pnStudentFragment=new PNStudentFragment();

        adapter.addFragment(pnAllFragment,"All");
        adapter.addFragment(pnTeacherFragment,"Teacher");
        adapter.addFragment(pnAdminFragment,"Admin");
        adapter.addFragment(pnStudentFragment,"Student");
        viewPager.setAdapter(adapter);
    }
}
