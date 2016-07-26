package com.weextablayout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by gan on 2016/7/26.
 */
public class WeexVpTabLayout extends WeexTabLayout implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private float mStartValue;

    public WeexVpTabLayout(Context context) {
        this(context, null);
    }

    public WeexVpTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeexVpTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        setTabViewAnim(new TabViewAnim() {
            @Override
            public void setTabViewAnim(float startValue, float endValue, View view) {

            }
        });
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.addOnPageChangeListener(this);
        mStartValue = mWidth / 2 - mLp.width / 2;

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mLp.leftMargin = (int) (mStartValue + mWidth * positionOffset + position * mWidth);
        mLineView.setLayoutParams(mLp);
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        mTabView.setClickIndex(position);
        mTabView.invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
