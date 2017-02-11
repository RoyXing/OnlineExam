package com.onlineexamination.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onlineexamination.R;
import com.onlineexamination.adapter.PersonAdapter;
import com.onlineexamination.bean.PersonItemBean;
import com.onlineexamination.utils.TimeUtil;
import com.onlineexamination.view.ExpandListview;
import com.onlineexamination.view.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 庞品 on 2017/2/9.
 */

public class PersonFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.from(getActivity()).inflate(R.layout.fragment_person, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.with(getActivity()).load("http://img5.imgtn.bdimg.com/it/u=1252835403,3371975364&fm=23&gp=0.jpg").into((RoundImageView) view.findViewById(R.id.user_head_img));
        ((TextView)view.findViewById(R.id.user_name)).setText(TimeUtil.getDate()+"：庞品");
        List<PersonItemBean>personItemBeen=new ArrayList<>();
        personItemBeen.add(new PersonItemBean());
        personItemBeen.add(new PersonItemBean());
        personItemBeen.add(new PersonItemBean());
        personItemBeen.add(new PersonItemBean());
        personItemBeen.add(new PersonItemBean());
        personItemBeen.add(new PersonItemBean());
        personItemBeen.add(new PersonItemBean());
        personItemBeen.add(new PersonItemBean());
        personItemBeen.add(new PersonItemBean());
        personItemBeen.add(new PersonItemBean());
        personItemBeen.add(new PersonItemBean());
        personItemBeen.add(new PersonItemBean());
        ((ExpandListview)view.findViewById(R.id.person_listview)).setAdapter(new PersonAdapter(getActivity(),0,personItemBeen));
    }


}
