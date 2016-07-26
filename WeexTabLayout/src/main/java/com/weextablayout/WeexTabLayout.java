package com.weextablayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by gan on 2016/7/25.
 */
public class WeexTabLayout extends LinearLayout {
    private TabView mTabView;
    private View mLineView; //要加载的View
    private LinearLayout mlltIndicator;
    private float mWidth;
    private LayoutParams mLp;
    public static final int STOP = 0;
    public static final int MOVING = 1;
    private int mStates = STOP;
    private TabViewAnim mTabViewAnim;

    public WeexTabLayout(Context context) {
        this(context, null);

    }

    public WeexTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeexTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tab_view, this);
        mTabView = (TabView) findViewById(R.id.view_mytb);
        mlltIndicator = (LinearLayout) findViewById(R.id.lyt_line);
        mTabView.setOnItemClickListener(new TabView.OnItemClickListener() {
            @Override
            public void onItemClick(int index) {
                if (mTabViewAnim == null) {
                    setDefaultAnim(index);
                } else {
                    mTabViewAnim.setTabViewAnim(mLp.leftMargin, mWidth / 2 - mLineView.getWidth() / 2 + index *
                            mWidth, mLineView);
                }
            }
        });
    }

    private void setDefaultAnim(int index) {
        final float endValue = mWidth / 2 - mLineView.getWidth() / 2 + index * mWidth;
        final ValueAnimator anim = ValueAnimator.ofFloat(mLp.leftMargin, endValue);

        anim.setDuration(150);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStates = MOVING;
                mLp.leftMargin = Math.round((Float) animation.getAnimatedValue());
                mLineView.setLayoutParams(mLp);
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

    /**
     * 传入要添加的view
     */
    public void addLineView(View lineView) {
        this.mLineView = lineView;
        initLineView();
    }


    private void initLineView() {
        mLp = (LayoutParams) mLineView.getLayoutParams();
        mLp.leftMargin = (int) (mWidth / 2 - mLp.width / 2);
        mLineView.setLayoutParams(mLp);
        mlltIndicator.addView(mLineView);
        invalidate();
    }

    public void setTextList(List<String> list) {
        mTabView.setTextList(list);
        mWidth = getResources().getDisplayMetrics().widthPixels / mTabView.getCount();
    }


    public void setTabViewAnim(TabViewAnim tabViewAnim) {
        mTabViewAnim = tabViewAnim;
    }

    public interface TabViewAnim {
        void setTabViewAnim(float startValue, float endValue, View view);
    }


}
