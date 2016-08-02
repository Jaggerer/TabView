package com.weextablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gan on 2016/7/25.
 */
public class TabView extends View {

    private List<String> mTextList = new ArrayList<>();

    private Paint mBackGroudPaint;
    private Paint mTextPaint;
    private Paint mDividerPaint;

    public int mTextSelectedColor = Color.parseColor("#00FF00"); //选中的文字颜色
    public int mBackgroudColor = Color.parseColor("#1E2124"); //背景灰色
    public int mTextColor = Color.parseColor("#DCDCDC"); //文字颜色
    public int mDividerColor = Color.parseColor("#E2E1E3"); //分割线颜色
    public int mUnderLineColor = Color.parseColor("#ffffff"); //指示器颜色

    public float mUnderLineHeight; //指示器高度
    public float mDividerWidth; //分割线的宽度
    public float mDividerPadding; //分割线的宽度
    public float mPaddingBottom; //距离底部的高度

    public float mTextSize;

    private int mWidth;
    private int mHeight;


    public float mCellWidth;
    private int clickIndex = 0; //点击的item，默认为0
    private OnItemClickListener onItemClickListener;
    public boolean mClickable = true;

    public TabView(Context context) {
        this(context, null);
    }


    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initView();
    }

    private void initPaint() {
        mBackGroudPaint = new Paint();
        mTextPaint = new Paint();
        mDividerPaint = new Paint();

    }

    private void initView() {
        mTextList.add("tab1");
        mTextList.add("tab2");
        mTextList.add("tab3");
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackgroud(canvas, mBackgroudColor);
        for (int i = 0; i < mTextList.size(); i++) {
            drawText(canvas, i, mTextList.get(i), mTextColor);
        }
        drawClickedText(canvas, clickIndex, mTextList.get(clickIndex), mTextSelectedColor);

        drawDivider(canvas, mDividerColor, mDividerWidth);
    }

    /**
     * 画出背景
     */
    private void drawBackgroud(Canvas canvas, int color) {
        mBackGroudPaint.setColor(color);
        canvas.drawRect(0, 0, mWidth, mHeight - mPaddingBottom, mBackGroudPaint);
    }

    /**
     * 画出文字
     */
    //如果是第一个item，index传0
    private void drawText(Canvas canvas, int index, String text, int color) {
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(color);
        float cellX = mCellWidth * index + (mCellWidth - mTextPaint.measureText(text)) / 2;
        float cellY = ((canvas.getHeight() / 2) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2));
        canvas.drawText(text, cellX, cellY, mTextPaint);
    }

    /**
     * 画出选中的背景和文字
     */
    //如果是第一个item，则index传0
    private void drawClickedText(Canvas canvas, int index, String text, int textColor) {
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(mTextSize);
        drawText(canvas, index, text, textColor);
    }

    /**
     * 画出中间分割线
     */
    private void drawDivider(Canvas canvas, int dividerColor, float width) {
        mDividerPaint.setColor(dividerColor);
        mDividerPaint.setStrokeWidth(width);
        for (int i = 0; i < mTextList.size() - 1; i++) {
            canvas.drawLine((i + 1) * mCellWidth, 0 + mDividerPadding, (i + 1) * mCellWidth, mHeight -
                    mDividerPadding, mDividerPaint);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mClickable) {
                    getClickItem(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:

                if (onItemClickListener != null && mClickable) {
                    onItemClickListener.onItemClick(clickIndex);
                }
                break;
        }
        return true;
    }

    private void getClickItem(float x, float y) {
        //如果点击了第一个item，那么返回的是0
        clickIndex = (int) Math.floor(x / mCellWidth);
        invalidate();
    }

    //给控件设置监听事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int index);
    }

    public void setTextList(List<String> textList) {
        mTextList.clear();
        mTextList.addAll(textList);
    }

    public void measureCellWidth() {
        mCellWidth = mWidth / mTextList.size();
        invalidate();
    }


    public int getCount() {
        return mTextList.size();
    }

    public void setClickIndex(int index) {
        clickIndex = index;
    }

}


