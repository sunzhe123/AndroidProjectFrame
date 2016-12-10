package com.hither.androidframe.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hither.androidframe.R;
import com.hither.androidframe.project.welcome.widget.RotateDownPageTransformer;
import com.hither.androidframe.tools.SharedPrefUtils;
import com.hither.androidframe.tools.StatusBarUtil2;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity implements View.OnClickListener {
    private ViewPager mViewPager;
    private int[] mImgIds = new int[]{R.drawable.guide_1,
            R.drawable.guide_2, R.drawable.guide_3};
    private List<ImageView> mImageViews = new ArrayList<>();

    public void initStatusBar() {
        StatusBarUtil2.setTranslucent(this);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        setContentView(R.layout.activity_welcome);
        initView();
    }


    public void initView() {
       initData();
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mViewPager.setPageTransformer(true, new RotateDownPageTransformer());
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mImageViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }


            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                container.addView(mImageViews.get(position));
                return mImageViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {

                container.removeView(mImageViews.get(position));
            }
        };
        mViewPager.setAdapter(pagerAdapter);
    }

    private void initData() {
        for (int i = 0; i < mImgIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(mImgIds[i]);
            mImageViews.add(imageView);
            if (i == mImgIds.length - 1) {
                imageView.setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        SharedPrefUtils.putBoolean("isFirstEnter", false);
        Intent intent_main = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent_main);
        finish();
    }
}
