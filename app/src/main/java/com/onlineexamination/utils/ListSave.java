package com.onlineexamination.utils;

import com.onlineexamination.bean.ExamQuestionBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 庞品 on 2017/3/10.
 */

public class ListSave {
    private static ListSave sing = new ListSave();
    private List<ExamQuestionBean> list = new ArrayList<>();

    private ListSave() {
    }

    public static ListSave getListSave() {
        return sing;
    }

    public void setList(List<ExamQuestionBean> set) {
        list.addAll(set);
    }

    public List<ExamQuestionBean> getList() {
        return list;
    }
}
