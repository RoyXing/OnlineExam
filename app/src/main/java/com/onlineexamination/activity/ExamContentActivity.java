package com.onlineexamination.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.onlineexamination.R;
import com.onlineexamination.adapter.ExamContentAdapter;
import com.onlineexamination.bean.ExamQuestionBean;
import com.onlineexamination.config.Config;
import com.onlineexamination.fragment.ExamContentFragment;
import com.onlineexamination.utils.JsonToBean;
import com.onlineexamination.utils.ListSave;
import com.onlineexamination.utils.SharedPreferencesDB;
import com.onlineexamination.utils.TimeUtils;
import com.onlineexamination.utils.ToastUtils;
import com.onlineexamination.view.DepthPageTransformer;
import com.onlineexamination.view.DialogNoData;
import com.onlineexamination.view.DialogView;
import com.onlineexamination.view.TitleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 庞品 on 2017/2/14.
 * 题目详情界面
 */

public class ExamContentActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private TitleView titleView;
    private TextView texttime, textQuestion;

    private List<Fragment> fragmentList;
    private ExamContentAdapter adapter;
    private boolean start = true;//开始计时flag

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_content);
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        titleView = (TitleView) findViewById(R.id.toolbar);
        texttime = (TextView) findViewById(R.id.text_time);
        textQuestion = (TextView) findViewById(R.id.text_question);

        titleView.setCustomTitle(getIntent().getStringExtra("name"));
        titleView.setRightText("提交");
        titleView.isShowRightText(true);
        titleView.setRightTextOnClickListener(this);
        titleView.setLeftImageOnClickListener(this);
        titleView.isShowLeftImage(true);
        viewPager.setOnPageChangeListener(this);
        initData();
    }


    private void initData() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < ListSave.getListSave().getList().size(); i++) {
            ExamContentFragment fragment = new ExamContentFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        adapter = new ExamContentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setAdapter(adapter);
        textQuestion.setText("第1/" + fragmentList.size() + "题");
        //setViewPagerScrollSpeed();
        countTime();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                //finish();
                onBackPressed();
                break;
            case R.id.title_right_text:
                AlertDialog.Builder builder = new AlertDialog.Builder(ExamContentActivity.this);
                builder.setMessage("提交答案").setTitle("考试提醒").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        start = false;
                        getScore();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

                break;

        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(ExamContentActivity.this);
        builder.setMessage("结束答题").setTitle("考试提醒").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                start = false;
                getScore();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    private long time = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time++;
            texttime.setText("用时：" + TimeUtils.countTime(time));
        }
    };

    /**
     * 启动计时
     */
    private void countTime() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (start) {
                    try {
                        Thread.sleep(1000);
                        handler.sendEmptyMessage(1);
                    } catch (Exception e) {
                    }

                }
            }
        }.start();

    }

    //提交成绩
    private void setScore(int grade) {
        final DialogView dialogView = new DialogView(this);
        dialogView.show();
        dialogView.setMessage("试卷内容加载中，请稍等...");
        OkHttpUtils
                .post()
                .url(Config.SCORE)
                .addParams("paper_id", ListSave.getListSave().getList().get(0).getPaper_id())
                .addParams("student_id", SharedPreferencesDB.getInstance().getString("userid", ""))
                .addParams("score", grade + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.toast("服务器错误");
                        if (dialogView != null)
                            dialogView.close();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000) {
                                ToastUtils.toast("成绩上传完成");
                                finish();
                            } else {
                                ToastUtils.toast("成绩上传失败，请重试");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.toast("解析出错");
                        }

                        if (dialogView != null)
                            dialogView.close();

                    }
                });
    }

    /**
     * 计算成绩
     */
    private void getScore() {
        int score = 0;
        for (ExamQuestionBean bean : ListSave.getListSave().getList()) {
            if (bean.getSelectAnswer()!=null&&bean.getSelectAnswer().equals(bean.getAnswer())) {
                score += Integer.parseInt(bean.getGrade());
            }
        }
        setScore(score);
    }

    /**
     * 控制viewpager的滑动速度,当前未使用
     */
//    FixedSpeedScroller mScroller = null;
//    private void setViewPagerScrollSpeed() {
//        try {
//            Field field = ViewPager.class.getDeclaredField("mScroller");
//            field.setAccessible(true);
//            mScroller = new FixedSpeedScroller(viewPager.getContext(),new AccelerateInterpolator());
//            field.set(viewPager, mScroller);
//        } catch (Exception e) {
//        }
//    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        textQuestion.setText("第" + (position + 1) + "/" + fragmentList.size() + "题");

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
