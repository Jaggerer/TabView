package com.horustablayout;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.weextablayout.WeexTabLayout;
import com.weextablayout.WeexVpTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainVpActivity extends AppCompatActivity {
    private WeexVpTabLayout mTlVp;
    private ViewPager mViewPager;

    private List<View> mViewList = new ArrayList<View>();
    private List<String> mNameList = new ArrayList<>();
    private View view1, view2, view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vp);
        mTlVp = (WeexVpTabLayout) findViewById(R.id.tl_vp);
        mViewPager = (ViewPager) findViewById(R.id.vp_pager);
        initTabLayout();
        initViewPager();
        mTlVp.setOnTabClickListener(new WeexTabLayout.TabClickListener() {
            @Override
            public void onTabClick(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
    }

    private void initTabLayout() {
        mNameList.add("page1");
        mNameList.add("page2");
        mNameList.add("page3");
        mTlVp.setTextList(mNameList);
        //设置indicator
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.line);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(120, 20);
        lp.gravity = Gravity.BOTTOM;
        imageView.setLayoutParams(lp);
        mTlVp.addLineView(imageView);
    }

    private void initViewPager() {
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.vp_layout1, null);
        view2 = inflater.inflate(R.layout.vp_layout2, null);
        view3 = inflater.inflate(R.layout.vp_layout3, null);

        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);

        mTlVp.setViewPager(mViewPager);
        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mViewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViewList.get(position));


                return mViewList.get(position);
            }
        };


        mViewPager.setAdapter(pagerAdapter);

    }
}

