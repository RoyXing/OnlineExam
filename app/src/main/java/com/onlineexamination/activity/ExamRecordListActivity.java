package com.onlineexamination.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.onlineexamination.R;
import com.onlineexamination.adapter.ExamRecordAdapter;
import com.onlineexamination.bean.ExamRecordBean;
import com.onlineexamination.bean.ExamTestNameBean;
import com.onlineexamination.config.Config;
import com.onlineexamination.utils.JsonToBean;
import com.onlineexamination.utils.SharedPreferencesDB;
import com.onlineexamination.utils.ToastUtils;
import com.onlineexamination.view.BaseActivity;
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
 * Created by 庞品 on 2017/3/8.
 */

public class ExamRecordListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TitleView toolbar;
    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private List<ExamRecordBean> lists;
    private ExamRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_record_list);
        initView();
    }

    private void initView() {
        toolbar = (TitleView) findViewById(R.id.toolbar);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        listView = (ListView) findViewById(R.id.listview_exam_record);
        toolbar.setCustomTitle("考试成绩");
        toolbar.isShowLeftImage(true);
        toolbar.setLeftImageOnClickListener(this);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecordExamList();
            }
        });
        lists = new ArrayList<>();
        adapter = new ExamRecordAdapter(this, 0, lists);
        listView.setAdapter(adapter);
        getRecordExamList();
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }

    /**
     * 获取当前分类的试卷列表
     */
    private void getRecordExamList() {
        final DialogView dialogView = new DialogView(this);
        dialogView.show();
        dialogView.setMessage("试卷列表加载中...");
        OkHttpUtils
                .post()
                .url(Config.ACHIEVEMEN)
                .addParams("student_id", SharedPreferencesDB.getInstance().getString("userid", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.toast(e.toString());
                        if (dialogView != null)
                            dialogView.close();
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000) {
                                List<ExamRecordBean> list = JsonToBean.getBeans(jsonObject.optString("response"), ExamRecordBean.class);
                                if (list.size() > 0) {
                                    lists.clear();
                                    lists.addAll(list);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    final DialogNoData dialogNoData = new DialogNoData(ExamRecordListActivity.this);
                                    dialogNoData.show();
                                    dialogNoData.setMessage("你还没参加过考试哦");
                                    dialogNoData.textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogNoData.dismiss();
                                        }
                                    });
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (dialogView != null)
                            dialogView.close();
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //ToastUtils.toast(lists.get(position).getPaper_name());
    }
}
