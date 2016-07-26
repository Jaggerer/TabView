package com.weextablayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
    private TabClickListener mTabClickListener;

    public WeexTabLayout(Context context) {
        this(context, null);

    }

    public WeexTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeexTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setAttrs(attrs);
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WeexTabLayout);
        try {
            int textColor = a.getColor(R.styleable.WeexTabLayout_text_color, Color.parseColor("#DCDCDC"));
            int textSelectColor = a.getColor(R.styleable.WeexTabLayout_text_selected_color, Color.parseColor
                    ("#00FF00"));
            int backgroundColor = a.getColor(R.styleable.WeexTabLayout_backgroud_color, Color.parseColor("#1E2124"));
            float textSize = a.getDimension(R.styleable.WeexTabLayout_text_size, ScreenUtil.sp2px(getContext(), 12));
            mTabView.mTextColor = textColor;
            mTabView.mTextSelectedColor = textSelectColor;
            mTabView.mBackgroudColor = backgroundColor;
            mTabView.mTextSize = textSize;
        } finally {
            a.recycle();
            mTabView.invalidate();
        }
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
                mTabClickListener.onTabClick(index);
            }
        });
    }

    private void setDefaultAnim(int index) {
        final float endValue = mWidth / 2 - mLineView.getWidth() / 2 + index * mWidth;
        final ValueAnimator anim = ValueAnimator.ofFloat(mLp.leftMargin, endValue);

        anim.setDuration(200);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //动画执行时，将tablayout设置为不可点击
                mTabView.mClickable = false;
                mStates = MOVING;
                mLp.leftMargin = Math.round((Float) animation.getAnimatedValue());
                mLineView.setLayoutParams(mLp);
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

    public void setOnTabClickListener(TabClickListener tabClickListener) {
        mTabClickListener = tabClickListener;
    }

    public interface TabViewAnim {
        void setTabViewAnim(float startValue, float endValue, View view);
    }

    public interface TabClickListener {
        void onTabClick(int index);
    }

    public void setClickable(boolean clickable) {
        mTabView.mClickable = clickable;
    }

    public boolean getClickable() {
        return mTabView.mClickable;
    }

}
