package com.weextablayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gan on 2016/7/26.
 */
public class WeexVpTabLayout extends WeexTabLayout implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private float mStartValue;
    private int mStates = STOP;
    private int lastOffSet;
    private int lastPosition;
    private VpTabLayoutAnimation mVpTabLayoutAnimation;
    private List<ObjectAnimator> mObjectAnimList = new ArrayList<>();

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
    public void onPageScrolled(int position, final float positionOffset, int positionOffsetPixels) {
        if (mLp != null) {
            int endValue, startValue;
//            如果像右边滑动
            if (lastOffSet != 0 && positionOffsetPixels - lastOffSet > 0) {
                endValue = (int) (mStartValue + (position + 1) * mWidth);
                startValue = (int) (mStartValue + position * mWidth);
                mStates = MOVING;
            } else if (lastOffSet != 0 && positionOffsetPixels - lastOffSet < 0) { //如果向左滑动并且position不为0
                endValue = (int) (mStartValue + position * mWidth);
                startValue = (int) (mStartValue + (position + 1) * mWidth);
                mStates = MOVING;
            } else {
                mStates = STOP;
                endValue = mLp.leftMargin;
                startValue = mLp.leftMargin;
            }

            if (mStates == MOVING) {
                if (mVpTabLayoutAnimation != null) {
                    mVpTabLayoutAnimation.setVpTabLayoutAnimation(startValue, endValue, mLineView);
                    ValueAnimator anim = ValueAnimator.ofFloat(startValue, endValue); // 设置位移动画
                    anim.setDuration(200);
                    if (lastPosition - position != 0) {
                        anim.setCurrentPlayTime((long) (anim.getDuration()));
                    } else if (positionOffsetPixels - lastOffSet < 0) {
                        anim.setCurrentPlayTime((long) (anim.getDuration() * (1 - positionOffset)));
                    } else {
                        anim.setCurrentPlayTime((long) (anim.getDuration() * positionOffset));
                    }
                    anim.setTarget(mViewPager);
                    mLp.leftMargin = Math.round((Float) anim.getAnimatedValue());
                    mLineView.setLayoutParams(mLp);


                    if (mObjectAnimList.size() != 0) {
                        for (int i = 0; i < mObjectAnimList.size(); i++) {
                            ObjectAnimator objectAnimator = mObjectAnimList.get(i);
                            objectAnimator.setDuration(200);
                            if (lastPosition - position != 0) {
                                objectAnimator.setCurrentPlayTime((long) (objectAnimator.getDuration()));
                            } else if (positionOffsetPixels - lastOffSet < 0) {
                                objectAnimator.setCurrentPlayTime((long) (objectAnimator.getDuration() * (1 -
                                        positionOffset)));
                            } else {
                                objectAnimator.setCurrentPlayTime((long) (objectAnimator.getDuration() *
                                        positionOffset));
                            }
                            objectAnimator.setTarget(mViewPager);
                        }

                    }
                }
            }
            lastOffSet = positionOffsetPixels;
            lastPosition = position;

        }
    }


    public void addObjectAnim(ObjectAnimator objectAnim) {
        mObjectAnimList.add(objectAnim);
    }

    @Override
    public void onPageSelected(int position) {
        setClickIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setVpTabLayoutAnimation(VpTabLayoutAnimation vpTabLayoutAnimation) {
        mVpTabLayoutAnimation = vpTabLayoutAnimation;
    }

    public interface VpTabLayoutAnimation {
        void setVpTabLayoutAnimation(int startValue, int endValue, View view);
    }

}
