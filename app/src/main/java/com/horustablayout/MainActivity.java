package com.horustablayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private TabView mTbView;
    private int mStates = TabView.STOP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTbView = (TabView) findViewById(R.id.tv_view);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(50, 50));

        //添加indicator
        mTbView.addLineView(imageView);

        //添加动画
        mTbView.setTabViewAnim(new TabView.TabViewAnim() {
            @Override
            public void setTabViewAnim(float startValue, final float endValue, View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", endValue - startValue);
                mStates = TabView.MOVING;

                animator.setDuration(200);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mTbView.invalidate();
                    }
                });
                animator.start();
            }
        });

    }
}
