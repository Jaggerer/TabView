package com.horustablayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weextablayout.WeexTabLayout;
import com.weextablayout.WeexVpTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gan on 2016/8/2.
 */
public class TestFragment extends Fragment {
    private WeexTabLayout mTab;
    private WeexVpTabLayout mTlVp;
    private ViewPager mViewPager;
    private List<View> mViewList = new ArrayList<View>();
    private List<String> mNameList = new ArrayList<>();
    private View view1, view2, view3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTab = (WeexTabLayout) view.findViewById(R.id.tv_view);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        mTab.setTextList(list);


        mTlVp = (WeexVpTabLayout) view.findViewById(R.id.tl_vp);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_pager);
        initTabLayout();
        initViewPager();
        mTlVp.setOnTabClickListener(new WeexTabLayout.TabClickListener() {
            @Override
            public void onTabClick(int index) {
                mViewPager.setCurrentItem(index);
            }
        });
        return view;
    }

    private void initTabLayout() {
        mNameList.add("page1");
        mNameList.add("page2");
        mNameList.add("page3");
        mTlVp.setTextList(mNameList);
//        //设置indicator
//        ImageView imageView = new ImageView(this);
//        imageView.setImageResource(R.drawable.line);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(120, 20);
//        lp.gravity = Gravity.BOTTOM;
//        imageView.setLayoutParams(lp);
//        mTlVp.addIndicator(imageView);
    }

    private void initViewPager() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view1 = inflater.inflate(R.layout.vp_layout1, null);
        view2 = inflater.inflate(R.layout.vp_layout2, null);
        view3 = inflater.inflate(R.layout.vp_layout3, null);

        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);

        mTlVp.setViewPager(mViewPager);

        mTlVp.setVpTabLayoutAnimation(new WeexVpTabLayout.VpTabLayoutAnimation() {
            @Override
            public void setVpTabLayoutAnimation(int startValue, int endValue, View view) {
                ValueAnimator animator = ValueAnimator.ofFloat(startValue, endValue); // 设置位移动画
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.5f,1.0f);
                mTlVp.setAnim(animator);
                mTlVp.setObjectAnim(objectAnimator);
            }
        });

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mViewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViewList.get(position));


                return mViewList.get(position);
            }
        };

        mViewPager.setAdapter(pagerAdapter);

    }
}
