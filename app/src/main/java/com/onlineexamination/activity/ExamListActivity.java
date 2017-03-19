package com.onlineexamination.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.onlineexamination.R;
import com.onlineexamination.adapter.ExamTestNameAdapter;
import com.onlineexamination.bean.ExamQuestionBean;
import com.onlineexamination.bean.ExamTestNameBean;
import com.onlineexamination.bean.ExamTypeBean;
import com.onlineexamination.config.Config;
import com.onlineexamination.utils.JsonToBean;
import com.onlineexamination.utils.ListSave;
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
 * Created by 庞品 on 2017/3/4.
 */

public class ExamListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TitleView titleView;
    private EditText editText;
    private ImageView search;
    private String id;
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private List<ExamTestNameBean> list;
    private ExamTestNameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);
        initView();
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.et_search_content);
        search = (ImageView) findViewById(R.id.search);
        titleView = (TitleView) findViewById(R.id.toolbar);
        titleView.setCustomTitle(getIntent().getStringExtra("name"));
        id = getIntent().getStringExtra("id");
        titleView.isShowLeftImage(true);
        titleView.setLeftImageOnClickListener(this);
        listView = (ListView) findViewById(R.id.listview_exam_list);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        listView.setOnItemClickListener(this);
        search.setOnClickListener(this);
        list = new ArrayList<>();
        adapter = new ExamTestNameAdapter(this, 0, list);
        listView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getExamList();
            }
        });
        getExamList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.search:
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    getSearch(editText.getText().toString());
                } else {
                    ToastUtils.toast("输入不能为空");
                }
                break;
        }
    }

    /**
     * 获取当前分类的试卷列表
     */
    private void getExamList() {
        final DialogView dialogView = new DialogView(this);
        dialogView.show();
        dialogView.setMessage("试卷列表加载中...");
        OkHttpUtils
                .post()
                .url(Config.EXAM_LIST)
                .addParams("paperType_id", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(ExamListActivity.this, e.toString());
                        if (dialogView != null)
                            dialogView.close();
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000) {
                                List<ExamTestNameBean> examTypeBeanList = JsonToBean.getBeans(jsonObject.opt("response").toString(), ExamTestNameBean.class);
                                if (examTypeBeanList.size() > 0) {
                                    list.clear();
                                    list.addAll(examTypeBeanList);
                                    adapter.notifyDataSetChanged();
                                } else {

                                    final DialogNoData dialogNoData = new DialogNoData(ExamListActivity.this);
                                    dialogNoData.show();
                                    dialogNoData.setMessage("当前分类暂无试题");
                                    dialogNoData.textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            ExamListActivity.this.finish();
                                            dialogNoData.close();
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

    /**
     * 根据模糊搜索试卷
     */
    private void getSearch(String keyWord) {
        final DialogView dialogView = new DialogView(this);
        dialogView.show();
        dialogView.setMessage("数据搜索中...");
        OkHttpUtils
                .post()
                .url(Config.EXAM_SEARCH)
                .addParams("keyword", keyWord)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(ExamListActivity.this, e.toString());
                        if (dialogView != null)
                            dialogView.close();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000) {
                                List<ExamTestNameBean> examTypeBeanList = JsonToBean.getBeans(jsonObject.opt("response").toString(), ExamTestNameBean.class);
                                if (examTypeBeanList.size() > 0) {
                                    showSearch(examTypeBeanList);
                                } else {

                                    final DialogNoData dialogNoData = new DialogNoData(ExamListActivity.this);
                                    dialogNoData.show();
                                    dialogNoData.setMessage("未查询到结果");
                                    dialogNoData.textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogNoData.close();
                                        }
                                    });
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (dialogView != null)
                            dialogView.close();

                    }
                });
    }

    //加载试卷内容
    private void getExamContent(String id, final String name) {
        final DialogView dialogView = new DialogView(this);
        dialogView.show();
        dialogView.setMessage("试卷内容加载中，请稍等...");
        OkHttpUtils
                .post()
                .url(Config.EXAM_CONTENT)
                .addParams("paper_id", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(ExamListActivity.this, "服务器错误");
                        if (dialogView != null)
                            dialogView.close();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000) {
                                List<ExamQuestionBean> examTypeBeanList = JsonToBean.getBeans(jsonObject.opt("response").toString(), ExamQuestionBean.class);
                                if (examTypeBeanList.size() > 0) {
                                    ListSave.getListSave().getList().clear();
                                    ListSave.getListSave().setList(examTypeBeanList);
                                    Intent intent = new Intent(ExamListActivity.this, ExamContentActivity.class);
                                    intent.putExtra("name", name);
                                    startActivity(intent);
                                } else {

                                    final DialogNoData dialogNoData = new DialogNoData(ExamListActivity.this);
                                    dialogNoData.show();
                                    dialogNoData.setMessage("当前试卷还未添加题目");
                                    dialogNoData.textView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogNoData.close();
                                        }
                                    });
                                }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExamListActivity.this);
        builder.setMessage("即将开始答题").setTitle("考试提醒").setPositiveButton("开始", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ExamTestNameBean bean = list.get(position);
                //先加载考试内容
                getExamContent(bean.getId(), bean.getName());
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    private void showSearch(final List<ExamTestNameBean> result) {
        final AlertDialog.Builder builderr = new AlertDialog.Builder(ExamListActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_search, null);
        builderr.setTitle("搜索结果").setView(view).setPositiveButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ListView listView = (ListView) view.findViewById(R.id.listview_search);

        ExamTestNameAdapter adapter = new ExamTestNameAdapter(this, 0, result);
        listView.setAdapter(adapter);
        builderr.create().show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ExamListActivity.this);
                builder.setMessage("即将开始答题").setTitle("考试提醒").setPositiveButton("开始", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getExamContent(result.get(position).getId(), result.get(position).getName());
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });
    }
}
