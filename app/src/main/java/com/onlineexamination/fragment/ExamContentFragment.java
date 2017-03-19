package com.onlineexamination.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.onlineexamination.R;
import com.onlineexamination.bean.ExamQuestionBean;
import com.onlineexamination.utils.ListSave;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 庞品 on 2017/2/14.
 */

public class ExamContentFragment extends Fragment {
    private RadioGroup radioGroup;
    private RadioButton A, B, C, D;
    private TextView question;
    private List<RadioButton> radioButtonList;
    private int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.from(getActivity()).inflate(R.layout.fragment_exam_content, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        position = getArguments().getInt("position");
        radioGroup = (RadioGroup) view.findViewById(R.id.anser_father);
        A = (RadioButton) view.findViewById(R.id.anser_son1);
        B = (RadioButton) view.findViewById(R.id.anser_son2);
        C = (RadioButton) view.findViewById(R.id.anser_son3);
        D = (RadioButton) view.findViewById(R.id.anser_son4);
        question = (TextView) view.findViewById(R.id.text_question);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                colorChange();
                switch (checkedId) {
                    case R.id.anser_son1:
                        A.setTextColor(getResources().getColor(R.color.green_complete));
                        ListSave.getListSave().getList().get(position).setSelectAnswer("A");
                        break;
                    case R.id.anser_son2:
                        B.setTextColor(getResources().getColor(R.color.green_complete));
                        ListSave.getListSave().getList().get(position).setSelectAnswer("B");
                        break;
                    case R.id.anser_son3:
                        C.setTextColor(getResources().getColor(R.color.green_complete));
                        ListSave.getListSave().getList().get(position).setSelectAnswer("C");
                        break;
                    case R.id.anser_son4:
                        D.setTextColor(getResources().getColor(R.color.green_complete));
                        ListSave.getListSave().getList().get(position).setSelectAnswer("D");
                        break;
                }
            }
        });
        initData();
    }

    /**
     * 初始化选项和根据选项动态设置选项的个数
     */
    private void initData() {
        radioButtonList = new ArrayList<>();
        radioButtonList.add(A);
        radioButtonList.add(B);
        radioButtonList.add(C);
        radioButtonList.add(D);
        //取出当前页面应当显示的题目
        ExamQuestionBean questionbean = ListSave.getListSave().getList().get(position);
        question.setText(questionbean.getTitle());
        //循环显示题目
        for (int i = 0; i < questionbean.getOptionList().size(); i++) {
            radioButtonList.get(i).setText(questionbean.getOptionList().get(i).getTab() + "." + questionbean.getOptionList().get(i).getContent());
        }
        //控制选项的个数
        if (radioButtonList.size() > questionbean.getOptionList().size()) {
            for (int i = questionbean.getOptionList().size(); i < questionbean.getOptionList().size(); i++) {
                radioButtonList.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void colorChange() {
        A.setTextColor(Color.parseColor("#444444"));
        B.setTextColor(Color.parseColor("#444444"));
        C.setTextColor(Color.parseColor("#444444"));
        D.setTextColor(Color.parseColor("#444444"));
    }
}
