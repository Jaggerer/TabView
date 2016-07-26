package com.horustablayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.weextablayout.WeexTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private WeexTabLayout mTbView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTbView = (WeexTabLayout) findViewById(R.id.tv_view);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        mTbView.setTextList(list);
        //添加indicator
        mTbView.addLineView(imageView);

        //添加动画
//        mTbView.setTabViewAnim(new WeexTabLayout.TabViewAnim() {
//            @Override
//            public void setTabViewAnim(float startValue, final float endValue, final View view) {
//
//                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", endValue - startValue);
//                animator.setDuration(200);
//                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        view.invalidate();
//                    }
//                });
//                animator.start();
//
//            }
//        });

    }
}
