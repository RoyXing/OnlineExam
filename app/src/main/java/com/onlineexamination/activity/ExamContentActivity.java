package com.onlineexamination.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.onlineexamination.R;
import com.onlineexamination.adapter.ExamContentAdapter;
import com.onlineexamination.adapter.MenuTipAdapter;
import com.onlineexamination.fragment.ExamContentFragment;
import com.onlineexamination.utils.ToastUtils;
import com.onlineexamination.view.DepthPageTransformer;
import com.onlineexamination.view.FixedSpeedScroller;
import com.onlineexamination.view.TitleView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 庞品 on 2017/2/14.
 * 题目详情界面
 */

public class ExamContentActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private TitleView titleView;
    private ImageView last, next;
    private List<Fragment> fragmentList;
    private ExamContentAdapter adapter;
    private int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_content);
        initView();
        initData();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        titleView = (TitleView) findViewById(R.id.toolbar);
        last = (ImageView) findViewById(R.id.img_last);
        next = (ImageView) findViewById(R.id.img_next);
        titleView.setCustomTitle("计算机2级测试题");
        titleView.setLeftImageOnClickListener(this);
        titleView.isShowLeftImage(true);
        last.setOnClickListener(this);
        next.setOnClickListener(this);
        last.setVisibility(View.GONE);
        viewPager.setOnPageChangeListener(this);
    }

    /**
     * 控制viewpager的滑动速度
     */
    FixedSpeedScroller mScroller = null;
    private void setViewPagerScrollSpeed() {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            mScroller = new FixedSpeedScroller(viewPager.getContext(),new AccelerateInterpolator());
            field.set(viewPager, mScroller);
        } catch (Exception e) {
        }
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ExamContentFragment fragment = new ExamContentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        adapter = new ExamContentAdapter(getSupportFragmentManager(), fragmentList);
        //viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setAdapter(adapter);
        setViewPagerScrollSpeed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.img_last:
                if (position - 1 >= 0) {
                    change(position - 1);
                } else {
                    ToastUtils.toast("已经是第一题了！");
                }
                break;
            case R.id.img_next:
                if (position + 1 < fragmentList.size()) {
                    change(position + 1);
                } else {
                    ToastUtils.toast("已经是最后一题了！");
                }

                break;
        }
    }

    private void change(int position) {
        viewPager.setCurrentItem(position, true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
        if (position + 1 == fragmentList.size()) {
            next.setVisibility(View.GONE);
        } else {
            next.setVisibility(View.VISIBLE);
        }
        if (position == 0) {
            last.setVisibility(View.GONE);
        } else {
            last.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
