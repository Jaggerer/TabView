package com.weextablayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by gan on 2016/7/25.
 */
public class WeexTabLayout extends LinearLayout {
    TabView mTabView;
    View mLineView; //要加载的View
    LinearLayout mlltIndicator;
    float mWidth;
    LayoutParams mLp;
    public static final int STOP = 0;
    public static final int MOVING = 1;
    int mStates = STOP;
    TabViewAnim mTabViewAnim;
    TabClickListener mTabClickListener;
    private int underLineColor;
    private float underLineHeight;

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
            int dividerColor = a.getColor(R.styleable.WeexTabLayout_divider_color, Color.TRANSPARENT);
            underLineColor = a.getColor(R.styleable.WeexTabLayout_underline_color, Color.RED);
            float dividerWidth = a.getDimension(R.styleable.WeexTabLayout_divider_width, ScreenUtil.dip2px
                    (getContext(), 0));
            float dividerPadding = a.getDimension(R.styleable.WeexTabLayout_divider_padding, ScreenUtil.dip2px
                    (getContext(), 0));
            float paddingBottom = a.getDimension(R.styleable.WeexTabLayout_padding_bottom, ScreenUtil.dip2px
                    (getContext(), 0));
            underLineHeight = a.getDimension(R.styleable.WeexTabLayout_underline_height, ScreenUtil.dip2px
                    (getContext(), 2));

            mTabView.mTextColor = textColor;
            mTabView.mTextSelectedColor = textSelectColor;
            mTabView.mBackgroudColor = backgroundColor;
            mTabView.mTextSize = textSize;

            mTabView.mDividerColor = dividerColor;
            mTabView.mDividerWidth = dividerWidth;
            mTabView.mDividerPadding = dividerPadding;
            mTabView.mUnderLineColor = underLineColor;
            mTabView.mUnderLineHeight = underLineHeight;
            mTabView.mPaddingBottom = paddingBottom;
        } finally {
            a.recycle();
            mTabView.invalidate();
        }
    }


    private void setDefaultIndicator() {
        View view = new View(getContext());
        view.setBackgroundColor(underLineColor);
        LayoutParams layoutParams = new LayoutParams((int) mWidth, (int) underLineHeight);
        layoutParams.gravity = Gravity.BOTTOM;
        view.setLayoutParams(layoutParams);
        addIndicator(view);
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
                if (mTabClickListener != null) {
                    mTabClickListener.onTabClick(index);
                }
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

    /**
     * 传入要添加的view
     */
    public void addIndicator(View lineView) {
        this.mLineView = lineView;
        initLineView();
    }

    public void setClickIndex(int index) {
        mTabView.setClickIndex(index);
        if (mTabViewAnim == null) {
            setDefaultAnim(index);
        } else {
            mTabViewAnim.setTabViewAnim(mLp.leftMargin, mWidth / 2 - mLineView.getWidth() / 2 + index *
                    mWidth, mLineView);
        }

        mTabView.invalidate();
    }

    private void initLineView() {
        mLp = (LayoutParams) mLineView.getLayoutParams();
        mLp.leftMargin = (int) (mWidth / 2 - mLp.width / 2);
        mLineView.setLayoutParams(mLp);
        mlltIndicator.addView(mLineView);
        invalidate();
    }

    public void setTextList(final List<String> list) {
        post(new Runnable() {
            @Override
            public void run() {
                mTabView.setTextList(list);
                mWidth = mTabView.mCellWidth;
                if (mLineView == null) {
                    setDefaultIndicator();
                }
            }
        });
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
