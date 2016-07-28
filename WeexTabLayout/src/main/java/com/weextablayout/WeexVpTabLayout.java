package com.weextablayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by gan on 2016/7/26.
 */
public class WeexVpTabLayout extends WeexTabLayout implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private float mStartValue;
    private ValueAnimator anim;

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
        post(new Runnable() {
            @Override
            public void run() {
                mStartValue = mWidth / 2 - mLp.width / 2;
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mLp != null) {
            mLp.leftMargin = (int) (mStartValue + mWidth * positionOffset + position * mWidth);
            mLineView.setLayoutParams(mLp);
            invalidate();
        }
    }

    @Override
    public void onPageSelected(int position) {
        setClickIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setDefaultAnim(int index) {
        final float endValue = mWidth / 2 - mLineView.getWidth() / 2 + index * mWidth;
        anim = ValueAnimator.ofFloat(mLp.leftMargin, endValue);
//        anim.setCurrentFraction();
        anim.setDuration(200);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //动画执行时，将tablayout设置为不可点击
                mTabView.mClickable = false;
                mStates = MOVING;
                mLp.leftMargin = Math.round((Float) animation.getAnimatedValue());
                mLineView.setLayoutParams(mLp);
                Log.d("tagtagtag", "getAnimatedFraction" + animation.getAnimatedFraction());
                if ((Float) animation.getAnimatedValue() == endValue) {
                    mStates = STOP;
                    mTabView.mClickable = true;
                }
                invalidate();
            }
        });
        if (mStates == STOP) {
            anim.start();
        }
    }
}
