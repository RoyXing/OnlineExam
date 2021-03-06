package com.onlineexamination.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineexamination.R;
import com.onlineexamination.adapter.CommunicationCommitAdapter;
import com.onlineexamination.bean.TopicBean;
import com.onlineexamination.bean.TopicCommitBean;
import com.onlineexamination.bean.TopicCommitLocalBean;
import com.onlineexamination.config.Config;
import com.onlineexamination.utils.JsonToBean;
import com.onlineexamination.utils.SharedPreferencesDB;
import com.onlineexamination.utils.TimeUtils;
import com.onlineexamination.utils.ToastUtils;
import com.onlineexamination.view.BaseActivity;
import com.onlineexamination.view.DialogView;
import com.onlineexamination.view.ExpandListview;
import com.onlineexamination.view.RoundImageView;
import com.onlineexamination.view.TitleView;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by roy on 2016/12/23.
 * 社区详情类
 */

public class CommunicationDatialsActivity extends BaseActivity implements View.OnClickListener {
    TitleView mToolbar;
    RoundImageView userImg;
    TextView userName, articleTitle, articleContent, articleCommit, articleTime;
    ExpandListview expandListview;
    ScrollView scrollView;
    EditText edCommitContent;
    Button btnCommit;
    View footview;
    CardView head;
    List<TopicCommitBean> list;
    CommunicationCommitAdapter adapter;
    TopicCommitLocalBean topicBean;
    DialogView dialogView;
    int page = 0;
    String commitNumber = "";//传给列表界面的评论数
    boolean write = false;//用判定是否是提交时刷新
    TopicBean topicBeanf;//上个界面传过来的数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_datials);
        initView();
        topicBean = new TopicCommitLocalBean();
        topicBean.setUserId(SharedPreferencesDB.getInstance().getString("userid", ""));
        topicBeanf = (TopicBean) getIntent().getBundleExtra("intent").getSerializable("bean");
        onEvent(topicBeanf);

    }

    private void initView() {
        mToolbar = (TitleView) findViewById(R.id.toolbar);
        mToolbar.setCustomTitle("帖子详情");
        mToolbar.isShowLeftImage(true);
        mToolbar.setLeftImageOnClickListener(this);
        articleCommit = (TextView) findViewById(R.id.article_commit);
        articleTitle = (TextView) findViewById(R.id.article_title);
        articleContent = (TextView) findViewById(R.id.article_content);
        userName = (TextView) findViewById(R.id.username);
        articleTime = (TextView) findViewById(R.id.article_time);
        userImg = (RoundImageView) findViewById(R.id.userimg);
        expandListview = (ExpandListview) findViewById(R.id.exlistview);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        edCommitContent = (EditText) findViewById(R.id.ed_commit);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        head = (CardView) findViewById(R.id.cardview);
        footview = LayoutInflater.from(this).inflate(R.layout.footview, null);
        head.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        list = new ArrayList<>();
        dialogView = new DialogView(this);
        adapter = new CommunicationCommitAdapter(this, 0, list);
        expandListview.addFooterView(footview);
        expandListview.setAdapter(adapter);
        expandListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                topicBean.setParentId(list.get(i).getCommentId());
                edCommitContent.setText("");
                edCommitContent.setHint("回复" + (i + 1) + "楼");
                InputMethodManager imm = (InputMethodManager) CommunicationDatialsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                edCommitContent.setFocusable(true);
            }
        });
        //加载更多
        footview.findViewById(R.id.cardview_foot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDatials();
            }
        });
    }

    /**
     * 列表界面传递过来的数据
     *
     * @param bean
     */
    public void onEvent(TopicBean bean) {
        if (bean != null) {
            userName.setText(bean.getUserName());
            //articleTitle.setText(bean.getBean().getTitle());
            articleTitle.setVisibility(View.GONE);
            articleContent.setText(bean.getContent());
            articleCommit.setText("评论数：" + bean.getCommentNum());
            articleTime.setText(TimeUtils.longToString(bean.getCreateTime()));
            Picasso.with(this).load(bean.getIcon()).placeholder(R.mipmap.applogo).error(R.mipmap.applogo).into(userImg);
            topicBean.setTopicId(bean.getTopicId());
            topicBean.setParentId("");
            getDatials();
        }
    }

    /**
     * 获取之前的评论
     */
    private void getDatials() {
        dialogView.show();
        dialogView.setMessage("评论数据加载中...");
        OkHttpUtils
                .post()
                .url(Config.TOPIC + topicBean.getTopicId())
                .addParams("page", page + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(CommunicationDatialsActivity.this, e.toString());
                        if (dialogView != null)
                            dialogView.close();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000 ) {
                                TopicBean topicBean = JsonToBean.getBean(jsonObject.optString("response").toString(), TopicBean.class);
                                //如果评论的数据多余当前显示的数据，只让上面的评论数+1
                                setHeadData(topicBean);
                                if (write) {//如果是评论时刷新数据
                                    write = false;
                                    if (list.size() >= 10) {//超过10条，不予理会
                                        expandListview.removeFooterView(footview);
                                        expandListview.addFooterView(footview);
                                    } else {//未满10条，刷新界面，显示那条数据
                                        listDataSet(topicBean);
                                    }
                                } else {//不是的时候走正常的路
                                    listDataSet(topicBean);
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

    /**
     * 列表数据更新
     *
     * @param result
     */
    private void listDataSet(TopicBean result) {
        expandListview.removeFooterView(footview);
        ToastUtils.show(CommunicationDatialsActivity.this, "评论加载成功");
        if (page == 0)
            list.clear();
        //去除重复的数据，在评论数据未满整数时
        if (list.size() > 0 && (page + 1) * 10 > list.size()) {
            int size = list.size() % 10;
            for (int i = size - 1; i >= 0; i--) {
                list.remove(page * 10 + i);
            }
        }
        if (result.getCommentList().size() >= 10) {
            expandListview.addFooterView(footview);
            page++;
        } else {
            expandListview.removeFooterView(footview);
        }
        list.addAll(result.getCommentList());
        adapter.notifyDataSetChanged();
        //if (page == 0)
        //scrollView.smoothScrollTo(0, 0);
    }

    //对头部的数据进行更新
    private void setHeadData(TopicBean result) {
        userName.setText(result.getUserName());
        //articleTitle.setText(bean.getBean().getTitle());
        articleTitle.setVisibility(View.GONE);
        articleContent.setText(result.getContent());
        articleCommit.setText("评论数：" + result.getCommentNum());
        articleTime.setText(TimeUtils.longToString(result.getCreateTime()));
        commitNumber = result.getCommentNum();
    }

    /**
     * 提交评论
     */
    private void writeCommit() {
        dialogView.show();
        dialogView.setMessage("评论发送中...");
        OkHttpUtils
                .post()
                .url(Config.ADD_COMMENT_TOPIC)
                .addParams("parentId", topicBean.getParentId())
                .addParams("content", topicBean.getContent())
                .addParams("topicId", topicBean.getTopicId())
                .addParams("userId", topicBean.getUserId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.show(CommunicationDatialsActivity.this, e.toString());
                        if (dialogView != null)
                            dialogView.close();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000) {
                                write = true;
                                if (list.size() < 10) {//小于10条的时候，直接刷新
                                    page = 0;
                                }
                                ToastUtils.show(CommunicationDatialsActivity.this, "评论成功");
                                getDatials();
                            } else {
                                ToastUtils.show(CommunicationDatialsActivity.this, "评论失败！");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (dialogView != null)
                            dialogView.close();
                    }

                });
    }

    @Override
    public void onBackPressed() {//返回时把当前的评论数返回，用于列表界面的刷新
        returnIntent();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                returnIntent();
                finish();
                break;
            case R.id.btn_commit:
                if (TextUtils.isEmpty(edCommitContent.getText().toString())) {
                    Toast.makeText(CommunicationDatialsActivity.this, "请输入评论的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                topicBean.setContent(edCommitContent.getText().toString());
                writeCommit();
                edCommitContent.setText("");
                edCommitContent.setHint("期待你的评论");
                topicBean.setParentId("");
                InputMethodManager imm = (InputMethodManager) CommunicationDatialsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edCommitContent.getWindowToken(), 0);
                break;
            case R.id.cardview:
                edCommitContent.setText("");
                edCommitContent.setHint("期待你的评论");
                topicBean.setParentId("");
                InputMethodManager immm = (InputMethodManager) CommunicationDatialsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                immm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                edCommitContent.setFocusable(true);
                break;
        }
    }

    /**
     * 评论次数数据返回
     */
    private void returnIntent() {
        Intent intent = new Intent();
        intent.putExtra("number", commitNumber);
        setResult(Activity.RESULT_OK, intent);
    }
}
