package com.onlineexamination.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.onlineexamination.R;
import com.onlineexamination.view.BaseActivity;
import com.onlineexamination.view.TitleView;


/**
 * Created by 庞品 on 2017/1/23.
 */

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private TitleView toolbar;
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = (TitleView) findViewById(R.id.toolbar);
        toolbar.setCustomTitle("关于我们");
        toolbar.isShowLeftImage(true);
        toolbar.setLeftImageOnClickListener(this);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvContent.setText("放入相关简介即可");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
