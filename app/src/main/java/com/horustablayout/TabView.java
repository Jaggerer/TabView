package com.horustablayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by gan on 2016/7/25.
 */
public class TabView extends LinearLayout {
    private MyTabLayout mMyTabLayout;
    private ImageView mIvIndicator;
    private float mWidth;
    private LayoutParams mLp;
    private static final int STOP = 0;
    private static final int MOVING = 1;
    private int mStates = STOP;

    public TabView(Context context) {
        this(context, null);

    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tab_view, this);
        mMyTabLayout = (MyTabLayout) findViewById(R.id.view_mytb);
        mIvIndicator = (ImageView) findViewById(R.id.iv_indicator);
        mWidth = getResources().getDisplayMetrics().widthPixels / mMyTabLayout.getCount();
        mIvIndicator.post(new Runnable() {
            @Override
            public void run() {
                mLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams
                        .WRAP_CONTENT);
                mLp.leftMargin = (int) (mWidth / 2 - mIvIndicator.getWidth() / 2);
                mIvIndicator.setLayoutParams(mLp);
                invalidate();
            }
        });


        mMyTabLayout.setOnItemClickListener(new MyTabLayout.OnItemClickListener() {
            @Override
            public void onItemClick(final int index) {
                final float endValue = mWidth / 2 - mIvIndicator.getWidth() / 2 + index * mWidth;
                final ValueAnimator anim = ValueAnimator.ofFloat(mLp.leftMargin, endValue);

                anim.setDuration(300);

                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mStates = MOVING;
                        mLp.leftMargin = Math.round((Float) animation.getAnimatedValue());
                        mIvIndicator.setLayoutParams(mLp);
                        if ((Float) animation.getAnimatedValue() == endValue) {
                            mStates = STOP;
                        }
                        invalidate();
                    }
                });
                if (mStates == STOP) {
                    anim.start();
                }
            }
        });
    }


}
