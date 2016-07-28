package com.horustablayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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

//        //设置indicator
//        ImageView imageView = new ImageView(this);
//        imageView.setImageResource(R.drawable.line);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(120, 20);
//        lp.gravity = Gravity.BOTTOM;
//        imageView.setLayoutParams(lp);

        //设置tab名称的数组
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        mTbView.setTextList(list);

//        添加indicator
//        mTbView.addIndicator(imageView);

        mTbView.setOnTabClickListener(new WeexTabLayout.TabClickListener() {
            @Override
            public void onTabClick(int index) {
                Toast.makeText(MainActivity.this, "index --->" + index, Toast.LENGTH_SHORT).show();
            }
        });

        //添加动画
//        mTbView.setTabViewAnim(new WeexTabLayout.TabViewAnim() {
//            @Override
//            public void setTabViewAnim(final float startValue, final float endValue, final View view) {

//                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", endValue - startValue);
//                animator.setDuration(500);
//                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        //当动画开始时，让tablayout不能点击
//                        mTbView.setClickable(false);
//                        if ((Float) animation.getAnimatedValue() == (endValue - startValue)) {
//                            mTbView.setClickable(true);
//                        }
//                        view.invalidate();
//                    }
//                });
//                if (mTbView.getClickable()) {
//                    animator.start();
//                }

//            }
//        });


    }
}
