package com.onlineexamination.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.onlineexamination.R;
import com.onlineexamination.activity.AboutActivity;
import com.onlineexamination.activity.LoginActivity;
import com.onlineexamination.activity.MainActivity;
import com.onlineexamination.activity.MyWriteAndMyCommitActivity;
import com.onlineexamination.adapter.PersonAdapter;
import com.onlineexamination.bean.PersonItemBean;
import com.onlineexamination.utils.SharedPreferencesDB;
import com.onlineexamination.utils.TimeUtil;
import com.onlineexamination.utils.ToastUtils;
import com.onlineexamination.view.ExpandListview;
import com.onlineexamination.view.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 庞品 on 2017/2/9.
 */

public class PersonFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ExpandListview expandListview;
    private TextView username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.from(getActivity()).inflate(R.layout.fragment_person, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandListview = (ExpandListview) view.findViewById(R.id.person_listview);
        Picasso.with(getActivity()).load(SharedPreferencesDB.getInstance().getString("userimgae", "null")).into((RoundImageView) view.findViewById(R.id.user_head_img));
        ((TextView) view.findViewById(R.id.user_name)).setText(TimeUtil.getDate() + "："+SharedPreferencesDB.getInstance().getString("username","未登录"));
        List<PersonItemBean> personItemBeen = new ArrayList<>();
        personItemBeen.add(new PersonItemBean("我的发帖", R.mipmap.my_write));
        personItemBeen.add(new PersonItemBean("与我相关", R.mipmap.with_me));
        personItemBeen.add(new PersonItemBean("成绩记录", R.mipmap.exam_result));
        personItemBeen.add(new PersonItemBean("关于我们", R.mipmap.about_me));
        personItemBeen.add(new PersonItemBean("退出登录", R.mipmap.exit));
        expandListview.setAdapter(new PersonAdapter(getActivity(), 0, personItemBeen));
        expandListview.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent myWrite = new Intent(getActivity(), MyWriteAndMyCommitActivity.class);
                myWrite.putExtra("my", "0");
                startActivity(myWrite);
                break;
            case 1:
                Intent myCommit = new Intent(getActivity(), MyWriteAndMyCommitActivity.class);
                myCommit.putExtra("my", "1");
                startActivity(myCommit);
                break;
            case 2:
                break;
            case 3:
                Intent about = new Intent(getActivity(), AboutActivity.class);
                startActivity(about);
                break;
            case 4:
                ToastUtils.toast("退出账号");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                SharedPreferencesDB.getInstance().setBoolean("isLogin", false);
                getActivity().finish();
                break;
            case 5:
                break;
        }
    }
}
