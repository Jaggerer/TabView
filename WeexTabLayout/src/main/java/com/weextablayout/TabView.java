package com.weextablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
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
    private Paint mSelectCellPaint;

    public int mTextSelectedColor = Color.parseColor("#00FF00"); //选中的文字颜色


    public int mBackgroudGray = (Color.parseColor("#1E2124")); //背景灰色
    public int mTextColor = (Color.parseColor("#DCDCDC")); //背景灰色

    private int mWidth;
    private int mHeight;

    private float mCellWidth;
    private int clickIndex = 0; //点击的item，默认为0
    private OnItemClickListener onItemClickListener;


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
        mBackGroudPaint.setColor(mBackgroudGray);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(24);
        mSelectCellPaint = new Paint();


    }

    private void initView() {
        mWidth = getResources().getDisplayMetrics().widthPixels;
//        mHeight = 80;

        mTextList.add("tab1");
        mTextList.add("tab2");
        mTextList.add("tab3");
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getResources().getDisplayMetrics().widthPixels;
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackgroud(canvas, mBackgroudGray);
        for (int i = 0; i < mTextList.size(); i++) {
            drawText(canvas, i, mTextList.get(i), mTextColor);
        }
        drawClickedText(canvas, clickIndex, mTextList.get(clickIndex), mTextSelectedColor);
    }

    /**
     * 画出背景
     */
    private void drawBackgroud(Canvas canvas, int color) {
        mBackGroudPaint.setColor(color);
        canvas.drawRect(0, 0, mWidth, mHeight, mBackGroudPaint);
    }

    /**
     * 画出文字
     */
    //如果是第一个item，index传0
    private void drawText(Canvas canvas, int index, String text, int color) {
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
        drawText(canvas, index, text, textColor);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getClickItem(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                if (onItemClickListener != null) {
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
        mCellWidth = mWidth / mTextList.size();
        invalidate();
    }



    public int getCount() {
        return mTextList.size();
    }


}


