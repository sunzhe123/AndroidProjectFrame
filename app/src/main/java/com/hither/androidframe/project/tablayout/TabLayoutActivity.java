package com.hither.androidframe.project.tablayout;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hither.androidframe.R;
import com.hither.androidframe.project.tablayout.adapter.PageAdapter;
import com.hither.androidframe.project.tablayout.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        viewPager = $(R.id.viewPagerID);
        tabLayout = $(R.id.tabLayoutID);
        list = new ArrayList<>();
        TabLayout.Tab tab1 = tabLayout.newTab();
        TabLayout.Tab tab2 = tabLayout.newTab();
        TabLayout.Tab tab3 = tabLayout.newTab();
        TabLayout.Tab tab4 = tabLayout.newTab();
        tab1.setText("标签一").setIcon(R.drawable.tablayout_discover_bg);
        tab2.setText("标签二").setIcon(R.drawable.tablayout_relation_bg);
        tab3.setText("标签三").setIcon(R.drawable.tablayout_strategy_bg);
        tab4.setText("标签四").setIcon(R.drawable.tablayout_person_bg);
        tabLayout.addTab(tab1);
        tabLayout.addTab(tab2);
        tabLayout.addTab(tab3);
        tabLayout.addTab(tab4);
        PageFragment pageFragment1 = PageFragment.newInstance(1);
        PageFragment pageFragment2 = PageFragment.newInstance(2);
        PageFragment pageFragment3 = PageFragment.newInstance(3);
        PageFragment pageFragment4 = PageFragment.newInstance(4);
        list.add(pageFragment1);
        list.add(pageFragment2);
        list.add(pageFragment3);
        list.add(pageFragment4);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }
}
