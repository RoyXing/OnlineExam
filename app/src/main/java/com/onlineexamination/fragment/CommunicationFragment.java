package com.onlineexamination.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.onlineexamination.R;
import com.onlineexamination.activity.CommunicationDatialsActivity;
import com.onlineexamination.activity.WriteCommunicationActivity;
import com.onlineexamination.adapter.CommunicationAdapter;
import com.onlineexamination.bean.TopicBean;
import com.onlineexamination.config.Config;
import com.onlineexamination.utils.JsonToBean;
import com.onlineexamination.utils.SharedPreferencesDB;
import com.onlineexamination.utils.ToastUtils;
import com.onlineexamination.view.DialogView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


/**
 * Created by roy on 2016/12/16.
 * 社区
 */

public class CommunicationFragment extends Fragment {

    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private View footview;
    private List<TopicBean> list;
    private CommunicationAdapter adapter;
    public DialogView dialogView;
    int page = 0;
    int positon;//点击的位置
    int how;//那个界面，界面传过来的；
    String urls = "";//多个界面，根据how动态改变url
    String userid = "";//需要传送的userid

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout_commincation, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        listView = (ListView) view.findViewById(R.id.listview_communitcation);
        footview = LayoutInflater.from(getActivity()).inflate(R.layout.footview, null);
        list = new ArrayList<>();
        adapter = new CommunicationAdapter(getContext(), 0, list);
        listView.setAdapter(adapter);
        listView.addFooterView(footview);
        listener();
        getHow();
    }

    //根据过来的界面加以处理
    private void getHow() {
        how = getArguments().getInt("how");
        switch (how) {
            //我的发帖
            case 0:
                urls = Config.MY_WRITE;

                userid = SharedPreferencesDB.getInstance().getString("userid", "");
                break;
            //与我相关
            case 1:
                urls = Config.MY_COMMINT;
                userid = SharedPreferencesDB.getInstance().getString("userid", "");
                break;
            //话题列表
            case 2:
                urls = Config.TOPIC_LIST;
                userid = "";
                break;
        }
        getList();
    }

    private void listener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positon = position;
                Intent intent = new Intent(getActivity(), CommunicationDatialsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", list.get(position));
                intent.putExtra("intent", bundle);
                startActivityForResult(intent, 1000);
            }
        });
        //刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                getList();

            }
        });
        //加载更多
        footview.findViewById(R.id.cardview_foot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getList();
            }
        });
    }

    /**
     * 进行数据下载,用于我的发帖和与我相关,else是帖子列表
     */
    private void getList() {
        dialogView = new DialogView(getActivity());
        dialogView.show();
        dialogView.setMessage("贴子加载中...");
        if (how == 0 || how == 1) {
            OkHttpUtils
                    .post()
                    .url(urls)
                    .addParams("page", page + "")
                    .addParams("studentId", userid)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtils.show(getActivity(), e.toString());
                            if (dialogView != null)
                                dialogView.close();
                            refreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("code") == 10000) {
                                    List<TopicBean> topicBeanList = JsonToBean.getBeans(jsonObject.opt("response").toString(), TopicBean.class);
                                    listView.removeFooterView(footview);
                                    if (page == 0)
                                        list.clear();
                                    if (topicBeanList.size() >= 10) {
                                        page++;
                                        listView.addFooterView(footview);
                                    } else {
                                        listView.removeFooterView(footview);
                                    }
                                    list.addAll(topicBeanList);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            refreshLayout.setRefreshing(false);
                            if (dialogView != null)
                                dialogView.close();

                        }

                    });
        } else {
            OkHttpUtils
                    .post()
                    .url(urls)
                    .addParams("page", page + "")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtils.show(getActivity(), e.toString());
                            if (dialogView != null)
                                dialogView.close();
                            refreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.optInt("code") == 10000) {
                                    List<TopicBean> topicBeanList = JsonToBean.getBeans(jsonObject.opt("response").toString(), TopicBean.class);
                                    listView.removeFooterView(footview);
                                    if (page == 0)
                                        list.clear();
                                    if (topicBeanList.size() >= 10) {
                                        page++;
                                        listView.addFooterView(footview);
                                    } else {
                                        listView.removeFooterView(footview);
                                    }
                                    list.addAll(topicBeanList);
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            refreshLayout.setRefreshing(false);
                            if (dialogView != null)
                                dialogView.close();

                        }

                    });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                list.get(positon).setCommentNum(data.getStringExtra("number"));
                adapter.notifyDataSetChanged();
            }
        }

    }


}
