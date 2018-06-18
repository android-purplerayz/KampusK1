package com.example.android2.kampuskannekt;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android2.kampuskannekt.ParentEmailFragment.InboxFragment;
import com.example.android2.kampuskannekt.ParentEmailFragment.PEviewPagerAdapter;
import com.example.android2.kampuskannekt.ParentEmailFragment.SentFragment;

public class ParentEmailActivity extends AppCompatActivity {

    private Button btnPEmail1Back;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    PEviewPagerAdapter pEviewPagerAdapter;

    InboxFragment inboxFragment;
    SentFragment sentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_email);
        btnPEmail1Back=(Button)findViewById(R.id.btnPEmail1Back);

        btnPEmail1Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentEmailActivity.this,ParentDashboardActivity.class));
            }
        });
        tabLayout=(TabLayout)findViewById(R.id.tablayoutPEmail1);
        viewPager=(ViewPager)findViewById(R.id.viewpagerPEmail);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                tabLayout.getTabAt(position).select();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void setupViewPager(ViewPager viewPager)
    {
        pEviewPagerAdapter = new PEviewPagerAdapter(getSupportFragmentManager());
        inboxFragment=new InboxFragment();
        sentFragment=new SentFragment();
        pEviewPagerAdapter.addFragment(inboxFragment,"Inbox");
        pEviewPagerAdapter.addFragment(sentFragment,"Sent");

        viewPager.setAdapter(pEviewPagerAdapter);
    }
}
