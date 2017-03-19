package com.onlineexamination.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.onlineexamination.R;
import com.onlineexamination.activity.ExamContentActivity;
import com.onlineexamination.activity.ExamListActivity;
import com.onlineexamination.adapter.ExamTypeAdapter;
import com.onlineexamination.bean.ExamTypeBean;
import com.onlineexamination.bean.TopicBean;
import com.onlineexamination.config.Config;
import com.onlineexamination.utils.JsonToBean;
import com.onlineexamination.utils.ToastUtils;
import com.onlineexamination.view.DialogView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by 庞品 on 2017/2/9.
 */

public class ExamFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private ExamTypeAdapter adapter;
    private List<ExamTypeBean> list;
    DialogView dialogView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.from(getActivity()).inflate(R.layout.fragment_exam, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listview_exam);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        listView.setOnItemClickListener(this);
        list = new ArrayList<>();
        adapter = new ExamTypeAdapter(getContext(), 0, list);
        listView.setAdapter(adapter);
        getExamType();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getExamType();
            }
        });
    }

    private void getExamType() {
        dialogView = new DialogView(getActivity());
        dialogView.show();
        dialogView.setMessage("试卷类型加载中...");
        OkHttpUtils
                .post()
                .url(Config.EXAM_TYPE)
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
                                List<ExamTypeBean> examTypeBeanList = JsonToBean.getBeans(jsonObject.opt("response").toString(), ExamTypeBean.class);
                                if (examTypeBeanList.size() > 0) {
                                    list.clear();
                                    list.addAll(examTypeBeanList);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    ToastUtils.toast("当前暂无试卷分类，请重新加载...");
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
        Intent intent = new Intent(getActivity(), ExamListActivity.class);
        intent.putExtra("id", list.get(position).getId() + "");
        intent.putExtra("name", list.get(position).getName());
        startActivity(intent);
    }
}
