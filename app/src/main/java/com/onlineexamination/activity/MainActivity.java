package com.onlineexamination.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.view.menu.MenuAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlineexamination.R;
import com.onlineexamination.adapter.MenuTipAdapter;
import com.onlineexamination.fragment.CommunicationFragment;
import com.onlineexamination.fragment.ExamFragment;
import com.onlineexamination.fragment.PersonFragment;
import com.onlineexamination.view.TitleView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener ,ViewPager.OnPageChangeListener{
    private LinearLayout lineExamination, lineCommunication, linePerson;
    private TextView textExamination, textCommunication, textPerson;
    private ImageView imgExamination, imgCommunication, imgPerson;
    private ViewPager viewPager;
    private TitleView titleView;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 控件初始化
     */
    private void initView() {
        lineExamination = (LinearLayout) findViewById(R.id.line_examination);
        lineCommunication = (LinearLayout) findViewById(R.id.line_communication);
        linePerson = (LinearLayout) findViewById(R.id.line_person);
        textExamination = (TextView) findViewById(R.id.text_examination);
        textCommunication = (TextView) findViewById(R.id.text_communication);
        textPerson = (TextView) findViewById(R.id.text_person);
        imgExamination = (ImageView) findViewById(R.id.img_examination);
        imgCommunication = (ImageView) findViewById(R.id.img_communication);
        imgPerson = (ImageView) findViewById(R.id.img_person);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        titleView = (TitleView) findViewById(R.id.toolbar);
        titleView.setCustomTitle("在线考试");
        lineExamination.setOnClickListener(this);
        lineCommunication.setOnClickListener(this);
        linePerson.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);
        initFragment();
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new ExamFragment());
        fragmentList.add(new CommunicationFragment());
        fragmentList.add(new PersonFragment());
        MenuTipAdapter adapter = new MenuTipAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
    }

    private void change(int positon) {
        viewPager.setCurrentItem(positon, false);
    }

    @Override
    public void onClick(View v) {
        res();
        switch (v.getId()) {
            case R.id.line_examination:
                change(0);
                titleView.setCustomTitle("在线考试");
                textExamination.setTextColor(getResources().getColor(R.color.app_color));
                imgExamination.setImageResource(R.mipmap.write_press);
                break;
            case R.id.line_communication:
                change(1);
                titleView.setCustomTitle("社区");
                textCommunication.setTextColor(getResources().getColor(R.color.app_color));
                imgCommunication.setImageResource(R.mipmap.communication_press);
                break;
            case R.id.line_person:
                change(2);
                titleView.setCustomTitle("个人中心");
                textPerson.setTextColor(getResources().getColor(R.color.app_color));
                imgPerson.setImageResource(R.mipmap.person_press);
                break;
        }
    }

    private void res() {
        textExamination.setTextColor(getResources().getColor(R.color.menu_tip_text_color));
        textCommunication.setTextColor(getResources().getColor(R.color.menu_tip_text_color));
        textPerson.setTextColor(getResources().getColor(R.color.menu_tip_text_color));
        imgExamination.setImageResource(R.mipmap.write_false);
        imgCommunication.setImageResource(R.mipmap.communication_false);
        imgPerson.setImageResource(R.mipmap.person_false);
    }

    @Override
    public void onPageSelected(int position) {
        res();
        switch (position) {
            case 0:
                titleView.setCustomTitle("在线考试");
                textExamination.setTextColor(getResources().getColor(R.color.app_color));
                imgExamination.setImageResource(R.mipmap.write_press);
                break;
            case 1:
                titleView.setCustomTitle("社区");
                textCommunication.setTextColor(getResources().getColor(R.color.app_color));
                imgCommunication.setImageResource(R.mipmap.communication_press);
                break;
            case 2:
                titleView.setCustomTitle("个人中心");
                textPerson.setTextColor(getResources().getColor(R.color.app_color));
                imgPerson.setImageResource(R.mipmap.person_press);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
}
